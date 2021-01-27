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
public class SpecialReportWriter extends ReportWriter {
    private static final Logger log = LoggerFactory.getLogger(SpecialReportWriter.class);

    public SpecialReportWriter(SparqlQueryManagerService sparqlQueryManagerService) {
		//this.sparqlQueryManagerService = sparqlQueryManagerService;
		//this.rwUtils = new RWUtils(sparqlQueryManagerService);
		super(sparqlQueryManagerService);
	}

	public String runSpecialReport(String templateFile, String outputFile, String conceptFile, String restURL, String namedGraph) {
		Vector output_vec = new Vector();

		log.info("runSpecialReport using templateFile: " + templateFile);
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
        String heading = getHeading(reportTemplate);
        output_vec.add(heading);

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

        //EvsVersionInfo evsVersionInfo = getEvsVersionInfo(restURL, namedGraph);
        //logFile.println("Version: " + evsVersionInfo.getVersion());
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
        String root = reportTemplate.getRootConceptCode();

	    List<String> subsets = sparqlQueryManagerService.getSubsets(namedGraph, root, restURL);
	    logFile.println("========subsets===== " + subsets.size());


	    Vector subset_codes = new Vector();
	    String subset_code = null;
	    Vector subsets_with_extensible = new Vector();
	    //for (int i=0; i<subsets.size(); i++) {
		for (int i=0; i<subsets.size(); i++) {
			String subset = subsets.get(i);
			logFile.println("========subset===== " + subset);
			Vector u = ParserUtils.parseData(subset, '|');
			String subset_label = "TBD";
			subset_code = (String) u.elementAt(0);
			String extensible = "null";
			extensible = (String) u.elementAt(1);
			if (extensible.compareTo("Yes") == 0 ||extensible.compareTo("No") == 0 ) {
				subsets_with_extensible.add(subset);
			}
		}
        logFile.println("subsets_with_extensible: " + subsets_with_extensible.size());
        int lcv = 0;
        //for (int i=0; i<subsets_with_extensible.size(); i++) {
		for (int i=0; i<3; i++) {
			String subset = (String) subsets_with_extensible.elementAt(i);
			lcv++;
			logFile.println("(" + lcv + ") " + subset);
			Vector u = ParserUtils.parseData(subset, '|');
			subset_code = (String) u.elementAt(0);
			String extensible = (String) u.elementAt(1);
            Vector w = process_subset(reportTemplate, restURL, namedGraph, subset_code, extensible);
            logFile.println("process_subset returns: " + w.size());

            output_vec.addAll(w);
			try {
				reportOutput = updateReport(reportOutput, reportTemplate, w);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
        /*
         * Print out tab separated and Excel output files
         */
        String outputFileText = outputFile + ".txt";
        saveToFile(outputFileText, output_vec);
        toExcel(outputFileText, new int[] { 0, 1 });

        logFile.println("");
        logFile.println("********************************");
        logFile.println("Completed: " + LocalDateTime.now());
		logFile.close();

		return "success";

	}

    public HashMap createSubsetMemberConceptHashMap(List<String> list) {
		HashMap hmap = new HashMap();
		for (int i=0; i<list.size(); i++) {
			String line = (String) list.get(i);
			Vector u = ParserUtils.parseData(line, '|');
			String member_concept_code = (String) u.elementAt(1);
			Vector w = new Vector();
			if (hmap.containsKey(member_concept_code)) {
				w = (Vector) hmap.get(member_concept_code);
			}
			w.add(line);
			hmap.put(member_concept_code, w);
		}
		return hmap;
	}


	public String parseSubsetConceptData(String code, List<String> list) {
		if (list == null || list.size() == 0) return null;

		String firstLine = (String) list.get(0);
		Vector u = ParserUtils.parseData(firstLine, '|');
		StringBuffer buf = new StringBuffer();
		buf.append(code).append("\t");
		buf.append("").append("\t");
		buf.append((String) u.elementAt(0)).append("\t"); //Yes or No
		buf.append((String) u.elementAt(1)).append("\t"); //label

		String cdisc_submisstion_value = null;
		String cdisc_def = null;
		String ncit_pt = null;
		StringBuffer syn_buf = new StringBuffer();
		for (int i=0; i<list.size(); i++) {
			String line = (String) list.get(i);
            u = ParserUtils.parseData(line, '|');
			String termName = (String) (String) u.elementAt(2);
			String termSource = (String) (String) u.elementAt(4);
			String termType = (String) (String) u.elementAt(6);
			if (termSource.compareTo("CDISC") == 0 && termType.compareTo("PT") == 0) {
				cdisc_submisstion_value = termName;
			}
			if (termType.compareTo("SY") == 0 & termSource.compareTo("CDISC") == 0) {
				syn_buf.append(termName).append("||");
			}
			cdisc_def = (String) (String) u.elementAt(8);
			ncit_pt = (String) (String) u.elementAt(10);
		}
		String syns = syn_buf.toString();
		if (syns.length() > 0) {
			syns = syns.substring(0, syns.length()-2);
		}
		buf.append(cdisc_submisstion_value).append("\t");
		buf.append(syns).append("\t");
		buf.append(cdisc_def).append("\t");
		buf.append(ncit_pt).append("\t");
        return buf.toString();
	}


    public Vector process_subset(Template template, String restURL, String namedGraph, String subset_code, String expansible) {
		Vector v = new Vector();
		List<String> list = sparqlQueryManagerService.getSubsetCconceptData(namedGraph, subset_code, restURL);
		String s = parseSubsetConceptData(subset_code, list);
		v.add(s);
        list = sparqlQueryManagerService.getSubsetMemberConceptData(namedGraph, subset_code, restURL);
		HashMap hmap = createSubsetMemberConceptHashMap(list);
		Iterator it = hmap.keySet().iterator();
		while (it.hasNext()) {
			String member_concept_code = (String) it.next();
			Vector member_concept_data = (Vector) hmap.get(member_concept_code);
			s = parse_member_concept_data(template, namedGraph, member_concept_code, member_concept_data, restURL);
			v.add(s);
		}
		return v;
	}

	public String parse_member_concept_data(Template template, String namedGraph, String code, Vector v, String restURL) {
/*
0 ?x_label
1 ?x_code
2 ?p_label
3 ?p_value
4 ?a_prop_label
5 ?a_target
6 ?q1_label
7 ?q1_value
8 ?q2_label
9 ?q2_value
10 ?b_target
11 ?b_prop_label
12 ?q5_value
13 ?r_label
15 ?q3_label
16 ?q4_value
*/
        String first_line = (String) v.elementAt(0);
		Vector u0 = ParserUtils.parseData(first_line, '|');
		String subset_code = (String) u0.elementAt(14);
		String subset_label = (String) u0.elementAt(13);
		StringBuffer buf = new StringBuffer();
		buf.append(code).append("\t");
		buf.append(subset_code).append("\t");
		buf.append("").append("\t");
		buf.append(subset_label).append("\t");
		int cdisc_pt_knt = 0;
		String submission_value = null;
		String alt_def = null;
		String nci_pt = null;
		StringBuffer syn_buf = new StringBuffer();
		for (int i=0; i<v.size(); i++) {
			String line = (String) v.elementAt(i);
			Vector u = ParserUtils.parseData(line, '|');
			String t = (String) u.elementAt(9);
			if (t.compareTo("PT") == 0) {
				cdisc_pt_knt++;
				submission_value = (String) u.elementAt(5);
			} else if (t.compareTo("SY") == 0) {
				t = (String) u.elementAt(5);
				syn_buf.append(t).append("||");
			}
			alt_def = (String) u.elementAt(10);
			nci_pt = (String) u.elementAt(3);
		}
		if (cdisc_pt_knt > 1) {
			List list = null;
			String propertyName = "FULL_SYN";
			Vector qualifierNames = new Vector();
			qualifierNames.add("Term Source");
			qualifierNames.add("Term Type");
			Vector qualifierValues = new Vector();
			qualifierValues.add("NCI");
			qualifierValues.add("AB");
			list = sparqlQueryManagerService.getMatchedAnnotatedTarget(namedGraph, subset_code, propertyName, qualifierNames, qualifierValues, restURL);
			String nci_ab = (String) list.get(0);
			submission_value = null;
			for (int i=0; i<v.size(); i++) {
				String line = (String) v.elementAt(i);
				Vector u = ParserUtils.parseData(line, '|');
				String source_code = (String) u.elementAt(16);
				if (source_code != null && source_code.compareTo(nci_ab) == 0) {
					submission_value = (String) u.elementAt(16);
					break;
				}
			}
		}
		buf.append(submission_value).append("\t");
		String syn = syn_buf.toString();
		if (syn.endsWith("||")) {
			syn = syn.substring(0, syn.length()-2);
		}
		buf.append(syn).append("\t");
		buf.append(alt_def).append("\t");
		buf.append(nci_pt);
		return buf.toString();
	}

	 public static void saveToFile(String outputfile, String t) {
		 Vector v = new Vector();
		 v.add(t);
		 saveToFile(outputfile, v);
	 }

	 public static void saveToFile(String outputfile, Vector v) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(outputfile, "UTF-8");
			if (v != null && v.size() > 0) {
				for (int i=0; i<v.size(); i++) {
					String t = (String) v.elementAt(i);
					pw.println(t);
				}
		    }
		} catch (Exception ex) {

		} finally {
			try {
				pw.close();
				System.out.println("Output file " + outputfile + " generated.");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	 }

	public static void saveToFile(PrintWriter pw, Vector v) {
		if (v != null && v.size() > 0) {
			for (int i=0; i<v.size(); i++) {
				String t = (String) v.elementAt(i);
				pw.println(t);
			}
		}
	}

    public static void toExcel(String textfile, int[] ncitCodeColumns) {
        try {
            String delimiter = "\t";
            AsciiToExcelFormatter formatter = new AsciiToExcelFormatter();
            formatter.setNcitUrl("http://ncit-dev.nci.nih.gov/ncitbrowser/");
            formatter.setNcitCodeColumns(ncitCodeColumns);
            formatter.convert(textfile, delimiter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Report updateReport(Report reportOutput, Template template, Vector w) {
		List <TemplateColumn> templateColumns = template.getColumns();
		ReportRow reportRow = new ReportRow();
		String contentSepartor = " || ";
		for (int k=0; k<w.size(); k++) {
			String line = (String) w.elementAt(k);
			Vector v = ParserUtils.parseData(line, '\t');
			int i = 0;
			for (TemplateColumn column: templateColumns) {
				String name = column.getLabel();
				String value = (String) v.elementAt(i);
				i++;
				ReportColumn reportColumn = new ReportColumn(name, value);
				reportRow.getColumns().add(reportColumn);
			}
			reportOutput.getRows().add(reportRow);
		}
		return reportOutput;
	}

    public static String getHeading(Template reportTemplate) {
		StringBuffer buf = new StringBuffer();
		for (TemplateColumn templateColumn: reportTemplate.getColumns()) {
			buf.append(templateColumn.getLabel()).append("\t");
		}
		String heading = buf.toString();
		return heading.substring(0, heading.length()-1);
	}
}
