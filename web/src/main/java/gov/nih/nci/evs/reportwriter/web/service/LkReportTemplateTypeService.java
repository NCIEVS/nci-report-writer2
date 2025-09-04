package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.LkReportTemplateType;

/** The Interface LkReportTemplateTypeService. */
public interface LkReportTemplateTypeService {

  /**
   * Find all.
   *
   * @return the list
   */
  public List<LkReportTemplateType> findAll();
}
