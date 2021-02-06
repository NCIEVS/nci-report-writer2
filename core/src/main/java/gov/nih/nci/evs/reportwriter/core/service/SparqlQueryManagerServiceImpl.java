package gov.nih.nci.evs.reportwriter.core.service;
import gov.nih.nci.evs.reportwriter.core.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.nih.nci.evs.reportwriter.core.model.evs.EvsAxiom;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsConcept;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsProperty;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsVersionInfo;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsAssociation;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsSupportedAssociation;
import gov.nih.nci.evs.reportwriter.core.model.evs.EvsSupportedRole;
import gov.nih.nci.evs.reportwriter.core.model.sparql.Bindings;
import gov.nih.nci.evs.reportwriter.core.model.sparql.Sparql;
import gov.nih.nci.evs.reportwriter.core.properties.StardogProperties;
import gov.nih.nci.evs.reportwriter.core.util.EVSUtils;
import gov.nih.nci.evs.reportwriter.core.util.RESTUtils;

@Service
/**
 *
 * Contains methods for executing SPARQL queries and converting the results
 * into Class instances.
 *
 */
public class SparqlQueryManagerServiceImpl implements SparqlQueryManagerService {

	private static final Logger log = LoggerFactory.getLogger(QueryBuilderServiceImpl.class);

	@Autowired
	StardogProperties stardogProperties;

	@Autowired
	QueryBuilderService queryBuilderService;

	public RESTUtils restUtils = null;

	@PostConstruct
	/**
	 * Construct a RESTUtils class based on environment variables
	 */
	public void postInit() {
		restUtils = new RESTUtils(stardogProperties.getUsername(),
				stardogProperties.getPassword(),stardogProperties.getReadTimeout(),stardogProperties.getConnectTimeout());
	}

//KLO
	public void setQueryBuilderService(QueryBuilderService queryBuilderService) {
		this.queryBuilderService = queryBuilderService;
	}

	public void setRESTUtils(RESTUtils restUtils) {
		this.restUtils = restUtils;
	}

	/**
	 * Return the list of potential NamedGraph's
	 *
	 * @return List namedGraphs
	 */
	public List <String> getNamedGraphs(String restURL) {
		String queryPrefix = queryBuilderService.contructPrefix();
		String query = queryBuilderService.constructNamedGraphQuery();
		String res = restUtils.runSPARQL(queryPrefix + query, restURL);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArrayList<String> namedGraphs = new ArrayList<String>();
		try {
			Sparql sparqlResult = mapper.readValue(res, Sparql.class);
			Bindings[] bindings = sparqlResult.getResults().getBindings();
			for (Bindings b : bindings) {
				namedGraphs.add(b.getNamedGraph().getValue());
			}
		} catch (Exception ex) {
			System.out.println("Bad News Exception");
			System.out.println(ex);
		}
		return namedGraphs;
	}

	/**
	 * Return a list of properties for a concept.
	 *
	 * @param conceptCode Concept code.
	 * @return List of EVSProperty instances.
	 */
	public List<EvsProperty> getEvsProperties(String conceptCode, String namedGraph, String restURL) {

		String queryPrefix = queryBuilderService.contructPrefix();
		String query = queryBuilderService.constructPropertyQuery(conceptCode,namedGraph);
		String res = restUtils.runSPARQL(queryPrefix + query, restURL);

        ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArrayList<EvsProperty> evsProperties = new ArrayList<EvsProperty>();
		try {
			Sparql sparqlResult = mapper.readValue(res, Sparql.class);
			Bindings[] bindings = sparqlResult.getResults().getBindings();
			for (Bindings b : bindings) {
				EvsProperty evsProperty = new EvsProperty();
				/*
				 * If the code does not exist, then the property is an external
				 * property, so we need to handle as a special case.
				 */
				if (b.getPropertyCode() != null) {
					evsProperty.setCode(b.getPropertyCode().getValue());
				} else {
					String property = b.getProperty().getValue();
					String [] strs = property.split("#");
					try {
						evsProperty.setCode(strs[1]);
			     	} catch (Exception ex) {
			     		evsProperty.setCode("");
			     	}
				}
				evsProperty.setLabel(b.getPropertyLabel().getValue());
				evsProperty.setValue(b.getPropertyValue().getValue());
				evsProperties.add(evsProperty);
			}
		} catch (Exception ex) {
			System.out.println("Bad News Exception");
			System.out.println(ex);
		}
		return evsProperties;
	}

