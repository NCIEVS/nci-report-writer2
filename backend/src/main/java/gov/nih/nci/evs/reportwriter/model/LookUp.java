package gov.nih.nci.evs.reportwriter.model;

public class LookUp {

	String label;
	String value;
	
	public LookUp() {};
	
	public LookUp(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
