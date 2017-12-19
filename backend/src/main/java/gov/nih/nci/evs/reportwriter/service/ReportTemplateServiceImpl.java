package gov.nih.nci.evs.reportwriter.service;

import java.util.ArrayList;
import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.nih.nci.evs.reportwriter.controller.ReportTemplateController;
import gov.nih.nci.evs.reportwriter.model.ReportTemplate;
import gov.nih.nci.evs.reportwriter.model.ReportTemplateColumn;
import gov.nih.nci.evs.reportwriter.repository.ReportTemplateColumnRepository;
import gov.nih.nci.evs.reportwriter.repository.ReportTemplateRepository;
import gov.nih.nci.evs.reportwriter.support.ReportTemplateUI;

@Service
public class ReportTemplateServiceImpl implements ReportTemplateService {
	
	private static final Logger log = LoggerFactory.getLogger(ReportTemplateServiceImpl.class);

	
	@Autowired
	ReportTemplateRepository reportTemplateRepository;
	

	@Autowired
	ReportTemplateColumnRepository reportTemplateColumnRepository;
	
	public List <ReportTemplate> findAll() {
		
		return (List) reportTemplateRepository.findAll();
	}
	
	
	@Transactional
	public ReportTemplateUI save(ReportTemplate reportTemplate) {
		
		List<ReportTemplateColumn> reportTemplateColumns = reportTemplate.getReportTemplateColumns();		
		reportTemplate.setReportTemplateColumns(null);
		
		//save the tempalte
		ReportTemplate reportTemplateRet =reportTemplateRepository.save(reportTemplate);
		
		log.info("id" + reportTemplateRet.getId());
		
		
		
		reportTemplateRet = reportTemplateRepository.findOne(reportTemplateRet.getId());
		
		for (ReportTemplateColumn reportTemplateColumn : reportTemplateColumns) {
			
			
			reportTemplateColumn.setReportTemplate(reportTemplateRet);
			ReportTemplateColumn reportTemplateColumnRet = reportTemplateColumnRepository.save(reportTemplateColumn);
			
			log.info("reportTemplateColumnRet " + reportTemplateColumnRet.getId() + " - " + reportTemplateColumnRet.getLabel());
		
			
		}
		
		ReportTemplateUI reportTemplateUI = new ReportTemplateUI();
		
		
		reportTemplateUI.setId(reportTemplateRet.getId());
		reportTemplateUI.setLevel(reportTemplateRet.getLevel());
		reportTemplateUI.setName(reportTemplateRet.getName());
		reportTemplateUI.setRootConceptCode(reportTemplateRet.getRootConceptCode());
		reportTemplateUI.setType(reportTemplateRet.getType());
		reportTemplateUI.setAssociation(reportTemplateRet.getAssociation());
		reportTemplateUI.setStatus(reportTemplateRet.getStatus());
		reportTemplateUI.setSortColumn(reportTemplateRet.getSortColumn());
		
		return reportTemplateUI;
		
	}
	
	
	public ReportTemplate findOne(Integer id) {
		return reportTemplateRepository.findOne(id);
		
	}

}
