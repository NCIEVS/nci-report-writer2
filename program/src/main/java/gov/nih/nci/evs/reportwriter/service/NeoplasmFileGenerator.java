package gov.nih.nci.evs.reportwriter.core.service;

import gov.nih.nci.evs.reportwriter.core.util.*;
import gov.nih.nci.evs.restapi.util.*;

import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.net.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <!-- LICENSE_TEXT_START -->
 * Copyright 2011, MSC. This software was developed in conjunction
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
 *   3. The names "The National Cancer Institute", "NCI" and "MSC" must
 *      not be used to endorse or promote products derived from this software.
 *   4. This license does not authorize the incorporation of this software
 *      into any third party proprietary programs. This license does not
 *      authorize the recipient to use any trademarks owned by either NCI
 *      or MSC.
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
 *     Initial implementation ongki@nih.gov
 *
 */

public class NeoplasmFileGenerator {

	private static final Logger log = LoggerFactory.getLogger(NeoplasmFileGenerator.class);

    public static String NCI_THESAURUS_OWL = "ThesaurusInferred_forTS.owl";
    public static String NEOPLASM_CORE_TERMINOLOGY = "Neoplasm_Core_Terminology";
    public static String NEOPLASM_CORE_RELS_NCIT_MOLECULAR = "Neoplasm_Core_Rels_NCIt_Molecular";
    public static String NEOPLASM_CORE_HIERARCHY_FILE = "Neoplasm_Core_Hierarchy.txt";
    public static String NEOPLASM_CORE_HIERARCHY_By_NEOPLASTIC_STATUS_FILE = "Neoplasm_Core_Hierarchy_By_Neoplastic_Status.txt";
    public static String NEOPLASM_CORE_HIERARCHY_HTML = "Neoplasm_Core_Hierarchy.html";
    public static String NEOPLASM_CORE_HIERARCHY_By_NEOPLASTIC_STATUS_HTML = "Neoplasm_Core_Hierarchy_By_Neoplastic_Status.html";
    public static String NEOPLASM_CORE_MAPPING_NCIM_TERMS_XLS = "Neoplasm_Core_Mappings_NCIm_Terms.xls";
    public static String NEOPLASM_CORE_MAPPING_NCIM_TERMS_XLSX = "Neoplasm_Core_Mappings_NCIm_Terms.xlsx";

    public static String NEOPLASM_FTP_SITE_URL = "https://evs.nci.nih.gov/ftp1/NCI_Thesaurus/Neoplasm/";

    private static String SUMMARY_DATA_AUTHOR = "SUMMARY_DATA_AUTHOR";
    private static String SUMMARY_DATA_KEYWORDS = "SUMMARY_DATA_KEYWORDS";
    private static String SUMMARY_DATA_TITLE = "SUMMARY_DATA_TITLE";
    private static String SUMMARY_DATA_SUBJECT = "SUMMARY_DATA_SUBJECT";

    public static void generateHierarchyFiles(String textfile, String owlfile, String ncit_version) {
        NeoplasmHierarchyUtils util = new NeoplasmHierarchyUtils(textfile, owlfile);
        Vector codes = new Vector();
        codes.add("C4741");
        codes.add("C3263");
        Vector v = util.getASCIITree(codes, util.get_code_set());
        Utils.saveToFile(NEOPLASM_CORE_HIERARCHY_FILE, v);
        //v = util.appendNeoplasticStatus(v);
        //Utils.saveToFile(NEOPLASM_CORE_HIERARCHY_PLUS_FILE, v);
        int lines_to_skip = 0;
        String title = "NCIt Neoplasm Core Hierarchy by Morphology and Site";
        Vector contents = new Vector();
        contents.add("Neoplasm by Site");
        contents.add("Neoplasm by Morphology");
 	    ascii2HTMLTree(NEOPLASM_CORE_HIERARCHY_FILE, ncit_version, lines_to_skip, title, contents);

        codes = new Vector();
        codes.add("C3677");
        codes.add("C9305");
        codes.add("C3646");
        v = util.getASCIITree(codes, util.get_code_set());
        Utils.saveToFile(NEOPLASM_CORE_HIERARCHY_By_NEOPLASTIC_STATUS_FILE, v);
        //v = util.appendNeoplasticStatus(v);
        //Utils.saveToFile(NEOPLASM_CORE_HIERARCHY_By_NEOPLASTIC_STATUS_PLUS_FILE, v);
        title = "NCIt Neoplasm Core Hierarchy by Neoplastic Status";
        contents = new Vector();
        contents.add("Malignant Neoplasm");
        contents.add("Benign Neoplasm");
        contents.add("Neoplasm of Uncertain Malignant Potential");
 	    ascii2HTMLTree(NEOPLASM_CORE_HIERARCHY_By_NEOPLASTIC_STATUS_FILE, ncit_version, lines_to_skip, title, contents);
	}

