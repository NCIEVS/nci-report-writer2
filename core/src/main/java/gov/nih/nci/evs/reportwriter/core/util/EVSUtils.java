package gov.nih.nci.evs.reportwriter.core.util;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.evs.reportwriter.core.model.evs.EvsAxiom;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsProperty;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsSynonym;

/**
 * 
 * Helper class for retrieving information from the Concept properties
 *
 */
public class EVSUtils {
	
	public EVSUtils() {}
	
	/**
	 * Return a property value if the code matches a property code.
	 * 
	 * @param code property code.
	 * @param properties List of properties.
	 * @return List of property values.
	 */
	public static List <String> getProperty(String code, List <EvsProperty>properties) {
		ArrayList <String> results = new ArrayList<String>();
		for ( EvsProperty property: properties) {
			if (property.getCode().equals(code)) {
				results.add(property.getValue());
			}
		}
		return results;
	}

	/**
	 * Return a concept code.
	 * 
	 * @param properties List of properties.
	 * @return Concept code.
	 */
	public static String getConceptCode(List <EvsProperty>properties) {
		List <String> results =  getProperty("NHC0",properties);
		if (results.size() == 0) {
			return null;
		} else {
			return results.get(0);
		}
	}

	/**
	 * Return a concept display name. 
	 * 
	 * @param properties List of properties.
	 * @return Concept display name.
	 */
	public static String getDisplayName(List <EvsProperty>properties) {
		List <String> results =  getProperty("P107",properties);
		if (results.size() == 0) {
			return null;
		} else {
			return results.get(0);
		}
	}

	/**
	 * Return a concept preferred name. 
	 * 
	 * @param properties List of properties.
	 * @return Concept preferred name.
	 */
	public static String getPreferredName(List <EvsProperty>properties) {
		List <String> results = getProperty("P108",properties);
		if (results.size() == 0) {
			return null;
		} else {
			return results.get(0);
		}
	}

	/**
	 * Return a concept preferred name. 
	 * 
	 * @param properties List of properties.
	 * @return Concept preferred name.
	 */
	public static String getNeoplasticStatus(List <EvsProperty>properties) {
		List <String> results = getProperty("P363",properties);
		if (results.size() == 0) {
			return null;
		} else {
			return results.get(0);
		}
	}

	/**
	 * Return a concept definition. 
	 * 
	 * @param properties List of properties.
	 * @return Concept definition.
	 */
	public static String getDefinition(List <EvsProperty>properties) {
		List <String> results = getProperty("P97",properties);
		if (results.size() == 0) {
			return null;
		} else {
			return results.get(0);
		}
	}

	/**
	 * Return a concept's semantic types.
	 * 
	 * @param properties List of properties.
	 * @return Concept semantic types.
	 */
	public static List <String> getSemanticType(List <EvsProperty>properties) {
		return getProperty("P106",properties);
	}

	/**
	 * Return a concept's full synonyms.
	 * 
	 * @param axioms List of axioms.
	 * @return Concept full synonyms.
	 */
	public static List <EvsSynonym> getFullSynonym(List <EvsAxiom>axioms) {
		ArrayList <EvsSynonym> results = new ArrayList<EvsSynonym>();
		for ( EvsAxiom axiom: axioms) {
			if (axiom.getAnnotatedProperty().equals("P90")) {
				EvsSynonym synonym = new EvsSynonym();
				synonym.setCode("P90");
				synonym.setLabel(axiom.getAnnotatedTarget());
				synonym.setTermName(axiom.getAnnotatedTarget());
				synonym.setTermGroup(axiom.getTermGroup());
				synonym.setTermSource(axiom.getTermSource());
				synonym.setSourceCode(axiom.getSourceCode());
				synonym.setSubSourceName(axiom.getSubsourceName());
				results.add(synonym);
			}
		}
		return results;
	}
	
}