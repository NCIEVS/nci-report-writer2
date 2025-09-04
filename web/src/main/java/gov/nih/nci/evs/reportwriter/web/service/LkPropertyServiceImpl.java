package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.LkProperty;
import gov.nih.nci.evs.reportwriter.web.repository.LkPropertyRepository;

/** The Class LkPropertyServiceImpl. */
@Service
public class LkPropertyServiceImpl implements LkPropertyService {

  /** The lk property repository. */
  @Autowired LkPropertyRepository lkPropertyRepository;

  /* see superclass */
  @Override
  public List<LkProperty> findAll() {

    return (List<LkProperty>) lkPropertyRepository.findAll();
  }
}
