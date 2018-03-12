package gov.nih.nci.evs.reportwriter.web.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.nih.nci.evs.reportwriter.web.controller.ReportWriterController;
import gov.nih.nci.evs.reportwriter.web.model.ReportTemplate;
import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateColumn;
import gov.nih.nci.evs.reportwriter.web.properties.WebProperties;
import gov.nih.nci.evs.reportwriter.web.repository.ReportTemplateColumnRepository;
import gov.nih.nci.evs.reportwriter.web.repository.ReportTemplateRepository;
import gov.nih.nci.evs.reportwriter.web.support.FileUI;
import gov.nih.nci.evs.reportwriter.web.support.ReportTemplateUI;

@Service
public class ReportTemplateServiceImpl implements ReportTemplateService {
	
	private static final Logger log = LoggerFactory.getLogger(ReportTemplateServiceImpl.class);

	
	@Autowired
	ReportTemplateRepository reportTemplateRepository;
	
	@Autowired
	WebProperties webProperties;

	@Autowired
	ReportTemplateColumnRepository reportTemplateColumnRepository;
	
	public List <ReportTemplateUI> findAll() {
		
		List <ReportTemplate>  reportTemplates = (List <ReportTemplate>)reportTemplateRepository.findAll();
		
		List<ReportTemplateUI> reportTemplateUIs = new ArrayList<ReportTemplateUI>();
		
		for (ReportTemplate reportTemplate: reportTemplates) {
			ReportTemplateUI reportTemplateUI = new ReportTemplateUI();
			reportTemplateUI.setId(reportTemplate.getId());
			reportTemplateUI.setName(reportTemplate.getName());
			reportTemplateUI.setStatus(reportTemplate.getStatus());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
			String txtDateCreated = reportTemplate.getDateCreated().format(formatter);
			reportTemplateUI.setDateCreated(txtDateCreated);
			String txtDateUpdated = reportTemplate.getDateLastUpdated().format(formatter);
			reportTemplateUI.setDateLastUpdated(txtDateUpdated);
			reportTemplateUIs.add(reportTemplateUI);
		}
		return reportTemplateUIs;
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
		reportTemplatedb.setDateCreated(LocalDateTime.now());
		reportTemplatedb.setDateLastUpdated(LocalDateTime.now());
		reportTemplatedb.setCreatedBy("system");
		reportTemplatedb.setLastUpdatedBy("system");
		
		
		//save the tempalte
		ReportTemplate reportTemplateRet =reportTemplateRepository.save(reportTemplatedb);
		
		log.info("id" + reportTemplateRet.getId());
		reportTemplate.setId(reportTemplateRet.getId());
		
		
		reportTemplateRet = reportTemplateRepository.findOne(reportTemplateRet.getId());
		
		for (ReportTemplateColumn reportTemplateColumn : reportTemplateColumns) {
			
			
			reportTemplateColumn.setReportTemplate(reportTemplateRet);
			reportTemplateColumn.setDateCreated(LocalDateTime.now());
			reportTemplateColumn.setDateLastUpdated(LocalDateTime.now());
			reportTemplateColumn.setCreatedBy("system");
			reportTemplateColumn.setLastUpdatedBy("system");
			
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
		reportTemplatedb.setDateLastUpdated(LocalDateTime.now());		
		reportTemplatedb.setLastUpdatedBy("system");
		reportTemplatedb.getColumns().clear();
		reportTemplateRepository.save(reportTemplatedb);
		
		ReportTemplate reportTemplateRet = reportTemplateRepository.findOne(reportTemplate.getId());
		
		for (ReportTemplateColumn reportTemplateColumn : reportTemplateColumns) {			
			reportTemplateColumn.setReportTemplate(reportTemplateRet);
			reportTemplateColumn.setCreatedBy("system");
			reportTemplateColumn.setDateCreated(LocalDateTime.now());
			reportTemplateColumn.setDateLastUpdated(LocalDateTime.now());			
			reportTemplateColumn.setLastUpdatedBy("system");
			ReportTemplateColumn reportTemplateColumnRet = reportTemplateColumnRepository.save(reportTemplateColumn);

			log.info("reportTemplateColumnRet " + reportTemplateColumnRet.getId() + " - "
					+ reportTemplateColumnRet.getLabel());

		}
		
		
		
		return reportTemplate;
		
	}
	
	
	
	public ReportTemplateUI clone(ReportTemplateUI reportTemplate) {
		ReportTemplate oldReportTemplate = reportTemplateRepository.findOne(reportTemplate.getId());
		
		List<ReportTemplateColumn> oldReportTemplateColumns = oldReportTemplate.getColumns();	
		
		
		ReportTemplate newReportTemplate = new ReportTemplate();
		newReportTemplate.setLevel(oldReportTemplate.getLevel());
		newReportTemplate.setName(reportTemplate.getName());
		newReportTemplate.setRootConceptCode(oldReportTemplate.getRootConceptCode());
		newReportTemplate.setType(oldReportTemplate.getType());
		newReportTemplate.setAssociation(oldReportTemplate.getAssociation());
		newReportTemplate.setStatus(oldReportTemplate.getStatus());
		newReportTemplate.setSortColumn(oldReportTemplate.getSortColumn());		
		newReportTemplate.setCreatedBy("system");
		newReportTemplate.setDateCreated(LocalDateTime.now());
		newReportTemplate.setDateLastUpdated(LocalDateTime.now());		
		newReportTemplate.setLastUpdatedBy("system");
	
		ReportTemplate newReportTemplateRet =  reportTemplateRepository.save(newReportTemplate);
		
		ReportTemplate newReportTemplatedb = reportTemplateRepository.findOne(newReportTemplateRet.getId());
		
				
		for (ReportTemplateColumn oldReportTemplateColumn : oldReportTemplateColumns) {	
			ReportTemplateColumn newReportTemplateColumn = new ReportTemplateColumn();
			newReportTemplateColumn.setColumnNumber(oldReportTemplateColumn.getColumnNumber());
			newReportTemplateColumn.setLabel(oldReportTemplateColumn.getLabel());
			newReportTemplateColumn.setDisplay(oldReportTemplateColumn.getDisplay());
			newReportTemplateColumn.setProperty(oldReportTemplateColumn.getProperty());
			newReportTemplateColumn.setPropertyType(oldReportTemplateColumn.getPropertyType());
			newReportTemplateColumn.setSource(oldReportTemplateColumn.getSource());
			newReportTemplateColumn.setGroup(oldReportTemplateColumn.getGroup());
			newReportTemplateColumn.setSubsource(oldReportTemplateColumn.getSubsource());	
			newReportTemplateColumn.setReportTemplate(newReportTemplatedb);
			newReportTemplateColumn.setCreatedBy("system");
			newReportTemplateColumn.setDateCreated(LocalDateTime.now());
			newReportTemplateColumn.setDateLastUpdated(LocalDateTime.now());			
			newReportTemplateColumn.setLastUpdatedBy("system");
		    reportTemplateColumnRepository.save(newReportTemplateColumn);

			

		}
		
		reportTemplate.setId(newReportTemplatedb.getId());
		
		return reportTemplate;
	}
	
	
	public ReportTemplate findOne(Integer id) {
		return reportTemplateRepository.findOne(id);
		
	}
	
	

}