	/**
	 * Return the label for a concept
	 *
	 * @param conceptCode Concept code.
	 * @return Concept label
	 */
	public String getEvsConceptLabel(String conceptCode, String namedGraph, String restURL) {

		String queryPrefix = queryBuilderService.contructPrefix();
		String query = queryBuilderService.constructConceptQuery(conceptCode,namedGraph);
		String res = restUtils.runSPARQL(queryPrefix + query, restURL);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String conceptLabel = null;
		try {
			Sparql sparqlResult = mapper.readValue(res, Sparql.class);
			Bindings[] bindings = sparqlResult.getResults().getBindings();
			if (bindings.length == 1) {
				conceptLabel =  bindings[0].getConceptLabel().getValue();
			}
		} catch (Exception ex) {
			System.out.println("Bad News Exception");
			System.out.println(ex);
		}
		return conceptLabel;
	}

	/**
	 * Return a list of axioms for a concept.
	 *
	 * @param conceptCode Concept code.
	 * @return List of EVSAxiom instances.
	 */
	public List<EvsAxiom> getEvsAxioms(String conceptCode, String namedGraph, String restURL) {

		String queryPrefix = queryBuilderService.contructPrefix();
		String query = queryBuilderService.constructAxiomQuery(conceptCode,namedGraph);
		String res = restUtils.runSPARQL(queryPrefix + query, restURL);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArrayList<EvsAxiom> evsAxioms = new ArrayList<EvsAxiom>();
		try {
			Sparql sparqlResult = mapper.readValue(res, Sparql.class);
			Bindings[] bindings = sparqlResult.getResults().getBindings();
			EvsAxiom evsAxiom = new EvsAxiom();
			Boolean sw = false;
			String oldAxiom = "";
			for (Bindings b : bindings) {

				String axiom = b.getAxiom().getValue();
				String property = b.getAxiomProperty().getValue().split("#")[1];
				String value = b.getAxiomValue().getValue();
				if (value.contains("#")) {
					value = value.split("#")[1];
				}

				if (sw && !axiom.equals(oldAxiom)) {
					evsAxioms.add(evsAxiom);
					evsAxiom = new EvsAxiom();
				}
				sw = true;
				oldAxiom = axiom;

				switch (property) {
				case "annotatedSource":
					evsAxiom.setAnnotatedSource(value);
					break;
				case "annotatedTarget":
					evsAxiom.setAnnotatedTarget(value);
					break;
				case "annotatedProperty":
					evsAxiom.setAnnotatedProperty(value);
					break;
				case "type":
					evsAxiom.setType(value);
					break;
				case "P380":
					evsAxiom.setDefinitionReviewDate(value);
					break;
				case "P379":
					evsAxiom.setDefinitionReviewerName(value);
					break;
				case "P393":
					evsAxiom.setRelationshipToTarget(value);
					break;
				case "P395":
					evsAxiom.setTargetCode(value);
					break;
				case "P394":
					evsAxiom.setTargetTermType(value);
					break;
				case "P396":
					evsAxiom.setTargetTerminology(value);
					break;

                //[EVSREPORT2-36] Adding Target_Terminology_Version Qualifier
				case "P397":
					evsAxiom.setTargetTerminologyVersion(value);
					break;

				case "P381":
					evsAxiom.setAttr(value);
					break;
				case "P378":
					evsAxiom.setDefSource(value);
					break;
				case "P389":
					evsAxiom.setGoEvi(value);
					break;
				case "P387":
					evsAxiom.setGoId(value);
					break;
				case "P390":
					evsAxiom.setGoSource(value);
					break;
				case "P385":
					evsAxiom.setSourceCode(value);
					break;
				case "P391":
					evsAxiom.setSourceDate(value);
					break;
				case "P386":
					evsAxiom.setSubsourceName(value);
					break;
				case "P383":
					evsAxiom.setTermGroup(value);
					break;
				case "P384":
					evsAxiom.setTermSource(value);
					break;
				case "xref-source":
                    evsAxiom.setXrefSource(value);
                    break;

				default:

				}
			}
			evsAxioms.add(evsAxiom);

		} catch (Exception ex) {
			System.out.println("Bad News Exception");
			System.out.println(ex);
		}
		return evsAxioms;
	}

