package gov.nih.nci.evs.reportwriter.core.model.evs;

import com.fasterxml.jackson.annotation.JsonIgnore;

/** The Class EvsSuperclass. */
public class EvsSuperclass {

  /** The superclass. */
  private String superclass;

  /** The code. */
  private String code;

  /** The label. */
  private String label;

  /**
   * Gets the superclass.
   *
   * @return the superclass
   */
  @JsonIgnore
  public String getSuperclass() {
    return superclass;
  }

  /**
   * Sets the superclass.
   *
   * @param superclass the new superclass
   */
  public void setSuperclass(String superclass) {
    this.superclass = superclass;
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
}
