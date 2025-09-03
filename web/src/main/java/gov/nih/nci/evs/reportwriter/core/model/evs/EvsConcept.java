package gov.nih.nci.evs.reportwriter.core.model.evs;

import java.util.List;

public class EvsConcept {

	private String code;
	private String label;
	private String definition;
	private String displayName;
	private String preferredName;
	private String neoplasticStatus;
	private List <EvsConcept> subclasses;
	private List <EvsConcept> superclasses;
	private List <String> semanticTypes;
	private List <EvsSynonym> synonyms;
	private List <EvsProperty> properties;
	private List <EvsAxiom> axioms;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getPreferredName() {
		return preferredName;
	}
	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}
	public String getNeoplasticStatus() {
		return neoplasticStatus;
	}
	public void setNeoplasticStatus(String neoplasticStatus) {
		this.neoplasticStatus = neoplasticStatus;
	}
	public List<EvsConcept> getSubclasses() {
		return subclasses;
	}
	public void setSubclasses(List<EvsConcept> subclasses) {
		this.subclasses = subclasses;
	}
	public List<EvsConcept> getSuperclasses() {
		return superclasses;
	}
	public void setSuperclasses(List<EvsConcept> superclasses) {
		this.superclasses = superclasses;
	}
	public List<String> getSemanticTypes() {
		return semanticTypes;
	}
	public void setSemanticTypes(List<String> semanticTypes) {
		this.semanticTypes = semanticTypes;
	}
	public List<EvsSynonym> getSynonyms() {
		return synonyms;
	}
	public void setSynonyms(List<EvsSynonym> synonyms) {
		this.synonyms = synonyms;
	}
	public List<EvsProperty> getProperties() {
		return properties;
	}
	public void setProperties(List<EvsProperty> properties) {
		this.properties = properties;
	}
	public List<EvsAxiom> getAxioms() {
		return axioms;
	}
	public void setAxioms(List<EvsAxiom> axioms) {
		this.axioms = axioms;
	}
}
