package gov.nih.nci.evs.reportwriter.web.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.ReportTask;
import gov.nih.nci.evs.reportwriter.web.model.ReportTemplate;
import gov.nih.nci.evs.reportwriter.web.support.ReportTaskUI;

public interface ReportTaskService {
	
	public List <ReportTaskUI> getAllTasksExceptDeleted();
	
	public List <ReportTaskUI>  getAllDeletedTasks();
	
	public void runReport(ReportTask reportTask);
	
	public ReportTask save(ReportTask reportTask);
	
	public ReportTask findOne(Integer reportTaskId);

}
