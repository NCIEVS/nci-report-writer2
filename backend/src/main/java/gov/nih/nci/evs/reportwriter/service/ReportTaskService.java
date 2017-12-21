package gov.nih.nci.evs.reportwriter.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.model.ReportTask;
import gov.nih.nci.evs.reportwriter.support.ReportTaskUI;

public interface ReportTaskService {
	
	public List <ReportTaskUI> getAllTasksExceptDeleted();

}
