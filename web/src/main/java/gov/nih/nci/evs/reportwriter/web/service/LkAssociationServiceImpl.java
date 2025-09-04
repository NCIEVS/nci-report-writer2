package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.LkAssociation;
import gov.nih.nci.evs.reportwriter.web.repository.LkAssociationRepository;

/** The Class LkAssociationServiceImpl. */
@Service
public class LkAssociationServiceImpl implements LkAssociationService {

  /** The lk assocation repository. */
  @Autowired LkAssociationRepository lkAssocationRepository;

  /* see superclass */
  @Override
  public List<LkAssociation> findAll() {

    return (List<LkAssociation>) lkAssocationRepository.findAll();
  }
}
