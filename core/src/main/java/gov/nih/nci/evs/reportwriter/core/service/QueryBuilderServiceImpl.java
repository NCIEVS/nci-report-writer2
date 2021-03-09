package gov.nih.nci.evs.reportwriter.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

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
				"PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>",
				"PREFIX dc:<http://purl.org/dc/elements/1.1/>");

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

	/**
	 * Return the SPARQL VersionInfo Query
	 *
	 * @param namedGraph Named graph.
	 * @return SPARQL Version Info query
	 */
	public String constructVersionInfoQuery(String namedGraph) {
		StringBuffer query = new StringBuffer();
		query.append("SELECT ?version ?date ?comment\n");
		query.append("{ GRAPH <" + namedGraph + ">");
		query.append("	{\n");
        query.append("    ?o a owl:Ontology .\n");
		query.append("    ?o owl:versionInfo ?version .\n");
        query.append("    ?o dc:date ?date .\n");
        query.append("    ?o rdfs:comment ?comment\n");
		query.append("  }\n");
		query.append("}\n");

		//System.out.println(query.toString());

		return query.toString();
	}

	/**
	 * Return the SPARQL NamedGraph Query
	 *
	 * @return SPARQL NamedGraph query
	 */
	public String constructNamedGraphQuery() {
		StringBuffer query = new StringBuffer();
		query.append("SELECT distinct ?namedGraph {\n");
		//query.append("  { ?s ?p ?o }\n");
		//query.append("UNION\n");
		query.append("  { graph ?namedGraph {?s ?p ?o}}\n");
		query.append("}\n");

		//System.out.println(query.toString());
		return query.toString();
	}

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //[EVSREPORT2-42] Associations in Report Writer. KLO, 02202020
	public String construct_concept_in_subset_query(String conceptCode, String namedGraph) {
		String associationName = "Concept_In_Subset";
		return construct_associated_concept_query(namedGraph, associationName, conceptCode);
	}

	public String construct_associated_concept_query(String namedGraph, String associationName, String code) {
		boolean sourceOf = true;
		return construct_associated_concept_query(namedGraph, associationName, code, sourceOf);
	}

	public String construct_get_associated_concepts(String namedGraph, String association) {
		return construct_get_associated_concepts(namedGraph, association, true);
	}

