package gov.nih.nci.evs.rw.service;

public interface QueryBuilderService {
	
	public String contructPrefix();

	public String constructConceptQuery(String conceptCode,String namedGraph);
	
	public String constructPropertyQuery(String conceptCode,String namedGraph);
	
	public String constructAxiomQuery(String conceptCode, String namedGraph);
	
	public String constructSubclassQuery(String conceptCode, String namedGraph);

	public String constructSuperclassQuery(String conceptCode, String namedGraph);

	public String constructConceptInSubsetQuery(String conceptCode, String namedGraph);

}
