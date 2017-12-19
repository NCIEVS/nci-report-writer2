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
	public ReportTemplateUI create(ReportTemplateUI reportTemplate) {
		
		List<ReportTemplateColumn> reportTemplateColumns = reportTemplate.getColumns();		
		reportTemplate.setColumns(null);
		
		
		ReportTemplate reportTemplatedb = new ReportTemplate();
		
		
		reportTemplatedb.setLevel(reportTemplate.getLevel());
		reportTemplatedb.setName(reportTemplate.getName());
		reportTemplatedb.setRootConceptCode(reportTemplate.getRootConceptCode());
		reportTemplatedb.setType(reportTemplate.getType());
		reportTemplatedb.setAssociation(reportTemplate.getAssociation());
		reportTemplatedb.setStatus(reportTemplate.getStatus());
		reportTemplatedb.setSortColumn(reportTemplate.getSortColumn());
		
		//save the tempalte
		ReportTemplate reportTemplateRet =reportTemplateRepository.save(reportTemplatedb);
		
		log.info("id" + reportTemplateRet.getId());
		reportTemplate.setId(reportTemplateRet.getId());
		
		
		reportTemplateRet = reportTemplateRepository.findOne(reportTemplateRet.getId());
		
		for (ReportTemplateColumn reportTemplateColumn : reportTemplateColumns) {
			
			
			reportTemplateColumn.setReportTemplate(reportTemplateRet);
			ReportTemplateColumn reportTemplateColumnRet = reportTemplateColumnRepository.save(reportTemplateColumn);
			
			log.info("reportTemplateColumnRet " + reportTemplateColumnRet.getId() + " - " + reportTemplateColumnRet.getLabel());
		
			
		}
		
	
		
		
		
		
		return reportTemplate;
		
	}
	
	@Transactional
	public ReportTemplateUI save(ReportTemplateUI reportTemplate) {
		
		List<ReportTemplateColumn> reportTemplateColumns = reportTemplate.getColumns();		
		
		
		//save the template	
		log.info("id**-" + reportTemplate.getId());
		
		ReportTemplate reportTemplatedb = reportTemplateRepository.findOne(reportTemplate.getId());		
		reportTemplatedb.setLevel(reportTemplate.getLevel());
		reportTemplatedb.setName(reportTemplate.getName());
		reportTemplatedb.setRootConceptCode(reportTemplate.getRootConceptCode());
		reportTemplatedb.setType(reportTemplate.getType());
		reportTemplatedb.setAssociation(reportTemplate.getAssociation());
		reportTemplatedb.setStatus(reportTemplate.getStatus());
		reportTemplatedb.setSortColumn(reportTemplate.getSortColumn());
		reportTemplatedb.getColumns().clear();
		reportTemplateRepository.save(reportTemplatedb);
		
		ReportTemplate reportTemplateRet = reportTemplateRepository.findOne(reportTemplate.getId());
		
		for (ReportTemplateColumn reportTemplateColumn : reportTemplateColumns) {			
			reportTemplateColumn.setReportTemplate(reportTemplateRet);
			ReportTemplateColumn reportTemplateColumnRet = reportTemplateColumnRepository.save(reportTemplateColumn);

			log.info("reportTemplateColumnRet " + reportTemplateColumnRet.getId() + " - "
					+ reportTemplateColumnRet.getLabel());

		}
		
		
		
		return reportTemplate;
		
	}
	
	
	public ReportTemplate findOne(Integer id) {
		return reportTemplateRepository.findOne(id);
		
	}

}
