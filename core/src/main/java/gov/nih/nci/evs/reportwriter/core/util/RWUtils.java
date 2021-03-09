package gov.nih.nci.evs.reportwriter.core.util;

import gov.nih.nci.evs.reportwriter.core.service.*;
import gov.nih.nci.evs.reportwriter.core.configuration.*;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.nio.charset.StandardCharsets;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.core.model.evs.EvsAxiom;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsConcept;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsAssociation;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsSupportedAssociation;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsSupportedRole;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsProperty;
import gov.nih.nci.evs.reportwriter.core.model.report.Report;
import gov.nih.nci.evs.reportwriter.core.model.report.ReportColumn;
import gov.nih.nci.evs.reportwriter.core.model.report.ReportRow;
import gov.nih.nci.evs.reportwriter.core.model.template.TemplateColumn;
import gov.nih.nci.evs.reportwriter.core.service.SparqlQueryManagerService;
import gov.nih.nci.evs.reportwriter.core.service.SparqlQueryManagerServiceImpl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import gov.nih.nci.evs.reportwriter.core.model.evs.EvsConcept;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsVersionInfo;
import gov.nih.nci.evs.reportwriter.core.model.report.Report;
import gov.nih.nci.evs.reportwriter.core.model.report.ReportColumn;
import gov.nih.nci.evs.reportwriter.core.model.report.ReportRow;
import gov.nih.nci.evs.reportwriter.core.model.template.Template;
import gov.nih.nci.evs.reportwriter.core.model.template.TemplateColumn;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

@Service
/**
 *
 * Helper methods for running a report based on a Report Template.
 *
 */
public class RWUtils {
	private HashMap associationLabel2CodeHashMap = null;
	private HashMap roleLabel2CodeHashMap = null;

	private static final Logger log = LoggerFactory.getLogger(RWUtils.class);

	@Autowired
	SparqlQueryManagerService sparqlQueryManagerService;

	public RWUtils() {
	}

	public RWUtils(SparqlQueryManagerService sparqlQueryManagerService) {
		this.sparqlQueryManagerService = sparqlQueryManagerService;
	}

	public void setAssociationLabel2CodeHashMap(HashMap hmap) {
		this.associationLabel2CodeHashMap = hmap;
		/*
		Iterator it = hmap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = (String) hmap.get(key);
			System.out.println(key + " --> " + value);
		}
		*/
	}

	public void setRoleLabel2CodeHashMap(HashMap hmap) {
		this.roleLabel2CodeHashMap = hmap;
	}
/////////////////////////////////
	/**
	 * Run the ConceptInSubset query and then process the report based on the report template.
	 *
	 * @param reportOutput ReportOutput contains the data retrieved from the SPARQL queries and processing.
	 * @param rootConcept Root Concept.
	 * @param conceptHash ConceptHash cache to improve performance.
	 * @param templateColumns TemplateColumns contain template definitions for each column.
	 */
	public void processSubset(List<String> codes, Report reportOutput, HashMap<String,EvsConcept> conceptHash, List <TemplateColumn> templateColumns, PrintWriter logFile, String namedGraph, String restURL) {
		List <EvsConcept> associatedConcepts = new ArrayList();
		for (int i=0; i<codes.size(); i++) {
			String code = (String) codes.get(i);
	        EvsConcept assocConcept = sparqlQueryManagerService.getEvsConceptDetailShort(code,namedGraph,restURL);
	        assocConcept.setProperties(sparqlQueryManagerService.getEvsProperties(code,namedGraph,restURL));
	        assocConcept.setAxioms(sparqlQueryManagerService.getEvsAxioms(code,namedGraph,restURL));
	        associatedConcepts.add(assocConcept);
		}
        processSubset(reportOutput, associatedConcepts, conceptHash, templateColumns, logFile, namedGraph, restURL);
	}

	public void processSubset(Report reportOutput, List <EvsConcept> associatedConcepts, HashMap<String,EvsConcept> conceptHash, List <TemplateColumn> templateColumns,PrintWriter logFile, String namedGraph, String restURL) {
		int total = 0;
		for (EvsConcept concept: associatedConcepts) {
			total += 1;
			if (total % 100 == 0) {
				log.info("Number of associations processed: " + total);
				System.out.println("Number of associations processed: " + total);
				logFile.println("Number of associations processed: " + total);
			}
			if (conceptHash.containsKey(concept.getCode())) {
				concept = conceptHash.get(concept.getCode());
			} else {
				concept.setProperties(sparqlQueryManagerService.getEvsProperties(concept.getCode(), namedGraph, restURL));
				concept.setAxioms(sparqlQueryManagerService.getEvsAxioms(concept.getCode(), namedGraph, restURL));
				conceptHash.put(concept.getCode(), concept);
			}
			try {
				writeColumnData(reportOutput,null,concept,conceptHash,templateColumns,namedGraph,restURL);
			} catch (Exception ex) {
				logFile.println("ERROR: writeColumnData failed at " + concept.getLabel() + " (" + concept.getCode() + ")");
			}
		}
	}

