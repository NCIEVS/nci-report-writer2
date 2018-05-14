package gov.nih.nci.evs.reportwriter.web.service;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import gov.nih.nci.evs.reportwriter.core.model.evs.EvsVersionInfo;
import gov.nih.nci.evs.reportwriter.core.properties.CoreProperties;
import gov.nih.nci.evs.reportwriter.core.properties.StardogProperties;
import gov.nih.nci.evs.reportwriter.core.service.ReportWriter;
import gov.nih.nci.evs.reportwriter.web.model.ReportTask;
import gov.nih.nci.evs.reportwriter.web.model.ReportTemplate;
import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateColumn;
import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateConceptList;
import gov.nih.nci.evs.reportwriter.web.repository.ReportTaskRepository;
import gov.nih.nci.evs.reportwriter.web.support.FileUI;
import gov.nih.nci.evs.reportwriter.web.support.ReportData;
import gov.nih.nci.evs.reportwriter.web.support.ReportTaskOutput;
import gov.nih.nci.evs.reportwriter.web.support.ReportTaskUI;
import gov.nih.nci.evs.reportwriter.web.support.ReportTemplateUI;
import gov.nih.nci.evs.reportwriter.web.support.TableHeader;

@Service
public class ReportTaskServiceImpl implements ReportTaskService {

	private static final Logger log = LoggerFactory.getLogger(ReportTaskServiceImpl.class);

	@Autowired
	ReportTaskRepository reportTaskRepository;

	@Autowired
	ReportTemplateService reportTemplateService;

	@Autowired
	ReportTemplateConceptListService reportTemplateConceptListService;

	@Autowired
	CoreProperties coreProperties;

	@Autowired
	StardogProperties stardogProperties;

	@Autowired
	ReportWriter reportWriter;

	public List<ReportTaskUI> getAllTasksExceptDeleted() {

		List<ReportTask> reportTasks = (List<ReportTask>) reportTaskRepository.findByStatusNot("Deleted");

		List<ReportTaskUI> reportTaskUIs = new ArrayList<ReportTaskUI>();

		for (ReportTask reportTask : reportTasks) {
			log.debug("id - " + reportTask.getId());
			log.debug(reportTask.getReportTemplate().getName());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
			// if (!reportTask.getStatus().equalsIgnoreCase("Deleted")) {
			ReportTaskUI reportTaskUI = new ReportTaskUI();
			reportTaskUI.setId(reportTask.getId());
			// log.info(reportTask.getId() + " - " + reportTask.getStatus());
			reportTaskUI.setStatus(reportTask.getStatus());
			reportTaskUI.setReportTemplateName(reportTask.getReportTemplate().getName());
			reportTaskUI.setReportTemplateId(reportTask.getReportTemplate().getId());
			String txtDateCreated = reportTask.getDateCreated().format(formatter);
			reportTaskUI.setDateCreated(txtDateCreated);
			String txtDateStarted = "";
			if (reportTask.getDateStarted() != null) {
				txtDateStarted = reportTask.getDateStarted().format(formatter);
			}
			reportTaskUI.setDateStarted(txtDateStarted);
			String txtDateCompleted = "";
			if (reportTask.getDateCompleted() != null) {
				txtDateCompleted = reportTask.getDateCompleted().format(formatter);
			}
			reportTaskUI.setDateCompleted(txtDateCompleted);
			reportTaskUI.setVersion(reportTask.getVersion());
			reportTaskUIs.add(reportTaskUI);
			// }

		}
		return reportTaskUIs;

	}

	public ReportTask createReportTask(ReportTemplate reportTemplate) {
		
		EvsVersionInfo evsVersionInfo = reportWriter.getEvsVersionInfo();
		ReportTask reportTask = new ReportTask();
		reportTask.setStatus("Pending");
		reportTask.setReportTemplate(reportTemplate);
		reportTask.setDateCreated(LocalDateTime.now());
		reportTask.setDateLastUpdated(LocalDateTime.now());
		reportTask.setCreatedBy("system");
		reportTask.setLastUpdatedBy("system");
		ReportTask reportTaskRet = save(reportTask);
		reportTask.setVersion(evsVersionInfo.getVersion());
		return reportTaskRet;
	}
	
