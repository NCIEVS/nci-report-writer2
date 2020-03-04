package gov.nih.nci.evs.reportwriter.core.service;

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

}
