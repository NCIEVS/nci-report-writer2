package gov.nih.nci.evs.reportwriter.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import gov.nih.nci.evs.reportwriter.core.model.evs.EvsVersionInfo;
import gov.nih.nci.evs.reportwriter.web.model.LkGeneric;
import gov.nih.nci.evs.reportwriter.web.model.LkProperty;
import gov.nih.nci.evs.reportwriter.web.model.LookUp;
import gov.nih.nci.evs.reportwriter.web.model.ReportTask;
import gov.nih.nci.evs.reportwriter.web.model.ReportTemplate;
import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateColumn;
import gov.nih.nci.evs.reportwriter.web.service.LkAssociationService;
import gov.nih.nci.evs.reportwriter.web.service.LkAttrService;
import gov.nih.nci.evs.reportwriter.web.service.LkDisplayService;
import gov.nih.nci.evs.reportwriter.web.service.LkGroupService;
import gov.nih.nci.evs.reportwriter.web.service.LkPropertyService;
import gov.nih.nci.evs.reportwriter.web.service.LkPropertyTypeService;
import gov.nih.nci.evs.reportwriter.web.service.LkReportStatusService;
import gov.nih.nci.evs.reportwriter.web.service.LkReportTemplateStatusService;
import gov.nih.nci.evs.reportwriter.web.service.LkReportTemplateTypeService;
import gov.nih.nci.evs.reportwriter.web.service.LkSourceService;
import gov.nih.nci.evs.reportwriter.web.service.LkSubsourceService;
import gov.nih.nci.evs.reportwriter.web.service.ReportTaskService;
import gov.nih.nci.evs.reportwriter.web.service.ReportTemplateColumnService;
import gov.nih.nci.evs.reportwriter.web.service.ReportTemplateService;
import gov.nih.nci.evs.reportwriter.web.support.FileUI;
import gov.nih.nci.evs.reportwriter.web.support.ReportTaskOutput;
import gov.nih.nci.evs.reportwriter.web.support.ReportTaskUI;
import gov.nih.nci.evs.reportwriter.web.support.ReportTemplateUI;
import gov.nih.nci.evs.reportwriter.web.support.RunReportTemplateInfo;
import gov.nih.nci.evs.reportwriter.web.util.MediaTypes;
import jakarta.servlet.http.HttpServletResponse;

/** The Class ReportWriterController. */
@RestController
@RequestMapping("/reportwriter")
public class ReportWriterController {

  /** The Constant log. */
  private static final Logger log = LoggerFactory.getLogger(ReportWriterController.class);

  /** The lk association service. */
  @Autowired LkAssociationService lkAssociationService;

  /** The lk display service. */
  @Autowired LkDisplayService lkDisplayService;

  /** The lk group service. */
  @Autowired LkGroupService lkGroupService;

  /** The lk property service. */
  @Autowired LkPropertyService lkPropertyService;

  /** The lk property type service. */
  @Autowired LkPropertyTypeService lkPropertyTypeService;

  /** The lk report status service. */
  @Autowired LkReportStatusService lkReportStatusService;

  /** The lk report template status service. */
  @Autowired LkReportTemplateStatusService lkReportTemplateStatusService;

  /** The lk report template type service. */
  @Autowired LkReportTemplateTypeService lkReportTemplateTypeService;

  /** The lk source service. */
  @Autowired LkSourceService lkSourceService;

  /** The lk subsource service. */
  @Autowired LkSubsourceService lkSubsourceService;

  /** The lk attr service. */
  @Autowired LkAttrService lkAttrService;

  /** The report template service. */
  @Autowired ReportTemplateService reportTemplateService;

  /** The report template column service. */
  @Autowired ReportTemplateColumnService reportTemplateColumnService;

  /** The report task service. */
  @Autowired ReportTaskService reportTaskService;

