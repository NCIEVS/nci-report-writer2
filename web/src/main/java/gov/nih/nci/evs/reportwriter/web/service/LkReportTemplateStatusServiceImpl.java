package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.LkReportTemplateStatus;
import gov.nih.nci.evs.reportwriter.web.repository.LkReportTemplateStatusRepository;

/** The Class LkReportTemplateStatusServiceImpl. */
@Service
public class LkReportTemplateStatusServiceImpl implements LkReportTemplateStatusService {

  /** The lk report template status repository. */
  @Autowired LkReportTemplateStatusRepository lkReportTemplateStatusRepository;

  /* see superclass */
  @Override
  public List<LkReportTemplateStatus> findAll() {

    return (List<LkReportTemplateStatus>) lkReportTemplateStatusRepository.findAll();
  }
}
