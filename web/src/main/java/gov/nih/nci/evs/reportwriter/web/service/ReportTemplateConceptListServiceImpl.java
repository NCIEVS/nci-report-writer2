package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateConceptList;
import gov.nih.nci.evs.reportwriter.web.repository.ReportTemplateConceptListRepository;

/** The Class ReportTemplateConceptListServiceImpl. */
@Service
public class ReportTemplateConceptListServiceImpl implements ReportTemplateConceptListService {

  /** The report template concept list repository. */
  @Autowired ReportTemplateConceptListRepository reportTemplateConceptListRepository;

  /* see superclass */
  @Override
  public List<ReportTemplateConceptList> findAll() {

    return (List<ReportTemplateConceptList>) reportTemplateConceptListRepository.findAll();
  }

  /* see superclass */
  @Override
  public List<ReportTemplateConceptList> getReportTemplateConceptListsByReportTemplateID(
      Integer reportTemplateId) {

    return reportTemplateConceptListRepository.getReportTemplateConceptListsByReportTemplateId(
        reportTemplateId);
  }
}