/*
	public String construct_associated_concept_query(String namedGraph, String associationName, String code, boolean sourceOf) {
		StringBuffer buf = new StringBuffer();
		if (sourceOf) {
			buf.append("SELECT ?x_code ?x_label").append("\n");
		} else {
			buf.append("SELECT ?z_code ?z_label").append("\n");
		}

		buf.append("{").append("\n");
		buf.append("    graph <" + namedGraph + ">").append("\n");
		buf.append("    {").append("\n");
		buf.append("            ?x a owl:Class .").append("\n");
		buf.append("            ?x rdfs:label ?x_label .").append("\n");

		if (sourceOf) {
			buf.append("            ?x :NHC0 ?x_code .").append("\n");
		} else if (code != null) {
			buf.append("            ?x :NHC0 \"" + code + "\"^^<http://www.w3.org/2001/XMLSchema#string> .").append("\n");
		}

		buf.append("            ?y a owl:AnnotationProperty .").append("\n");
		buf.append("            ?y rdfs:label \"" + associationName + "\"^^<http://www.w3.org/2001/XMLSchema#string> .").append("\n");

		buf.append("            ?x ?y ?z .").append("\n");
		buf.append("            ?z a owl:Class .").append("\n");
		buf.append("            ?z rdfs:label ?z_label .").append("\n");

		if (!sourceOf) {
			buf.append("            ?z :NHC0 ?z_code .").append("\n");
		} else if (code != null) {
			buf.append("            ?z :NHC0 \"" + code + "\"^^<http://www.w3.org/2001/XMLSchema#string> .").append("\n");
		}

		buf.append("            ?y rdfs:range ?y_range").append("\n");
		buf.append("    }").append("\n");
		buf.append("    FILTER (str(?y_range)=\"http://www.w3.org/2001/XMLSchema#anyURI\")").append("\n");
		buf.append("}").append("\n");
		return buf.toString();
	}


	public String construct_get_associated_concepts(String namedGraph, String association, boolean sourceOf) {
		StringBuffer buf = new StringBuffer();
		if (sourceOf) {
			buf.append("SELECT distinct ?x_label ?x_code ?y_label ?z_label ?z_code").append("\n");
		} else {
			buf.append("SELECT distinct ?z_label ?z_code ?y_label ?x_label ?x_code").append("\n");
		}
		buf.append("{").append("\n");
		buf.append("    graph <" + namedGraph + ">").append("\n");
		buf.append("    {").append("\n");
		buf.append("            ?x a owl:Class .").append("\n");
		buf.append("            ?x rdfs:label ?x_label .").append("\n");
		buf.append("            ?x :NHC0 ?x_code .").append("\n");
		buf.append("            ?y a owl:AnnotationProperty .").append("\n");
		buf.append("            ?x ?y ?z .").append("\n");
		buf.append("            ?y rdfs:label ?y_label .").append("\n");
		buf.append("            ?z :NHC0 ?z_code .").append("\n");
		buf.append("            ?z rdfs:label ?z_label .").append("\n");
		buf.append("            ?y rdfs:label " + "\"" + association + "\"^^xsd:string ").append("\n");
		buf.append("    }").append("\n");
		buf.append("}").append("\n");
		return buf.toString();
	}
*/


	public String construct_associated_concept_query(String namedGraph, String associationName, String code, boolean sourceOf) {
		StringBuffer buf = new StringBuffer();
		if (sourceOf) {
			buf.append("SELECT ?sourceCode ?sourceName").append("\n");
		} else {
			buf.append("SELECT ?targetCode ?targetName").append("\n");
		}

		buf.append("{").append("\n");
		buf.append("    graph <" + namedGraph + ">").append("\n");
		buf.append("    {").append("\n");
		buf.append("            ?x a owl:Class .").append("\n");
		buf.append("            ?x rdfs:label ?sourceName .").append("\n");

		if (sourceOf) {
			buf.append("            ?x :NHC0 ?sourceCode .").append("\n");
		} else if (code != null) {
			buf.append("            ?x :NHC0 \"" + code + "\"^^<http://www.w3.org/2001/XMLSchema#string> .").append("\n");
		}

		buf.append("            ?y a owl:AnnotationProperty .").append("\n");
		buf.append("            ?y rdfs:label \"" + associationName + "\"^^<http://www.w3.org/2001/XMLSchema#string> .").append("\n");

		buf.append("            ?x ?y ?z .").append("\n");
		buf.append("            ?z a owl:Class .").append("\n");
		buf.append("            ?z rdfs:label ?targetName .").append("\n");

		if (!sourceOf) {
			buf.append("            ?z :NHC0 ?targetCode .").append("\n");
		} else if (code != null) {
			buf.append("            ?z :NHC0 \"" + code + "\"^^<http://www.w3.org/2001/XMLSchema#string> .").append("\n");
		}

		buf.append("            ?y rdfs:range ?y_range").append("\n");
		buf.append("    }").append("\n");
		buf.append("    FILTER (str(?y_range)=\"http://www.w3.org/2001/XMLSchema#anyURI\")").append("\n");
		buf.append("}").append("\n");
		return buf.toString();
	}


	public String construct_get_associated_concepts(String namedGraph, String association, boolean sourceOf) {
		StringBuffer buf = new StringBuffer();
		if (sourceOf) {
			buf.append("SELECT distinct ?sourceName ?sourceCode ?associationName ?targetName ?targetCode").append("\n");
		} else {
			buf.append("SELECT distinct ?targetName ?targetCode ?associationName ?sourceName ?sourceCode").append("\n");
		}
		buf.append("{").append("\n");
		buf.append("    graph <" + namedGraph + ">").append("\n");
		buf.append("    {").append("\n");
		buf.append("            ?x a owl:Class .").append("\n");
		buf.append("            ?x rdfs:label ?sourceName .").append("\n");
		buf.append("            ?x :NHC0 ?sourceCode .").append("\n");
		buf.append("            ?y a owl:AnnotationProperty .").append("\n");
		buf.append("            ?x ?y ?z .").append("\n");
		buf.append("            ?y rdfs:label ?associationName .").append("\n");
		buf.append("            ?z :NHC0 ?targetCode .").append("\n");
		buf.append("            ?z rdfs:label ?targetName .").append("\n");
		buf.append("            ?y rdfs:label " + "\"" + association + "\"^^xsd:string ").append("\n");
		buf.append("    }").append("\n");
		buf.append("}").append("\n");
		return buf.toString();
	}

    public String constructSupportedAssociationQuery(String namedGraph) {
		StringBuffer query = new StringBuffer();
		query.append("SELECT distinct ?supportedAssociationName ?supportedAssociationCode").append("\n");
		query.append("{").append("\n");
		query.append("  GRAPH <" + namedGraph + ">").append("\n");
		query.append("	{").append("\n");
        query.append("    ?x a owl:AnnotationProperty .").append("\n");
		query.append("    ?x :NHC0 ?supportedAssociationCode .").append("\n");
        query.append("    ?x rdfs:label ?supportedAssociationName .").append("\n");
        query.append("    ?x rdfs:range ?x_range").append("\n");
		query.append("  }").append("\n");
		query.append("  FILTER (str(?x_range)=\"http://www.w3.org/2001/XMLSchema#anyURI\")").append("\n");
		query.append("}").append("\n");
		return query.toString();
	}

	public String construct_get_subsets(String named_graph, String root, boolean subset_code_only) {
		StringBuffer buf = new StringBuffer();
		if (subset_code_only) {
			buf.append("select distinct ?x_code ?p0_value").append("\n");
		} else {
			buf.append("select distinct ?x_label ?x_code ?p0_label ?p0_value ").append("\n");
		}
		buf.append("from <" + named_graph + ">").append("\n");
		buf.append("where  {").append("\n");
		buf.append("                ?x a owl:Class .").append("\n");
		buf.append("                ?x :NHC0 ?x_code .").append("\n");
		buf.append("                ?x rdfs:label ?x_label .").append("\n");
		buf.append("                ?y a owl:Class .").append("\n");
		buf.append("                ?y :NHC0 ?y_code .").append("\n");
		buf.append("                ?y :NHC0 \"" + root + "\"^^xsd:string .").append("\n");
		buf.append("                ?y rdfs:label ?y_label .").append("\n");
		buf.append("                ?x ?p ?y .").append("\n");
		buf.append("                ?p rdfs:label ?p_label .").append("\n");
		buf.append("                ?p rdfs:label \"Concept_In_Subset\"^^xsd:string .").append("\n");
		buf.append("").append("\n");
		buf.append("").append("\n");
		buf.append("	OPTIONAL {").append("\n");
		buf.append("                ?x ?p0 ?p0_value .").append("\n");
		buf.append("                ?p0 rdfs:label \"Extensible_List\"^^xsd:string .").append("\n");
		buf.append("                ?p0 rdfs:label ?p0_label .").append("\n");
		buf.append("	}").append("\n");
		buf.append("").append("\n");
		buf.append("}").append("\n");
		return buf.toString();
	}

	public String construct_get_concepts_in_subset(String named_graph, String code, boolean code_only) {
        StringBuffer buf = new StringBuffer();
        if (code_only) {
			buf.append("select distinct ?x_code").append("\n");
		} else {
        	buf.append("select distinct ?x_label ?x_code ?p_label ?y_label ?y_code ").append("\n");
		}
        buf.append("from <" + named_graph + ">").append("\n");
        buf.append("where  { ").append("\n");
        buf.append("                ?x a owl:Class .").append("\n");
        buf.append("                ?x :NHC0 ?x_code .").append("\n");
        buf.append("                ?x rdfs:label ?x_label .").append("\n");
        buf.append("                ?y a owl:Class .").append("\n");
        buf.append("                ?y :NHC0 ?y_code .").append("\n");
        buf.append("                ?y :NHC0 \"" + code + "\"^^xsd:string .").append("\n");
        buf.append("                ?y rdfs:label ?y_label .").append("\n");
        buf.append("                ?x ?p ?y .").append("\n");
        buf.append("                ?p rdfs:label ?p_label .").append("\n");
        buf.append("                ?p rdfs:label \"Concept_In_Subset\"^^xsd:string .").append("\n");
        buf.append("}").append("\n");
        return buf.toString();
	}

    public String construct_get_subset_member_concept_data(String named_graph, String subset_code) {
        StringBuffer buf = new StringBuffer();
        buf.append("select distinct ?x_label ?x_code ?p_label ?p_value ?a_prop_label ?a_target ?q1_label ?q1_value ?q2_label ?q2_value ?b_target ?b_prop_label ?q5_value ?r_label ?r_code ?q3_label ?q3_value ").append("\n");
        buf.append("from <" + named_graph + ">").append("\n");
        buf.append("").append("\n");
        buf.append("where  {").append("\n");
        //r: root
        buf.append("                ?r a owl:Class .").append("\n");
        buf.append("                ?r :NHC0 ?r_code .").append("\n");
        buf.append("                ?r :NHC0 \"" + subset_code + "\"^^xsd:string .").append("\n");
        buf.append("                ?r rdfs:label ?r_label .").append("\n");
        buf.append("").append("\n");
        buf.append("                ?x a owl:Class .").append("\n");
        buf.append("                ?x :NHC0 ?x_code .").append("\n");
        buf.append("                ?x rdfs:label ?x_label .").append("\n");
        buf.append("").append("\n");
        buf.append("                ?x ?p0 ?r .").append("\n");
        buf.append("                ?p0 rdfs:label ?p0_label .").append("\n");
        buf.append("                ?p0 rdfs:label \"Concept_In_Subset\"^^xsd:string .").append("\n");
        buf.append(" ").append("\n");
        buf.append("                ?x ?p ?p_value .").append("\n");
        buf.append("                ?p rdfs:label ?p_label .").append("\n");
        buf.append("                ?p rdfs:label \"Preferred_Name\"^^xsd:string .").append("\n");
        buf.append("").append("\n");
        buf.append("                ?a a owl:Axiom .").append("\n");
        buf.append("                ?a owl:annotatedSource ?x .").append("\n");
        buf.append("                ?a owl:annotatedProperty ?a_prop .").append("\n");
        buf.append("                ?a owl:annotatedTarget ?a_target .").append("\n");
        buf.append("                ?a_prop rdfs:label ?a_prop_label .").append("\n");
        buf.append("                ?a_prop rdfs:label \"FULL_SYN\"^^xsd:string .").append("\n");
        buf.append("").append("\n");
        buf.append("                ?a ?q1 ?q1_value .").append("\n");
        buf.append("                ?a ?q1 \"CDISC\"^^xsd:string .").append("\n");
        buf.append("                ?q1 rdfs:label ?q1_label .").append("\n");
        buf.append("                ?q1 rdfs:label \"Term Source\"^^xsd:string .").append("\n");
        buf.append("").append("\n");
        buf.append("                ?a ?q2 ?q2_value .").append("\n");
        buf.append("                ?q2 rdfs:label ?q2_label .").append("\n");
        buf.append("                ?q2 rdfs:label \"Term Type\"^^xsd:string .").append("\n");

        buf.append("").append("\n");
        buf.append("                ?b a owl:Axiom .").append("\n");
        buf.append("                ?b owl:annotatedSource ?x .").append("\n");
        buf.append("                ?b owl:annotatedProperty ?b_prop .").append("\n");
        buf.append("                ?b owl:annotatedTarget ?b_target .").append("\n");
        buf.append("                ?b_prop rdfs:label ?b_prop_label .").append("\n");
        buf.append("                ?b_prop rdfs:label \"ALT_DEFINITION\"^^xsd:string .").append("\n");
        buf.append("").append("\n");
        buf.append("                ?b ?q5 ?q5_value .").append("\n");
        buf.append("                ?b ?q5 \"CDISC\"^^xsd:string .").append("\n");
        buf.append("                ?q5 rdfs:label ?q5_label .").append("\n");
        buf.append("                ?q5 rdfs:label \"Definition Source\"^^xsd:string .").append("\n");
        buf.append("").append("\n");

        buf.append("                OPTIONAL{").append("\n");
        buf.append("                ?a ?q3 ?q3_value .").append("\n");
        buf.append("                ?q3 rdfs:label ?q3_label .").append("\n");
        buf.append("                ?q3 rdfs:label \"Source Code\"^^xsd:string .").append("\n");
        buf.append("                }").append("\n");
        buf.append("}").append("\n");

        return buf.toString();
	}

	public String construct_get_matched_annotated_target(String named_graph, String code, String propertyName, Vector qualifierNames, Vector qualifierValues) {
        StringBuffer buf = new StringBuffer();
        buf.append("select distinct ?a_target").append("\n");
        buf.append("from <" + named_graph + ">").append("\n");
        buf.append("where  { ").append("\n");
        buf.append("                ?x a owl:Class .").append("\n");
        buf.append("                ?x :NHC0 ?x_code .").append("\n");
        buf.append("                ?x :NHC0 \"" + code + "\"^^xsd:string .").append("\n");
        buf.append("                ?x rdfs:label ?x_label .").append("\n");
        buf.append("").append("\n");
        buf.append("                ?a a owl:Axiom .").append("\n");
        buf.append("                ?a owl:annotatedSource ?x .").append("\n");
        buf.append("                ?a owl:annotatedProperty ?p .").append("\n");
        buf.append("                ?a owl:annotatedTarget ?a_target .").append("\n");
        buf.append("                ?p rdfs:label ?p_label .").append("\n");
        buf.append("                ?p rdfs:label \"" + propertyName + "\"^^xsd:string .").append("\n");
        buf.append("                ").append("\n");

        int j = 0;
        for (int i=0; i<qualifierNames.size(); i++) {
			String qualifierName = (String) qualifierNames.elementAt(i);
			String qualifierValue = (String) qualifierValues.elementAt(i);
            j++;
			buf.append("                ?a ?q" + j + " ?q" + j + "_value .").append("\n");
			buf.append("                ?a ?q" + j + " \"" + qualifierValue + "\"^^xsd:string .").append("\n");
			buf.append("                ?q" + j + " rdfs:label ?q" + j + "_label .").append("\n");
			buf.append("                ?q" + j + " rdfs:label \"" + qualifierName + "\"^^xsd:string .").append("\n");
	    }
        buf.append("}").append("\n");
        return buf.toString();
	}

	public String construct_get_subset_concept_data(String named_graph, String code) {
		StringBuffer buf = new StringBuffer();
		buf.append("select distinct ?p1_value ?x_label ?a_target ?q1_label ?q1_value ?q2_label ?q2_value ?b_prop_label ?b_target ?p2_label ?p2_value").append("\n");
		buf.append("from <" + named_graph + ">").append("\n");
		buf.append("where  {").append("\n");
		buf.append("                ?x a owl:Class .").append("\n");
		buf.append("                ?x :NHC0 ?x_code .").append("\n");
		buf.append("                ?x :NHC0 \"" + code + "\"^^xsd:string .").append("\n");
		buf.append("                ?x rdfs:label ?x_label .").append("\n");
		buf.append("").append("\n");
		buf.append("                ?x ?p1 ?p1_value .").append("\n");
		buf.append("                ?p1 rdfs:label ?p1_label .").append("\n");
		buf.append("                ?p1 rdfs:label \"Extensible_List\"^^xsd:string .").append("\n");
		buf.append("").append("\n");
		buf.append("                ?x ?p2 ?p2_value .").append("\n");
		buf.append("                ?p2 rdfs:label ?p2_label .").append("\n");
		buf.append("                ?p2 rdfs:label \"Preferred_Name\"^^xsd:string .").append("\n");
		buf.append("").append("\n");
		buf.append("                ?a a owl:Axiom .").append("\n");
		buf.append("                ?a owl:annotatedSource ?x .").append("\n");
		buf.append("                ?a owl:annotatedProperty ?a_prop .").append("\n");
		buf.append("                ?a owl:annotatedTarget ?a_target .").append("\n");
		buf.append("                ?a_prop rdfs:label ?a_prop_label .").append("\n");
		buf.append("                ?a_prop rdfs:label \"FULL_SYN\"^^xsd:string .").append("\n");
		buf.append("").append("\n");
		buf.append("                ?a ?q1 ?q1_value .").append("\n");
		buf.append("                ?a ?q1 \"CDISC\"^^xsd:string .").append("\n");
		buf.append("                ?q1 rdfs:label ?q1_label .").append("\n");
		buf.append("                ?q1 rdfs:label \"Term Source\"^^xsd:string .").append("\n");
		buf.append("").append("\n");
		buf.append("                ?a ?q2 ?q2_value .").append("\n");
		buf.append("                ?q2 rdfs:label ?q2_label .").append("\n");
		buf.append("                ?q2 rdfs:label \"Term Type\"^^xsd:string .").append("\n");
		buf.append("").append("\n");
		buf.append("                ?b a owl:Axiom .").append("\n");
		buf.append("                ?b owl:annotatedSource ?x .").append("\n");
		buf.append("                ?b owl:annotatedProperty ?b_prop .").append("\n");
		buf.append("                ?b owl:annotatedTarget ?b_target .").append("\n");
		buf.append("                ?b_prop rdfs:label ?b_prop_label .").append("\n");
		buf.append("                ?b_prop rdfs:label \"ALT_DEFINITION\"^^xsd:string .").append("\n");
		buf.append("").append("\n");
		buf.append("                ?b ?q5 ?q5_value .").append("\n");
		buf.append("                ?b ?q5 \"CDISC\"^^xsd:string .").append("\n");
		buf.append("                ?q5 rdfs:label ?q5_label .").append("\n");
		buf.append("                ?q5 rdfs:label \"Definition Source\"^^xsd:string .").append("\n");
		buf.append("}").append("\n");
		return buf.toString();
	}

	public String constructSupportedRoleQuery(String named_graph) {
		StringBuffer buf = new StringBuffer();
		buf.append("").append("\n");
		buf.append("SELECT distinct ?supportedRoleName ?supportedRoleCode ").append("\n");
		buf.append("{").append("\n");
		buf.append("    graph <" + named_graph + "> {").append("\n");
		buf.append("            ?p a owl:ObjectProperty.").append("\n");
		buf.append("            ?p :NHC0 ?supportedRoleCode .").append("\n");
		buf.append("            ?p rdfs:label ?supportedRoleName ").append("\n");
		buf.append("    }").append("\n");
		buf.append("}").append("\n");
		return buf.toString();
	}

	public String construct_get_role_targets(String named_graph, String code, String roleName) {
		StringBuffer buf = new StringBuffer();
		buf.append("").append("\n");
		buf.append("SELECT distinct ?c_code").append("\n");
		buf.append("{").append("\n");
		buf.append("    graph <" + named_graph + "> {").append("\n");
		buf.append("            {").append("\n");
		buf.append("            ?x a owl:Class .").append("\n");
		buf.append("            ?x :NHC0 ?x_code .").append("\n");
		buf.append("            ?x :NHC0 \"" + code + "\"^^xsd:string .").append("\n");
		buf.append("            ?x rdfs:label ?x_label .").append("\n");
		buf.append("").append("\n");
		buf.append("            ?x owl:equivalentClass ?y .").append("\n");
		buf.append("            ?y (rdfs:subClassOf|(owl:intersectionOf/rdf:rest*/rdf:first))* ?r1 .").append("\n");
		buf.append("            ?r1 owl:onProperty ?p .").append("\n");
		buf.append("").append("\n");
		buf.append("            ?p :NHC0 ?p_code .").append("\n");
		buf.append("            ?p rdfs:label ?p_label .").append("\n");
		buf.append("            ?p rdfs:label \"" + roleName + "\"^^xsd:string .").append("\n");
		buf.append("").append("\n");
		buf.append("            ?r1 owl:someValuesFrom ?c .").append("\n");
		buf.append("            ?c :NHC0 ?c_code .").append("\n");
		buf.append("            ?c rdfs:label ?c_label .").append("\n");
		buf.append("            }").append("\n");
		buf.append("            UNION").append("\n");
		buf.append("            {").append("\n");
		buf.append("            ?x a owl:Class .").append("\n");
		buf.append("            ?x :NHC0 ?x_code .").append("\n");
		buf.append("            ?x :NHC0 \"" + code + "\"^^xsd:string .").append("\n");
		buf.append("            ?x rdfs:label ?x_label .").append("\n");
		buf.append("").append("\n");
		buf.append("            ?x owl:equivalentClass ?y .").append("\n");
		buf.append("            ?y (rdfs:subClassOf|(owl:unionOf/rdf:rest*/rdf:first))* ?r1 .").append("\n");
		buf.append("            ?r1 owl:onProperty ?p .").append("\n");
		buf.append("").append("\n");
		buf.append("            ?p :NHC0 ?p_code .").append("\n");
		buf.append("            ?p rdfs:label ?p_label .").append("\n");
		buf.append("            ?p rdfs:label \"" + roleName + "\"^^xsd:string .").append("\n");
		buf.append("").append("\n");
		buf.append("").append("\n");
		buf.append("            ?r1 owl:someValuesFrom ?c .").append("\n");
		buf.append("            ?c :NHC0 ?c_code .").append("\n");
		buf.append("            ?c rdfs:label ?c_label .").append("\n");
		buf.append("").append("\n");
		buf.append("            }").append("\n");
		buf.append("            UNION").append("\n");
		buf.append("            {").append("\n");
		buf.append("            ?x a owl:Class .").append("\n");
		buf.append("            ?x :NHC0 ?x_code .").append("\n");
		buf.append("            ?x :NHC0 \"" + code + "\"^^xsd:string .").append("\n");
		buf.append("            ?x rdfs:label ?x_label .").append("\n");
		buf.append("            ?x rdfs:subClassOf ?r .").append("\n");
		buf.append("").append("\n");
		buf.append("            ?r owl:onProperty ?p .").append("\n");
		buf.append("            ?p :NHC0 ?p_code .").append("\n");
		buf.append("            ?p rdfs:label ?p_label .").append("\n");
		buf.append("            ?p rdfs:label \"" + roleName + "\"^^xsd:string .").append("\n");
		buf.append("").append("\n");
		buf.append("            ?r owl:someValuesFrom ?c .").append("\n");
		buf.append("            ?c :NHC0 ?c_code .").append("\n");
		buf.append("            ?c rdfs:label ?c_label .").append("\n");
		buf.append("            }").append("\n");
		buf.append("    }").append("\n");
		buf.append("").append("\n");
		buf.append("}").append("\n");
		return buf.toString();
	}

	public String construct_get_association_targets(String named_graph, String code, String associationName) {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT distinct ?targetCode").append("\n");
		buf.append("{").append("\n");
		buf.append("    graph <" + named_graph + ">").append("\n");
		buf.append("    {").append("\n");
		buf.append("            ?x a owl:Class .").append("\n");
		buf.append("            ?x rdfs:label ?sourceName .").append("\n");
		buf.append("            ?x :NHC0 ?x_code .").append("\n");
		buf.append("            ?x :NHC0 \"" + code + "\"^^xsd:string .").append("\n");
		buf.append("            ?y a owl:AnnotationProperty .").append("\n");
		buf.append("            ?x ?y ?z .").append("\n");
		buf.append("            ?y rdfs:label ?associationName .").append("\n");
		buf.append("            ?y rdfs:label \"" + associationName + "\"^^xsd:string .").append("\n");
		buf.append("            ?z :NHC0 ?targetCode .").append("\n");
		buf.append("            ?z rdfs:label ?targetName .").append("\n");
		buf.append("    }").append("\n");
		buf.append("}").append("\n");
		return buf.toString();
	}

}
