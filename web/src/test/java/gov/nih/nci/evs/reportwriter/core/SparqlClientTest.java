package gov.nih.nci.evs.reportwriter.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.ResourceAccessException;

import com.sun.net.httpserver.HttpServer;

import gov.nih.nci.evs.reportwriter.core.service.QueryBuilderServiceImpl;
import gov.nih.nci.evs.reportwriter.core.service.SparqlQueryManagerServiceImpl;
import gov.nih.nci.evs.reportwriter.core.util.RESTUtils;
import gov.nih.nci.evs.reportwriter.core.util.SPARQLQueryRunner;

class SparqlClientTest {
  /** Checks SPARQL authentication, request encoding, and tolerant JSON parsing using a local server. */
  @Test
  void postsAuthenticatedQueryAndParsesBindingsWithExtraFields() throws Exception {
    var body = new AtomicReference<String>();
    var authorization = new AtomicReference<String>();
    var accept = new AtomicReference<String>();
    var method = new AtomicReference<String>();
    HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
    String response = """
        {"head":{"vars":["namedGraph"]},"extra":"ignored",
         "results":{"bindings":[{"namedGraph":{"type":"uri","value":"http://NCI_T"},
                                  "extraBinding":{"type":"literal","value":"ignored"}}]}}
        """;
    server.createContext("/query", exchange -> {
      method.set(exchange.getRequestMethod());
      body.set(URLDecoder.decode(new String(exchange.getRequestBody().readAllBytes(),
          StandardCharsets.UTF_8), StandardCharsets.UTF_8));
      authorization.set(exchange.getRequestHeaders().getFirst("Authorization"));
      accept.set(exchange.getRequestHeaders().getFirst("Accept"));
      byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
      exchange.getResponseHeaders().set("Content-Type", "application/sparql-results+json");
      exchange.sendResponseHeaders(200, bytes.length);
      try (var output = exchange.getResponseBody()) {
        output.write(bytes);
      }
    });
    server.start();
    try {
      String url = "http://127.0.0.1:" + server.getAddress().getPort() + "/query";
      var service = new SparqlQueryManagerServiceImpl();
      service.setQueryBuilderService(new QueryBuilderServiceImpl());
      service.setRESTUtils(new RESTUtils("user", "pass", 2, 2));
      assertEquals(List.of("http://NCI_T"), service.getNamedGraphs(url));
      assertEquals("POST", method.get());
      assertTrue(body.get().startsWith("query=PREFIX"));
      assertEquals("Basic dXNlcjpwYXNz", authorization.get());
      assertEquals("application/sparql-results+json", accept.get());
      assertEquals(response, new SPARQLQueryRunner("user", "pass", 2, 2)
          .runSPARQL("SELECT * WHERE { ?s ?p \"café\" }", url));
      assertEquals("query=SELECT * WHERE { ?s ?p \"café\" }", body.get());
    } finally {
      server.stop(0);
    }
  }

  /** Verifies that a slow SPARQL response triggers the configured read timeout. */
  @Test
  void readTimeoutRemainsBounded() throws Exception {
    HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
    server.createContext("/slow", exchange -> {
      try {
        Thread.sleep(2500);
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
      } finally {
        exchange.close();
      }
    });
    server.start();
    try {
      String url = "http://127.0.0.1:" + server.getAddress().getPort() + "/slow";
      assertTimeoutPreemptively(Duration.ofSeconds(4), () ->
          assertThrows(ResourceAccessException.class,
              () -> new RESTUtils("user", "pass", 1, 1).runSPARQL("ASK {}", url)));
    } finally {
      server.stop(0);
    }
  }
}
