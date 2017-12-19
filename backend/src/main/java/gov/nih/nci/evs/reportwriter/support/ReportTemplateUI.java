package gov.nih.nci.evs.reportwriter.support;

import java.util.List;

import gov.nih.nci.evs.reportwriter.model.ReportTemplateColumn;
import gov.nih.nci.evs.reportwriter.model.ReportTemplateConceptList;

public class ReportTemplateUI {
	
	
	private Integer id;
	private Integer level;
	private String name;
	private String rootConceptCode;
	private Integer sortColumn;
	private String status;
	private String type;
	private String association;
	private List<ReportTemplateColumn> reportTemplateColumns;
	

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
	public List<ReportTemplateColumn> getReportTemplateColumns() {
		return reportTemplateColumns;
	}
	public void setReportTemplateColumns(List<ReportTemplateColumn> reportTemplateColumns) {
		this.reportTemplateColumns = reportTemplateColumns;
	}
	
	

}
