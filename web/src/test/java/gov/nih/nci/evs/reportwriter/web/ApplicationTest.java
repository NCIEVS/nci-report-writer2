package gov.nih.nci.evs.reportwriter.web;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.json.JsonMapper;

import gov.nih.nci.evs.reportwriter.web.repository.ReportTemplateRepository;

/** Exercises the real MVC, Jackson, and JPA wiring without external services. */
@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:reportwriter;MODE=MySQL;DB_CLOSE_DELAY=-1;NON_KEYWORDS=VALUE",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
class ApplicationTest {
  @Autowired private MockMvc mvc;
  @Autowired private ReportTemplateRepository templates;
  @Autowired private JsonMapper mapper;

  @AfterEach
  void cleanDatabase() {
    templates.deleteAll();
  }

  /** Exercises template API serialization and H2 persistence, including columns and field filtering. */
  @Test
  void createsReadsUpdatesAndDeletesTemplateWithColumns() throws Exception {
    String request = """
        {"name":"Migration – café","type":"Association","status":"Active",
         "rootConceptCode":"C123","level":1,"sortColumn":1,
         "columns":[{"columnNumber":1,"label":"Code","display":"code",
                     "propertyType":"code","property":"NHC0"}]}
        """;
    String created = mvc.perform(post("/reportwriter/createTemplate")
            .contentType(MediaType.APPLICATION_JSON).content(request))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Migration – café"))
        .andExpect(jsonPath("$.columns[0].label").value("Code"))
        .andReturn().getResponse().getContentAsString();
    int id = mapper.readTree(created).get("id").asInt();
    assertTrue(templates.existsById(id));
    mvc.perform(get("/reportwriter/reporttemplate/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.rootConceptCode").value("C123"))
        .andExpect(jsonPath("$.columns[0].label").value("Code"))
        .andExpect(jsonPath("$.columns[0].reportTemplate").doesNotExist())
        .andExpect(jsonPath("$.dateCreated").doesNotExist());
    mvc.perform(post("/reportwriter/saveTemplate").contentType(MediaType.APPLICATION_JSON)
            .content(created.replace("Migration – café", "Updated template")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Updated template"));
    mvc.perform(get("/reportwriter/reporttemplates"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("Updated template"))
        .andExpect(jsonPath("$[0].dateCreated").isString());
    templates.deleteById(id);
    assertFalse(templates.existsById(id));
  }

  /** Checks that static frontend content, client-side routes, and the API welcome endpoint still work. */
  @Test
  void servesFrontendAndRoutesDeepLinks() throws Exception {
    mvc.perform(get("/index.html"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    mvc.perform(get("/home"))
        .andExpect(status().isOk()).andExpect(forwardedUrl("/index.html"));
    mvc.perform(get("/templates/edit/42"))
        .andExpect(status().isOk()).andExpect(forwardedUrl("/index.html"));
    mvc.perform(get("/reportwriter/"))
        .andExpect(status().isOk());
  }
}