	/**
	 * Return a list of subclass concepts for a concept.
	 *
	 * @param conceptCode Concept code.
	 * @return List of EvsConcept instances.
	 */
	public List<EvsConcept> getEvsSubclasses(String conceptCode, String namedGraph, String restURL) {

		String queryPrefix = queryBuilderService.contructPrefix();
		String query = queryBuilderService.constructSubclassQuery(conceptCode,namedGraph);
		String res = restUtils.runSPARQL(queryPrefix + query, restURL);
		//System.out.println(queryPrefix + "\n" + query);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArrayList<EvsConcept> evsSubclasses = new ArrayList<EvsConcept>();
		try {
			Sparql sparqlResult = mapper.readValue(res, Sparql.class);
			Bindings[] bindings = sparqlResult.getResults().getBindings();
			for (Bindings b : bindings) {
				EvsConcept evsSubclass = new EvsConcept();
				evsSubclass.setLabel(b.getSubclassLabel().getValue());
				evsSubclass.setCode(b.getSubclassCode().getValue());
				evsSubclasses.add(evsSubclass);
			}
		} catch (Exception ex) {
			System.out.println("Bad News Exception");
			System.out.println(ex);
		}
		return evsSubclasses;
	}

	/**
	 * Return a list of super concepts for a concept.
	 *
	 * @param conceptCode Concept code.
	 * @return List of EvsConcept instances.
	 */
	public List<EvsConcept> getEvsSuperclasses(String conceptCode, String namedGraph, String restURL) {

		String queryPrefix = queryBuilderService.contructPrefix();
		String query = queryBuilderService.constructSuperclassQuery(conceptCode,namedGraph);
		String res = restUtils.runSPARQL(queryPrefix + query, restURL);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArrayList<EvsConcept> evsSuperclasses = new ArrayList<EvsConcept>();
		try {
			Sparql sparqlResult = mapper.readValue(res, Sparql.class);
			Bindings[] bindings = sparqlResult.getResults().getBindings();
			for (Bindings b : bindings) {
				EvsConcept evsSuperclass = new EvsConcept();
				evsSuperclass.setLabel(b.getSuperclassLabel().getValue());
				evsSuperclass.setCode(b.getSuperclassCode().getValue());
				evsSuperclasses.add(evsSuperclass);
			}
		} catch (Exception ex) {
			System.out.println("Bad News Exception");
			System.out.println(ex);
		}
		return evsSuperclasses;
	}

	/**
	 * Return a list of concepts related to a concept using the ConceptInSubset relationship.
	 *
	 * @param conceptCode Concept code.
	 * @return List of EvsConcept instances.
	 */
	public List <EvsConcept> getEvsConceptInSubset(String conceptCode, String namedGraph, String restURL) {

		String queryPrefix = queryBuilderService.contructPrefix();
		String query = queryBuilderService.constructConceptInSubsetQuery(conceptCode,namedGraph);
		String res = restUtils.runSPARQL(queryPrefix + query, restURL);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArrayList<EvsConcept> evsConcepts = new ArrayList<EvsConcept>();
		try {
			Sparql sparqlResult = mapper.readValue(res, Sparql.class);
			Bindings[] bindings = sparqlResult.getResults().getBindings();
			for (Bindings b : bindings) {
				EvsConcept evsConcept = new EvsConcept();
				evsConcept.setLabel(b.getConceptLabel().getValue());
				evsConcept.setCode(b.getConceptCode().getValue());
				evsConcepts.add(evsConcept);
			}
		} catch (Exception ex) {
			System.out.println("Bad News Exception");
			System.out.println(ex);
		}
		return evsConcepts;
	}

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //[EVSREPORT2-42] Associations in Report Writer. KLO, 02202020
	public List <EvsConcept> getAssociatedEvsConcepts(String conceptCode, String namedGraph, String restURL, String associationName) {
        boolean sourceOf = true;
        return getAssociatedEvsConcepts(conceptCode, namedGraph, restURL, associationName, sourceOf);
	}

