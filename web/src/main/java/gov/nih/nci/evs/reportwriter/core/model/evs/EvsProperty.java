package gov.nih.nci.evs.reportwriter.core.model.evs;

/** The Class EvsProperty. */
public class EvsProperty {

  /** The code. */
  String code;

  /** The label. */
  String label;

  /** The value. */
  String value;

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
   * Gets the value.
   *
   * @return the value
   */
  public String getValue() {
    return value;
  }

  /**
   * Sets the value.
   *
   * @param value the new value
   */
  public void setValue(String value) {
    this.value = value;
  }

  /* see superclass */
  @Override
  public String toString() {
    return "Code: " + this.code + "\n" + " Label: " + this.label + "\n" + " Value: " + this.value;
  }
}
