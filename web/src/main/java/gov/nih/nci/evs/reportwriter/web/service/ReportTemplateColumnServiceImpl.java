package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateColumn;
import gov.nih.nci.evs.reportwriter.web.repository.ReportTemplateColumnRepository;

/** The Class ReportTemplateColumnServiceImpl. */
@Service
public class ReportTemplateColumnServiceImpl implements ReportTemplateColumnService {

  /** The report template column repository. */
  @Autowired ReportTemplateColumnRepository reportTemplateColumnRepository;

  /* see superclass */
  @Override
  public List<ReportTemplateColumn> findAll() {

    return (List<ReportTemplateColumn>) reportTemplateColumnRepository.findAll();
  }

  /* see superclass */
  @Override
  public List<ReportTemplateColumn> getReportColumnsByReportTemplateID(Integer reportTemplateId) {

    return reportTemplateColumnRepository.getReportColumnsByReportTemplateId(reportTemplateId);
  }
}
