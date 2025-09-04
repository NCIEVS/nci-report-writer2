package gov.nih.nci.evs.reportwriter.core.model.evs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/** The Class EvsSubclass. */
public class EvsSubclass {

  /** The subclass. */
  private String subclass;

  /** The code. */
  private String code;

  /** The label. */
  private String label;

  /** The properties. */
  private List<EvsProperty> properties;

  /** The axioms. */
  private List<EvsAxiom> axioms;

  /**
   * Gets the subclass.
   *
   * @return the subclass
   */
  @JsonIgnore
  public String getSubclass() {
    return subclass;
  }

  /**
   * Sets the subclass.
   *
   * @param subclass the new subclass
   */
  public void setSubclass(String subclass) {
    this.subclass = subclass;
  }

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
