package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.LkSource;

/** The Interface LkSourceService. */
public interface LkSourceService {

  /**
   * Find all.
   *
   * @return the list
   */
  public List<LkSource> findAll();
}
