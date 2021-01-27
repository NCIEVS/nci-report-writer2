package gov.nih.nci.evs.reportwriter.core.service;
import gov.nih.nci.evs.reportwriter.core.configuration.*;
import gov.nih.nci.evs.reportwriter.formatter.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import gov.nih.nci.evs.reportwriter.core.model.evs.EvsConcept;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsVersionInfo;
import gov.nih.nci.evs.reportwriter.core.model.report.Report;
import gov.nih.nci.evs.reportwriter.core.model.report.ReportColumn;
import gov.nih.nci.evs.reportwriter.core.model.report.ReportRow;
import gov.nih.nci.evs.reportwriter.core.model.template.Template;
import gov.nih.nci.evs.reportwriter.core.model.template.TemplateColumn;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsSupportedAssociation;

//import gov.nih.nci.evs.reportwriter.core.util.RWUtils;
import gov.nih.nci.evs.reportwriter.core.util.*;

@Service
/**
 * Generates a report based on a report template file.
 *
 */
public class ReportWriter {

	private static final Logger log = LoggerFactory.getLogger(ReportWriter.class);

    @Autowired
    SparqlQueryManagerService sparqlQueryManagerService;

    @Autowired
    RWUtils rwUtils;

    public ReportWriter(SparqlQueryManagerService sparqlQueryManagerService) {
		this.sparqlQueryManagerService = sparqlQueryManagerService;
		this.rwUtils = new RWUtils(sparqlQueryManagerService);
	}

	public void setAssociationLabel2CodeHashMap(String restURL, String namedGraph) {
		HashMap associationLabel2CodeHashMap = new HashMap();
		List<EvsSupportedAssociation> list = this.sparqlQueryManagerService.getEvsSupportedAssociations(namedGraph, restURL);
		for (int i=0; i<list.size(); i++) {
			EvsSupportedAssociation e = list.get(i);
			associationLabel2CodeHashMap.put(e.getName(), e.getCode());
		}
		rwUtils.setAssociationLabel2CodeHashMap(associationLabel2CodeHashMap);
	}

