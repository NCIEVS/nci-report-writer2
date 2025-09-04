package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.LkReportStatus;
import gov.nih.nci.evs.reportwriter.web.repository.LkReportStatusRepository;

/** The Class LkReportStatusServiceImpl. */
@Service
public class LkReportStatusServiceImpl implements LkReportStatusService {

  /** The lk report status repository. */
  @Autowired LkReportStatusRepository lkReportStatusRepository;

  /* see superclass */
  @Override
  public List<LkReportStatus> findAll() {

    return (List<LkReportStatus>) lkReportStatusRepository.findAll();
  }
}
