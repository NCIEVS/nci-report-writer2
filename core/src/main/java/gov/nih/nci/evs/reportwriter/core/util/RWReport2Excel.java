package gov.nih.nci.evs.reportwriter.core.util;

import java.io.*;
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
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsSupportedRole;


@Service
/**
 * Generates a report based on a report template file.
 *
 */
public class RWReport2Excel {
	private static final Logger log = LoggerFactory.getLogger(RWReport2Excel.class);

    public RWReport2Excel() {

	}

	public static Vector readFile(String filename)
	{
		Vector v = new Vector();
		try {
			BufferedReader in = new BufferedReader(
			   new InputStreamReader(
						  new FileInputStream(filename), "UTF8"));
			String str;
			while ((str = in.readLine()) != null) {
				v.add(str);
			}
            in.close();
		} catch (Exception ex) {
            ex.printStackTrace();
		}
		return v;
	}

    public static Vector parseData(String line, char delimiter) {
		if(line == null) return null;
		Vector w = new Vector();
		StringBuffer buf = new StringBuffer();
		for (int i=0; i<line.length(); i++) {
			char c = line.charAt(i);
			if (c == delimiter) {
				w.add(buf.toString());
				buf = new StringBuffer();
			} else {
				buf.append(c);
			}
		}
		w.add(buf.toString());
		return w;
	}

    public static boolean checkIfFileExists(String filename) {
		String currentDir = System.getProperty("user.dir");
		File f = new File(currentDir + "\\" + filename);
		if(f.exists() && !f.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}

	public Report loadReport(String outputFileText) {
         Vector data_vec = readFile(outputFileText);
         return loadReport(data_vec);
	}

	public Report loadReport(Vector data_vec) {
		 Report report = new Report();
		 ArrayList <ReportRow> rows = new ArrayList<ReportRow>();
		 ReportRow row = null;
         String heading = (String) data_vec.elementAt(0);
         Vector labels = parseData(heading, '\t');
		 List<ReportColumn> columns = new ArrayList<ReportColumn>();
		 row = new ReportRow();
		 for (int j=0; j<labels.size(); j++) {
			 String value = (String) labels.elementAt(j);
			 columns.add(new ReportColumn((String) labels.elementAt(j), value));
		 }
		 row.setColumns(columns);
		 rows.add(row);
         for (int i=1; i<data_vec.size(); i++) {
			 String line = (String) data_vec.elementAt(i);
			 Vector values = parseData(line, '\t');
			 columns = new ArrayList<ReportColumn>();
			 row = new ReportRow();
			 for (int j=0; j<values.size(); j++) {
				 String value = (String) values.elementAt(j);
				 columns.add(new ReportColumn((String) labels.elementAt(j), value));
			 }
			 row.setColumns(columns);
			 rows.add(row);
		 }
		 report.setRows(rows);
         return report;
	}

	public String generate(String restURL, String namedGraph, String version, String outputFileText) {
		int n = outputFileText.lastIndexOf(".");
		String outputFile = outputFileText.substring(0, n);
		String templateFile = outputFile + ".template";

        boolean exists = checkIfFileExists(templateFile);
        if (!exists) {
			System.out.println("Unable to find the template file - " + templateFile);
			return "failure";
		}

		Report reportOutput = loadReport(outputFileText);
	    return generate(restURL, namedGraph, version,
	                    templateFile, reportOutput, outputFile);

	}

	public String generate(String restURL, String namedGraph, String version,
	                       String templateFile, Report reportOutput, String outputFile) {
        /*
         * Print out tab separated and Excel output files
         */

        Template reportTemplate = null;
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
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

        String outputFileExcel = outputFile + ".xls";
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

        ArrayList <String> columnHeadings = new ArrayList <String>();
        ArrayList <ReportRow> reportRows = reportOutput.getRows();

		try {
		   	Row excelRow = sheet.createRow(rowIndex++);
         	Cell versionCell = excelRow.createCell(0);
	        versionCell.setCellValue("Version: " + version);
	        versionCell.setCellStyle(headerStyle);
         	Cell graphCell = excelRow.createCell(1);
	        graphCell.setCellValue("NamedGraph: " + namedGraph);
	        graphCell.setCellStyle(headerStyle);
	        Cell databaseCell = excelRow.createCell(2);
	        databaseCell.setCellValue("REST URL: " + restURL);
	        databaseCell.setCellStyle(headerStyle);

			excelRow = sheet.createRow(rowIndex++);
	       	int cellIndex = 0;

        	for (TemplateColumn templateColumn: reportTemplate.getColumns()) {
		       	columnHeadings.add(templateColumn.getLabel());
		       	Cell cell = excelRow.createCell(cellIndex++);
		        cell.setCellValue(templateColumn.getLabel());
		        cell.setCellStyle(headerStyle);
        	}

            /*
             * Sort the rows based on the template sortColumn specification
             */
            Integer sortColumnTemp = 0;
            if (reportTemplate.getSortColumn() != null) {
            	sortColumnTemp = reportTemplate.getSortColumn() - 1;
            }
            final Integer sortColumn = sortColumnTemp;
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
		for (int i=1; i<reportRows.size(); i++) {
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

    public static void main(String[] args) {
		long ms = System.currentTimeMillis();
		String restURL = args[0];
		String namedGraph = args[1];
		String username = args[2];
		String password = args[3];

        String version = "21.03b";
        String textOutputFile = "PCDC_EWS_Terminology.txt";
	    String retval = new RWReport2Excel().generate(restURL, namedGraph, version, textOutputFile);
	    System.out.println(retval);
	}
}
