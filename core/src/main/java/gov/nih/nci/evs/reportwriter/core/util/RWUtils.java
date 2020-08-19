package gov.nih.nci.evs.reportwriter.core.util;

import gov.nih.nci.evs.reportwriter.core.service.*;
import gov.nih.nci.evs.reportwriter.core.configuration.*;

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
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsProperty;
import gov.nih.nci.evs.reportwriter.core.model.report.Report;
import gov.nih.nci.evs.reportwriter.core.model.report.ReportColumn;
import gov.nih.nci.evs.reportwriter.core.model.report.ReportRow;
import gov.nih.nci.evs.reportwriter.core.model.template.TemplateColumn;
import gov.nih.nci.evs.reportwriter.core.service.SparqlQueryManagerService;
import gov.nih.nci.evs.reportwriter.core.service.SparqlQueryManagerServiceImpl;

@Service
/**
 *
 * Helper methods for running a report based on a Report Template.
 *
 */
public class RWUtils {
	private static HashMap associationLabel2CodeHashMap = null;
	static {
		associationLabel2CodeHashMap = new HashMap();
		associationLabel2CodeHashMap.put("Concept_In_Subset", "A8");
		associationLabel2CodeHashMap.put("Has_CDRH_Parent", "A10");
		associationLabel2CodeHashMap.put("Has_CTCAE_5_Parent", "A15");
		associationLabel2CodeHashMap.put("Has_Data_Element", "A12");
		associationLabel2CodeHashMap.put("Has_Free_Acid_Or_Base_Form", "A6");
		associationLabel2CodeHashMap.put("Has_INC_Parent", "A16");
		associationLabel2CodeHashMap.put("Has_NICHD_Parent", "A11");
		associationLabel2CodeHashMap.put("Has_PCDC_Data_Type", "A23");
		associationLabel2CodeHashMap.put("Has_Pharmaceutical_Administration_Method", "A19");
		associationLabel2CodeHashMap.put("Has_Pharmaceutical_Basic_Dose_Form", "A18");
		associationLabel2CodeHashMap.put("Has_Pharmaceutical_Intended_Site", "A20");
		associationLabel2CodeHashMap.put("Has_Pharmaceutical_Release_Characteristics", "A21");
		associationLabel2CodeHashMap.put("Has_Pharmaceutical_State_Of_Matter", "A17");
		associationLabel2CodeHashMap.put("Has_Pharmaceutical_Transformation", "A22");
		associationLabel2CodeHashMap.put("Has_Salt_Form", "A5");
		associationLabel2CodeHashMap.put("Has_Target", "A7");
		associationLabel2CodeHashMap.put("Is_PCDC_AML_Permissible_Value_For", "A24");
		associationLabel2CodeHashMap.put("Is_Related_To_Endogenous_Product", "A9");
		associationLabel2CodeHashMap.put("Neoplasm_Has_Special_Category", "A14");
		associationLabel2CodeHashMap.put("Qualifier_Applies_To", "A4");
		associationLabel2CodeHashMap.put("Related_To_Genetic_Biomarker", "A13");
		associationLabel2CodeHashMap.put("Role_Has_Domain", "A1");
		associationLabel2CodeHashMap.put("Role_Has_Parent", "A3");
		associationLabel2CodeHashMap.put("Role_Has_Range", "A2");
		associationLabel2CodeHashMap.put("Value_Set_Is_Paired_With", "A25");
		associationLabel2CodeHashMap.put("Has_PCDC_AML_Permissible_Value", "A26");
		associationLabel2CodeHashMap.put("Has_CTDC_Value", "A27");
	};
	private static final Logger log = LoggerFactory.getLogger(RWUtils.class);

	@Autowired
	SparqlQueryManagerService sparqlQueryManagerService;

	public RWUtils(SparqlQueryManagerService sparqlQueryManagerService) {
		this.sparqlQueryManagerService = sparqlQueryManagerService;
	}

