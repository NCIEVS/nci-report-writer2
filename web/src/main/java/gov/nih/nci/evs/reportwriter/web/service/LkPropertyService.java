package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.LkProperty;

/** The Interface LkPropertyService. */
public interface LkPropertyService {

  /**
   * Find all.
   *
   * @return the list
   */
  public List<LkProperty> findAll();
}