	public List <EvsConcept> getAssociatedEvsConcepts(String conceptCode, String namedGraph, String restURL, String associationName, boolean sourceOf) {
		String queryPrefix = queryBuilderService.contructPrefix();
		String query = queryBuilderService.construct_associated_concept_query(namedGraph, associationName, conceptCode, sourceOf);
		String res = restUtils.runSPARQL(queryPrefix + "\n" + query, restURL);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArrayList<EvsConcept> evsConcepts = new ArrayList<EvsConcept>();
		try {
			Sparql sparqlResult = mapper.readValue(res, Sparql.class);
			Bindings[] bindings = sparqlResult.getResults().getBindings();
			for (Bindings b : bindings) {
				EvsConcept evsConcept = new EvsConcept();
				evsConcept.setLabel(b.getConceptLabel().getValue());
				evsConcept.setCode(b.getConceptCode().getValue());
				evsConcepts.add(evsConcept);
			}
		} catch (Exception ex) {
			System.out.println("Bad News Exception");
			System.out.println(ex);
		}

		return evsConcepts;
	}
    ///////////////////////////////////////////////////////////////////////////////////////////////


	/**
	 * Return concept detail complete content.
	 *
	 * @param conceptCode Concept code.
	 * @return EvsConcept instance.
	 */
	public EvsConcept getEvsConceptDetail(String conceptCode, String namedGraph, String restURL) {
		EvsConcept evsConcept = new EvsConcept();
		List <EvsProperty> properties = getEvsProperties(conceptCode, namedGraph, restURL);
		List <EvsAxiom> axioms = getEvsAxioms(conceptCode, namedGraph, restURL);
		evsConcept.setCode(EVSUtils.getConceptCode(properties));
		evsConcept.setDefinition(EVSUtils.getDefinition(properties));
		evsConcept.setPreferredName(EVSUtils.getPreferredName(properties));
		evsConcept.setDisplayName(EVSUtils.getDisplayName(properties));
		evsConcept.setNeoplasticStatus(EVSUtils.getNeoplasticStatus(properties));
		evsConcept.setSemanticTypes(EVSUtils.getSemanticType(properties));
		List <EvsConcept> subclasses = getEvsSubclasses(conceptCode, namedGraph, restURL);
		List <EvsConcept> superclasses = getEvsSuperclasses(conceptCode, namedGraph, restURL);
		evsConcept.setSubclasses(subclasses);
		evsConcept.setSuperclasses(superclasses);
		evsConcept.setSynonyms(EVSUtils.getFullSynonym(axioms));

		return evsConcept;
	}

	/**
	 * Return concept detail short content.
	 *
	 * @param conceptCode Concept code.
	 * @return EvsConcept instance.
	 */
	public EvsConcept getEvsConceptDetailShort(String conceptCode, String namedGraph, String restURL) {
		EvsConcept evsConcept = new EvsConcept();
		List <EvsProperty> properties = getEvsProperties(conceptCode, namedGraph, restURL);
		evsConcept.setProperties(properties);
		List <EvsAxiom> axioms = getEvsAxioms(conceptCode, namedGraph, restURL);
		evsConcept.setAxioms(axioms);
		String conceptLabel = getEvsConceptLabel(conceptCode, namedGraph, restURL);
		evsConcept.setCode(EVSUtils.getConceptCode(properties));
		evsConcept.setLabel(conceptLabel);
		evsConcept.setDefinition(EVSUtils.getDefinition(properties));
		evsConcept.setPreferredName(EVSUtils.getPreferredName(properties));
		evsConcept.setDisplayName(EVSUtils.getDisplayName(properties));
		evsConcept.setNeoplasticStatus(EVSUtils.getNeoplasticStatus(properties));
		evsConcept.setSemanticTypes(EVSUtils.getSemanticType(properties));

		return evsConcept;
	}

