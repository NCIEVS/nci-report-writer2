package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.LkDisplay;
import gov.nih.nci.evs.reportwriter.web.repository.LkDisplayRepository;

/** The Class LkDisplayServiceImpl. */
@Service
public class LkDisplayServiceImpl implements LkDisplayService {

  /** The lk display repository. */
  @Autowired LkDisplayRepository lkDisplayRepository;

  /* see superclass */
  @Override
  public List<LkDisplay> findAll() {

    return (List<LkDisplay>) lkDisplayRepository.findAll();
  }
}
