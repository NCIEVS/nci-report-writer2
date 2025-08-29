package gov.nih.nci.evs.reportwriter.web.support;

import java.util.Date;

import gov.nih.nci.evs.reportwriter.web.model.ReportTemplate;

public class ReportTaskUI {
	
	private Integer id;
	private String dateCompleted;
	private String dateCreated;
	private String dateStarted;
	private String reportTemplateName;
	private Integer reportTemplateId;
	private String status;
	private String version;
	private String graphName;
	private String databaseUrl;
	private String databaseType;
	
	public ReportTaskUI() {
		
	}
	
	public ReportTaskUI(Integer id,String dateCompleted,String dateCreated,String dateStarted,
			String reportTemplateName,Integer reportTemplateId, String status, String version) {
		this.id = id;
		this.dateCompleted = dateCompleted;
		this.dateCreated = dateCreated;
		this.dateStarted = dateStarted;
		this.reportTemplateName = reportTemplateName;
		this.reportTemplateId = reportTemplateId;
		this.status = status;
		this.version = version;
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDateCompleted() {
		return dateCompleted;
	}
	public void setDateCompleted(String dateCompleted) {
		this.dateCompleted = dateCompleted;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getDateStarted() {
		return dateStarted;
	}
	public void setDateStarted(String dateStarted) {
		this.dateStarted = dateStarted;
	}
	public String getReportTemplateName() {
		return reportTemplateName;
	}
	public void setReportTemplateName(String reportTemplateName) {
		this.reportTemplateName = reportTemplateName;
	}
	public Integer getReportTemplateId() {
		return reportTemplateId;
	}
	public void setReportTemplateId(Integer reportTemplateId) {
		this.reportTemplateId = reportTemplateId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getGraphName() {
		return graphName;
	}

	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}

	public String getDatabaseUrl() {
		return databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}
}
