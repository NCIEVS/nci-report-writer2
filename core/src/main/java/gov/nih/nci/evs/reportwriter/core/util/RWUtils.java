package gov.nih.nci.evs.reportwriter.core.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nih.nci.evs.reportwriter.core.model.evs.EvsAxiom;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsConcept;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsProperty;
import gov.nih.nci.evs.reportwriter.core.model.report.Report;
import gov.nih.nci.evs.reportwriter.core.model.report.ReportColumn;
import gov.nih.nci.evs.reportwriter.core.model.report.ReportRow;
import gov.nih.nci.evs.reportwriter.core.model.template.TemplateColumn;
import gov.nih.nci.evs.reportwriter.core.service.SparqlQueryManagerService;

@Service
/**
 * 
 * Helper methods for running a report based on a Report Template.
 *
 */
public class RWUtils {
	
	private static final Logger log = LoggerFactory.getLogger(RWUtils.class);
	
	@Autowired
	SparqlQueryManagerService sparqlQueryManagerService;
	
	public RWUtils() {}
	
	/**
	 * Run the ConceptInSubset query and then process the report based on the report template.
	 * 
	 * @param reportOutput ReportOutput contains the data retrieved from the SPARQL queries and processing.
	 * @param rootConcept Root Concept.
	 * @param conceptHash ConceptHash cache to improve performance.
	 * @param templateColumns TemplateColumns contain template definitions for each column.
	 */
	public void processConceptInSubset(Report reportOutput, EvsConcept rootConcept, HashMap<String,EvsConcept> conceptHash, List <TemplateColumn> templateColumns,PrintWriter logFile, String namedGraph ) {
		
		List <EvsConcept> associatedConcepts = sparqlQueryManagerService.getEvsConceptInSubset(rootConcept.getCode(), namedGraph);
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
				concept.setProperties(sparqlQueryManagerService.getEvsProperties(concept.getCode(), namedGraph));
				concept.setAxioms(sparqlQueryManagerService.getEvsAxioms(concept.getCode(), namedGraph));
				conceptHash.put(concept.getCode(), concept);
			}
			writeColumnData(reportOutput,rootConcept,concept,conceptHash,templateColumns,namedGraph);
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
	public void processConceptSubclasses(Report reportOutput, EvsConcept parentConcept, HashMap<String,EvsConcept> conceptHash, List <TemplateColumn> templateColumns, PrintWriter logFile, String namedGraph) {
		List <EvsConcept> subclasses = sparqlQueryManagerService.getEvsSubclasses(parentConcept.getCode(), namedGraph);
		log.info("Parent Concept: " + parentConcept.getCode() + " Number of Subclasses: " + subclasses.size());
		System.out.println("Parent Concept: " + parentConcept.getCode() + " Number of Subclasses: " + subclasses.size());
		logFile.println("Parent Concept: " + parentConcept.getCode() + " Number of Subclasses: " + subclasses.size());
		for (EvsConcept subclass: subclasses) {
			if (conceptHash.containsKey(subclass.getCode())) {
				subclass = conceptHash.get(subclass.getCode());
			} else {
				subclass.setProperties(sparqlQueryManagerService.getEvsProperties(subclass.getCode(), namedGraph));
				subclass.setAxioms(sparqlQueryManagerService.getEvsAxioms(subclass.getCode(), namedGraph));
				conceptHash.put(subclass.getCode(), subclass);
			}
			processConceptInSubset(reportOutput,subclass,conceptHash,templateColumns,logFile, namedGraph);
			processConceptSubclasses(reportOutput,subclass,conceptHash,templateColumns,logFile, namedGraph);
		}
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
	public void processConceptSubclassesOnly(Report reportOutput,EvsConcept parentConcept,HashMap<String,EvsConcept> conceptHash,List <TemplateColumn> templateColumns, int currentLevel, int maxLevel, PrintWriter logFile, String namedGraph) {
		if (currentLevel < maxLevel) {
			List <EvsConcept> subclasses = sparqlQueryManagerService.getEvsSubclasses(parentConcept.getCode(), namedGraph);
			log.info("Parent Concept: " + parentConcept.getCode() + " Number of Subclasses: " + subclasses.size());
			System.out.println("Parent Concept: " + parentConcept.getCode() + " Number of Subclasses: " + subclasses.size());
			logFile.println("Parent Concept: " + parentConcept.getCode() + " Number of Subclasses: " + subclasses.size());
			for (EvsConcept subclass: subclasses) {
				log.debug(subclass.getCode());
				if (conceptHash.containsKey(subclass.getCode())) {
					subclass = conceptHash.get(subclass.getCode());
				} else {
					subclass.setProperties(sparqlQueryManagerService.getEvsProperties(subclass.getCode(), namedGraph));
					subclass.setAxioms(sparqlQueryManagerService.getEvsAxioms(subclass.getCode(), namedGraph));
					conceptHash.put(subclass.getCode(), subclass);
				}
				writeColumnData(reportOutput,parentConcept,subclass,conceptHash,templateColumns,namedGraph);
				processConceptSubclassesOnly(reportOutput,subclass,conceptHash,templateColumns,currentLevel + 1,maxLevel,logFile, namedGraph);
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
	public void processConceptList(Report reportOutput, HashMap<String,EvsConcept> conceptHash, List <TemplateColumn> templateColumns, String conceptFile, PrintWriter logFile, String namedGraph ) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(conceptFile));
			String line;
			int total = 0;
			while ((line = br.readLine()) != null) {
		        EvsConcept concept = sparqlQueryManagerService.getEvsConceptDetailShort(line,namedGraph);	
				concept.setProperties(sparqlQueryManagerService.getEvsProperties(concept.getCode(),namedGraph));
				concept.setAxioms(sparqlQueryManagerService.getEvsAxioms(concept.getCode(),namedGraph));
				writeColumnData(reportOutput,concept,concept,conceptHash,templateColumns,namedGraph);
				
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
	public void writeColumnData(Report reportOutput, EvsConcept parentConcept, EvsConcept concept, HashMap<String,EvsConcept> conceptHash, List <TemplateColumn> templateColumns, String namedGraph) {
		ReportRow reportRow = new ReportRow();
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
			            nichdParentConcept = sparqlQueryManagerService.getEvsConceptDetailShort(code,namedGraph);	
			            nichdParentConcept.setProperties(sparqlQueryManagerService.getEvsProperties(code,namedGraph));
			            nichdParentConcept.setAxioms(sparqlQueryManagerService.getEvsAxioms(code,namedGraph));	
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
			            nichdParentConcept = sparqlQueryManagerService.getEvsConceptDetailShort(code,namedGraph);	
			            nichdParentConcept.setProperties(sparqlQueryManagerService.getEvsProperties(code,namedGraph));
			            nichdParentConcept.setAxioms(sparqlQueryManagerService.getEvsAxioms(code,namedGraph));	
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
			            nichdParentConcept = sparqlQueryManagerService.getEvsConceptDetailShort(code,namedGraph);	
			            nichdParentConcept.setProperties(sparqlQueryManagerService.getEvsProperties(code,namedGraph));
			            nichdParentConcept.setAxioms(sparqlQueryManagerService.getEvsAxioms(code,namedGraph));	
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
			            nichdParentConcept = sparqlQueryManagerService.getEvsConceptDetailShort(code,namedGraph);	
			            nichdParentConcept.setProperties(sparqlQueryManagerService.getEvsProperties(code,namedGraph));
			            nichdParentConcept.setAxioms(sparqlQueryManagerService.getEvsAxioms(code,namedGraph));	
			            conceptHash.put(code, nichdParentConcept);
					}
					
					if (property.equals("P90")) {
						values = getFullSynonym(column,nichdParentConcept.getAxioms());
					} else {
						System.err.println("2nd CDRH Parent Property Not Supported");
					}
				}
			} else {
			  // Don't do anything for now	
			}

			columnString = String.join("|", values);
			String name = column.getLabel();
			ReportColumn reportColumn = new ReportColumn(name,columnString);
			reportRow.getColumns().add(reportColumn);
		}
		reportOutput.getRows().add(reportRow);
	}
	
