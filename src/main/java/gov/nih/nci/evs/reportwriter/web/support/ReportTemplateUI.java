package gov.nih.nci.evs.reportwriter.web.support;

import java.time.LocalDateTime;
import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateColumn;
import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateConceptList;

public class ReportTemplateUI {
	
	
	private Integer id;
	private Integer level;
	private String name;
	private String rootConceptCode;
	private Integer sortColumn;
	private String status;
	private String type;
	private String association;
	private List<ReportTemplateColumn> columns;
	private String dateCreated;
	private String dateLastUpdated;
		

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAssociation() {
		return association;
	}
	public void setAssociation(String association) {
		this.association = association;
	}
	public String getRootConceptCode() {
		return rootConceptCode;
	}
	public void setRootConceptCode(String rootConceptCode) {
		this.rootConceptCode = rootConceptCode;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(Integer sortColumn) {
		this.sortColumn = sortColumn;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ReportTemplateColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<ReportTemplateColumn> columns) {
		this.columns = columns;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getDateLastUpdated() {
		return dateLastUpdated;
	}
	public void setDateLastUpdated(String dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}
	
	

}
