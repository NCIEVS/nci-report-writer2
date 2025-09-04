package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.LkAssociation;

/** The Interface LkAssociationService. */
public interface LkAssociationService {

  /**
   * Find all.
   *
   * @return the list
   */
  public List<LkAssociation> findAll();
}
