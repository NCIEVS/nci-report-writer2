package gov.nih.nci.evs.reportwriter.web;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import gov.nih.nci.evs.reportwriter.web.support.ReportTaskUI;
import gov.nih.nci.evs.reportwriter.web.support.ReportTemplateUI;
import gov.nih.nci.evs.reportwriter.web.support.RunReportTemplateInfo;

/** Generates and downloads a report using the configured live database and graph. */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
public class ReportTaskIT {

  private final int templateId = Integer.parseInt(System.getenv("RW_TEST_TEMPLATE_ID"));

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  /** Runs a template against live services and checks completion and generated report downloads. */
  @Test
  public void generatesAndDownloadsReport() throws Exception {
    ResponseEntity<ArrayList<ReportTemplateUI>> response =
        restTemplate.exchange(
            String.format(
                "http://localhost:%d/ncireportwriter2/reportwriter/reporttemplates", port),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<ArrayList<ReportTemplateUI>>() {});
    assertEquals(HttpStatus.OK, response.getStatusCode());
    RunReportTemplateInfo runReportTemplateInfo = new RunReportTemplateInfo();
    runReportTemplateInfo.setReportTemplates(
        response.getBody().stream()
            .filter(template -> template.getId() == templateId)
            .collect(Collectors.toCollection(ArrayList::new)));
    runReportTemplateInfo.setDatbaseType("monthly");
    assertEquals(
        1,
        runReportTemplateInfo.getReportTemplates().size(),
        "RW_TEST_TEMPLATE_ID must identify an existing template in the configured database");
    assertNotEquals(
        "ConceptList",
        runReportTemplateInfo.getReportTemplates().getFirst().getType(),
        "Use an Association or Inverse Association template; ConceptList requires a separate upload");
    HttpEntity<RunReportTemplateInfo> httpEntity =
        new HttpEntity<RunReportTemplateInfo>(runReportTemplateInfo);
    ResponseEntity<ArrayList<ReportTaskUI>> reportTasksResponse =
        restTemplate.exchange(
            String.format(
                "http://localhost:%d/ncireportwriter2/reportwriter/runReportTemplates", port),
            HttpMethod.POST,
            httpEntity,
            new ParameterizedTypeReference<ArrayList<ReportTaskUI>>() {});
    assertEquals(HttpStatus.OK, reportTasksResponse.getStatusCode());
    ArrayList<ReportTaskUI> tasks = reportTasksResponse.getBody();
    assertEquals(1, tasks.size());
    int taskId = tasks.getFirst().getId();
    await()
        .atMost(5, TimeUnit.MINUTES)
        .pollDelay(1, TimeUnit.SECONDS)
        .pollInterval(1, TimeUnit.SECONDS)
        .until(() -> completed(taskId));
    byte[] text = download("getTxtReport", taskId);
    assertTrue(
        new String(text, StandardCharsets.UTF_8).lines().filter(line -> !line.isBlank()).count() > 1,
        "Report must contain data beyond its header");
    try (HSSFWorkbook workbook =
        new HSSFWorkbook(new ByteArrayInputStream(download("getXLSReport", taskId)))) {
      assertTrue(workbook.getNumberOfSheets() > 0);
      assertTrue(workbook.getSheetAt(0).getPhysicalNumberOfRows() > 1);
    }
    download("getTemplateReport", taskId);
    download("getLogReport", taskId);
    System.out.println("Verified report downloads for task " + taskId);
  }

  private byte[] download(String endpoint, int taskId) {
    ResponseEntity<byte[]> response =
        restTemplate.getForEntity(
            "http://localhost:" + port + "/ncireportwriter2/reportwriter/" + endpoint + "/" + taskId,
            byte[].class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(
        response.getHeaders().getFirst("Content-Disposition"),
        "Expected a report attachment, not an HTML error redirect");
    assertNotNull(response.getBody());
    assertTrue(response.getBody().length > 0, endpoint + " returned an empty file");
    return response.getBody();
  }

  private boolean completed(int taskId) {
    ResponseEntity<ArrayList<ReportTaskUI>> response =
        restTemplate.exchange(
            String.format("http://localhost:%d/ncireportwriter2/reportwriter/reporttasks", port),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<ArrayList<ReportTaskUI>>() {});
    assertEquals(HttpStatus.OK, response.getStatusCode());
    ReportTaskUI task =
        response.getBody().stream()
            .filter(candidate -> candidate.getId() == taskId)
            .findFirst()
            .orElseThrow(() -> new AssertionError("Report task not found: " + taskId));
    assertNotEquals("Failed", task.getStatus(), "Report generation failed for task " + taskId);
    return "Completed".equals(task.getStatus()) && StringUtils.hasText(task.getDateCompleted());
  }
}
