package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.LkReportStatus;

/** The Interface LkReportStatusService. */
public interface LkReportStatusService {

  /**
   * Find all.
   *
   * @return the list
   */
  public List<LkReportStatus> findAll();
}
