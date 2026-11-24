package gov.nih.nci.evs.reportwriter.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.test.util.ReflectionTestUtils;

import tools.jackson.dataformat.yaml.YAMLMapper;

import gov.nih.nci.evs.reportwriter.core.model.template.Template;
import gov.nih.nci.evs.reportwriter.core.properties.CoreProperties;
import gov.nih.nci.evs.reportwriter.core.service.ReportWriter;
import gov.nih.nci.evs.reportwriter.formatter.AsciiToExcelFormatter;
import gov.nih.nci.evs.reportwriter.formatter.AsciiToTextFormatter;
import gov.nih.nci.evs.reportwriter.web.model.ReportTemplate;
import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateColumn;
import gov.nih.nci.evs.reportwriter.web.model.ReportTask;
import gov.nih.nci.evs.reportwriter.web.repository.ReportTaskRepository;
import gov.nih.nci.evs.reportwriter.web.service.ReportTaskServiceImpl;
import gov.nih.nci.evs.reportwriter.web.service.ReportTemplateService;

class ReportFormatsTest {
  @TempDir Path directory;

  /** Checks that Jackson 3 can still read the existing YAML report templates. */
  @Test
  void readsExistingExampleTemplates() throws Exception {
    var mapper = YAMLMapper.builder().configureForJackson2().build();
    try (var paths = Files.list(Path.of("../examples/templates"))) {
      var templates = paths.filter(p -> p.toString().endsWith(".template")).toList();
      assertFalse(templates.isEmpty());
      for (Path path : templates) {
        Template template = mapper.readValue(path.toFile(), Template.class);
        assertNotNull(template.getName(), path.toString());
        assertNotNull(template.getColumns(), path.toString());
        assertFalse(template.getColumns().isEmpty(), path.toString());
      }
    }
  }

  /** Checks the service's exported template file preserves report data and omits persistence fields. */
  @Test
  void exportedYamlRoundTripsIntoReportEngineModel() throws Exception {
    ReportTemplate template = new ReportTemplate();
    template.setId(123);
    template.setName("Café report");
    template.setType("Association");
    template.setRootConceptCode("C123");
    template.setLevel(1);
    template.setSortColumn(1);
    template.setStatus("Active");
    ReportTemplateColumn column = new ReportTemplateColumn();
    column.setId(456);
    column.setColumnNumber(1);
    column.setLabel("Code");
    column.setDisplay("code");
    column.setReportTemplate(template);
    template.setColumns(List.of(column));
    ReportTask task = new ReportTask();
    task.setId(789);
    task.setReportTemplate(template);
    task.setDatabaseUrl("http://unused.example/query");
    CoreProperties properties = new CoreProperties();
    properties.setOutputDirectory(directory.toString());
    ReportTemplateService templates = mock(ReportTemplateService.class);
    ReportWriter writer = mock(ReportWriter.class);
    ReportTaskServiceImpl service = new ReportTaskServiceImpl();
    ReflectionTestUtils.setField(service, "coreProperties", properties);
    ReflectionTestUtils.setField(service, "reportTemplateService", templates);
    ReflectionTestUtils.setField(service, "reportTaskRepository", mock(ReportTaskRepository.class));
    ReflectionTestUtils.setField(service, "reportWriter", writer);
    when(templates.findOne(123)).thenReturn(template);
    String reportName = directory + "/9/Task-789/Task-789";
    String templateFile = reportName + ".template";
    when(writer.runReport(templateFile, reportName, "", task.getDatabaseUrl())).thenReturn("success");

    service.runReport(task);

    verify(writer).runReport(templateFile, reportName, "", task.getDatabaseUrl());
    assertEquals("Completed", task.getStatus());
    String yaml = Files.readString(Path.of(templateFile));
    assertFalse(yaml.contains("id:"));
    assertFalse(yaml.contains("status:"));
    assertFalse(yaml.contains("reportTemplate:"));
    Template parsed = YAMLMapper.builder().configureForJackson2().build()
        .readValue(yaml, Template.class);
    assertEquals("Café report", parsed.getName());
    assertEquals("C123", parsed.getRootConceptCode());
    assertEquals("Code", parsed.getColumns().getFirst().getLabel());
  }

  /** Guards against default-charset regressions by checking UTF-8 text survives report conversion. */
  @Test
  void convertsTextReportWithoutChangingUtf8Characters() throws Exception {
    Path text = directory.resolve("report.txt");
    Path formatted = directory.resolve("report.edt");
    Files.writeString(text, "Code\tPreferred Term\nC123\tCafé – β\n");
    assertTrue(new AsciiToTextFormatter().convert(text.toString(), "\t", formatted.toString()));
    assertTrue(Files.readString(formatted).contains("Café – β"));
  }

  /** Checks that Excel conversion preserves report headers, data rows, and accented characters. */
  @Test
  void convertsTextReportToReadableExcel() throws Exception {
    Path text = directory.resolve("report.txt");
    Path excel = directory.resolve("report.xls");
    Files.writeString(text, "Code\tPreferred Term\nC123\tCafé\nC456\tSecond term\n");
    assertTrue(new AsciiToExcelFormatter().convert(text.toString(), "\t", excel.toString()));
    try (var input = Files.newInputStream(excel); var workbook = new HSSFWorkbook(input)) {
      var sheet = workbook.getSheetAt(0);
      assertEquals("Code", sheet.getRow(0).getCell(0).getStringCellValue());
      assertEquals("C123", sheet.getRow(1).getCell(0).getStringCellValue());
      assertEquals("Café", sheet.getRow(1).getCell(1).getStringCellValue());
      assertEquals(2, sheet.getLastRowNum());
    }
  }
}
