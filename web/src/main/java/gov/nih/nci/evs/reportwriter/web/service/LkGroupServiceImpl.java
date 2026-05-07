package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.LkGroup;
import gov.nih.nci.evs.reportwriter.web.repository.LkGroupRepository;

/** The Class LkGroupServiceImpl. */
@Service
public class LkGroupServiceImpl implements LkGroupService {

  /** The lk group repository. */
  @Autowired LkGroupRepository lkGroupRepository;

  /* see superclass */
  @Override
  public List<LkGroup> findAll() {

    return (List<LkGroup>) lkGroupRepository.findAll();
  }
}
