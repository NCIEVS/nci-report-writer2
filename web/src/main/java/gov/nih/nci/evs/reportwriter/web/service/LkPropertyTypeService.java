package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.LkPropertyType;

/** The Interface LkPropertyTypeService. */
public interface LkPropertyTypeService {

  /**
   * Find all.
   *
   * @return the list
   */
  public List<LkPropertyType> findAll();
}
