package gov.nih.nci.evs.reportwriter.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
/**
 * Contains the SPARQL queries used by the ReportWriter
*/
public class QueryBuilderServiceImpl implements QueryBuilderService {

	private static final Logger log = LoggerFactory.getLogger(QueryBuilderServiceImpl.class);

	/**
	 * Return the SPARQL prefix defintion
	 * 
	 * @return SPARQL prefix
	 */
	public String contructPrefix() {

		String prefix = String.join(System.getProperty("line.separator"),
				"PREFIX :<http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#>",
				"PREFIX base:<http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#>",
				"PREFIX owl:<http://www.w3.org/2002/07/owl#>",
				"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>",
				"PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>",
				"PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>");

		return prefix;
	}
	
	/**
	 * Return the SPARQL concept query
	 * 
	 *  @param conceptCode Concept code.
	 *  @param namedGraph Named graph.
	 *  @return SPARQL concept query
	 */
	public String constructConceptQuery(String conceptCode, String namedGraph) {

		StringBuffer query = new StringBuffer();
		query.append("SELECT ?conceptLabel\n");
		query.append("{ GRAPH <" + namedGraph + ">\n");
		query.append("  { ?concept a owl:Class .\n");
		query.append("    ?concept :NHC0 " + "\"" + conceptCode + "\" .\n");
		query.append("    ?concept rdfs:label ?conceptLabel \n");
		query.append("  }\n");
		query.append("}\n");
		return query.toString();
	}

	/**
	 * Return the SPARQL concept property query
	 * 
	 *  @param conceptCode Concept code.
	 *  @param namedGraph Named graph.
	 *  @return SPARQL concept property query
	 */
	public String constructPropertyQuery(String conceptCode, String namedGraph) {

		StringBuffer query = new StringBuffer();
		query.append("SELECT ?property ?propertyCode  ?propertyLabel ?propertyValue\n");
		query.append("{ GRAPH <" + namedGraph + ">");
		query.append("  { ?concept a owl:Class .\n");
		query.append("    ?concept :NHC0 " + "\"" + conceptCode + "\" .\n");
		query.append("    ?concept ?property ?propertyValue .");
		query.append("    ?property a owl:AnnotationProperty .\n");
		query.append("    ?property rdfs:label ?propertyLabel .\n");
		query.append("    OPTIONAL { ?property :NHC0 ?propertyCode }\n");
		query.append("  }\n");
		query.append("}\n");
		return query.toString();
	}

	/**
	 * Return the SPARQL concept axiom query
	 * 
	 *  @param conceptCode Concept code.
	 *  @param namedGraph Named graph.
	 *  @return SPARQL concept axiom query
	 */
	public String constructAxiomQuery(String conceptCode, String namedGraph) {
		StringBuffer query = new StringBuffer();
		query.append("SELECT ?axiom ?axiomProperty  ?axiomValue\n");
		query.append("{ GRAPH <" + namedGraph + ">");
		query.append("    {\n");
		query.append("      ?axiom a owl:Axiom .\n");
		query.append("      ?axiom owl:annotatedSource :" + conceptCode + " .\n");
		query.append("      ?axiom ?axiomProperty ?axiomValue\n");
		query.append("    }\n");
		query.append("}\n");
		query.append("ORDER BY ?axiom\n");

		return query.toString();
	}

