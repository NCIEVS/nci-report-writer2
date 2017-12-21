package gov.nih.nci.evs.reportwriter.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.model.ReportTask;
import gov.nih.nci.evs.reportwriter.repository.ReportTaskRepository;
import gov.nih.nci.evs.reportwriter.support.ReportTaskUI;

@Service
public class ReportTaskServiceImpl implements ReportTaskService {
	
	private static final Logger log = LoggerFactory.getLogger(ReportTaskServiceImpl.class);
	
	@Autowired
	ReportTaskRepository reportTaskRepository;
	
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

}