    public static void generateNeoplasmCoreRelationshipFiles(String textfile, String owlfile, String sheetName) {
		NeoplasmCoreRelationships neoplasmCoreRelationships = new NeoplasmCoreRelationships(textfile, owlfile);

		String inputfile = NEOPLASM_CORE_RELS_NCIT_MOLECULAR + ".txt";
		System.out.println("getRestrictions...");
		neoplasmCoreRelationships.getRestrictions(inputfile);
		System.out.println(inputfile + " generated.");

		System.out.println("generateCSVFile...");
		//neoplasmCoreRelationships.generateCSVFile(inputfile, csvfile);
		String csvfile = gov.nih.nci.evs.reportwriter.core.util.ExcelReadWriteUtils.delimited2CSV(inputfile, '|');
		System.out.println(csvfile + " generated.");

		System.out.println("convertToExcel...");
		try {
			char delim = '|';
			String excelfile = gov.nih.nci.evs.reportwriter.core.util.ExcelReadWriteUtils.text2XLS(inputfile, delim, sheetName);
			System.out.println(excelfile + " generated.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//neoplasmCoreRelationships.convertToExcel(csvfile);
	}

	public static void downloadExcel(String uri) {
		URL u;
		InputStream is = null;
		int n = uri.lastIndexOf("/");
		String outputfile = null;
		if (n != -1) {
			outputfile = uri.substring(n+1, uri.length());
		}
		System.out.println(outputfile);
		if (outputfile == null) return;
		try {
			u = new URL(uri);
			is = u.openStream();
			byte[] buffer = new byte[8 * 1024];
			try {
				OutputStream output = new FileOutputStream(outputfile);
				try {
					int bytesRead;
					while ((bytesRead = is.read(buffer)) != -1) {
						output.write(buffer, 0, bytesRead);
					}
				} finally {
				output.close();
				}
			} finally {
				is.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


 	public static void ascii2HTMLTree(String asciiTree, String ncit_version, int lines_to_skip, String title, Vector contents) {
		ASCII2HTMLTreeConverter generator = new ASCII2HTMLTreeConverter();
		int n = asciiTree.lastIndexOf(".");
        String outputfile = asciiTree.substring(0, n) + ".html";
        String label = title;
        generator.run(asciiTree, outputfile, lines_to_skip, title, label, ncit_version, contents);
	}


	public static void main(String[] args) {
		boolean runReportOnly = true;
		String version = null;

		System.out.println("Step 1: Download NCI Thesaurus from the ftp site.");
		if (!gov.nih.nci.evs.restapi.util.Utils.checkIfFileExists(NCI_THESAURUS_OWL)) {
			gov.nih.nci.evs.reportwriter.core.util.NCItDownload.download();
		} else {
			System.out.println(NCI_THESAURUS_OWL + " exists.");
		}
		String owlfile = NCI_THESAURUS_OWL;
		OWLScanner scanner = new OWLScanner(owlfile);
		version = scanner.extractVersion();    //sheetNames.get(0);
		System.out.println("OWL version: " + version);
		scanner.get_owl_vec().clear();

		System.out.println("Step 2: Generate NCI Neoplasm Terminology report.");
		long ms = System.currentTimeMillis();
		String templateFile = args[0];
		System.out.println("templateFile: " + templateFile);
		int n = templateFile.lastIndexOf(".");
		String textfile = NEOPLASM_CORE_TERMINOLOGY + ".txt";
		System.out.println("textfile: " + textfile);

		if (!gov.nih.nci.evs.restapi.util.Utils.checkIfFileExists(textfile)) {
			String conceptFile = null;
			String restURL = args[1];
			System.out.println("restURL: " + restURL);
			String namedGraph = args[2];
			System.out.println("namedGraph: " + namedGraph);
			List codes = null;
			String str = null;
			SparqlQueryManagerService sparqlQueryManagerService = SpringUtils.createSparqlQueryManagerService();
			str = new ReportWriter(sparqlQueryManagerService).runReport(templateFile, NEOPLASM_CORE_TERMINOLOGY, conceptFile, restURL, namedGraph);
		} else {
			System.out.println(textfile + " exists.");
		}

		if (runReportOnly) {
			System.exit(0);
		}

		String csvfile = gov.nih.nci.evs.reportwriter.core.util.ExcelReadWriteUtils.tabDelimited2CSV(textfile);
		System.out.println(csvfile + " generated.");

		String xlsfile = NEOPLASM_CORE_TERMINOLOGY + ".xls";
		List<String> sheetNames = gov.nih.nci.evs.reportwriter.core.util.ExcelReadWriteUtils.getXLSSheetNames(xlsfile);
		try {
			String excelFileName = gov.nih.nci.evs.reportwriter.core.util.ExcelReadWriteUtils.writeXLSXFile(textfile, '\t', sheetNames.get(0));
			System.out.println(excelFileName + " generated.");
			System.out.println("Total NCI Neoplasm Terminology report generation run time (ms): " + (System.currentTimeMillis() - ms));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

        ms = System.currentTimeMillis();
        System.out.println("Step 3: Generate NCI Neoplasm Relationship reports.");
        generateNeoplasmCoreRelationshipFiles(textfile, owlfile, version);
		System.out.println("Total NCI Neoplasm Relationship report generation run time (ms): " + (System.currentTimeMillis() - ms));

        ms = System.currentTimeMillis();
        System.out.println("Step 4: Generate NCI Neoplasm Hierarchy reports.");
        generateHierarchyFiles(textfile, owlfile, version);

		CUIAndStatusAppender appender = new CUIAndStatusAppender(owlfile);

		String inputfile = NEOPLASM_CORE_HIERARCHY_FILE;
		String outputfile = appender.appendCUIAndStatus(inputfile);
		System.out.println("Output file " + outputfile + " generated.");

		inputfile = NEOPLASM_CORE_HIERARCHY_HTML;
		outputfile = appender.appendCUIAndStatus(inputfile);
		System.out.println("Output file " + outputfile + " generated.");

		inputfile = NEOPLASM_CORE_HIERARCHY_By_NEOPLASTIC_STATUS_FILE;
		outputfile = appender.appendCUIAndStatus(inputfile);
		System.out.println("Output file " + outputfile + " generated.");

		inputfile = NEOPLASM_CORE_HIERARCHY_By_NEOPLASTIC_STATUS_HTML;
		outputfile = appender.appendCUIAndStatus(inputfile);
		System.out.println("Output file " + outputfile + " generated.");
		System.out.println("Total NCI Neoplasm Hierarchy report generation run time (ms): " + (System.currentTimeMillis() - ms));

		if (!gov.nih.nci.evs.restapi.util.Utils.checkIfFileExists(NEOPLASM_CORE_MAPPING_NCIM_TERMS_XLS)) {
			System.out.println(NEOPLASM_CORE_MAPPING_NCIM_TERMS_XLS + " does not exists.");
			System.out.println("Downloading " + NEOPLASM_CORE_MAPPING_NCIM_TERMS_XLS + " ..." );
			downloadExcel(NEOPLASM_FTP_SITE_URL + NEOPLASM_CORE_MAPPING_NCIM_TERMS_XLS);

		} else {
			System.out.println(NEOPLASM_CORE_MAPPING_NCIM_TERMS_XLS + " exists.");
		}

		String excelfile = NEOPLASM_CORE_MAPPING_NCIM_TERMS_XLS;
		n = excelfile.lastIndexOf(".");

		String xlsxfile = excelfile.substring(0, n) + ".xlsx";
		String asciitree = excelfile.substring(0, n) + ".txt";
		String htmlfile = excelfile.substring(0, n) + ".html";

		try {
			XLStoXLSX.run(NEOPLASM_CORE_MAPPING_NCIM_TERMS_XLS, xlsxfile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		NCImMappingToASCIITree runner = new NCImMappingToASCIITree(excelfile, asciitree);
		runner.generate_ascii_tree(excelfile, asciitree);

		NCImASCII2HTMLTreeConverter converter = new NCImASCII2HTMLTreeConverter(asciitree);
		converter.generate(htmlfile, version);


		if (!gov.nih.nci.evs.restapi.util.Utils.checkIfFileExists(NEOPLASM_CORE_MAPPING_NCIM_TERMS_XLSX)) {
			System.out.println(NEOPLASM_CORE_MAPPING_NCIM_TERMS_XLSX + " does not exists.");
			System.out.println("Downloading " + NEOPLASM_CORE_MAPPING_NCIM_TERMS_XLSX + " ..." );
			downloadExcel(NEOPLASM_FTP_SITE_URL + NEOPLASM_CORE_MAPPING_NCIM_TERMS_XLSX);

		} else {
			System.out.println(NEOPLASM_CORE_MAPPING_NCIM_TERMS_XLSX + " exists.");
		}

        try {
        	xlsxfile = NEOPLASM_CORE_MAPPING_NCIM_TERMS_XLSX;
        	n = xlsxfile.lastIndexOf(".");
        	textfile = xlsxfile.substring(0, n) + ".txt";
			Vector v = gov.nih.nci.evs.reportwriter.core.util.ExcelReadWriteUtils.readXLSXFile(xlsxfile);
			gov.nih.nci.evs.reportwriter.core.util.ExcelReadWriteUtils.saveToFile(textfile, v);

			char delim = '\t';
			String sheetName = xlsxfile.substring(0, n);
			xlsfile = gov.nih.nci.evs.reportwriter.core.util.ExcelReadWriteUtils.text2XLS(textfile, delim, sheetName);

			asciitree = xlsfile.substring(0, n) + ".txt";
			htmlfile = xlsfile.substring(0, n) + ".html";

			runner = new NCImMappingToASCIITree(xlsfile, asciitree);
			runner.generate_ascii_tree(xlsfile, asciitree);

			converter = new NCImASCII2HTMLTreeConverter(asciitree);
			converter.generate(htmlfile, version);

			String curr_dir = PackagingUtils.getCurrentWorkingDirectory();
			File folder = new File(curr_dir);
			String extension = "html";
			List list = PackagingUtils.listFilesInFolder(folder, extension);
			for (int i=0; i<list.size(); i++) {
				String filename = (String) list.get(i);
				PackagingUtils.removeImagesDir(filename);
			}

			String[] keys = new String[4];
			keys[0] = SUMMARY_DATA_AUTHOR;
			keys[1] = SUMMARY_DATA_KEYWORDS;
			keys[2] = SUMMARY_DATA_TITLE;
			keys[3] = SUMMARY_DATA_SUBJECT;

			String[] values = new String[4];
			values[0] = "NCI/EVS";
			values[1] = "Cancer, Neoplasm";
			values[2] = "Neoplasm Core";
			values[3] = "Neoplasm_Core_" + version;

			extension = "xlsx";
			list = PackagingUtils.listFilesInFolder(folder, extension);
			for (int i=0; i<list.size(); i++) {
				String filename = (String) list.get(i);
				System.out.println(filename);
				XLSXMetadataUtils.setSummaryData(filename, keys, values);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
}

