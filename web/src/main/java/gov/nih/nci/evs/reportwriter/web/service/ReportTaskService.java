package gov.nih.nci.evs.reportwriter.web.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import gov.nih.nci.evs.reportwriter.core.model.evs.EvsVersionInfo;
import gov.nih.nci.evs.reportwriter.web.exception.InvalidInputParameterException;
import gov.nih.nci.evs.reportwriter.web.model.ReportTask;
import gov.nih.nci.evs.reportwriter.web.model.ReportTemplate;
import gov.nih.nci.evs.reportwriter.web.support.FileUI;
import gov.nih.nci.evs.reportwriter.web.support.ReportTaskOutput;
import gov.nih.nci.evs.reportwriter.web.support.ReportTaskUI;
import gov.nih.nci.evs.reportwriter.web.support.ReportTemplateUI;

public interface ReportTaskService {
	
	public List <ReportTaskUI> getAllTasksExceptDeleted();
	
	public List <ReportTaskUI>  getAllDeletedTasks();
	
	public ReportTemplateUI getReportNameByTaskId(Integer reportTaskId);
	
	public void runReport(ReportTask reportTask);
	
	public void storeFile(ReportTask reportTaskRet, MultipartFile file) throws IllegalStateException, IOException;
	
	public ReportTask save(ReportTask reportTask);
	
	public ReportTask createReportTask(ReportTemplate reportTemplate, String databaseType);
	
	public ReportTask findOne(Integer reportTaskId);
	
	public ReportTask deleteReportTask(Integer reportTaskId);
	
	public FileUI getDetailedReportTask(String id,String fileType) throws FileNotFoundException;
	
	public ReportTaskOutput getReportTaskData(String id) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;

	public EvsVersionInfo getVersionInfo(String databaseType);
	
	public FileUI convertReportTask(String id, String type, String column, String fileType)  throws FileNotFoundException, InvalidInputParameterException;
}
