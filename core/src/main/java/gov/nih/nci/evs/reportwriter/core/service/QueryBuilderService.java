package gov.nih.nci.evs.reportwriter.core.service;

import java.util.*;

public interface QueryBuilderService {

	public String contructPrefix();

	public String constructConceptQuery(String conceptCode,String namedGraph);

	public String constructPropertyQuery(String conceptCode,String namedGraph);

	public String constructAxiomQuery(String conceptCode, String namedGraph);

	public String constructSubclassQuery(String conceptCode, String namedGraph);

	public String constructSuperclassQuery(String conceptCode, String namedGraph);

	public String constructConceptInSubsetQuery(String conceptCode, String namedGraph);

	public String constructVersionInfoQuery(String namedGraph);

	public String construct_associated_concept_query(String namespace, String associationName, String code, boolean sourceOf);

	public String constructNamedGraphQuery();

	public String construct_get_associated_concepts(String namedGraph, String association, boolean sourceOf);

    public String constructSupportedAssociationQuery(String namedGraph);

    public String construct_get_subsets(String named_graph, String root, boolean subset_code_only);

	public String construct_get_concepts_in_subset(String named_graph, String code, boolean code_only);

    public String construct_get_subset_member_concept_data(String named_graph, String subset_code);

	public String construct_get_matched_annotated_target(String named_graph, String code, String propertyName, Vector qualifierNames, Vector qualifierValues);

	public String construct_get_subset_concept_data(String named_graph, String code);

}
