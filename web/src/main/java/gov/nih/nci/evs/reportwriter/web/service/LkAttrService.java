package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.LkAttr;

/** The Interface LkAttrService. */
public interface LkAttrService {

  /**
   * Find all.
   *
   * @return the list
   */
  public List<LkAttr> findAll();
}
