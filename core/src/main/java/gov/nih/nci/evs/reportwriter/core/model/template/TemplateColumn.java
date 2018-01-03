package gov.nih.nci.evs.reportwriter.core.model.template;

public class TemplateColumn {
	private Integer columnNumber;
	private String label;
	private String display;
	private String propertyType;
	private String property;
	private String source;
	private String group;
	private String subsource;

	public Integer getColumnNumber() {
		return columnNumber;
	}
	public void setColumnNumber(Integer columnNumber) {
		this.columnNumber = columnNumber;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getSubsource() {
		return subsource;
	}
	public void setSubsource(String subsource) {
		this.subsource = subsource;
	}
}