	public List<ReportTaskUI> getAllDeletedTasks() {

		List<ReportTask> reportTasks = (List<ReportTask>) reportTaskRepository.findByStatus("Deleted");

		List<ReportTaskUI> reportTaskUIs = new ArrayList<ReportTaskUI>();

		for (ReportTask reportTask : reportTasks) {
			log.info("id - " + reportTask.getId());
			log.info(reportTask.getReportTemplate().getName());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
			// if (!reportTask.getStatus().equalsIgnoreCase("Deleted")) {
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
			reportTaskUI.setVersion(reportTask.getVersion());

			reportTaskUIs.add(reportTaskUI);
			// }

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
	public void runReport(ReportTask reportTask) {
		int reportTemplateId = reportTask.getReportTemplate().getId();
		ReportTemplate reportTemplate = reportTemplateService.findOne(reportTemplateId);
		List<ReportTemplateColumn> columns = reportTemplate.getColumns();

		String outputDirectory = coreProperties.getOutputDirectory();
		String reportName = "Task-" + reportTask.getId();

		/*
		 * When generating task output in the file system, we decided to use the last
		 * digit in the task_id as the top level directory name. This gives an even
		 * distribution for task folders across 10 top level folders, reducing the
		 * number of task folders within one Linux directory.
		 */
		String lastDigit = Integer.toString(reportTask.getId());
		lastDigit = lastDigit.substring(lastDigit.length() - 1);
		String outputDirectoryName = outputDirectory + "/" + lastDigit + "/" + reportName;
		try {
			Path path = Paths.get(outputDirectoryName);
			Files.createDirectory(path);
		} catch (IOException ex) {

		}
		String reportTemplateName = reportName + ".template";
		reportName = outputDirectoryName + "/" + reportName;

		/*
		 * These properties are not needed the ReportTemplate YAML file. They will be
		 * filtered out when generating the file.
		 */
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		Set<String> ignoreProperties = new HashSet<String>();
		ignoreProperties.add("reportTemplateConceptLists");
		ignoreProperties.add("id");
		ignoreProperties.add("status");
		ignoreProperties.add("createdBy");
		ignoreProperties.add("lastUpdatedBy");
		ignoreProperties.add("dateCreated");
		ignoreProperties.add("dateLastUpdated");
		ignoreProperties.add("columns.id");
		ignoreProperties.add("columns.createdBy");
		ignoreProperties.add("columns.lastUpdatedBy");
		ignoreProperties.add("columns.dateCreated");
		ignoreProperties.add("columns.dateLastUpdated");

		SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept(ignoreProperties);
		FilterProvider filters = new SimpleFilterProvider().addFilter("yamlFilter", theFilter);
		try {
			String str = mapper.writer(filters).writeValueAsString(reportTemplate);
			String templateFileName = outputDirectoryName + "/" + reportTemplateName;
			BufferedWriter writer = new BufferedWriter(new FileWriter(templateFileName));
			writer.write(str);
			writer.close();
			
			String conceptListFileName = "";
			if (reportTemplate.getType().equals("ConceptList")) {
				conceptListFileName = outputDirectoryName + "/ConceptList.txt";
				BufferedWriter conceptListWriter = new BufferedWriter(new FileWriter(conceptListFileName));
				for (ReportTemplateConceptList concept: reportTemplateConceptListService.getReportTemplateConceptListsByReportTemplateID(reportTemplateId)) {
					conceptListWriter.write(concept.getConceptCode() + "\n");
				}
				conceptListWriter.close();
			}

			log.info("Running Report");
			reportTask.setDateStarted(LocalDateTime.now());
			reportTask.setDateLastUpdated(LocalDateTime.now());
			reportTask.setStatus("Started");
			save(reportTask);
			String status = reportWriter.runReport(templateFileName, reportName, conceptListFileName);
			reportTask.setDateCompleted(LocalDateTime.now());
			reportTask.setDateLastUpdated(LocalDateTime.now());
			if (status.equals("success")) {
				reportTask.setStatus("Completed");
			} else {
				reportTask.setStatus("Failed");
			}
			save(reportTask);
			log.info("Report Completed");
		} catch (Exception ex) {
			System.err.println(ex);
		}

	}

	public ReportTask findOne(Integer reportTaskId) {

		return reportTaskRepository.findOne(reportTaskId);

	}
	
	@Transactional
	public ReportTemplateUI getReportNameByTaskId(Integer reportTaskId) {
		
		ReportTask reportTask = reportTaskRepository.findOne(reportTaskId);
		ReportTemplate  reportTemplate = reportTask.getReportTemplate();
		ReportTemplateUI reportTemplateUI = new ReportTemplateUI();
		reportTemplateUI.setName(reportTemplate.getName());
		return reportTemplateUI;
		
		
		
	}

	@Transactional
	public ReportTask deleteReportTask(Integer reportTaskId) {
		ReportTask reportTask = reportTaskRepository.findOne(reportTaskId);
		reportTask.setStatus("Deleted");
		return reportTaskRepository.save(reportTask);

	}

	public FileUI getDetailedReportTask(String id, String fileType) throws FileNotFoundException {
		FileUI fileUI = new FileUI();

		String outputDirectory = coreProperties.getOutputDirectory();
		log.info("outputDirectory - " + outputDirectory);
		String lastDigitofId = id.substring(id.length() - 1);
		log.info("lastDigitofId - " + lastDigitofId);
		String filePath = outputDirectory + "/" + lastDigitofId + "/Task-" + id + "/Task-" + id + "." + fileType;
		log.info("filePath - " + filePath);
		fileUI.setFilePath(filePath);
		ReportTemplateUI reportTemplateUI = getReportNameByTaskId(Integer.valueOf(id));
		String fileName = reportTemplateUI.getName() + "-Task-" + id + "." + fileType;
		//String fileName = "Task-" + id + "." + fileType;
		log.info("fileName - " + fileName);
		fileUI.setFileName(fileName);
		InputStream is = new FileInputStream(filePath);
		fileUI.setRawFileStream(is);
		return fileUI;

	}

	public ReportTaskOutput getReportTaskData(String id) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ReportTaskOutput reportTaskOutput = new ReportTaskOutput();
		String outputDirectory = coreProperties.getOutputDirectory();
		log.info("outputDirectory - " + outputDirectory);
		String lastDigitofId = id.substring(id.length() - 1);
		log.info("lastDigitofId - " + lastDigitofId);
		String filePath = outputDirectory + "/" + lastDigitofId + "/Task-" + id + "/Task-" + id + ".txt";
		log.info("filePath - " + filePath);
		ArrayList<String> list = new ArrayList<String>();

		Files.lines(Paths.get(filePath)).forEach(line -> {
			//log.debug(line);
			list.add(line);
		});

		int count = 0;
		ArrayList<TableHeader> header = new ArrayList<TableHeader>();
		ArrayList<ReportData> data = new ArrayList<ReportData>();
		 int headerLength = 0; 
		for (String st : list) {
			String[] datacolumn = st.split("\\t");
           
			if (count == 0) {
				String fieldname = "column";
				headerLength = datacolumn.length;
				for (int index = 0; index < datacolumn.length; index++) {
					TableHeader tableHeader = new TableHeader();
					String value = datacolumn[index];
					tableHeader.setHeader(value);
					tableHeader.setField(fieldname + (index + 1));
					header.add(tableHeader);
				}
				
			} else {
				String fieldname = "Column";
				ReportData reportData = new ReportData();
				for (int index = 0; index < headerLength; index++) {
					
					int columnIndex = index + 1;
					Method setColumnMethod = ReportData.class.getMethod("set" + fieldname + columnIndex, String.class);
					if (index < datacolumn.length) {
						setColumnMethod.invoke(reportData, datacolumn[index]);
					} else {
						setColumnMethod.invoke(reportData, "");
						
					}
				}
				data.add(reportData);
			}
			++count;
		}
		reportTaskOutput.setHeader(header);
		reportTaskOutput.setData(data);
		return reportTaskOutput;
	}
}
