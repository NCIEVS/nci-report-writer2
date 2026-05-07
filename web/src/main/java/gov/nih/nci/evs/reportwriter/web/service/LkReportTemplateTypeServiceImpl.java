package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.LkReportTemplateType;
import gov.nih.nci.evs.reportwriter.web.repository.LkReportTemplateTypeRepository;

/** The Class LkReportTemplateTypeServiceImpl. */
@Service
public class LkReportTemplateTypeServiceImpl implements LkReportTemplateTypeService {

  /** The lk report template type repository. */
  @Autowired LkReportTemplateTypeRepository lkReportTemplateTypeRepository;

  /* see superclass */
  @Override
  public List<LkReportTemplateType> findAll() {

    return (List<LkReportTemplateType>) lkReportTemplateTypeRepository.findAll();
  }
}