	/**
	 * Run the ConceptInSubset query and then process the report based on the report template.
	 *
	 * @param reportOutput ReportOutput contains the data retrieved from the SPARQL queries and processing.
	 * @param rootConcept Root Concept.
	 * @param conceptHash ConceptHash cache to improve performance.
	 * @param templateColumns TemplateColumns contain template definitions for each column.
	 */
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
			writeColumnData(reportOutput,rootConcept,concept,conceptHash,templateColumns,namedGraph,restURL);
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
				writeColumnData(reportOutput,rootConcept,concept,conceptHash,templateColumns,namedGraph,restURL);
				processConceptInSubset(reportOutput, concept, conceptHash, templateColumns, currentLevel+1, maxLevel, logFile, namedGraph, restURL);
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
				writeColumnData(reportOutput,parentConcept,subclass,conceptHash,templateColumns,namedGraph,restURL);
				processConceptSubclassesOnly(reportOutput,subclass,conceptHash,templateColumns,currentLevel + 1,maxLevel,logFile, namedGraph, restURL);
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
				writeColumnData(reportOutput,concept,concept,conceptHash,templateColumns,namedGraph,restURL);

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
		for (TemplateColumn column: templateColumns) {
			String propertyType = column.getPropertyType();
			String property = column.getProperty();
			String columnString = "";
			List <String> values = new ArrayList <String>();
			List <EvsProperty> conceptProperties = concept.getProperties();
			List <EvsAxiom> conceptAxioms = concept.getAxioms();
			if (propertyType.equals("code")) {
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
				values = EVSUtils.getProperty(property, parentConcept.getProperties());
			} else if (propertyType.equals("Associated Concept Property")) {
				if (property.equals("P90")) {
					values = getFullSynonym(column,parentConcept.getAxioms());
				} else {
					values = EVSUtils.getProperty(property, parentConcept.getProperties());
				}
////KLO 11/26/2019 ///////////////////////////////////////////////////////////////////////////////////////////
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
    			List <EvsAxiom> axioms = concept.getAxioms();
    			for (EvsAxiom axiom: axioms) {
    				List <String> v = new ArrayList <String> ();
    				if (axiom.getAnnotatedProperty().equals("P375")) {
   				    	v.add(axiom.getAnnotatedTarget());
       		    		v.add(axiom.getTargetTerminology());
       		    		//[EVSREPORT2-36] Adding Target_Terminology_Version Qualifier
       		    		v.add(axiom.getTargetTerminologyVersion());
   				    	v.add(axiom.getRelationshipToTarget());
  	    		    	v.add(axiom.getTargetTermType());
   	    	    		v.add(axiom.getTargetCode());
    					String vv = String.join(propertySepartor, v);
    					values.add(vv);
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
			/*
			} else if (propertyType.equals("Pharmaceutical_State_Of_Matter")) {
				values = processAssociation("A17", conceptProperties, property, column, conceptHash, namedGraph, restURL);
			} else if (propertyType.equals("Pharmaceutical_Basic_Dose_Form")) {
				values = processAssociation("A18", conceptProperties, property, column, conceptHash, namedGraph, restURL);
			} else if (propertyType.equals("Pharmaceutical_Administration_Method")) {
				values = processAssociation("A19", conceptProperties, property, column, conceptHash, namedGraph, restURL);
			} else if (propertyType.equals("Pharmaceutical_Intended_Site")) {
				values = processAssociation("A20", conceptProperties, property, column, conceptHash, namedGraph, restURL);
			} else if (propertyType.equals("Pharmaceutical_Release_Characteristics")) {
				values = processAssociation("A21", conceptProperties, property, column, conceptHash, namedGraph, restURL);
			} else if (propertyType.equals("Pharmaceutical_Transformation")) {
				values = processAssociation("A22", conceptProperties, property, column, conceptHash, namedGraph, restURL);
			} else {
			  // Don't do anything for now
			}
			*/

			} else if (associationLabel2CodeHashMap.containsKey(propertyType)) {
				String associationCode = (String) associationLabel2CodeHashMap.get(propertyType);
				values = processAssociation(associationCode, conceptProperties, property, column, conceptHash, namedGraph, restURL);
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
	}

/*
	public List <String> processAssociation(String associationCode, List <EvsProperty> conceptProperties,
			String property, TemplateColumn column, HashMap<String,EvsConcept> conceptHash, String namedGraph, String restURL) {
		List <String> values = new ArrayList <String>();
		List <String> properties = EVSUtils.getProperty(associationCode, conceptProperties);
		for (int i = 0; i < properties.size(); i++ ) {
			String code = properties.get(i).replaceAll("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#","");
			EvsConcept assocConcept = null;
			if (conceptHash.containsKey(code)) {
				assocConcept = conceptHash.get(code);
			} else {
	            assocConcept = sparqlQueryManagerService.getEvsConceptDetailShort(code,namedGraph,restURL);
	            assocConcept.setProperties(sparqlQueryManagerService.getEvsProperties(code,namedGraph,restURL));
	            assocConcept.setAxioms(sparqlQueryManagerService.getEvsAxioms(code,namedGraph,restURL));
	            conceptHash.put(code, assocConcept);
			}
			if (property.equals("P90")) {
				values.addAll(getFullSynonym(column,assocConcept.getAxioms()));
			} else {
				System.err.println("Association Property Not Supported");
			}
		}
		return values;
	}
*/


	public List <String> processAssociation(String associationCode, List <EvsProperty> conceptProperties,
			String property, TemplateColumn column, HashMap<String,EvsConcept> conceptHash, String namedGraph, String restURL) {
		List <String> values = new ArrayList <String>();
		List <String> properties = EVSUtils.getProperty(associationCode, conceptProperties);
		for (int i = 0; i < properties.size(); i++ ) {
			String code = properties.get(i).replaceAll("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#","");
			EvsConcept assocConcept = null;
			if (conceptHash.containsKey(code)) {
				assocConcept = conceptHash.get(code);
			} else {
	            assocConcept = sparqlQueryManagerService.getEvsConceptDetailShort(code,namedGraph,restURL);
	            assocConcept.setProperties(sparqlQueryManagerService.getEvsProperties(code,namedGraph,restURL));
	            assocConcept.setAxioms(sparqlQueryManagerService.getEvsAxioms(code,namedGraph,restURL));
	            conceptHash.put(code, assocConcept);
			}
			String col_property = column.getProperty();
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
			} else {
				List <EvsProperty> asso_conceptProperties = assocConcept.getProperties();
				values.addAll(EVSUtils.getProperty(col_property, asso_conceptProperties));
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

		/*
		try {
            ObjectMapper writer = new ObjectMapper();
            System.out.println(writer.writerWithDefaultPrettyPrinter().writeValueAsString(axiomsKeep));
        } catch (Exception ex){
        	System.err.println(ex);
        }
        */

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
					//System.out.println("Concept: " + rootConcept.getCode() + " Number of associations: " + associatedConcepts.size());
					logFile.println("Concept: " + rootConcept.getCode() + " Number of associations: " + associatedConcepts.size());
				}
				int total = 0;
				for (EvsConcept concept: associatedConcepts) {
					//System.out.println(concept.getCode() + " " + concept.getLabel());
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
					writeColumnData(reportOutput,rootConcept,concept,conceptHash,templateColumns,namedGraph,restURL);
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
				//rootConcept.setCode(code);
				//rootConcept.setLabel(name);
				rootConcept = sparqlQueryManagerService.getEvsConceptDetailShort(code, namedGraph, restURL);
				rootConcept.setProperties(sparqlQueryManagerService.getEvsProperties(rootConcept.getCode(), namedGraph, restURL));
				rootConcept.setAxioms(sparqlQueryManagerService.getEvsAxioms(rootConcept.getCode(), namedGraph, restURL));

				List<EvsConcept> associatedConcepts = (List) root2AssociatedConceptHashMap.get(key);
				for (EvsConcept concept: associatedConcepts) {
					//System.out.println(concept.getCode() + " " + concept.getLabel());
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
					writeColumnData(reportOutput,rootConcept,concept,conceptHash,templateColumns,namedGraph,restURL);
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

}
