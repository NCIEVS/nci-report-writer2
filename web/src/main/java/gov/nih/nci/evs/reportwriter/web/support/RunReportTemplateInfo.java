package gov.nih.nci.evs.reportwriter.web.support;

import java.util.ArrayList;

/** The Class RunReportTemplateInfo. */
public class RunReportTemplateInfo {

  /** The datbase type. */
  private String datbaseType;

  /** The report templates. */
  private ArrayList<ReportTemplateUI> reportTemplates;

  /**
   * Gets the report templates.
   *
   * @return the report templates
   */
  public ArrayList<ReportTemplateUI> getReportTemplates() {
    return reportTemplates;
  }

  /**
   * Sets the report templates.
   *
   * @param reportTemplates the new report templates
   */
  public void setReportTemplates(ArrayList<ReportTemplateUI> reportTemplates) {
    this.reportTemplates = reportTemplates;
  }

  /**
   * Gets the datbase type.
   *
   * @return the datbase type
   */
  public String getDatbaseType() {
    return datbaseType;
  }

  /**
   * Sets the datbase type.
   *
   * @param datbaseType the new datbase type
   */
  public void setDatbaseType(String datbaseType) {
    this.datbaseType = datbaseType;
  }
}
