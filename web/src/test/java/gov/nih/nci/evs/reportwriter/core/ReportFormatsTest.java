package gov.nih.nci.evs.reportwriter.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import tools.jackson.databind.ser.std.SimpleBeanPropertyFilter;
import tools.jackson.databind.ser.std.SimpleFilterProvider;
import tools.jackson.dataformat.yaml.YAMLMapper;

import gov.nih.nci.evs.reportwriter.core.model.template.Template;
import gov.nih.nci.evs.reportwriter.formatter.AsciiToExcelFormatter;
import gov.nih.nci.evs.reportwriter.formatter.AsciiToTextFormatter;
import gov.nih.nci.evs.reportwriter.web.model.ReportTemplate;
import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateColumn;

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

  /** Verifies that filtered template YAML omits persistence fields and preserves report data. */
  @Test
  void filteredYamlRoundTripsIntoReportEngineModel() {
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
    var mapper = YAMLMapper.builder().configureForJackson2().build();
    var filters = new SimpleFilterProvider().addFilter("yamlFilter",
        SimpleBeanPropertyFilter.serializeAllExcept(Set.of("id", "status")));
    String yaml = mapper.writer(filters).writeValueAsString(template);
    assertFalse(yaml.contains("id:"));
    assertFalse(yaml.contains("status:"));
    assertFalse(yaml.contains("reportTemplate:"));
    Template parsed = mapper.readValue(yaml, Template.class);
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