	/**
	 * Return the EvsVersion Information
	 *
	 * @return EvsVersionInfo instance.
	 */
	public EvsVersionInfo getEvsVersionInfo(String namedGraph, String restURL) {
		String queryPrefix = queryBuilderService.contructPrefix();
		String query = queryBuilderService.constructVersionInfoQuery(namedGraph);
		String res = restUtils.runSPARQL(queryPrefix + query, restURL);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		EvsVersionInfo evsVersionInfo = new EvsVersionInfo();
		try {
			Sparql sparqlResult = mapper.readValue(res, Sparql.class);
			Bindings[] bindings = sparqlResult.getResults().getBindings();
			for (Bindings b : bindings) {
				evsVersionInfo.setVersion(b.getVersion().getValue());
				evsVersionInfo.setDate(b.getDate().getValue());
				evsVersionInfo.setComment(b.getComment().getValue());
			}
		} catch (Exception ex) {
			System.out.println("Bad News Exception");
			System.out.println(ex);
		}
		return evsVersionInfo;
	}


	public List <EvsAssociation> getEvsAssociations(String namedGraph, String restURL, String associationName, boolean sourceOf) {
		String queryPrefix = queryBuilderService.contructPrefix();
		String query = queryBuilderService.construct_get_associated_concepts(namedGraph, associationName, sourceOf);
		String res = restUtils.runSPARQL(queryPrefix + "\n" + query, restURL);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArrayList<EvsAssociation> evsAssociations = new ArrayList<EvsAssociation>();
		try {
			Sparql sparqlResult = mapper.readValue(res, Sparql.class);
			Bindings[] bindings = sparqlResult.getResults().getBindings();
			for (Bindings b : bindings) {
				EvsAssociation evsAssociation = new EvsAssociation();
				evsAssociation.setSourceName(b.getSourceName().getValue());
				evsAssociation.setSourceCode(b.getSourceCode().getValue());
				evsAssociation.setAssociationName(b.getAssociationName().getValue());
				evsAssociation.setTargetName(b.getTargetName().getValue());
				evsAssociation.setTargetCode(b.getTargetCode().getValue());
				evsAssociations.add(evsAssociation);
			}
		} catch (Exception ex) {
			System.out.println("Bad News Exception");
			System.out.println(ex);
			//ex.printStackTrace();
		}
		return evsAssociations;
    }

	public List <EvsSupportedAssociation> getEvsSupportedAssociations(String namedGraph, String restURL) {
		String queryPrefix = queryBuilderService.contructPrefix();
		String query = queryBuilderService.constructSupportedAssociationQuery(namedGraph);
		String res = restUtils.runSPARQL(queryPrefix + "\n" + query, restURL);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArrayList<EvsSupportedAssociation> evsSupportedAssociations = new ArrayList<EvsSupportedAssociation>();
		try {
			Sparql sparqlResult = mapper.readValue(res, Sparql.class);
			Bindings[] bindings = sparqlResult.getResults().getBindings();
			for (Bindings b : bindings) {
				EvsSupportedAssociation evsSupportedAssociation = new EvsSupportedAssociation();
				evsSupportedAssociation.setName(b.getSupportedAssociationName().getValue());
				evsSupportedAssociation.setCode(b.getSupportedAssociationCode().getValue());
				evsSupportedAssociations.add(evsSupportedAssociation);
			}
		} catch (Exception ex) {
			System.out.println("Bad News Exception");
			System.out.println(ex);
		}
		return evsSupportedAssociations;
    }


	public List<String> getSubsets(String named_graph, String root, String restURL) {
		String queryPrefix = queryBuilderService.contructPrefix();
		boolean subset_code_only = true;
		String query = queryBuilderService.construct_get_subsets(named_graph, root, subset_code_only);
		String json = restUtils.runSPARQL(queryPrefix + "\n" + query, restURL);
		Vector v = new JSONUtils().parseJSON(json);
		v = new ParserUtils().getResponseValues(v);
		v = new SortUtils().quickSort(v);
		List<String> list = new ArrayList();
		for (int i=0; i<v.size(); i++) {
			String t = (String) v.elementAt(i);
		    list.add(t);
		}
		return list;
	}

	public List<String> getConceptsInSubset(String named_graph, String code, String restURL) {
		String queryPrefix = queryBuilderService.contructPrefix();
		boolean code_only = true;
		String query = queryBuilderService.construct_get_concepts_in_subset(named_graph, code, code_only);
		String json = restUtils.runSPARQL(queryPrefix + "\n" + query, restURL);
		Vector v = new JSONUtils().parseJSON(json);
		v = new ParserUtils().getResponseValues(v);
		v = new SortUtils().quickSort(v);
		List<String> list = new ArrayList();
		for (int i=0; i<v.size(); i++) {
			String t = (String) v.elementAt(i);
		    list.add(t);
		}
		return list;
	}


