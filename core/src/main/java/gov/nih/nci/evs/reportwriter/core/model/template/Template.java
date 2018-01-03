package gov.nih.nci.evs.reportwriter.core.model.template;

import java.util.List;


public class Template {
	private String name;
	private String type;
	private String rootConceptCode;
	private String association;
	private Integer level;
	private Integer sortColumn;
	
	private List <TemplateColumn> columns;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRootConceptCode() {
		return rootConceptCode;
	}

	public void setRootConceptCode(String rootConceptCode) {
		this.rootConceptCode = rootConceptCode;
	}

	public String getAssociation() {
		return association;
	}

	public void setAssociation(String association) {
		this.association = association;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(Integer sortColumn) {
		this.sortColumn = sortColumn;
	}


	public List<TemplateColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<TemplateColumn> columns) {
		this.columns = columns;
	}
	
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("Name: " + name + "\n");
		str.append("Type: " + type + "\n");
		str.append("Root Concept Code: " + rootConceptCode + "\n");
		str.append("Association: " + association + "\n");
		str.append("Level: " + level + "\n");
		str.append("Sort Column: " + sortColumn + "\n");
		return str.toString();
	}
}
