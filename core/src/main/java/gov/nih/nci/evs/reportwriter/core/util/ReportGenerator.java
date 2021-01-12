package gov.nih.nci.evs.reportwriter.core.util;
import gov.nih.nci.evs.reportwriter.core.service.*;
import gov.nih.nci.evs.reportwriter.core.configuration.*;

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
import gov.nih.nci.evs.reportwriter.core.util.*;

/**
 * <!-- LICENSE_TEXT_START -->
 * Copyright 2021 MSC. This software was developed in conjunction
 * with the National Cancer Institute, and so to the extent government
 * employees are co-authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *   1. Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the disclaimer of Article 3,
 *      below. Redistributions in binary form must reproduce the above
 *      copyright notice, this list of conditions and the following
 *      disclaimer in the documentation and/or other materials provided
 *      with the distribution.
 *   2. The end-user documentation included with the redistribution,
 *      if any, must include the following acknowledgment:
 *      "This product includes software developed by MSC and the National
 *      Cancer Institute."   If no such end-user documentation is to be
 *      included, this acknowledgment shall appear in the software itself,
 *      wherever such third-party acknowledgments normally appear.
 *   3. The names "The National Cancer Institute" and "MSC" must
 *      not be used to endorse or promote products derived from this software.
 *   4. This license does not authorize the incorporation of this software
 *      into any third party proprietary programs. This license does not
 *      authorize the recipient to use any trademarks owned by either NCI
 *      or MSC
 *   5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED
 *      WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *      OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE
 *      DISCLAIMED. IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE,
 *      MSC, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT,
 *      INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 *      BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *      LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *      CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 *      LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 *      ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *      POSSIBILITY OF SUCH DAMAGE.
 * <!-- LICENSE_TEXT_END -->
 */

/**
 * @author EVS Team
 * @version 1.0
 *
 * Modification history:
 *     Initial implementation kim.ong@nih.gov
 *
 */

/**
 * Generates a report based on a report template file and a text file.
 *
 */
public class ReportGenerator {

	private static final Logger log = LoggerFactory.getLogger(ReportGenerator.class);

    public ReportGenerator() {
	}

	public String generate(String templateFile, String rw2textfile, String restURL, String namedGraph) {
        Report reportOutput = ReportLoader.load(rw2textfile);
        int n = rw2textfile.lastIndexOf(".");
        String outputFile = rw2textfile.substring(0, n);
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

        /*
         * Print out tab separated and Excel output files
         */
        String outputFileText = outputFile + "_echo.txt";
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
		   	Row excelRow = sheet.createRow(rowIndex++);
         	//Cell versionCell = excelRow.createCell(0);
	        //versionCell.setCellValue("Version: " + evsVersionInfo.getVersion());
	        //versionCell.setCellStyle(headerStyle);
         	Cell graphCell = excelRow.createCell(0);
	        graphCell.setCellValue("NamedGraph: " + namedGraph);
	        graphCell.setCellStyle(headerStyle);
	        Cell databaseCell = excelRow.createCell(1);
	        databaseCell.setCellValue("REST URL: " + restURL);
	        databaseCell.setCellStyle(headerStyle);

			ArrayList <String> columnHeadings = new ArrayList <String>();
	       	excelRow = sheet.createRow(rowIndex++);
	       	int cellIndex = 0;

			columnHeadings.add("Row");
			Cell cell = excelRow.createCell(cellIndex++);
			cell.setCellValue("Row");
			cell.setCellStyle(headerStyle);

        	for (TemplateColumn templateColumn: reportTemplate.getColumns()) {
		       	columnHeadings.add(templateColumn.getLabel());
		       	cell = excelRow.createCell(cellIndex++);
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

            for (ReportRow row: reportRows) {
            	excelRow = sheet.createRow(rowIndex++);
        	    ArrayList <String> values = new ArrayList <String>();
        	    cellIndex = 0;
        	    for (ReportColumn column: row.getColumns()) {
                    values.add(column.getValue());
                    cell =  excelRow.createCell(cellIndex++);
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
        	e.printStackTrace();
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

		System.out.println("Output file " + outputFileExcel + " generated.");

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
		return new ReportWriterRunner().getEvsVersionInfo(namedGraph, restURL);
	}

	public static void main(String[] args) {
		ReportGenerator generator = new ReportGenerator();
		String templateFile = args[0];
		String rw2textfile = args[1];
		String restURL = args[2];
		String namedGraph = args[3];
		String retstr = generator.generate(templateFile, rw2textfile, restURL, namedGraph);
	}
}
