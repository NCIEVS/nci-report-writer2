package gov.nih.nci.evs.reportwriter.core.model.evs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EvsSubclass {
	
	private String subclass;
	private String code;
	private String label;
	
	private List <EvsProperty> properties;
	private List <EvsAxiom> axioms;

	@JsonIgnore
	public String getSubclass() {
		return subclass;
	}
	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}
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