	/**
	 * Find FullSynonym axioms that match the search criteria.
	 * 
	 * @param column Template column definiton.
	 * @param axioms List of concept axioms
	 * @return List of full synonyms that match the search criteria.
	 */
	public ArrayList <String> getFullSynonym(TemplateColumn column,List <EvsAxiom> axioms) {
		ArrayList <String> values = new ArrayList<String>();
		
		List <EvsAxiom>axiomsCopy = new ArrayList<EvsAxiom>(axioms);
		List <EvsAxiom>axiomsKeep = new ArrayList<EvsAxiom>();
		String property = column.getProperty();
		String termSource = column.getSource();
		String termGroup = column.getGroup();
		String subsource = column.getSubsource();
		String display = column.getDisplay();
		
		List <EvsAxiom>axioms1 = new ArrayList<EvsAxiom>();
		if (property != null) {
			for (EvsAxiom axiom: axiomsCopy) {
			    if (property.equals(axiom.getAnnotatedProperty())) {
			    	axioms1.add(axiom);
			    }
			}
			axiomsKeep = new ArrayList<EvsAxiom>(axioms1);
		}

		List <EvsAxiom>axioms2 = new ArrayList<EvsAxiom>();
		if (termSource != null) {
			for (EvsAxiom axiom: axioms1) {
			    if (termSource.equals(axiom.getTermSource())) {
			    	axioms2.add(axiom);
			    }
			}
			axiomsKeep = new ArrayList<EvsAxiom>(axioms2);
		}

		List <EvsAxiom>axioms3 = new ArrayList<EvsAxiom>();
		if (termGroup != null) {
			for (EvsAxiom axiom: axioms2) {
			    if (termGroup.equals(axiom.getTermGroup())) {
			    	axioms3.add(axiom);
			    }
			}
			axiomsKeep = new ArrayList<EvsAxiom>(axioms3);
		}

		List <EvsAxiom>axioms4 = new ArrayList<EvsAxiom>();
		if (subsource != null) {
			for (EvsAxiom axiom: axioms3) {
			    if (subsource.equals(axiom.getSubsourceName())) {
			    	axioms4.add(axiom);
			    }
			}
			axiomsKeep = new ArrayList<EvsAxiom>(axioms4);
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
		
		List <EvsAxiom>axiomsCopy = new ArrayList<EvsAxiom>(axioms);
		List <EvsAxiom>axiomsKeep = new ArrayList<EvsAxiom>();
		String property = column.getProperty();
		String defSource = column.getSource();
		String attr = column.getGroup();
		String display = column.getDisplay();

		
		List <EvsAxiom>axioms1 = new ArrayList<EvsAxiom>();
		if (property != null) {
			for (EvsAxiom axiom: axiomsCopy) {
			    if (property.equals(axiom.getAnnotatedProperty())) {
			    	axioms1.add(axiom);
			    }
			}
			axiomsKeep = new ArrayList<EvsAxiom>(axioms1);
		}


		List <EvsAxiom>axioms2 = new ArrayList<EvsAxiom>();
		if (defSource != null) {
			for (EvsAxiom axiom: axioms1) {
			    if (defSource.equals(axiom.getDefSource())) {
			    	axioms2.add(axiom);
			    }
			}
			axiomsKeep = new ArrayList<EvsAxiom>(axioms2);
		}

		List <EvsAxiom>axioms3 = new ArrayList<EvsAxiom>();
		if (attr != null) {
			for (EvsAxiom axiom: axioms2) {
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
		
		List <EvsAxiom>axiomsCopy = new ArrayList<EvsAxiom>(axioms);
		List <EvsAxiom>axiomsKeep = new ArrayList<EvsAxiom>();
		String property = column.getProperty();
		String defSource = column.getSource();
		String attr = column.getGroup();
		String display = column.getDisplay();

		
		List <EvsAxiom>axioms1 = new ArrayList<EvsAxiom>();
		if (property != null) {
			for (EvsAxiom axiom: axiomsCopy) {
			    if (property.equals(axiom.getAnnotatedProperty())) {
			    	axioms1.add(axiom);
			    }
			}
			axiomsKeep = new ArrayList<EvsAxiom>(axioms1);
		}


		List <EvsAxiom>axioms2 = new ArrayList<EvsAxiom>();
		if (defSource != null) {
			for (EvsAxiom axiom: axioms1) {
			    if (defSource.equals(axiom.getXrefSource())) {
			    	axioms2.add(axiom);
			    }
			}
			axiomsKeep = new ArrayList<EvsAxiom>(axioms2);
		}

		List <EvsAxiom>axioms3 = new ArrayList<EvsAxiom>();
		if (attr != null) {
			for (EvsAxiom axiom: axioms2) {
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
}
