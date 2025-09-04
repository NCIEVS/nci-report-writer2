package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.LkReportTemplateStatus;

/** The Interface LkReportTemplateStatusService. */
public interface LkReportTemplateStatusService {

  /**
   * Find all.
   *
   * @return the list
   */
  public List<LkReportTemplateStatus> findAll();
}
