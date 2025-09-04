package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateConceptList;

/** The Interface ReportTemplateConceptListService. */
public interface ReportTemplateConceptListService {

  /**
   * Find all.
   *
   * @return the list
   */
  public List<ReportTemplateConceptList> findAll();

  /**
   * Gets the report template concept lists by report template ID.
   *
   * @param reportTemplateId the report template id
   * @return the report template concept lists by report template ID
   */
  public List<ReportTemplateConceptList> getReportTemplateConceptListsByReportTemplateID(
      Integer reportTemplateId);
}
