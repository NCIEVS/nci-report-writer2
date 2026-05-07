package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.LkDisplay;

/** The Interface LkDisplayService. */
public interface LkDisplayService {

  /**
   * Find all.
   *
   * @return the list
   */
  public List<LkDisplay> findAll();
}