    public List<String> getSubsetMemberConceptData(String named_graph, String subset_code, String restURL) {
		String queryPrefix = queryBuilderService.contructPrefix();
		boolean code_only = true;
		String query = queryBuilderService.construct_get_subset_member_concept_data(named_graph, subset_code);
		String json = restUtils.runSPARQL(queryPrefix + "\n" + query, restURL);
		Vector v = new JSONUtils().parseJSON(json);
		v = new ParserUtils().getResponseValues(v);
		v = new SortUtils().quickSort(v);
		List<String> list = new ArrayList();
		for (int i=0; i<v.size(); i++) {
			String t = (String) v.elementAt(i);
		    list.add(t);
		}
		return list;
	}

	public List<String> getMatchedAnnotatedTarget(String named_graph, String code, String propertyName, Vector qualifierNames, Vector qualifierValues, String restURL) {
		String queryPrefix = queryBuilderService.contructPrefix();
		String query = queryBuilderService.construct_get_matched_annotated_target(named_graph, code, propertyName, qualifierNames, qualifierValues);
		String json = restUtils.runSPARQL(queryPrefix + "\n" + query, restURL);
		Vector v = new JSONUtils().parseJSON(json);
		v = new ParserUtils().getResponseValues(v);
		v = new SortUtils().quickSort(v);
		List<String> list = new ArrayList();
		for (int i=0; i<v.size(); i++) {
			String t = (String) v.elementAt(i);
		    list.add(t);
		}
		return list;
	}

	public List <EvsSupportedRole> getEvsSupportedRoles(String namedGraph, String restURL) {
		String queryPrefix = queryBuilderService.contructPrefix();
		String query = queryBuilderService.constructSupportedRoleQuery(namedGraph);
		String res = restUtils.runSPARQL(queryPrefix + "\n" + query, restURL);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ArrayList<EvsSupportedRole> evsSupportedRoles = new ArrayList<EvsSupportedRole>();
		try {
			Sparql sparqlResult = mapper.readValue(res, Sparql.class);
			Bindings[] bindings = sparqlResult.getResults().getBindings();
			for (Bindings b : bindings) {
				EvsSupportedRole evsSupportedRole = new EvsSupportedRole();
				evsSupportedRole.setName(b.getSupportedRoleName().getValue());
				evsSupportedRole.setCode(b.getSupportedRoleCode().getValue());
				evsSupportedRoles.add(evsSupportedRole);
			}
		} catch (Exception ex) {
			System.out.println("Bad News Exception");
			System.out.println(ex);
		}
		return evsSupportedRoles;
    }

    public List<String> getSubsetCconceptData(String named_graph, String code, String restURL) {
		String queryPrefix = queryBuilderService.contructPrefix();
		String query = queryBuilderService.construct_get_subset_concept_data(named_graph, code);
		String json = restUtils.runSPARQL(queryPrefix + "\n" + query, restURL);
		return parseJSON(json);
	}

	public List<String> getRoleTargets(String named_graph, String code, String roleName, String restURL) {
		String queryPrefix = queryBuilderService.contructPrefix();
		String query = queryBuilderService.construct_get_role_targets(named_graph, code, roleName);
		String json = restUtils.runSPARQL(queryPrefix + "\n" + query, restURL);
		return parseJSON(json);
	}

	public List<String> parseJSON(String json) {
		if (json == null) return null;
		Vector v = new JSONUtils().parseJSON(json);
		v = new ParserUtils().getResponseValues(v);
		v = new SortUtils().quickSort(v);
		List<String> list = new ArrayList();
		for (int i=0; i<v.size(); i++) {
			String t = (String) v.elementAt(i);
		    list.add(t);
		}
		return list;
	}

	public Vector executeQuery(String query, String restURL) {
		if (restUtils == null) {
		    System.out.println(	"ERROR: restUtils == null ");
		} else {
			System.out.println(	"restUtils OK");
		}
		String json = restUtils.runSPARQL(query, restURL);
		Vector v = new JSONUtils().parseJSON(json);
		v = new ParserUtils().getResponseValues(v);
		v = new SortUtils().quickSort(v);
		return v;
	}
}
