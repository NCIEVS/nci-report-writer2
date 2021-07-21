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
		String csvfile = ExcelReadWriteUtils.delimited2CSV(inputfile, '|');
		System.out.println(csvfile + " generated.");

		System.out.println("convertToExcel...");
		try {
			char delim = '|';
			String excelfile = ExcelReadWriteUtils.text2XLS(inputfile, delim, sheetName);
			System.out.println(excelfile + " generated.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//neoplasmCoreRelationships.convertToExcel(csvfile);
	}

 	public static void ascii2HTMLTree(String asciiTree, String ncit_version, int lines_to_skip, String title, Vector contents) {
		ASCII2HTMLTreeConverter generator = new ASCII2HTMLTreeConverter();
		int n = asciiTree.lastIndexOf(".");
        String outputfile = asciiTree.substring(0, n) + ".html";
        String label = title;
        generator.run(asciiTree, outputfile, lines_to_skip, title, label, ncit_version, contents);
	}

	public static void main(String[] args) {
		System.out.println("Step 1: Download NCI Thesaurus from the ftp site.");
		if (!gov.nih.nci.evs.restapi.util.Utils.checkIfFileExists(NCI_THESAURUS_OWL)) {
			gov.nih.nci.evs.reportwriter.core.util.NCItDownload.download();
		} else {
			System.out.println(NCI_THESAURUS_OWL + " exists.");
		}
		String owlfile = NCI_THESAURUS_OWL;
		OWLScanner scanner = new OWLScanner(owlfile);
		String version = scanner.extractVersion();    //sheetNames.get(0);
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

		String csvfile = ExcelReadWriteUtils.tabDelimited2CSV(textfile);
		System.out.println(csvfile + " generated.");

		String xlsfile = NEOPLASM_CORE_TERMINOLOGY + ".xls";
		List<String> sheetNames = ExcelReadWriteUtils.getXLSSheetNames(xlsfile);
		try {
			String excelFileName = ExcelReadWriteUtils.writeXLSXFile(textfile, '\t', sheetNames.get(0));
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

		} else {
			System.out.println(NEOPLASM_CORE_MAPPING_NCIM_TERMS_XLS + " exists.");
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
			converter.generate(htmlfile);
		}
    }

}

