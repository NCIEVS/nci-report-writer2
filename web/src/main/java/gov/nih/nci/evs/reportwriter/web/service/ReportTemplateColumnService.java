package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateColumn;

/** The Interface ReportTemplateColumnService. */
public interface ReportTemplateColumnService {

  /**
   * Find all.
   *
   * @return the list
   */
  public List<ReportTemplateColumn> findAll();

  /**
   * Gets the report columns by report template ID.
   *
   * @param reportTemplateId the report template id
   * @return the report columns by report template ID
   */
  public List<ReportTemplateColumn> getReportColumnsByReportTemplateID(Integer reportTemplateId);
}