	/**
	 * Return the SPARQL concept subclass query
	 * 
	 *  @param conceptCode Concept code.
	 *  @param namedGraph Named graph.
	 *  @return SPARQL concept subclass query
	 */
	public String constructSubclassQuery(String conceptCode, String namedGraph) {
		StringBuffer query = new StringBuffer();
		query.append("SELECT ?subclass ?subclassLabel ?subclassCode\n");
		query.append("{ GRAPH <" + namedGraph + ">");
		query.append("		{\n");
		query.append("		  {\n");
		query.append("		    {\n");
		query.append("		      ?superclass a owl:Class .\n");
		query.append("		      ?superclass :NHC0 \"" + conceptCode + "\" .\n");
		query.append("		      ?subclass rdfs:subClassOf ?superclass .\n");
		query.append("		      ?subclass a owl:Class .\n");
		query.append("		      ?subclass rdfs:label ?subclassLabel .\n");
		query.append("		      ?subclass :NHC0 ?subclassCode\n");
		query.append("		    }\n");
		query.append("		    FILTER (?superclass != ?subclass)\n");
		query.append("		  }\n");
		query.append("		  UNION\n");
		query.append("		  {\n");
		query.append("		    {\n");
		query.append("		      ?superclass a owl:Class .\n");
		query.append("		      ?superclass :NHC0 \"" + conceptCode + "\" .\n");
		query.append("		      ?equiv_concept owl:intersectionOf ?list .\n");
		query.append("		      ?list rdf:rest*/rdf:first ?superclass .\n");
		query.append("		      ?subclass owl:equivalentClass ?equiv_concept .\n");
		query.append("		      ?subclass a owl:Class .\n");
		query.append("		      ?subclass rdfs:label ?subclassLabel .\n");
		query.append("		      ?subclass :NHC0 ?subclassCode\n");
		query.append("		    }\n");
		query.append("		    FILTER (?superclass != ?subclass)\n");
		query.append("		  }\n");
		query.append("		}\n");
		query.append("}\n");
		query.append("ORDER by ?subclassLabel\n");

		return query.toString();
	}

	/**
	 * Return the SPARQL concept superclass query
	 * 
	 *  @param conceptCode Concept code.
	 *  @param namedGraph Named graph.
	 *  @return SPARQL concept superclass query
	 */
	public String constructSuperclassQuery(String conceptCode, String namedGraph) {
		StringBuffer query = new StringBuffer();
		query.append("SELECT ?superclass ?superclassLabel ?superclassCode\n");
		query.append("{ GRAPH <" + namedGraph + ">");
		query.append("		{\n");
		query.append("		  {\n");
		query.append("		    {\n");
		query.append("		      ?subclass a owl:Class .\n");
		query.append("		      ?subclass :NHC0 \"" + conceptCode + "\" .\n");
		query.append("		      ?subclass rdfs:subClassOf ?superclass .\n");
		query.append("		      ?superclass a owl:Class .\n");
		query.append("		      ?superclass rdfs:label ?superclassLabel .\n");
		query.append("		      ?superclass :NHC0 ?superclassCode\n");
		query.append("		    }\n");
		query.append("		    FILTER (?subclass != ?superclass)\n");
		query.append("		  }\n");
		query.append("		  UNION\n");
		query.append("		  {\n");
		query.append("		    {\n");
		query.append("		      ?subclass a owl:Class .\n");
		query.append("		      ?subclass :NHC0 \"" + conceptCode + "\" .\n");
		query.append("		      ?equiv_concept owl:intersectionOf ?list .\n");
		query.append("		      ?list rdf:rest*/rdf:first ?superclass .\n");
		query.append("		      ?subclass owl:equivalentClass ?equiv_concept .\n");
		query.append("		      ?superclass a owl:Class .\n");
		query.append("		      ?superclass rdfs:label ?superclassLabel .\n");
		query.append("		      ?superclass :NHC0 ?superclassCode\n");
		query.append("		    }\n");
		query.append("		    FILTER (?subclass != ?superclass)\n");
		query.append("		  }\n");
		query.append("		}\n");
		query.append("}\n");
		query.append("ORDER by ?superclassLabel\n");

		return query.toString();
	}

	/**
	 * Return the SPARQL ConceptInSubset  query
	 * 
	 *  @param conceptCode Concept code.
	 *  @param namedGraph Named graph.
	 *  @return SPARQL ConceptInSubset query
	 */
	public String constructConceptInSubsetQuery(String conceptCode, String namedGraph) {
		StringBuffer query = new StringBuffer();
		query.append("SELECT ?conceptCode ?conceptLabel\n");
		query.append("{ GRAPH <" + namedGraph + ">");
		query.append("	{\n");
        query.append("    ?concept a owl:Class .\n");
		query.append("    ?concept :NHC0 ?conceptCode .\n");
        query.append("    ?concept rdfs:label ?conceptLabel .\n");
        query.append("    ?concept :A8 :" + conceptCode + "\n");
		query.append("  }\n");
		query.append("}\n");
		query.append("ORDER BY ?conceptLabel\n");

		return query.toString();
	}
	
}