  /**
   * Home.
   *
   * @return the string
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public @ResponseBody String home() {
    return "Welcome to ReportWriter";
  }

  /**
   * Find all lk associations.
   *
   * @return the list
   */
  @RequestMapping(
      value = "/lkassociation",
      method = RequestMethod.GET,
      produces = "application/json")
  public @ResponseBody List<LookUp> findAllLkAssociations() {
    List<? extends LkGeneric> lks = lkAssociationService.findAll();
    return convertToLookUp(lks);
  }

  /**
   * Find all lk displays.
   *
   * @return the list
   */
  @RequestMapping(value = "/lkdisplay", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody List<LookUp> findAllLkDisplays() {
    List<? extends LkGeneric> lks = lkDisplayService.findAll();
    return convertToLookUp(lks);
  }

  /**
   * Find all lk groups.
   *
   * @return the list
   */
  @RequestMapping(value = "/lkgroup", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody List<LookUp> findAllLkGroups() {
    List<? extends LkGeneric> lks = lkGroupService.findAll();
    return convertToLookUp(lks);
  }

  /**
   * Find all lk property types.
   *
   * @return the list
   */
  @RequestMapping(
      value = "/lkpropertytype",
      method = RequestMethod.GET,
      produces = "application/json")
  public @ResponseBody List<LookUp> findAllLkPropertyTypes() {
    List<? extends LkGeneric> lks = lkPropertyTypeService.findAll();
    return convertToLookUp(lks);
  }

  /**
   * Find all lk report statuses.
   *
   * @return the list
   */
  @RequestMapping(
      value = "/lkreportstatus",
      method = RequestMethod.GET,
      produces = "application/json")
  public @ResponseBody List<LookUp> findAllLkReportStatuses() {
    List<? extends LkGeneric> lks = lkReportStatusService.findAll();
    return convertToLookUp(lks);
  }

  /**
   * Find all lk report template statuses.
   *
   * @return the list
   */
  @RequestMapping(
      value = "/lkreporttemplatestatus",
      method = RequestMethod.GET,
      produces = "application/json")
  public @ResponseBody List<LookUp> findAllLkReportTemplateStatuses() {
    List<? extends LkGeneric> lks = lkReportTemplateStatusService.findAll();
    return convertToLookUp(lks);
  }

  /**
   * Find all lk report template types.
   *
   * @return the list
   */
  @RequestMapping(
      value = "/lkreporttemplatetype",
      method = RequestMethod.GET,
      produces = "application/json")
  public @ResponseBody List<LookUp> findAllLkReportTemplateTypes() {
    List<? extends LkGeneric> lks = lkReportTemplateTypeService.findAll();
    return convertToLookUp(lks);
  }

  /**
   * Find all lk sources.
   *
   * @return the list
   */
  @RequestMapping(value = "/lksource", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody List<LookUp> findAllLkSources() {
    List<? extends LkGeneric> lks = lkSourceService.findAll();
    return convertToLookUp(lks);
  }

  /**
   * Find all lk attrs.
   *
   * @return the list
   */
  @RequestMapping(value = "/lkattr", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody List<LookUp> findAllLkAttrs() {
    List<? extends LkGeneric> lks = lkAttrService.findAll();
    return convertToLookUp(lks);
  }

  /**
   * Find all lk subsources.
   *
   * @return the list
   */
  @RequestMapping(value = "/lksubsource", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody List<LookUp> findAllLkSubsources() {
    List<? extends LkGeneric> lks = lkSubsourceService.findAll();
    return convertToLookUp(lks);
  }

  /**
   * Find all lk properties.
   *
   * @return the list
   */
  @RequestMapping(value = "/lkproperty", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody List<LookUp> findAllLkProperties() {
    List<LkProperty> lks = lkPropertyService.findAll();

    lks.sort(
        new Comparator<LkProperty>() {
          @Override
          public int compare(LkProperty m1, LkProperty m2) {
            return m1.getLabel().compareToIgnoreCase(m2.getLabel());
          }
        });

    List<LookUp> lookUps = new ArrayList<LookUp>();
    for (LkProperty lookUp : lks) {
      String label = lookUp.getLabel() + "(" + lookUp.getCode() + ") ";
      lookUps.add(new LookUp(label, lookUp.getCode()));
    }
    return lookUps;
  }

  /**
   * Convert to look up.
   *
   * @param lks the lks
   * @return the list
   */
  private List<LookUp> convertToLookUp(List<? extends LkGeneric> lks) {
    List<LookUp> lookUps = new ArrayList<LookUp>();
    for (LkGeneric lookUp : lks) {
      lookUps.add(new LookUp(lookUp.getName(), lookUp.getName()));
    }
    return lookUps;
  }

  /**
   * Version info.
   *
   * @param databaseType the database type
   * @return the evs version info
   * @throws JsonProcessingException the json processing exception
   */
  // Get all Templates
  @RequestMapping(
      value = "/versionInfo/{databaseType}",
      method = RequestMethod.GET,
      produces = "application/json")
  public @ResponseBody EvsVersionInfo versionInfo(@PathVariable String databaseType)
      throws JsonProcessingException {
    return reportTaskService.getVersionInfo(databaseType);
  }

  /**
   * Creates the template.
   *
   * @param reportTemplate the report template
   * @return the string
   * @throws JsonProcessingException the json processing exception
   */
  // Creating a new Template
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/createTemplate",
      consumes = "application/json",
      produces = "application/json")
  public @ResponseBody String createTemplate(@RequestBody final ReportTemplateUI reportTemplate)
      throws JsonProcessingException {

    log.info(reportTemplate.getName());
    log.info(reportTemplate.getStatus());

    ReportTemplateUI reportTemplateRet = reportTemplateService.create(reportTemplate);

    List<ReportTemplateColumn> reportTemplateColumns =
        reportTemplateColumnService.getReportColumnsByReportTemplateID(reportTemplateRet.getId());

    reportTemplateRet.setColumns(reportTemplateColumns);

    SimpleFilterProvider filter = new SimpleFilterProvider();
    filter.setFailOnUnknownId(false);
    ObjectMapper mapper = new ObjectMapper();
    String reportTemplateRetStr = mapper.writer(filter).writeValueAsString(reportTemplateRet);

    // log.info("test - " + reportTemplateRetStr);

    return reportTemplateRetStr;
  }

  /**
   * Save template.
   *
   * @param reportTemplate the report template
   * @return the string
   * @throws JsonProcessingException the json processing exception
   */
  // Creating a new Template
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/saveTemplate",
      consumes = "application/json",
      produces = "application/json")
  public @ResponseBody String saveTemplate(@RequestBody final ReportTemplateUI reportTemplate)
      throws JsonProcessingException {

    log.info("Id **- " + reportTemplate.getId());
    log.info(reportTemplate.getName());
    log.info(reportTemplate.getStatus());

    ReportTemplateUI reportTemplateRet = reportTemplateService.save(reportTemplate);

    List<ReportTemplateColumn> reportTemplateColumns =
        reportTemplateColumnService.getReportColumnsByReportTemplateID(reportTemplateRet.getId());
    reportTemplateRet.setColumns(reportTemplateColumns);

    SimpleFilterProvider filter = new SimpleFilterProvider();
    filter.setFailOnUnknownId(false);
    ObjectMapper mapper = new ObjectMapper();
    String reportTemplateRetStr = mapper.writer(filter).writeValueAsString(reportTemplateRet);

    // log.info("test - " + reportTemplateRetStr);

    return reportTemplateRetStr;
  }

  /**
   * Clone template.
   *
   * @param reportTemplate the report template
   * @return the string
   * @throws JsonProcessingException the json processing exception
   */
  // Creating a new Template
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/cloneTemplate",
      consumes = "application/json",
      produces = "application/json")
  public @ResponseBody String cloneTemplate(@RequestBody final ReportTemplateUI reportTemplate)
      throws JsonProcessingException {

    log.info("Id **- " + reportTemplate.getId());
    log.info(reportTemplate.getName());
    log.info(reportTemplate.getStatus());

    ReportTemplateUI reportTemplateRet = reportTemplateService.clone(reportTemplate);

    // List<ReportTemplateColumn> reportTemplateColumns =
    // reportTemplateColumnService.getReportColumnsByReportTemplateID(reportTemplateRet.getId());
    // reportTemplateRet.setColumns(reportTemplateColumns);

    SimpleFilterProvider filter = new SimpleFilterProvider();
    filter.setFailOnUnknownId(false);
    ObjectMapper mapper = new ObjectMapper();
    String reportTemplateRetStr = mapper.writer(filter).writeValueAsString(reportTemplateRet);

    // log.info("test - " + reportTemplateRetStr);

    return reportTemplateRetStr;
  }

  /**
   * Reporttemplates.
   *
   * @return the string
   * @throws JsonProcessingException the json processing exception
   */
  // Get all Templates
  @RequestMapping(
      value = "/reporttemplates",
      method = RequestMethod.GET,
      produces = "application/json")
  public @ResponseBody String reporttemplates() throws JsonProcessingException {
    List<ReportTemplateUI> reportTemplates = reportTemplateService.findAll();

    SimpleFilterProvider filter = new SimpleFilterProvider();
    filter.setFailOnUnknownId(false);
    ObjectMapper mapper = new ObjectMapper();
    String reportTemplatesStr = mapper.writer(filter).writeValueAsString(reportTemplates);

    // log.info("test - " + reportTemplatesStr);

    return reportTemplatesStr;
  }

  /**
   * Reporttemplates.
   *
   * @param reportTemplateId the report template id
   * @return the string
   * @throws JsonProcessingException the json processing exception
   */
  // Get all Templates
  @RequestMapping(
      value = "/reporttemplate/{reportTemplateId}",
      method = RequestMethod.GET,
      produces = "application/json")
  public @ResponseBody String reporttemplates(@PathVariable Integer reportTemplateId)
      throws JsonProcessingException {
    ReportTemplate reportTemplate = reportTemplateService.findOne(reportTemplateId);

    SimpleFilterProvider filter = new SimpleFilterProvider();
    filter.setFailOnUnknownId(false);
    ObjectMapper mapper = new ObjectMapper();
    String reportTemplateStr = mapper.writer(filter).writeValueAsString(reportTemplate);

    log.info("reportTemplateStr ---- " + reportTemplateStr);

    return reportTemplateStr;
  }

  /**
   * Reporttasks.
   *
   * @return the list
   */
  @RequestMapping(value = "/reporttasks", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody List<ReportTaskUI> reporttasks() {
    List<ReportTaskUI> reportTaskUIs = reportTaskService.getAllTasksExceptDeleted();

    return reportTaskUIs;
  }

  /**
   * Reporttasksdeleted.
   *
   * @return the list
   */
  @RequestMapping(
      value = "/reporttasksdeleted",
      method = RequestMethod.GET,
      produces = "application/json")
  public @ResponseBody List<ReportTaskUI> reporttasksdeleted() {
    List<ReportTaskUI> reportTaskUIs = reportTaskService.getAllDeletedTasks();

    return reportTaskUIs;
  }

  /**
   * Report name by task id.
   *
   * @param id the id
   * @return the report template UI
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @RequestMapping(value = "/reportNameByTaskId/{id}", method = RequestMethod.GET)
  public @ResponseBody ReportTemplateUI reportNameByTaskId(@PathVariable Integer id)
      throws IOException {
    log.info("getReportTask" + id);
    ReportTemplateUI reportTemplateUI = reportTaskService.getReportNameByTaskId(id);
    return reportTemplateUI;
  }

  /**
   * Delete report task.
   *
   * @param id the id
   * @return the report task
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @RequestMapping(value = "/deleteReportTask/{id}", method = RequestMethod.GET)
  public @ResponseBody ReportTask deleteReportTask(@PathVariable Integer id) throws IOException {

    ReportTask reportTask = reportTaskService.deleteReportTask(id);
    return reportTask;
  }

  /**
   * Gets the XLS report.
   *
   * @param id the id
   * @param response the response
   * @return the XLS report
   * @throws Exception the exception
   */
  @RequestMapping(value = "/getXLSReport/{id}", method = RequestMethod.GET)
  public ModelAndView getXLSReport(@PathVariable("id") String id, HttpServletResponse response)
      throws Exception {
    log.debug("In here getDetailedReport");

    FileUI fileUI = reportTaskService.getDetailedReportTask(id, "xls");

    getFile(fileUI, response);

    return null;
  }

  /**
   * Gets the txt report.
   *
   * @param id the id
   * @param response the response
   * @return the txt report
   * @throws Exception the exception
   */
  @RequestMapping(value = "/getTxtReport/{id}", method = RequestMethod.GET)
  public ModelAndView getTxtReport(@PathVariable("id") String id, HttpServletResponse response)
      throws Exception {
    log.debug("In here getTxtReport");

    FileUI fileUI = reportTaskService.getDetailedReportTask(id, "txt");

    getFile(fileUI, response);

    return null;
  }

  /**
   * Convert report.
   *
   * @param id the id
   * @param type the type
   * @param column the column
   * @param response the response
   * @return the model and view
   * @throws Exception the exception
   */
  @RequestMapping(value = "/convertReport/{id}/{type}/{column}", method = RequestMethod.GET)
  public ModelAndView convertReport(
      @PathVariable("id") String id,
      @PathVariable("type") String type,
      @PathVariable("column") String column,
      HttpServletResponse response)
      throws Exception {
    log.debug("In here getTxtReport");

    FileUI fileUI = reportTaskService.convertReportTask(id, type, column, "txt");

    getFile(fileUI, response);

    return null;
  }

  /**
   * Gets the template report.
   *
   * @param id the id
   * @param response the response
   * @return the template report
   * @throws Exception the exception
   */
  @RequestMapping(value = "/getTemplateReport/{id}", method = RequestMethod.GET)
  public ModelAndView getTemplateReport(@PathVariable("id") String id, HttpServletResponse response)
      throws Exception {
    log.debug("In here getTxtReport");

    FileUI fileUI = reportTaskService.getDetailedReportTask(id, "template");

    getFile(fileUI, response);

    return null;
  }

  /**
   * Gets the log report.
   *
   * @param id the id
   * @param response the response
   * @return the log report
   * @throws Exception the exception
   */
  @RequestMapping(value = "/getLogReport/{id}", method = RequestMethod.GET)
  public ModelAndView getLogReport(@PathVariable("id") String id, HttpServletResponse response)
      throws Exception {
    log.debug("In here getLogReport");

    FileUI fileUI = reportTaskService.getDetailedReportTask(id, "log");

    getFile(fileUI, response);

    return null;
  }

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
  @RequestMapping(value = "/getReportTaskData/{id}", method = RequestMethod.GET)
  public @ResponseBody ReportTaskOutput getReportTaskData(@PathVariable String id)
      throws IOException,
          NoSuchMethodException,
          SecurityException,
          IllegalAccessException,
          IllegalArgumentException,
          InvocationTargetException {

    ReportTaskOutput reportTaskOutput = reportTaskService.getReportTaskData(id);

    return reportTaskOutput;
  }

  /**
   * Gets the file.
   *
   * @param file the file
   * @param response the response
   * @return the file
   * @throws Exception the exception
   */
  protected void getFile(FileUI file, HttpServletResponse response) throws Exception {

    try (OutputStream out = response.getOutputStream(); ) {
      String fileName = file.getFileName();
      //
      // set to correct media-type
      //
      response.setContentType(MediaTypes.getMediaType(fileName));

      response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

      // Tell browser to validate cache
      response.addHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
      response.setHeader("Cache-Control", "max-age=0");
      // Using public somehow cures IE's inability to save attachment
      // (and doesn't seem to hurt other browsers)
      response.setHeader("Pragma", "public");
      try (final InputStream in = file.getRawFileStream()) {
        byte[] buf = new byte[16 * 1024]; // 16k buffer
        int nRead = 0;
        while ((nRead = in.read(buf)) != -1) {
          out.write(buf, 0, nRead);
        }
        out.flush();
      }
    }
  }

  /**
   * Run report templates.
   *
   * @param runReportTemplateInfo the run report template info
   * @return the array list
   * @throws JsonProcessingException the json processing exception
   */
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/runReportTemplates",
      consumes = "application/json",
      produces = "application/json")
  public @ResponseBody ArrayList<ReportTaskUI> runReportTemplates(
      @RequestBody final RunReportTemplateInfo runReportTemplateInfo)
      throws JsonProcessingException {
    log.debug("Database Type- " + runReportTemplateInfo.getDatbaseType());
    List<ReportTemplateUI> reportTemplates = runReportTemplateInfo.getReportTemplates();
    ArrayList<ReportTaskUI> reportTasks = new ArrayList<ReportTaskUI>();
    for (ReportTemplateUI reportTemplateUI : reportTemplates) {
      log.info(
          "template id - "
              + reportTemplateUI.getId()
              + " report name - "
              + reportTemplateUI.getName());
      ReportTemplate reportTemplate = reportTemplateService.findOne(reportTemplateUI.getId());
      ReportTask reportTaskRet =
          reportTaskService.createReportTask(
              reportTemplate, runReportTemplateInfo.getDatbaseType());
      log.info("TaskId" + reportTaskRet.getId());
      ReportTaskUI reportTaskUI = new ReportTaskUI();
      reportTaskUI.setReportTemplateName(reportTemplateUI.getName());
      reportTaskUI.setId(reportTaskRet.getId());
      reportTaskService.runReport(reportTaskRet);
      log.info("Run Report Submitted");
      reportTasks.add(reportTaskUI);
    }
    return reportTasks;
  }

  /**
   * Upload concept list.
   *
   * @param file the file
   * @param runReportTemplateInfo the run report template info
   * @param response the response
   * @return the array list
   * @throws Exception the exception
   */
  @RequestMapping(
      method = RequestMethod.POST,
      value = "/uploadConceptList",
      produces = "application/json")
  public @ResponseBody ArrayList<ReportTaskUI> UploadConceptList(
      @RequestPart("conceptList") MultipartFile file,
      @RequestPart("runReportTemplateInfo") RunReportTemplateInfo runReportTemplateInfo,
      HttpServletResponse response)
      throws Exception {

    log.info("Database type - " + runReportTemplateInfo.getDatbaseType());
    List<ReportTemplateUI> reportTemplates = runReportTemplateInfo.getReportTemplates();
    ArrayList<ReportTaskUI> reportTasks = new ArrayList<ReportTaskUI>();
    for (ReportTemplateUI reportTemplateUI : reportTemplates) {
      log.info(
          "template id - "
              + reportTemplateUI.getId()
              + " report name - "
              + reportTemplateUI.getName());
      ReportTemplate reportTemplate = reportTemplateService.findOne(reportTemplateUI.getId());
      ReportTask reportTaskRet =
          reportTaskService.createReportTask(
              reportTemplate, runReportTemplateInfo.getDatbaseType());
      log.info("TaskId" + reportTaskRet.getId());
      ReportTaskUI reportTaskUI = new ReportTaskUI();
      reportTaskUI.setReportTemplateName(reportTemplateUI.getName());
      reportTaskUI.setId(reportTaskRet.getId());
      reportTaskService.storeFile(reportTaskRet, file);

      reportTaskService.runReport(reportTaskRet);
      log.info("Run Report Submitted");
      reportTasks.add(reportTaskUI);
    }

    return reportTasks;
  }
}
