package gov.nih.nci.evs.reportwriter.core.util;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.evs.reportwriter.core.model.evs.EvsAxiom;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsProperty;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsSynonym;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsConcept;

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


		if (properties == null) {
			return results;
		}


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

////KLO 11/26/2019 ///////////////////////////////////////////////////////////////////////////////////////////
    public static List <String> getSuperclassCodes(EvsConcept concept) {
		List<String> list = new ArrayList <String>();
		try {
			if (concept == null) {
				return list;
			}
			List<EvsConcept> superclasses = concept.getSuperclasses();
			if (superclasses != null) {
				if (superclasses.size() == 0) {
					return list;
				}
				for (int i=0; i<superclasses.size(); i++) {
					EvsConcept superclass = superclasses.get(i);
					if (superclass != null) {
						list.add(superclass.getCode());
					}
				}
	    	}
	    } catch (Exception ex) {
			ex.printStackTrace();
		}
        return list;
	}


////KLO 01/25/2021 ///////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Return FULL_SYN term names that match a give set of qualifiers .
	 *
	 * @param axioms List of EvsAxiom.
	 * @return list of FULL_SYN term names
	 */

	public static List<String> getSynonymsWithQualifiers(List<EvsAxiom> axioms,
	    String termSource, String termType, String sourceCode, String subsourceName) {
	    List<String> list = new ArrayList<String>();
		List <EvsSynonym> full_syns = getFullSynonym(axioms);

		for (int i=0; i<full_syns.size(); i++) {
			EvsSynonym synonym = full_syns.get(i);

			System.out.println(synonym.getTermName() + "|" +
			                   synonym.getTermSource() + "|" +
			                   synonym.getTermGroup() + "|" +
			                   synonym.getSourceCode() + "|" +
			                   synonym.getSubSourceName());


			System.out.println(termSource +  " vs " + synonym.getTermSource());
			System.out.println(termType +  " vs " + synonym.getTermGroup());
			System.out.println(sourceCode +  " vs " + synonym.getSourceCode());
			System.out.println(subsourceName +  " vs " + synonym.getSubSourceName());

/*
ADCCPS source: CDISC term group: PT source code: QS-ADAS-Cog CDISC Version TESTCD subsource name: null
CDISC vs CDISC
PT vs PT
null vs QS-ADAS-Cog CDISC Version TESTCD
null vs null
*/

            boolean match = true;
			if (termSource != null) {
				if (synonym.getTermSource() == null || synonym.getTermSource().compareTo("null") == 0) {
					match = false;
				} else if (synonym.getTermSource().compareTo(termSource) != 0) {
					match = false;
				}
			}
			if (termType != null) {
				if (synonym.getTermGroup() == null || synonym.getTermGroup().compareTo("null") == 0) {
					match = false;
				} else if (synonym.getTermGroup().compareTo(termType) != 0) {
					match = false;
				}
			}
			if (sourceCode != null) {
				if (synonym.getSourceCode() == null || synonym.getSourceCode().compareTo("null") == 0) {
					match = false;
				} else if (synonym.getSourceCode().compareTo(sourceCode) != 0) {
					match = false;
				}
			}
			if (subsourceName != null) {
				if (synonym.getSubSourceName() == null || synonym.getSubSourceName().compareTo("null") == 0) {
					match = false;
				} else if (synonym.getSubSourceName().compareTo(subsourceName) != 0) {
					match = false;
				}
			}
			if (match) {
				list.add(synonym.getTermName());
			}
		}
		return list;
	}
}