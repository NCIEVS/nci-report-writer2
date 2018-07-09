package gov.nih.nci.evs.reportwriter.core.service;

import java.util.List;

import gov.nih.nci.evs.reportwriter.core.model.evs.EvsAxiom;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsConcept;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsProperty;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsVersionInfo;

public interface SparqlQueryManagerService {

	public String getEvsConceptLabel(String conceptCode, String namedGraph);
	
	public List <EvsProperty> getEvsProperties(String conceptCode, String namedGraph);
	
	public List<EvsAxiom> getEvsAxioms(String conceptCode, String namedGraph);
	
	public List<EvsConcept> getEvsSubclasses(String conceptCode, String namedGraph);
	
	public List<EvsConcept> getEvsSuperclasses(String conceptCode, String namedGraph);
	
	public EvsConcept getEvsConceptDetail(String conceptCode, String namedGraph);

	public EvsConcept getEvsConceptDetailShort(String conceptCode, String namedGraph);

	public List <EvsConcept> getEvsConceptInSubset(String conceptCode, String namedGraph);
	
	public EvsVersionInfo getEvsVersionInfo(String namedGraph);
	
	public List <String> getNamedGraphs();
}