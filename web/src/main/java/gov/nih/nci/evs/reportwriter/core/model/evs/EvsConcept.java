package gov.nih.nci.evs.reportwriter.core.model.evs;

import java.util.List;

/** The Class EvsConcept. */
public class EvsConcept {

  /** The code. */
  private String code;

  /** The label. */
  private String label;

  /** The definition. */
  private String definition;

  /** The display name. */
  private String displayName;

  /** The preferred name. */
  private String preferredName;

  /** The neoplastic status. */
  private String neoplasticStatus;

  /** The subclasses. */
  private List<EvsConcept> subclasses;

  /** The superclasses. */
  private List<EvsConcept> superclasses;

  /** The semantic types. */
  private List<String> semanticTypes;

  /** The synonyms. */
  private List<EvsSynonym> synonyms;

  /** The properties. */
  private List<EvsProperty> properties;

  /** The axioms. */
  private List<EvsAxiom> axioms;

  /**
   * Gets the code.
   *
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * Sets the code.
   *
   * @param code the new code
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * Gets the label.
   *
   * @return the label
   */
  public String getLabel() {
    return label;
  }

  /**
   * Sets the label.
   *
   * @param label the new label
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * Gets the definition.
   *
   * @return the definition
   */
  public String getDefinition() {
    return definition;
  }

  /**
   * Sets the definition.
   *
   * @param definition the new definition
   */
  public void setDefinition(String definition) {
    this.definition = definition;
  }

  /**
   * Gets the display name.
   *
   * @return the display name
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Sets the display name.
   *
   * @param displayName the new display name
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  /**
   * Gets the preferred name.
   *
   * @return the preferred name
   */
  public String getPreferredName() {
    return preferredName;
  }

  /**
   * Sets the preferred name.
   *
   * @param preferredName the new preferred name
   */
  public void setPreferredName(String preferredName) {
    this.preferredName = preferredName;
  }

  /**
   * Gets the neoplastic status.
   *
   * @return the neoplastic status
   */
  public String getNeoplasticStatus() {
    return neoplasticStatus;
  }

  /**
   * Sets the neoplastic status.
   *
   * @param neoplasticStatus the new neoplastic status
   */
  public void setNeoplasticStatus(String neoplasticStatus) {
    this.neoplasticStatus = neoplasticStatus;
  }

  /**
   * Gets the subclasses.
   *
   * @return the subclasses
   */
  public List<EvsConcept> getSubclasses() {
    return subclasses;
  }

  /**
   * Sets the subclasses.
   *
   * @param subclasses the new subclasses
   */
  public void setSubclasses(List<EvsConcept> subclasses) {
    this.subclasses = subclasses;
  }

  /**
   * Gets the superclasses.
   *
   * @return the superclasses
   */
  public List<EvsConcept> getSuperclasses() {
    return superclasses;
  }

  /**
   * Sets the superclasses.
   *
   * @param superclasses the new superclasses
   */
  public void setSuperclasses(List<EvsConcept> superclasses) {
    this.superclasses = superclasses;
  }

  /**
   * Gets the semantic types.
   *
   * @return the semantic types
   */
  public List<String> getSemanticTypes() {
    return semanticTypes;
  }

  /**
   * Sets the semantic types.
   *
   * @param semanticTypes the new semantic types
   */
  public void setSemanticTypes(List<String> semanticTypes) {
    this.semanticTypes = semanticTypes;
  }

  /**
   * Gets the synonyms.
   *
   * @return the synonyms
   */
  public List<EvsSynonym> getSynonyms() {
    return synonyms;
  }

  /**
   * Sets the synonyms.
   *
   * @param synonyms the new synonyms
   */
  public void setSynonyms(List<EvsSynonym> synonyms) {
    this.synonyms = synonyms;
  }

  /**
   * Gets the properties.
   *
   * @return the properties
   */
  public List<EvsProperty> getProperties() {
    return properties;
  }

  /**
   * Sets the properties.
   *
   * @param properties the new properties
   */
  public void setProperties(List<EvsProperty> properties) {
    this.properties = properties;
  }

  /**
   * Gets the axioms.
   *
   * @return the axioms
   */
  public List<EvsAxiom> getAxioms() {
    return axioms;
  }

  /**
   * Sets the axioms.
   *
   * @param axioms the new axioms
   */
  public void setAxioms(List<EvsAxiom> axioms) {
    this.axioms = axioms;
  }
}
