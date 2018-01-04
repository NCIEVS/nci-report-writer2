package gov.nih.nci.evs.reportwriter.web.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import gov.nih.nci.evs.reportwriter.core.properties.CoreProperties;
import gov.nih.nci.evs.reportwriter.core.properties.StardogProperties;
import gov.nih.nci.evs.reportwriter.core.service.ReportWriter;
import gov.nih.nci.evs.reportwriter.web.model.ReportTask;
import gov.nih.nci.evs.reportwriter.web.model.ReportTemplate;
import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateColumn;
import gov.nih.nci.evs.reportwriter.web.repository.ReportTaskRepository;
import gov.nih.nci.evs.reportwriter.web.support.ReportTaskUI;

@Service
public class ReportTaskServiceImpl implements ReportTaskService {
	
	private static final Logger log = LoggerFactory.getLogger(ReportTaskServiceImpl.class);
	
	@Autowired
	ReportTaskRepository reportTaskRepository;
	
	@Autowired
	ReportTemplateService reportTemplateService;

	@Autowired
	CoreProperties coreProperties;

	@Autowired
	StardogProperties stardogProperties;

	@Autowired
	ReportWriter reportWriter;
	
	public List<ReportTaskUI> getAllTasksExceptDeleted() {
		
		List<ReportTask> reportTasks = (List<ReportTask>)reportTaskRepository.findByStatusNot("Deleted");
		
		List<ReportTaskUI> reportTaskUIs = new ArrayList<ReportTaskUI>();
		
		for (ReportTask reportTask :reportTasks) {
			log.info("id - " + reportTask.getId());
			log.info(reportTask.getReportTemplate().getName());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
			//if (!reportTask.getStatus().equalsIgnoreCase("Deleted")) {
				ReportTaskUI reportTaskUI = new ReportTaskUI();
				reportTaskUI.setId(reportTask.getId());
				reportTaskUI.setStatus(reportTask.getStatus());
				reportTaskUI.setReportTemplateName(reportTask.getReportTemplate().getName());
				reportTaskUI.setReportTemplateId(reportTask.getReportTemplate().getId());
				String txtDateCreated = reportTask.getDateCreated().format(formatter);
				reportTaskUI.setDateCreated(txtDateCreated);
				String txtDateStarted = reportTask.getDateStarted().format(formatter);
				reportTaskUI.setDateStarted(txtDateStarted);
				String txtDateCompleted = reportTask.getDateCompleted().format(formatter);
				reportTaskUI.setDateCompleted(txtDateCompleted);
				
				reportTaskUIs.add(reportTaskUI);
			//}
			
		}
		return reportTaskUIs;
		
	}
	
		public List<ReportTaskUI> getAllDeletedTasks() {
		
		List<ReportTask> reportTasks = (List<ReportTask>)reportTaskRepository.findByStatus("Deleted");
		
		List<ReportTaskUI> reportTaskUIs = new ArrayList<ReportTaskUI>();
		
		for (ReportTask reportTask :reportTasks) {
			log.info("id - " + reportTask.getId());
			log.info(reportTask.getReportTemplate().getName());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
			//if (!reportTask.getStatus().equalsIgnoreCase("Deleted")) {
				ReportTaskUI reportTaskUI = new ReportTaskUI();
				reportTaskUI.setId(reportTask.getId());
				reportTaskUI.setStatus(reportTask.getStatus());
				reportTaskUI.setReportTemplateName(reportTask.getReportTemplate().getName());
				reportTaskUI.setReportTemplateId(reportTask.getReportTemplate().getId());
				String txtDateCreated = reportTask.getDateCreated().format(formatter);
				reportTaskUI.setDateCreated(txtDateCreated);
				String txtDateStarted = reportTask.getDateStarted().format(formatter);
				reportTaskUI.setDateStarted(txtDateStarted);
				String txtDateCompleted = reportTask.getDateCompleted().format(formatter);
				reportTaskUI.setDateCompleted(txtDateCompleted);
				
				reportTaskUIs.add(reportTaskUI);
			//}
			
		}
		return reportTaskUIs;
		
	}
		
	@Transactional
	public ReportTask save(ReportTask reportTask) {
		ReportTask reportTaskRet = reportTaskRepository.save(reportTask);
		log.info("id" + reportTask.getId());
		return reportTaskRet;
	}
	
	@Async
	public void runReport(int id) {
		ReportTemplate reportTemplate = reportTemplateService.findOne(id);
		List<ReportTemplateColumn> columns = reportTemplate.getColumns();
		
		String templateDirectory = coreProperties.getTemplateDirectory();
		String outputDirectory = coreProperties.getOutputDirectory();

		ReportTask reportTask = new ReportTask();
		reportTask.setStatus("Pending");
		//reportTask.setReportTemplateId(id);
		reportTask.setReportTemplate(reportTemplate);
		reportTask.setDateCreated(LocalDateTime.now());
		save(reportTask);
		String reportName = "Task-" + reportTask.getId();
		String reportTemplateName = reportName + ".template";
		String reportLogName = outputDirectory + "/" + reportName + ".log";
		reportName = outputDirectory + "/" + reportName;

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		Set<String> ignoreProperties = new HashSet<String>();
		ignoreProperties.add("reportTemplateConceptLists");
		ignoreProperties.add("id");
		ignoreProperties.add("status");
		ignoreProperties.add("columns.id");

		SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept(ignoreProperties);
		FilterProvider filters = new SimpleFilterProvider().addFilter("yamlFilter", theFilter);
		try {
			String str = mapper.writer(filters).writeValueAsString(reportTemplate);
			String fileName = templateDirectory + "/" + reportTemplateName;
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(str);
			writer.close();

			log.info("Running Report");
			reportTask.setDateStarted(LocalDateTime.now());
			reportTask.setStatus("Started");
			save(reportTask);
			log.info("RUNNING FROM PROGRAM");
			//ReportWriter reportWriter = new ReportWriter();
			reportWriter.runReport(fileName, reportName, "");
			reportTask.setDateCompleted(LocalDateTime.now());
			save(reportTask);
			log.info("Report Completed");
		} catch (Exception ex) {
			System.err.println(ex);
		}

	}
	
	public ReportTask findOne(Integer reportTaskId) {
		
		return reportTaskRepository.findOne(reportTaskId);
		
	}
	

}
