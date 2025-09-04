package gov.nih.nci.evs.reportwriter.web.service;

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

/** The Interface ReportTaskService. */
public interface ReportTaskService {

  /**
   * Gets the all tasks except deleted.
   *
   * @return the all tasks except deleted
   */
  public List<ReportTaskUI> getAllTasksExceptDeleted();

  /**
   * Gets the all deleted tasks.
   *
   * @return the all deleted tasks
   */
  public List<ReportTaskUI> getAllDeletedTasks();

  /**
   * Gets the report name by task id.
   *
   * @param reportTaskId the report task id
   * @return the report name by task id
   */
  public ReportTemplateUI getReportNameByTaskId(Integer reportTaskId);

  /**
   * Run report.
   *
   * @param reportTask the report task
   */
  public void runReport(ReportTask reportTask);

  /**
   * Store file.
   *
   * @param reportTaskRet the report task ret
   * @param file the file
   * @throws IllegalStateException the illegal state exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void storeFile(ReportTask reportTaskRet, MultipartFile file)
      throws IllegalStateException, IOException;

  /**
   * Save.
   *
   * @param reportTask the report task
   * @return the report task
   */
  public ReportTask save(ReportTask reportTask);

  /**
   * Creates the report task.
   *
   * @param reportTemplate the report template
   * @param databaseType the database type
   * @return the report task
   */
  public ReportTask createReportTask(ReportTemplate reportTemplate, String databaseType);

  /**
   * Find one.
   *
   * @param reportTaskId the report task id
   * @return the report task
   */
  public ReportTask findOne(Integer reportTaskId);

  /**
   * Delete report task.
   *
   * @param reportTaskId the report task id
   * @return the report task
   */
  public ReportTask deleteReportTask(Integer reportTaskId);

  /**
   * Gets the detailed report task.
   *
   * @param id the id
   * @param fileType the file type
   * @return the detailed report task
   * @throws FileNotFoundException the file not found exception
   */
  public FileUI getDetailedReportTask(String id, String fileType) throws FileNotFoundException;

  /**
   * Gets the report task data.
   *
   * @param id the id
   * @return the report task data
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws NoSuchMethodException the no such method exception
   * @throws SecurityException the security exception
   * @throws IllegalAccessException the illegal access exception
   * @throws IllegalArgumentException the illegal argument exception
   * @throws InvocationTargetException the invocation target exception
   */
  public ReportTaskOutput getReportTaskData(String id)
      throws IOException,
          NoSuchMethodException,
          SecurityException,
          IllegalAccessException,
          IllegalArgumentException,
          InvocationTargetException;

  /**
   * Gets the version info.
   *
   * @param databaseType the database type
   * @return the version info
   */
  public EvsVersionInfo getVersionInfo(String databaseType);

  /**
   * Convert report task.
   *
   * @param id the id
   * @param type the type
   * @param column the column
   * @param fileType the file type
   * @return the file UI
   * @throws FileNotFoundException the file not found exception
   * @throws InvalidInputParameterException the invalid input parameter exception
   */
  public FileUI convertReportTask(String id, String type, String column, String fileType)
      throws FileNotFoundException, InvalidInputParameterException;
}