	public void processConceptInSubset(Report reportOutput, EvsConcept rootConcept, HashMap<String,EvsConcept> conceptHash, List <TemplateColumn> templateColumns,PrintWriter logFile, String namedGraph, String restURL) {
		List <EvsConcept> associatedConcepts = sparqlQueryManagerService.getEvsConceptInSubset(rootConcept.getCode(), namedGraph, restURL);
		log.info("Concept: " + rootConcept.getCode() + " Number of associations: " + associatedConcepts.size());
		System.out.println("Concept: " + rootConcept.getCode() + " Number of associations: " + associatedConcepts.size());
		logFile.println("Concept: " + rootConcept.getCode() + " Number of associations: " + associatedConcepts.size());
		int total = 0;
		for (EvsConcept concept: associatedConcepts) {
			total += 1;
			if (total % 100 == 0) {
				log.info("Number of associations processed: " + total);
				System.out.println("Number of associations processed: " + total);
				logFile.println("Number of associations processed: " + total);
			}
			if (conceptHash.containsKey(concept.getCode())) {
				concept = conceptHash.get(concept.getCode());
			} else {
				concept.setProperties(sparqlQueryManagerService.getEvsProperties(concept.getCode(), namedGraph, restURL));
				concept.setAxioms(sparqlQueryManagerService.getEvsAxioms(concept.getCode(), namedGraph, restURL));
				conceptHash.put(concept.getCode(), concept);
			}
			try {
				writeColumnData(reportOutput,rootConcept,concept,conceptHash,templateColumns,namedGraph,restURL);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void processConceptInSubset(Report reportOutput, EvsConcept rootConcept, HashMap<String,EvsConcept> conceptHash, List <TemplateColumn> templateColumns, int currentLevel, int maxLevel, PrintWriter logFile, String namedGraph, String restURL) {
		if (currentLevel < maxLevel) {
			List <EvsConcept> associatedConcepts = sparqlQueryManagerService.getEvsConceptInSubset(rootConcept.getCode(), namedGraph, restURL);
			log.info("Concept: " + rootConcept.getCode() + " Number of associations: " + associatedConcepts.size());
			System.out.println("Concept: " + rootConcept.getCode() + " Number of associations: " + associatedConcepts.size());
			logFile.println("Concept: " + rootConcept.getCode() + " Number of associations: " + associatedConcepts.size());
			int total = 0;
			for (EvsConcept concept: associatedConcepts) {
				total += 1;
				if (total % 100 == 0) {
					log.info("Number of associations processed: " + total);
					System.out.println("Number of associations processed: " + total);
					logFile.println("Number of associations processed: " + total);
				}
				if (conceptHash.containsKey(concept.getCode())) {
					concept = conceptHash.get(concept.getCode());
				} else {
					concept.setProperties(sparqlQueryManagerService.getEvsProperties(concept.getCode(), namedGraph, restURL));
					concept.setAxioms(sparqlQueryManagerService.getEvsAxioms(concept.getCode(), namedGraph, restURL));
					conceptHash.put(concept.getCode(), concept);
				}

				try {
					writeColumnData(reportOutput,rootConcept,concept,conceptHash,templateColumns,namedGraph,restURL);
					processConceptInSubset(reportOutput, concept, conceptHash, templateColumns, currentLevel+1, maxLevel, logFile, namedGraph, restURL);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}


	/**
	 * Run the ConceptSubclasses query and then process the report based on the report template.
	 *
	 * @param reportOutput ReportOutput contains the data retrieved from the SPARQL queries and processing.
	 * @param rootConcept Root Concept.
	 * @param conceptHash ConceptHash cache to improve performance.
	 * @param templateColumns TemplateColumns contain template definitions for each column.
	 *
	 * This methods supports recursion.
	 */
	public void processConceptSubclasses(Report reportOutput, EvsConcept parentConcept, HashMap<String,EvsConcept> conceptHash, List <TemplateColumn> templateColumns, int currentLevel, int maxLevel, PrintWriter logFile, String namedGraph, String restURL) {
		if (currentLevel < maxLevel) {

			if (parentConcept == null) {
				log.info("Parent Concept is null.");
				System.out.println("(*) Parent Concept is null.");
				return;
			} else {
				System.out.println("(*) parentConcept.getCode(): " + parentConcept.getCode());
			}

		    List <EvsConcept> subclasses = sparqlQueryManagerService.getEvsSubclasses(parentConcept.getCode(), namedGraph, restURL);
    		log.info("Parent Concept: " + parentConcept.getCode() + " Number of Subclasses: " + subclasses.size());
	    	System.out.println("Parent Concept: " + parentConcept.getCode() + " Number of Subclasses: " + subclasses.size());
    		logFile.println("Parent Concept: " + parentConcept.getCode() + " Number of Subclasses: " + subclasses.size());
	    	for (EvsConcept subclass: subclasses) {
		    	if (conceptHash.containsKey(subclass.getCode())) {
			    	subclass = conceptHash.get(subclass.getCode());
    			} else {
	    			subclass.setProperties(sparqlQueryManagerService.getEvsProperties(subclass.getCode(), namedGraph, restURL));
		    		subclass.setAxioms(sparqlQueryManagerService.getEvsAxioms(subclass.getCode(), namedGraph, restURL));
			    	conceptHash.put(subclass.getCode(), subclass);
    			}
	    		processConceptInSubset(reportOutput,subclass,conceptHash,templateColumns,logFile, namedGraph, restURL);
		    	processConceptSubclasses(reportOutput,subclass,conceptHash,templateColumns, currentLevel + 1, maxLevel, logFile, namedGraph, restURL);
		    }
		}
		return;
	}

	/**
	 * Run the ConceptSubclasses query and then process the report based on the report template.
	 *
	 * This is a special case where the recursion limit is set by the maxLevel parameter.
	 *
	 * @param reportOutput ReportOutput contains the data retrieved from the SPARQL queries and processing.
	 * @param rootConcept Root Concept.
	 * @param conceptHash ConceptHash cache to improve performance.
	 * @param templateColumns TemplateColumns contain template definitions for each column.
	 * @param currentLevel Integer representing the current depth of the recursion.
	 * @param MaxLevel Integer representing the maximum depth of the recursion.
	 *
	 * This methods supports recursion, but limited by the maxLevel parameters.
	 */
	public void processConceptSubclassesOnly(Report reportOutput,EvsConcept parentConcept,HashMap<String,EvsConcept> conceptHash,List <TemplateColumn> templateColumns, int currentLevel, int maxLevel, PrintWriter logFile, String namedGraph, String restURL) {
		if (currentLevel < maxLevel) {

			if (parentConcept == null) {
				log.info("(*) Parent Concept is null.");
				System.out.println("Parent Concept is null.");
				return;
			} else {
				System.out.println("(*) parentConcept.getCode(): " + parentConcept.getCode());
			}

			List <EvsConcept> subclasses = sparqlQueryManagerService.getEvsSubclasses(parentConcept.getCode(), namedGraph, restURL);
			log.info("Parent Concept: " + parentConcept.getCode() + " Number of Subclasses: " + subclasses.size());
			System.out.println("Parent Concept: " + parentConcept.getCode() + " Number of Subclasses: " + subclasses.size());
			logFile.println("Parent Concept: " + parentConcept.getCode() + " Number of Subclasses: " + subclasses.size());
			for (EvsConcept subclass: subclasses) {
				log.debug(subclass.getCode());
				if (conceptHash.containsKey(subclass.getCode())) {
					subclass = conceptHash.get(subclass.getCode());
				} else {
					subclass.setProperties(sparqlQueryManagerService.getEvsProperties(subclass.getCode(), namedGraph, restURL));
					subclass.setAxioms(sparqlQueryManagerService.getEvsAxioms(subclass.getCode(), namedGraph, restURL));
					conceptHash.put(subclass.getCode(), subclass);
				}
				try {
					writeColumnData(reportOutput,parentConcept,subclass,conceptHash,templateColumns,namedGraph,restURL);
					processConceptSubclassesOnly(reportOutput,subclass,conceptHash,templateColumns,currentLevel + 1,maxLevel,logFile, namedGraph, restURL);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return;
	}

	/**
	 * Uses the conceptFile to locate individual concepts and then report on the concept using the template definition.
	 *
	 * The conceptFile is a text file containing one concept per line.
	 *
	 * @param reportOutput ReportOutput contains the data retrieved from the SPARQL queries and processing.
	 * @param conceptHash ConceptHash cache to improve performance.
	 * @param templateColumns TemplateColumns contain template definitions for each column.
	 * @param conceptFile File name of the input file.
	 *
	 */
	public void processConceptList(Report reportOutput, HashMap<String,EvsConcept> conceptHash, List <TemplateColumn> templateColumns, String conceptFile, PrintWriter logFile, String namedGraph, String restURL ) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(conceptFile));
			String line;
			int total = 0;
			while ((line = br.readLine()) != null) {
		        EvsConcept concept = sparqlQueryManagerService.getEvsConceptDetailShort(line,namedGraph,restURL);
				concept.setProperties(sparqlQueryManagerService.getEvsProperties(concept.getCode(),namedGraph,restURL));
				concept.setAxioms(sparqlQueryManagerService.getEvsAxioms(concept.getCode(),namedGraph,restURL));
				try {
					writeColumnData(reportOutput,concept,concept,conceptHash,templateColumns,namedGraph,restURL);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				total += 1;
				if (total % 100 == 0) {
					log.info("Number of concepts processed: " + total);
					System.out.println("Number of concepts processed: " + total);
				}
			}
		} catch (Exception ex) {
			System.err.println("Failed to process concept list file");
			return;
		}
	}

	public static boolean isNull(String str){
		if (str == null) return true;
		if (str.length() == 0) return true;
		if (str.compareTo("null") == 0) return true;
		return false;
	}

	/**
	 * Populate a row of data based on the SPARQL query results and the template column definitions.
	 *
	 * @param reportOutput ReportOutput contains the data retrieved from the SPARQL queries and processing.
	 * @param parentConcept Parent concept.
	 * @param concept Current concept.
	 * @param conceptHash ConceptHash cache to improve performance.
	 * @param templateColumns TemplateColumns contain template definitions for each column.
	 */
	public void writeColumnData(Report reportOutput, EvsConcept parentConcept, EvsConcept concept, HashMap<String,EvsConcept> conceptHash, List <TemplateColumn> templateColumns, String namedGraph, String restURL) {
		ReportRow reportRow = new ReportRow();
		String propertySepartor = " | ";
		String contentSepartor = " || ";
		String label = null;
		try {
			for (TemplateColumn column: templateColumns) {
				label = column.getLabel();
				String propertyType = column.getPropertyType();

				String property = column.getProperty();
				String columnString = "";
				List <String> values = new ArrayList <String>();
				List <EvsProperty> conceptProperties = concept.getProperties();
				List <EvsAxiom> conceptAxioms = concept.getAxioms();

				//CDISC Submission Value
				if (label.compareTo("CDISC Submission Value") == 0) {
					if (parentConcept != null) {
						List <EvsAxiom> parentAxioms = parentConcept.getAxioms();
						String CDISC_Submission_Value = "";
						try {
							CDISC_Submission_Value = getCDISCSubmissionValue(parentAxioms, conceptAxioms);
							values.add(CDISC_Submission_Value);
						} catch (Exception ex) {
							System.out.println("getCDISCSubmissionValue failed at " + concept.getCode());
						}
					}

				} else if (propertyType.equals("code")) {
					List <String> properties = EVSUtils.getProperty("NHC0", conceptProperties);
					values.add(properties.get(0));
				} else if (propertyType.equals("property")) {
					values = EVSUtils.getProperty(property, conceptProperties);
					if (property.compareTo("hasDbXref") == 0) {
						values = filterXrefCodes(property, values, column.getLabel());
					}
				} else if (propertyType.equals("FULL_SYN")) {
					values = getFullSynonym(column,conceptAxioms);
				} else if (propertyType.equals("DEFINITION")) {
					values = getDefinition(column,conceptAxioms);
				} else if (propertyType.equals("ALT_DEFINITION")) {
					values = getDefinition(column,conceptAxioms);
				/*
				 * Not needed because hasDbXref is now a property only. Mar 29, 2018
				} else if (propertyType.equals("hasDbXref")) {
					values = getDbXref(column,conceptAxioms);
				*/
				} else if (propertyType.equals("Associated Concept Code")) {
					if (parentConcept != null) {
						values = EVSUtils.getProperty(property, parentConcept.getProperties());
					}
				} else if (propertyType.equals("Associated Concept Property")) {
					if (parentConcept != null) {
						if (property.equals("P90")) {
							values = getFullSynonym(column,parentConcept.getAxioms());
						} else {
							values = EVSUtils.getProperty(property, parentConcept.getProperties());
						}
					}
				//KLO 11/26/2019 ///////////////////////////////////////////////////////////////////////////////////////////
				} else if (propertyType.equals("Parent Codes")) {
					List <EvsConcept> superclasses = sparqlQueryManagerService.getEvsSuperclasses(concept.getCode(), namedGraph, restURL);
					concept.setSuperclasses(superclasses);
					values = EVSUtils.getSuperclassCodes(concept);

				} else if (propertyType.equals("1st NICHD Parent Code")) {
					List <String> properties = EVSUtils.getProperty("A11", conceptProperties);
					if (properties.size() > 0) {
						String code = properties.get(0).replaceAll("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#","");
						values.add(code);
					}

				} else if (propertyType.equals("2nd NICHD Parent Code")) {
					List <String> properties = EVSUtils.getProperty("A11", conceptProperties);
					if (properties.size() > 1) {
						String code = properties.get(1).replaceAll("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#","");
						values.add(code);
					}
				} else if (propertyType.equals("1st NICHD Parent Property")) {
					List <String> properties = EVSUtils.getProperty("A11", conceptProperties);
					if (properties.size() > 0) {
						String code = properties.get(0).replaceAll("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#","");

						EvsConcept nichdParentConcept = null;
						if (conceptHash.containsKey(code)) {
							nichdParentConcept = conceptHash.get(code);
						} else {
							nichdParentConcept = sparqlQueryManagerService.getEvsConceptDetailShort(code,namedGraph,restURL);
							nichdParentConcept.setProperties(sparqlQueryManagerService.getEvsProperties(code,namedGraph,restURL));
							nichdParentConcept.setAxioms(sparqlQueryManagerService.getEvsAxioms(code,namedGraph,restURL));
							conceptHash.put(code, nichdParentConcept);
						}

						if (property.equals("P90")) {
							values = getFullSynonym(column,nichdParentConcept.getAxioms());
						} else {
							System.err.println("1st NICHD Parent Property Not Supported");
						}
					}
				} else if (propertyType.equals("2nd NICHD Parent Property")) {
					List <String> properties = EVSUtils.getProperty("A11", conceptProperties);
					if (properties.size() > 1) {
						String code = properties.get(1).replaceAll("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#","");

						EvsConcept nichdParentConcept = null;
						if (conceptHash.containsKey(code)) {
							nichdParentConcept = conceptHash.get(code);
						} else {
							nichdParentConcept = sparqlQueryManagerService.getEvsConceptDetailShort(code,namedGraph,restURL);
							nichdParentConcept.setProperties(sparqlQueryManagerService.getEvsProperties(code,namedGraph,restURL));
							nichdParentConcept.setAxioms(sparqlQueryManagerService.getEvsAxioms(code,namedGraph,restURL));
							conceptHash.put(code, nichdParentConcept);
						}

						if (property.equals("P90")) {
							values = getFullSynonym(column,nichdParentConcept.getAxioms());
						} else {
							System.err.println("2nd NICHD Parent Property Not Supported");
						}
					}
				} else if (propertyType.equals("1st CDRH Parent Code")) {
					List <String> properties = EVSUtils.getProperty("A10", conceptProperties);
					if (properties.size() > 0) {
						String code = properties.get(0).replaceAll("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#","");
						values.add(code);
					}
				} else if (propertyType.equals("2nd CDRH Parent Code")) {
					List <String> properties = EVSUtils.getProperty("A10", conceptProperties);
					if (properties.size() > 1) {
						String code = properties.get(1).replaceAll("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#","");
						values.add(code);
					}
				} else if (propertyType.equals("1st CDRH Parent Property")) {
					List <String> properties = EVSUtils.getProperty("A10", conceptProperties);
					if (properties.size() > 0) {
						String code = properties.get(0).replaceAll("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#","");

						EvsConcept nichdParentConcept = null;
						if (conceptHash.containsKey(code)) {
							nichdParentConcept = conceptHash.get(code);
						} else {
							nichdParentConcept = sparqlQueryManagerService.getEvsConceptDetailShort(code,namedGraph,restURL);
							nichdParentConcept.setProperties(sparqlQueryManagerService.getEvsProperties(code,namedGraph,restURL));
							nichdParentConcept.setAxioms(sparqlQueryManagerService.getEvsAxioms(code,namedGraph,restURL));
							conceptHash.put(code, nichdParentConcept);
						}

						if (property.equals("P90")) {
							values = getFullSynonym(column,nichdParentConcept.getAxioms());
						} else {
							System.err.println("1st CDRH Parent Property Not Supported");
						}
					}
				} else if (propertyType.equals("2nd CDRH Parent Property")) {
					List <String> properties = EVSUtils.getProperty("A10", conceptProperties);
					if (properties.size() > 1) {
						String code = properties.get(1).replaceAll("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#","");


						EvsConcept nichdParentConcept = null;
						if (conceptHash.containsKey(code)) {
							nichdParentConcept = conceptHash.get(code);
						} else {
							nichdParentConcept = sparqlQueryManagerService.getEvsConceptDetailShort(code,namedGraph,restURL);
							nichdParentConcept.setProperties(sparqlQueryManagerService.getEvsProperties(code,namedGraph,restURL));
							nichdParentConcept.setAxioms(sparqlQueryManagerService.getEvsAxioms(code,namedGraph,restURL));
							conceptHash.put(code, nichdParentConcept);
						}

						if (property.equals("P90")) {
							values = getFullSynonym(column,nichdParentConcept.getAxioms());
						} else {
							System.err.println("2nd CDRH Parent Property Not Supported");
						}
					}
				} else if (propertyType.equals("Maps_To Axiom")) {
					String source = column.getSource();
					List <EvsAxiom> axioms = concept.getAxioms();
					for (EvsAxiom axiom: axioms) {
						List <String> v = new ArrayList <String> ();
						if (axiom.getAnnotatedProperty().equals("P375")) {
							boolean include = true;
							if (!isNull(axiom.getTargetTerminology()) && axiom.getTargetTerminology().compareTo(source) != 0) {
								include = false;
							}
							if (include) {
								v.add(axiom.getAnnotatedTarget());
								v.add(axiom.getTargetTerminology());
								v.add(axiom.getTargetTerminologyVersion());
								v.add(axiom.getRelationshipToTarget());
								v.add(axiom.getTargetTermType());
								v.add(axiom.getTargetCode());
								String vv = String.join(propertySepartor, v);
								values.add(vv);
							}
						}
					}
				} else if (propertyType.equals("FULL_SYN Axiom")) {
					List <EvsAxiom> axioms = concept.getAxioms();
					for (EvsAxiom axiom: axioms) {
						List <String> v = new ArrayList <String> ();
						if (axiom.getAnnotatedProperty().equals("P90")) {
							v.add(axiom.getAnnotatedTarget());
							v.add(axiom.getTermSource());
							v.add(axiom.getTermGroup());
							v.add(axiom.getSourceCode());
							v.add(axiom.getSubsourceName());
							String vv = String.join(propertySepartor, v);
							values.add(vv);
						}
					}

				} else if (associationLabel2CodeHashMap.containsKey(propertyType)) {
					values = processAssociation(concept.getCode(), propertyType, conceptProperties, property, column, conceptHash, namedGraph, restURL);
				} else if (roleLabel2CodeHashMap.containsKey(propertyType)) {
					values = processRole(concept.getCode(), propertyType, conceptProperties, property, column, conceptHash, namedGraph, restURL);
				}

				if (values == null || values.size() == 0) {
				} else {
					columnString = String.join(contentSepartor, values);
				}

				String name = column.getLabel();
				ReportColumn reportColumn = new ReportColumn(name,columnString);
				reportRow.getColumns().add(reportColumn);
			}
			reportOutput.getRows().add(reportRow);
		} catch (Exception ex) {
			System.out.println("ERROR: writeColumnData failed at " + concept.getLabel() + " (" + concept.getCode() + ") - column label: " + label);
			if (parentConcept != null) {
				System.out.println("\tparentConcept: " + parentConcept.getLabel() + "(" + parentConcept.getCode() + ")");
		    }
		}
	}

	public List <String> processRole(String code, String roleName, List <EvsProperty> conceptProperties,
			String property, TemplateColumn column, HashMap<String,EvsConcept> conceptHash, String namedGraph, String restURL) {
		List <String> values = new ArrayList <String>();

		try {
			List<String> roleTargetCodes = sparqlQueryManagerService.getRoleTargets(namedGraph, code, roleName, restURL);
			if (roleTargetCodes == null || roleTargetCodes.size() == 0) return values;
			for (int i = 0; i < roleTargetCodes.size(); i++ ) {
				String targetCode = roleTargetCodes.get(i);
				EvsConcept assocConcept = null;
				if (conceptHash.containsKey(targetCode)) {
					assocConcept = conceptHash.get(targetCode);
				} else {
					assocConcept = sparqlQueryManagerService.getEvsConceptDetailShort(targetCode,namedGraph,restURL);
					assocConcept.setProperties(sparqlQueryManagerService.getEvsProperties(targetCode,namedGraph,restURL));
					assocConcept.setAxioms(sparqlQueryManagerService.getEvsAxioms(targetCode,namedGraph,restURL));
					conceptHash.put(targetCode, assocConcept);
				}
				String col_property = column.getProperty(); //target
				String col_display = column.getDisplay();
				List <EvsAxiom> conceptAxioms = assocConcept.getAxioms();

				if (col_property.compareTo("NHC0") == 0) {
					List <EvsProperty> asso_conceptProperties = assocConcept.getProperties();
					List <String> asso_properties = EVSUtils.getProperty("NHC0", asso_conceptProperties);
					values.add(asso_properties.get(0));
				} else if (col_property.compareTo("FULL_SYN") == 0 || col_property.compareTo("P90") == 0) {
					values.addAll(getFullSynonym(column,conceptAxioms));
				} else if (col_property.compareTo("DEFINITION") == 0 || col_property.compareTo("P97") == 0) {
					values.addAll(getDefinition(column,conceptAxioms));
				} else if (col_property.compareTo("ALT_DEFINITION") == 0 || col_property.compareTo("P325") == 0) {
					values.addAll(getDefinition(column,conceptAxioms));
				} else if (col_property.compareTo("term-name") == 0 || col_property.compareTo("P382") == 0) {
					values.addAll(getFullSynonym(column,conceptAxioms));
				} else if (col_display.compareTo("subsource_code") == 0 || col_display.compareTo("P385") == 0) {
					values.addAll(getFullSynonym(column,conceptAxioms));

				} else {
					List <EvsProperty> asso_conceptProperties = assocConcept.getProperties();
					values.addAll(EVSUtils.getProperty(col_property, asso_conceptProperties));
				}
			}
		} catch (Exception ex) {

		}
		return values;
	}

//values = processAssociation(associationCode, conceptProperties, property, column, conceptHash, namedGraph, restURL);

	public List <String> processAssociation(String code, String associationName, List <EvsProperty> conceptProperties,
			String property, TemplateColumn column, HashMap<String,EvsConcept> conceptHash, String namedGraph, String restURL) {

		List <String> values = new ArrayList <String>();

		try {
			List<String> associationTargetCodes = sparqlQueryManagerService.getAssociationTargets(namedGraph, code, associationName, restURL);
			if (associationTargetCodes == null || associationTargetCodes.size() == 0) return values;
			for (int i = 0; i < associationTargetCodes.size(); i++ ) {
				String targetCode = associationTargetCodes.get(i);
				EvsConcept assocConcept = null;
				if (conceptHash.containsKey(targetCode)) {
					assocConcept = conceptHash.get(targetCode);
				} else {
					assocConcept = sparqlQueryManagerService.getEvsConceptDetailShort(targetCode,namedGraph,restURL);
					assocConcept.setProperties(sparqlQueryManagerService.getEvsProperties(targetCode,namedGraph,restURL));
					assocConcept.setAxioms(sparqlQueryManagerService.getEvsAxioms(targetCode,namedGraph,restURL));
					conceptHash.put(targetCode, assocConcept);
				}
				String col_property = column.getProperty(); //target
				String col_display = column.getDisplay();
				List <EvsAxiom> conceptAxioms = assocConcept.getAxioms();

				if (col_property.compareTo("NHC0") == 0) {
					List <EvsProperty> asso_conceptProperties = assocConcept.getProperties();
					List <String> asso_properties = EVSUtils.getProperty("NHC0", asso_conceptProperties);
  		            values.add(asso_properties.get(0));

				} else if (col_property.compareTo("FULL_SYN") == 0 || col_property.compareTo("P90") == 0) {
					values.addAll(getFullSynonym(column,conceptAxioms));
				} else if (col_property.compareTo("DEFINITION") == 0 || col_property.compareTo("P97") == 0) {
					values.addAll(getDefinition(column,conceptAxioms));
				} else if (col_property.compareTo("ALT_DEFINITION") == 0 || col_property.compareTo("P325") == 0) {
					values.addAll(getDefinition(column,conceptAxioms));
				} else if (col_property.compareTo("term-name") == 0 || col_property.compareTo("P382") == 0) {
					values.addAll(getFullSynonym(column,conceptAxioms));
				} else if (col_display.compareTo("subsource_code") == 0 || col_display.compareTo("P385") == 0) {
					values.addAll(getFullSynonym(column,conceptAxioms));

				} else {
					List <EvsProperty> asso_conceptProperties = assocConcept.getProperties();
					values.addAll(EVSUtils.getProperty(col_property, asso_conceptProperties));
				}
			}
		} catch (Exception ex) {

		}
		return values;
	}


    public static void dumpList(String label, List list) {
		if (list == null) return;
		System.out.println("\n" + label + ":");
		if (list == null) return;
		if (list.size() == 0) {
			System.out.println("\tNone");
			return;
		}
        for (int i=0; i<list.size(); i++) {
			String t = (String) list.get(i);
			int j = i+1;
			System.out.println("\t(" + j + ") " + t);
		}
		System.out.println("\n");
	}

    public boolean compareQualifierValues(String value1, String value2) {
		if (value1 == null) return true; // no comparison needed
		if (value2 == null) return false;
		return value2.equals(value1);
	}

	public ArrayList <String> getMatchedValuesFromFullSynonyms(List<EvsAxiom> axioms, String qualifier,
	    String termGroup, String termSource, String sourceCode, String subsourceName) {
		ArrayList<String> values = new ArrayList<String>();

//10092020
		if (qualifier.equals("termName")) {
			for (EvsAxiom axiom: axioms) {
			    boolean matched =
			        compareQualifierValues(sourceCode, axiom.getSourceCode()) &&
			        compareQualifierValues(termSource, axiom.getTermSource()) &&
			        compareQualifierValues(subsourceName, axiom.getSubsourceName());
			    if (matched && axiom.getAnnotatedTarget() != null) {
					values.add(axiom.getAnnotatedTarget());
				}
			}

		} else if (qualifier.equals("termGroup")) {
			for (EvsAxiom axiom: axioms) {
			    boolean matched =
			        compareQualifierValues(sourceCode, axiom.getSourceCode()) &&
			        compareQualifierValues(termSource, axiom.getTermSource()) &&
			        compareQualifierValues(subsourceName, axiom.getSubsourceName());
			    if (matched && axiom.getTermGroup() != null) {
					values.add(axiom.getTermGroup());
				}

			}
		} else if (qualifier.equals("sourceCode")) {
			for (EvsAxiom axiom: axioms) {
			    boolean matched =
			        compareQualifierValues(termGroup, axiom.getTermGroup()) &&
			        //compareQualifierValues(sourceCode, axiom.getSourceCode()) &&
			        compareQualifierValues(termSource, axiom.getTermSource()) &&
			        compareQualifierValues(subsourceName, axiom.getSubsourceName());
			    if (matched && axiom.getSourceCode() != null) {
					values.add(axiom.getSourceCode());
				}

			}
		} else if (qualifier.equals("termSource")) {
			for (EvsAxiom axiom: axioms) {
			    boolean matched =
			        compareQualifierValues(termGroup, axiom.getTermGroup()) &&
			        compareQualifierValues(sourceCode, axiom.getSourceCode()) &&
			        //compareQualifierValues(termSource, axiom.getTermSource()) &&
			        compareQualifierValues(subsourceName, axiom.getSubsourceName());

			    if (matched && axiom.getTermSource() != null) {
					values.add(axiom.getTermSource());
				}
			}
		} else if (qualifier.equals("subsourceName")) {
			for (EvsAxiom axiom: axioms) {
			    boolean matched =
			        compareQualifierValues(termGroup, axiom.getTermGroup()) &&
			        compareQualifierValues(sourceCode, axiom.getSourceCode()) &&
			        compareQualifierValues(termSource, axiom.getTermSource());
			        //compareQualifierValues(subsourceName, axiom.getSubsourceName());

			    if (matched && axiom.getSubsourceName() != null) {
					values.add(axiom.getSubsourceName());
				}
			}
		}
		return values;
	}


	/**
	 * Find FullSynonym axioms that match the search criteria.
	 *
	 * @param column Template column definiton.
	 * @param axioms List of concept axioms
	 * @return List of full synonyms that match the search criteria.
	 */
	public ArrayList <String> getFullSynonym(TemplateColumn column, List<EvsAxiom> axioms) {
		ArrayList <String> values = new ArrayList<String>();

		List <EvsAxiom>axiomsKeep = new ArrayList<EvsAxiom>(axioms);
		String property = column.getProperty();
		String termSource = column.getSource();
		String termGroup = column.getGroup();
		String subsource = column.getSubsource();
		String attr = column.getAttr();
		String display = column.getDisplay();

/*
- columnNumber: 11
  label: "Belongs to Variable"
  display: "subsource_code"
  propertyType: "Is_PCDC_AML_Permissible_Value_For_Variable"
  property: "P385"
  source: "PCDC"
  group: "PT"
  subsource: "AML"
  attr: null
*/

		if (property.compareTo("property") == 0 && display.compareTo("subsource_code") == 0) {
			values = getMatchedValuesFromFullSynonyms(axioms, "sourceCode", termGroup, termSource, null, subsource);

		} else if (property.compareTo("P385") == 0 && display.compareTo("subsource_code") == 0) {
			values = getMatchedValuesFromFullSynonyms(axioms, "sourceCode", termGroup, termSource, null, subsource);

		} else if (display.compareTo("property") == 0 && (property.compareTo("P382") == 0 || property.compareTo("term-name") == 0)) {
			values = getMatchedValuesFromFullSynonyms(axioms, "termName", termGroup, termSource, null, subsource);

		} else {
			List <EvsAxiom>axioms1 = new ArrayList<EvsAxiom>();
			if (property != null) {
				for (EvsAxiom axiom: axiomsKeep) {
					if (property.equals(axiom.getAnnotatedProperty())) {
						axioms1.add(axiom);
					}
				}
				axiomsKeep = new ArrayList<EvsAxiom>(axioms1);
			}
			List <EvsAxiom>axioms2 = new ArrayList<EvsAxiom>();
			if (termSource != null) {
				for (EvsAxiom axiom: axiomsKeep) {
					if (termSource.equals(axiom.getTermSource())) {
						axioms2.add(axiom);
					}
				}
				axiomsKeep = new ArrayList<EvsAxiom>(axioms2);
			}

			List <EvsAxiom>axioms3 = new ArrayList<EvsAxiom>();
			if (termGroup != null) {
				for (EvsAxiom axiom: axiomsKeep) {
					if (termGroup.equals(axiom.getTermGroup())) {
						axioms3.add(axiom);
					}
				}
				axiomsKeep = new ArrayList<EvsAxiom>(axioms3);
			}

			List <EvsAxiom>axioms4 = new ArrayList<EvsAxiom>();
			if (subsource != null) {
				for (EvsAxiom axiom: axiomsKeep) {
					if (subsource.equals(axiom.getSubsourceName())) {
						axioms4.add(axiom);
					}
				}
				axiomsKeep = new ArrayList<EvsAxiom>(axioms4);
			}



			List <EvsAxiom>axioms5 = new ArrayList<EvsAxiom>();
			if (attr != null) {
				for (EvsAxiom axiom: axiomsKeep) {
					if (attr.equals(axiom.getAttr())) {
						axioms5.add(axiom);
					}
				}
				axiomsKeep = new ArrayList<EvsAxiom>(axioms5);
			}

			for (EvsAxiom axiom: axiomsKeep) {
				String sourcecode = axiom.getSourceCode();
				if (display.equals("property")) {
					values.add(axiom.getAnnotatedTarget());
				} else if (display.equals("subsource_code")) {
					if (axiom.getSourceCode() != null) {
						values.add(axiom.getSourceCode());
					}
				} else {
				  // Don't do anything at this time
				}
			}
		}
		return values;
	}

	/**
	 * Find Definition axioms that match the search criteria.
	 *
	 * @param column Template column definiton.
	 * @param axioms List of concept axioms
	 * @return List of definitions that match the search criteria.
	 */
	public ArrayList <String> getDefinition(TemplateColumn column,List <EvsAxiom> axioms) {
		ArrayList <String> values = new ArrayList<String>();

		List <EvsAxiom>axiomsKeep = new ArrayList<EvsAxiom>(axioms);
		String property = column.getProperty();
		String defSource = column.getSource();
		String display = column.getDisplay();

		/*
		 * This is a Hack, John C. August 31, 2018
		 * Originaly the templates did not include the "attr" property as an option
		 * So for definitions, this information was entered in the Group column.
		 * For the Aug 31 changes, the "attr" column was added, so for now we will
		 * keep backword compatibility by looking in both the "attr" and "group column,
		 * in case "group" was used in existing templates.
		 */
		String attr = column.getAttr();
		if (attr == null) {
			attr = column.getGroup();
		}

		List <EvsAxiom>axioms1 = new ArrayList<EvsAxiom>();
		if (property != null) {
			for (EvsAxiom axiom: axiomsKeep) {
			    if (property.equals(axiom.getAnnotatedProperty())) {
			    	axioms1.add(axiom);
			    }
			}
			axiomsKeep = new ArrayList<EvsAxiom>(axioms1);
		}

		List <EvsAxiom>axioms2 = new ArrayList<EvsAxiom>();
		if (defSource != null) {
			for (EvsAxiom axiom: axiomsKeep) {
			    if (defSource.equals(axiom.getDefSource())) {
			    	axioms2.add(axiom);
			    }
			}
			axiomsKeep = new ArrayList<EvsAxiom>(axioms2);
		}

		List <EvsAxiom>axioms3 = new ArrayList<EvsAxiom>();
		if (attr != null) {
			for (EvsAxiom axiom: axiomsKeep) {
			    if (attr.equals(axiom.getAttr())) {
			    	axioms3.add(axiom);
			    }
			}
			axiomsKeep = new ArrayList<EvsAxiom>(axioms3);
		}

		for (EvsAxiom axiom: axiomsKeep) {
			if (display.equals("property")) {
				values.add(axiom.getAnnotatedTarget());
			} else if (display.equals("subsource_code")) {
				values.add(axiom.getSourceCode());
			} else {
			  // Don't do anything at this time
			}
		}
		return values;
	}

	/**
	 * Find DbXref axioms that match the search criteria.
	 *
	 * @param column Template column definiton.
	 * @param axioms List of concept axioms
	 * @return List of definitions that match the search criteria.
	 */
	public ArrayList <String> getDbXref(TemplateColumn column,List <EvsAxiom> axioms) {
		ArrayList <String> values = new ArrayList<String>();

		List <EvsAxiom>axiomsKeep = new ArrayList<EvsAxiom>(axioms);
		String property = column.getProperty();
		String defSource = column.getSource();
		String attr = column.getGroup();
		String display = column.getDisplay();


		List <EvsAxiom>axioms1 = new ArrayList<EvsAxiom>();
		if (property != null) {
			for (EvsAxiom axiom: axiomsKeep) {
			    if (property.equals(axiom.getAnnotatedProperty())) {
			    	axioms1.add(axiom);
			    }
			}
			axiomsKeep = new ArrayList<EvsAxiom>(axioms1);
		}


		List <EvsAxiom>axioms2 = new ArrayList<EvsAxiom>();
		if (defSource != null) {
			for (EvsAxiom axiom: axiomsKeep) {
			    if (defSource.equals(axiom.getXrefSource())) {
			    	axioms2.add(axiom);
			    }
			}
			axiomsKeep = new ArrayList<EvsAxiom>(axioms2);
		}

		List <EvsAxiom>axioms3 = new ArrayList<EvsAxiom>();
		if (attr != null) {
			for (EvsAxiom axiom: axiomsKeep) {
			    if (attr.equals(axiom.getAttr())) {
			    	axioms3.add(axiom);
			    }
			}
			axiomsKeep = new ArrayList<EvsAxiom>(axioms3);
		}

		for (EvsAxiom axiom: axiomsKeep) {
			if (display.equals("property")) {
				values.add(axiom.getAnnotatedTarget());
			} else if (display.equals("subsource_code")) {
				values.add(axiom.getSourceCode());
			} else {
			  // Don't do anything at this time
			}
		}

		return values;
	}


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Run the getAssociatedConcepts query and then process the report based on the report template.
	 *
	 * @param reportOutput ReportOutput contains the data retrieved from the SPARQL queries and processing.
	 * @param rootConcept Root Concept.
	 * @param conceptHash ConceptHash cache to improve performance.
	 * @param templateColumns TemplateColumns contain template definitions for each column.
	 */

	public void processAssociatedConcepts(Report reportOutput, EvsConcept rootConcept, HashMap<String,EvsConcept> conceptHash, List <TemplateColumn> templateColumns,PrintWriter logFile, String namedGraph, String restURL, String associationName, boolean sourceOf) {
		if (rootConcept == null) {
			conceptHash = new HashMap();
			processAssociatedConcepts(reportOutput, conceptHash, templateColumns, logFile, namedGraph, restURL, associationName, sourceOf);
		} else {
			String rootConceptCode = rootConcept.getCode();
			if (rootConceptCode == null) {
				conceptHash = new HashMap();
				processAssociatedConcepts(reportOutput, conceptHash, templateColumns, logFile, namedGraph, restURL, associationName, sourceOf);
			} else {
				List <EvsConcept> associatedConcepts = sparqlQueryManagerService.getAssociatedEvsConcepts(rootConcept.getCode(), namedGraph, restURL, associationName, sourceOf);
				if (rootConcept != null) {
					log.info("Concept: " + rootConcept.getCode() + " Number of associations: " + associatedConcepts.size());
					logFile.println("Concept: " + rootConcept.getCode() + " Number of associations: " + associatedConcepts.size());
				}
				int total = 0;
				for (EvsConcept concept: associatedConcepts) {
					total += 1;
					if (total % 100 == 0) {
						log.info("Number of associations processed: " + total);
						System.out.println("Number of associations processed: " + total);
						logFile.println("Number of associations processed: " + total);
					}
					if (conceptHash.containsKey(concept.getCode())) {
						concept = conceptHash.get(concept.getCode());
					} else {
						try {
							concept.setProperties(sparqlQueryManagerService.getEvsProperties(concept.getCode(), namedGraph, restURL));
							concept.setAxioms(sparqlQueryManagerService.getEvsAxioms(concept.getCode(), namedGraph, restURL));
							conceptHash.put(concept.getCode(), concept);
						} catch (Exception ex) {
							System.out.println("Populating data error???");
						}
					}
					try {
						writeColumnData(reportOutput,rootConcept,concept,conceptHash,templateColumns,namedGraph,restURL);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
		    }
	    }
	}

	public HashMap getRoot2AssociatedConceptHashMap(List <EvsAssociation> evsAssociations) {
		HashMap root2AssociatedConceptHashMap = new HashMap();
		//String keyset = new HashSet();
		for (EvsAssociation evsAssociation: evsAssociations) {
			String key = evsAssociation.getSourceName() + "|" + evsAssociation.getSourceCode();
			List list = new ArrayList();
			if (root2AssociatedConceptHashMap.containsKey(key)) {
				list = (List) root2AssociatedConceptHashMap.get(key);
			}
			EvsConcept concept = new EvsConcept();
			concept.setCode(evsAssociation.getTargetCode());
			concept.setLabel(evsAssociation.getTargetName());
			list.add(concept);
			root2AssociatedConceptHashMap.put(key, list);
		}
		return root2AssociatedConceptHashMap;
    }

	public void processAssociatedConcepts(Report reportOutput, HashMap<String,EvsConcept> conceptHash, List <TemplateColumn> templateColumns,PrintWriter logFile, String namedGraph, String restURL, String associationName, boolean sourceOf) {
        try {
			List <EvsAssociation> evsAssociations = sparqlQueryManagerService.getEvsAssociations(namedGraph, restURL, associationName, sourceOf);
			HashMap root2AssociatedConceptHashMap = getRoot2AssociatedConceptHashMap(evsAssociations);
			ParserUtils parser = new ParserUtils();
			conceptHash = new HashMap();
			int total = 0;
			Iterator it = root2AssociatedConceptHashMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				Vector u = parser.parseData(key, '|');
				String name = (String) u.elementAt(0);
				String code = (String) u.elementAt(1);

				EvsConcept rootConcept = new EvsConcept();
				rootConcept = sparqlQueryManagerService.getEvsConceptDetailShort(code, namedGraph, restURL);
				rootConcept.setProperties(sparqlQueryManagerService.getEvsProperties(rootConcept.getCode(), namedGraph, restURL));
				rootConcept.setAxioms(sparqlQueryManagerService.getEvsAxioms(rootConcept.getCode(), namedGraph, restURL));

				List<EvsConcept> associatedConcepts = (List) root2AssociatedConceptHashMap.get(key);
				for (EvsConcept concept: associatedConcepts) {
					total += 1;
					if (total % 100 == 0) {
						log.info("Number of associations processed: " + total);
						System.out.println("Number of associations processed: " + total);
						logFile.println("Number of associations processed: " + total);
					}
					if (conceptHash.containsKey(concept.getCode())) {
						concept = conceptHash.get(concept.getCode());
					} else {
						concept.setProperties(sparqlQueryManagerService.getEvsProperties(concept.getCode(), namedGraph, restURL));
						concept.setAxioms(sparqlQueryManagerService.getEvsAxioms(concept.getCode(), namedGraph, restURL));
						conceptHash.put(concept.getCode(), concept);
					}
					try {
						writeColumnData(reportOutput,rootConcept,concept,conceptHash,templateColumns,namedGraph,restURL);
				    } catch (Exception ex) {
					    ex.printStackTrace();
				    }
				}
			}
		} catch (Exception ex) {

		}
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

    public static List <String> filterXrefCodes(String property, List <String> values, String label) {
		if (property.compareTo("hasDbXref") != 0) return values;
		if (label.compareToIgnoreCase("Xref Codes") == 0) return values;
		String label_lc = label.toLowerCase();
		Vector u = parseData(label_lc, ' ');
		String src = (String) u.elementAt(0);
        List <String> list = new ArrayList <String>();
        for (int i=0; i<values.size(); i++) {
			String code = values.get(i);
			String code_lc = code.toLowerCase();
			if (code_lc.startsWith(src)) {
				list.add(code);
			}
			//UBERON:0002481 || UBERON:0001474
			//IMDRF:E1901
		}
		return list;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    public Report loadReport(Vector data_vec, char delim) {
		if (data_vec == null || data_vec.size() == 0) return null;
		String heading = (String) data_vec.elementAt(0);
		Vector heading_vec = parseData(heading, delim);
		Report report = new Report();
		ArrayList<ReportRow> rows = new ArrayList<ReportRow>();
		for (int i=1; i<data_vec.size(); i++) {
			ReportRow row = new ReportRow();
			String line = (String) data_vec.elementAt(i);
			Vector u = parseData(line, delim);
			List<ReportColumn> columns = new ArrayList<ReportColumn>();
			for (int j=0; j<u.size(); j++) {
				ReportColumn column = new ReportColumn((String) heading_vec.elementAt(j),(String) u.elementAt(j));
				columns.add(column);
			}
			row.setColumns(columns);
			rows.add(row);
		}
		report.setRows(rows);
		return report;
	}

	private static CellStyle createWrapTextCellStyle(Workbook wb){
         CellStyle cs = wb.createCellStyle();
         cs.setWrapText( true );
         cs.setVerticalAlignment(VerticalAlignment.TOP);
         cs.setAlignment(HorizontalAlignment.LEFT);
         cs.setFillForegroundColor(IndexedColors.WHITE.getIndex());
         //cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         return cs;
    }

	public String reformat(ArrayList <String> columnHeadings, Vector data_vec, String outputFile, String version, String namedGraph, String restURL) {
        String outputFileText = outputFile + ".txt";
        String outputFileExcel = outputFile + ".xls";
        PrintWriter logFile = null;
        char delim = '\t';
        Report reportOutput = loadReport(data_vec, delim);
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

        CellStyle wrapTextCellStyle = createWrapTextCellStyle(wb);
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(outputFileText)),StandardCharsets.UTF_8),true);

		   	Row excelRow = sheet.createRow(rowIndex++);
         	Cell versionCell = excelRow.createCell(0);
	        versionCell.setCellValue("Version: " + version);//evsVersionInfo.getVersion());
	        versionCell.setCellStyle(headerStyle);
         	Cell graphCell = excelRow.createCell(1);
	        graphCell.setCellValue("NamedGraph: " + namedGraph);
	        graphCell.setCellStyle(headerStyle);
	        Cell databaseCell = excelRow.createCell(2);
	        databaseCell.setCellValue("REST URL: " + restURL);
	        databaseCell.setCellStyle(headerStyle);

			//ArrayList <String> columnHeadings = new ArrayList <String>();
	       	excelRow = sheet.createRow(rowIndex++);
	       	int cellIndex = 0;
        	for (int k=0; k<columnHeadings.size(); k++) {
				String label = columnHeadings.get(k);
		       	Cell cell = excelRow.createCell(cellIndex++);
		        cell.setCellValue(label);
		        cell.setCellStyle(headerStyle);
		        //cell.setCellStyle(wrapTextCellStyle);
        	}

            pw.write(String.join("\t",columnHeadings) + "\n");
            ArrayList <ReportRow> reportRows = reportOutput.getRows();
            for (ReportRow row: reportRows) {
            	excelRow = sheet.createRow(rowIndex++);
        	    ArrayList <String> values = new ArrayList <String>();
        	    cellIndex = 0;
        	    for (ReportColumn column: row.getColumns()) {
					String column_value = column.getValue();
                    values.add(column.getValue());
                    Cell cell =  excelRow.createCell(cellIndex++);
					cell.setCellStyle(wrapTextCellStyle);
					column_value = column_value.replace(" || ", "\n");
					column_value = column_value.trim();
				    cell.setCellValue(column_value);
        	    }
        	    String line = String.join("\t",values);
        	    pw.write(line + "\n");
            }

            for (int i = 0; i < columnHeadings.size(); i++) {
            	sheet.autoSizeColumn(i);
            }

			try {
				FileOutputStream fileOut = new FileOutputStream(outputFileExcel);
				wb.write(fileOut);
				fileOut.close();
				wb.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

        } catch (FileNotFoundException e) {
        	System.err.println("File Not Found Exception");
        	return "failure";
        } catch (Exception e) {
        	System.err.println("IOException");
        	return "failure";
        } finally {
    	    if (pw != null) {
    		    pw.close();
    	    }
        }
        System.out.println("Completed: " + LocalDateTime.now());
		return "success";
	}


	public static String getCDISCSubmissionValue(List<EvsAxiom> parentAxioms, List<EvsAxiom> axioms) {
		String termSource = "CDISC";
		String termType = "PT";
		String sourceCode = null;
		String subsourceName = null;
	    List<String> cdisc_pts = EVSUtils.getSynonymsWithQualifiers(axioms,
	       termSource, termType, sourceCode, subsourceName);

	    if (cdisc_pts == null || cdisc_pts.size() == 0) {
			System.out.println("WARNING: getCDISCSubmissionValue No CDISC PT is found.");
			return "CDISC PT not found???";
		} else if (cdisc_pts.size() == 1) {
			return cdisc_pts.get(0);
		}

		termSource = "NCI";
		termType = "AB";
		sourceCode = null;
		subsourceName = null;
	    List<String> nci_abs = EVSUtils.getSynonymsWithQualifiers(parentAxioms,
	       termSource, termType, sourceCode, subsourceName);
	    if (nci_abs == null || nci_abs.size() == 0) {
			System.out.println("WARNING: getCDISCSubmissionValue No NCI AB is found.");
			return "NCI AB not found???";
		}

	    String nci_ab = nci_abs.get(0);
		termSource = "CDISC";
		termType = "PT";
		sourceCode = nci_ab;
		subsourceName = null;
	    List<String> results = EVSUtils.getSynonymsWithQualifiers(axioms,
	       termSource, termType, sourceCode, subsourceName);
	    return results.get(0);
	}
}


