package gov.nih.nci.evs.reportwriter.core.service;

import java.util.List;
import java.util.Vector;

import gov.nih.nci.evs.reportwriter.core.model.evs.EvsAxiom;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsConcept;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsProperty;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsVersionInfo;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsAssociation;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsSupportedAssociation;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsSupportedRole;

public interface SparqlQueryManagerService {

	public String getEvsConceptLabel(String conceptCode, String namedGraph, String restURL);

	public List <EvsProperty> getEvsProperties(String conceptCode, String namedGraph, String restURL);

	public List<EvsAxiom> getEvsAxioms(String conceptCode, String namedGraph, String restURL);

	public List<EvsConcept> getEvsSubclasses(String conceptCode, String namedGraph, String resutURL);

	public List<EvsConcept> getEvsSuperclasses(String conceptCode, String namedGraph, String restURL);

	public EvsConcept getEvsConceptDetail(String conceptCode, String namedGraph, String restURL);

	public EvsConcept getEvsConceptDetailShort(String conceptCode, String namedGraph, String restURL);

	public List <EvsConcept> getEvsConceptInSubset(String conceptCode, String namedGraph, String restURL);

	public EvsVersionInfo getEvsVersionInfo(String namedGraph, String restURL);

	public List <String> getNamedGraphs(String restURL);

	public List <EvsConcept> getAssociatedEvsConcepts(String conceptCode, String namedGraph, String restURL, String associationName, boolean sourceOf);

	public List <EvsAssociation> getEvsAssociations(String namedGraph, String restURL, String associationName, boolean sourceOf);

	public List <EvsSupportedAssociation> getEvsSupportedAssociations(String namedGraph, String restURL);

	public List<String> getSubsets(String named_graph, String root, String restURL);

	public List<String> getConceptsInSubset(String named_graph, String code, String restURL);

    public List<String> getSubsetMemberConceptData(String named_graph, String subset_code, String restURL);

	public List<String> getMatchedAnnotatedTarget(String named_graph, String code, String propertyName, Vector qualifierNames, Vector qualifierValues, String restURL);

	public List<String> getSubsetCconceptData(String named_graph, String code, String restURL);

	public List <EvsSupportedRole> getEvsSupportedRoles(String namedGraph, String restURL);

	public List<String> getRoleTargets(String named_graph, String code, String roleName, String restURL);
}