	public String run_report(List codes, String templateFile, String outputFile, String conceptFile, String restURL, String namedGraph) {
		setAssociationLabel2CodeHashMap(restURL, namedGraph);
		log.info("runReport using templateFile: " + templateFile);
		log.info("restURL: " + restURL);
		log.info("namedGraph: " + namedGraph);

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Template reportTemplate = null;
        PrintWriter logFile = null;
        try {
            reportTemplate = mapper.readValue(new File(templateFile), Template.class);
            String logOutputFile = outputFile + ".log";
            logFile = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(logOutputFile)),StandardCharsets.UTF_8),true);
        } catch (Exception ex) {
			log.info("Report generation using " + templateFile + " failed.");
        	System.err.println(ex);
        	return "failure";
        }

        log.info("Template Information");
        log.info("********************");
        log.info(reportTemplate.toString());
        System.out.println("Template Information");
        System.out.println("********************");
        System.out.println(reportTemplate.toString());
        logFile.println("Started: " + LocalDateTime.now());
        logFile.println("********************************");
        logFile.println("");
        logFile.println("Template Information");
        logFile.println("********************************");

        EvsVersionInfo evsVersionInfo = getEvsVersionInfo(restURL, namedGraph);
        logFile.println("Version: " + evsVersionInfo.getVersion());
        logFile.println(reportTemplate.toString());

        // The conceptHash is used to improve performance, especially in the cases for reports that
        // are looking for parents concepts.  By first looking in the hash for the concept, time
        // can be saved by not repeating the SPARQL queries.
        HashMap <String,EvsConcept> conceptHash = new <String,EvsConcept> HashMap();

        int currentLevel = 0;
        int maxLevel = reportTemplate.getLevel().intValue();

        String templateType = reportTemplate.getType();
        Report reportOutput = new Report();

        String associationName = reportTemplate.getAssociation();
        boolean sourceOf = true;
        if (templateType.equals("Inverse Association")) {
			sourceOf = false;
		}

        rwUtils.processSubset(codes, reportOutput, conceptHash, reportTemplate.getColumns(), logFile, namedGraph, restURL);

        /*
         * Print out tab separated and Excel output files
         */
        String outputFileText = outputFile + ".txt";
        String outputFileExcel = outputFile + ".xls";
        PrintWriter pw = null;
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("report");
        sheet.createFreezePane(0, 1);
        int rowIndex = 0;

        Font headerFont = wb.createFont();
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        CellStyle headerStyle = createBorderedStyle(wb);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(outputFileText)),StandardCharsets.UTF_8),true);

			// Added May 10, 2018  to output the OWL version information
		   	Row excelRow = sheet.createRow(rowIndex++);
         	Cell versionCell = excelRow.createCell(0);
	        versionCell.setCellValue("Version: " + evsVersionInfo.getVersion());
	        versionCell.setCellStyle(headerStyle);
         	Cell graphCell = excelRow.createCell(1);
	        graphCell.setCellValue("NamedGraph: " + namedGraph);
	        graphCell.setCellStyle(headerStyle);
	        Cell databaseCell = excelRow.createCell(2);
	        databaseCell.setCellValue("REST URL: " + restURL);
	        databaseCell.setCellStyle(headerStyle);

			ArrayList <String> columnHeadings = new ArrayList <String>();
	       	excelRow = sheet.createRow(rowIndex++);
	       	int cellIndex = 0;
        	for (TemplateColumn templateColumn: reportTemplate.getColumns()) {
		       	columnHeadings.add(templateColumn.getLabel());
		       	Cell cell = excelRow.createCell(cellIndex++);
		        cell.setCellValue(templateColumn.getLabel());
		        cell.setCellStyle(headerStyle);
        	}
            //pw.write("Version: " + evsVersionInfo.getVersion() + "\n");
            pw.write(String.join("\t",columnHeadings) + "\n");

            /*
             * Sort the rows based on the template sortColumn specification
             */
            Integer sortColumnTemp = 0;
            if (reportTemplate.getSortColumn() != null) {
            	sortColumnTemp = reportTemplate.getSortColumn() - 1;
            }
            final Integer sortColumn = sortColumnTemp;
            ArrayList <ReportRow> reportRows = reportOutput.getRows();
            //KLO 10272020
            reportRows = removeDuplicates(reportRows);

            Collections.sort(reportRows,(row1, row2) -> row1.getColumns().get(sortColumn).getValue().compareTo(row2.getColumns().get(sortColumn).getValue()));

            for (ReportRow row: reportRows) {
            	excelRow = sheet.createRow(rowIndex++);
        	    ArrayList <String> values = new ArrayList <String>();
        	    cellIndex = 0;
        	    for (ReportColumn column: row.getColumns()) {
                    values.add(column.getValue());
                    Cell cell =  excelRow.createCell(cellIndex++);
		            cell.setCellValue(column.getValue());
        	    }
                pw.write(String.join("\t",values) + "\n");
            }

            for (int i = 0; i < reportTemplate.getColumns().size(); i++) {
            	sheet.autoSizeColumn(i);
            }

            OutputStream fos = new FileOutputStream(new File(outputFileExcel));
            wb.write(fos);
    	    fos.close();
        } catch (FileNotFoundException e) {
        	System.err.println("File Not Found Exception");
        	return "failure";
        } catch (IOException e) {
        	System.err.println("IOException");
        	return "failure";
        } finally {
    	    if (pw != null) {
    		    pw.close();
    	    }
        }

        logFile.println("");
        logFile.println("********************************");
        logFile.println("Completed: " + LocalDateTime.now());
		logFile.close();

		return "success";
	}

	public String runReport(String templateFile, String outputFile, String conceptFile, String restURL, String namedGraph) {
		setAssociationLabel2CodeHashMap(restURL, namedGraph);
		log.info("runReport using templateFile: " + templateFile);
		log.info("restURL: " + restURL);
		log.info("namedGraph: " + namedGraph);

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Template reportTemplate = null;
        PrintWriter logFile = null;
        try {
            reportTemplate = mapper.readValue(new File(templateFile), Template.class);

            String rootConceptCode = reportTemplate.getRootConceptCode();
            if (rootConceptCode != null) {
				rootConceptCode = rootConceptCode.trim();
				if (rootConceptCode.compareToIgnoreCase("Not specified") == 0 ||
					rootConceptCode.compareToIgnoreCase("Not applicable") == 0 ||
					rootConceptCode.compareToIgnoreCase("NA") == 0) {
					reportTemplate.setRootConceptCode(null);
				}
			} else {
				String datafile = reportTemplate.getType();
				List codes = ReportWriterRunner.readFile(datafile);
				return run_report(codes, templateFile, outputFile, conceptFile, restURL, namedGraph);
			}
			log.info("rootConceptCode: " + rootConceptCode);

            String logOutputFile = outputFile + ".log";
            logFile = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(logOutputFile)),StandardCharsets.UTF_8),true);
        } catch (Exception ex) {
			log.info("Report generation using " + templateFile + " failed.");
        	System.err.println(ex);
        	return "failure";
        }

        if (isCDISCReport(reportTemplate)) {
			return new SpecialReportWriter(sparqlQueryManagerService).runSpecialReport(templateFile, outputFile, conceptFile, restURL, namedGraph);
		}

        log.info("Template Information");
        log.info("********************");
        log.info(reportTemplate.toString());
        System.out.println("Template Information");
        System.out.println("********************");
        System.out.println(reportTemplate.toString());
        logFile.println("Started: " + LocalDateTime.now());
        logFile.println("********************************");
        logFile.println("");
        logFile.println("Template Information");
        logFile.println("********************************");

        EvsVersionInfo evsVersionInfo = getEvsVersionInfo(restURL, namedGraph);
        logFile.println("Version: " + evsVersionInfo.getVersion());
        logFile.println(reportTemplate.toString());



        // The conceptHash is used to improve performance, especially in the cases for reports that
        // are looking for parents concepts.  By first looking in the hash for the concept, time
        // can be saved by not repeating the SPARQL queries.
        HashMap <String,EvsConcept> conceptHash = new <String,EvsConcept> HashMap();

        int currentLevel = 0;
        int maxLevel = reportTemplate.getLevel().intValue();

        String templateType = reportTemplate.getType();
        Report reportOutput = new Report();

        String associationName = reportTemplate.getAssociation();
        boolean sourceOf = true;
        if (templateType.equals("Inverse Association")) {
			sourceOf = false;
		}
        if (templateType.equals("Association") || templateType.equals("Inverse Association")) {
        	String rootConceptCode = reportTemplate.getRootConceptCode();
        	System.out.println("(*) rootConceptCode: " + rootConceptCode);
            EvsConcept rootConcept = null;
            if (rootConceptCode != null) {
            	rootConcept = sparqlQueryManagerService.getEvsConceptDetailShort(rootConceptCode, namedGraph, restURL);
			}
			System.out.println("(*) rootConceptLabel: " + rootConcept.getLabel());

        	if (reportTemplate.getAssociation().equals("Concept_In_Subset") && sourceOf) {
                //rwUtils.processConceptInSubset(reportOutput, rootConcept, conceptHash, reportTemplate.getColumns(), logFile, namedGraph, restURL);

                //rwUtils.processConceptInSubset(reportOutput, rootConcept, conceptHash, reportTemplate.getColumns(), currentLevel, maxLevel, logFile, namedGraph, restURL);
                //rwUtils.processConceptSubclasses(reportOutput, rootConcept, conceptHash, reportTemplate.getColumns(), currentLevel, maxLevel, logFile,namedGraph,restURL);

                rwUtils.processConceptInSubset(reportOutput, rootConcept, conceptHash, reportTemplate.getColumns(), currentLevel, maxLevel, logFile, namedGraph, restURL);
                if (maxLevel > 1) {
                	rwUtils.processConceptSubclasses(reportOutput, rootConcept, conceptHash, reportTemplate.getColumns(), currentLevel, maxLevel, logFile,namedGraph,restURL);
			    }

        	} else if (reportTemplate.getAssociation().equals("Subclass") && sourceOf) {
                rwUtils.processConceptSubclassesOnly(reportOutput, rootConcept, conceptHash, reportTemplate.getColumns(), currentLevel, maxLevel, logFile, namedGraph,restURL);
        	} else {
                rwUtils.processAssociatedConcepts(reportOutput, rootConcept, conceptHash, reportTemplate.getColumns(), logFile, namedGraph, restURL, associationName, sourceOf);
        	}

        } else if (templateType.equals("ConceptList")) {
            rwUtils.processConceptList(reportOutput, conceptHash, reportTemplate.getColumns(), conceptFile,logFile,namedGraph,restURL);
        } else {
        	System.err.println("Invalid Template Type: " + templateType);
        	return "failure";
        }

        /*
         * Print out tab separated and Excel output files
         */
        String outputFileText = outputFile + ".txt";
        String outputFileExcel = outputFile + ".xls";
        PrintWriter pw = null;
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("report");
        sheet.createFreezePane(0, 1);
        int rowIndex = 0;

        Font headerFont = wb.createFont();
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        CellStyle headerStyle = createBorderedStyle(wb);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(outputFileText)),StandardCharsets.UTF_8),true);

			// Added May 10, 2018  to output the OWL version information
		   	Row excelRow = sheet.createRow(rowIndex++);
         	Cell versionCell = excelRow.createCell(0);
	        versionCell.setCellValue("Version: " + evsVersionInfo.getVersion());
	        versionCell.setCellStyle(headerStyle);
         	Cell graphCell = excelRow.createCell(1);
	        graphCell.setCellValue("NamedGraph: " + namedGraph);
	        graphCell.setCellStyle(headerStyle);
	        Cell databaseCell = excelRow.createCell(2);
	        databaseCell.setCellValue("REST URL: " + restURL);
	        databaseCell.setCellStyle(headerStyle);

			ArrayList <String> columnHeadings = new ArrayList <String>();
	       	excelRow = sheet.createRow(rowIndex++);
	       	int cellIndex = 0;
        	for (TemplateColumn templateColumn: reportTemplate.getColumns()) {
		       	columnHeadings.add(templateColumn.getLabel());
		       	Cell cell = excelRow.createCell(cellIndex++);
		        cell.setCellValue(templateColumn.getLabel());
		        cell.setCellStyle(headerStyle);
        	}
            //pw.write("Version: " + evsVersionInfo.getVersion() + "\n");
            pw.write(String.join("\t",columnHeadings) + "\n");

            /*
             * Sort the rows based on the template sortColumn specification
             */
            Integer sortColumnTemp = 0;
            if (reportTemplate.getSortColumn() != null) {
            	sortColumnTemp = reportTemplate.getSortColumn() - 1;
            }
            final Integer sortColumn = sortColumnTemp;
            ArrayList <ReportRow> reportRows = reportOutput.getRows();
            //KLO 10272020
            reportRows = removeDuplicates(reportRows);

            Collections.sort(reportRows,(row1, row2) -> row1.getColumns().get(sortColumn).getValue().compareTo(row2.getColumns().get(sortColumn).getValue()));

            for (ReportRow row: reportRows) {
            	excelRow = sheet.createRow(rowIndex++);
        	    ArrayList <String> values = new ArrayList <String>();
        	    cellIndex = 0;
        	    for (ReportColumn column: row.getColumns()) {
                    values.add(column.getValue());
                    Cell cell =  excelRow.createCell(cellIndex++);
		            cell.setCellValue(column.getValue());
        	    }
                pw.write(String.join("\t",values) + "\n");
            }

            for (int i = 0; i < reportTemplate.getColumns().size(); i++) {
            	sheet.autoSizeColumn(i);
            }

            OutputStream fos = new FileOutputStream(new File(outputFileExcel));
            wb.write(fos);
    	    fos.close();
        } catch (FileNotFoundException e) {
        	System.err.println("File Not Found Exception");
        	return "failure";
        } catch (IOException e) {
        	System.err.println("IOException");
        	return "failure";
        } finally {
    	    if (pw != null) {
    		    pw.close();
    	    }
        }

        logFile.println("");
        logFile.println("********************************");
        logFile.println("Completed: " + LocalDateTime.now());
		logFile.close();

		return "success";

	}

    private ArrayList<ReportRow> removeDuplicates(ArrayList<ReportRow> reportRows) {
		ArrayList<ReportRow> list = new ArrayList<ReportRow>();
		HashSet hset = new HashSet();
		for (int i=0; i<reportRows.size(); i++) {
			ReportRow row = (ReportRow) reportRows.get(i);
			String value = row.getValue();
			if (!hset.contains(value)) {
				list.add(row);
				hset.add(value);
			}
		}
		return list;
	}

	private static CellStyle createBorderedStyle(Workbook wb){
	        BorderStyle thin = BorderStyle.THIN;
	        short black = IndexedColors.BLACK.getIndex();

	        CellStyle style = wb.createCellStyle();
	        style.setBorderRight(thin);
	        style.setRightBorderColor(black);
	        style.setBorderBottom(thin);
	        style.setBottomBorderColor(black);
	        style.setBorderLeft(thin);
	        style.setLeftBorderColor(black);
	        style.setBorderTop(thin);
	        style.setTopBorderColor(black);
	        return style;
    }

	public EvsVersionInfo getEvsVersionInfo(String restURL,String namedGraph) {
		if (sparqlQueryManagerService == null) {
			log.info("sparqlQueryManagerService == null???");
			System.out.println("sparqlQueryManagerService == null???");
		} else {
			System.out.println("Calling sparqlQueryManagerService getEvsVersionInfo...");
			return sparqlQueryManagerService.getEvsVersionInfo(namedGraph,restURL);
		}
		return null;
	}

	public List <String> getNamedGraphs(String restURL) {
		return sparqlQueryManagerService.getNamedGraphs(restURL);
	}

    public boolean isCDISCReport(Template template) {
        System.out.println(template.toString());
        List<TemplateColumn> list = template.getColumns();
        for (int i=0; i<list.size(); i++) {
			TemplateColumn col = (TemplateColumn) list.get(i);
            if (col.getLabel().compareTo("CDISC Submission Value") == 0) {
				return true;
			}
		}
		return false;
	}
}
