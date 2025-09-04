package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.LkSubsource;

/** The Interface LkSubsourceService. */
public interface LkSubsourceService {

  /**
   * Find all.
   *
   * @return the list
   */
  public List<LkSubsource> findAll();
}
