package gov.nih.nci.evs.reportwriter.web.model;

public class LookUp {

  String label;
  String value;

  public LookUp() {}

  public LookUp(final String label, final String value) {
    this.label = label;
    this.value = value;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(final String label) {
    this.label = label;
  }

  public String getValue() {
    return value;
  }

  public void setValue(final String value) {
    this.value = value;
  }
}
