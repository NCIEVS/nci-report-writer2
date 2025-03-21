package gov.nih.nci.evs.reportwriter.web.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;


/**
 * The persistent class for the report_template_concept_list database table.
 * 
 */
@Entity
@Table(name="report_template_concept_list")
@NamedQuery(name="ReportTemplateConceptList.findAll", query="SELECT r FROM ReportTemplateConceptList r")
public class ReportTemplateConceptList implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String conceptCode;
	private ReportTemplate reportTemplate;
	private LocalDateTime dateCreated;
	private LocalDateTime dateLastUpdated;
	private String createdBy;
	private String lastUpdatedBy;	

	public ReportTemplateConceptList() {
	}


	@Id
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name="concept_code")
	public String getConceptCode() {
		return this.conceptCode;
	}

	public void setConceptCode(String conceptCode) {
		this.conceptCode = conceptCode;
	}


	//bi-directional many-to-one association to ReportTemplate
	@ManyToOne
	@JoinColumn(name="report_template_id")
	public ReportTemplate getReportTemplate() {
		return this.reportTemplate;
	}

	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
	}
	
	public LocalDateTime getDateLastUpdated() {
		return dateLastUpdated;
	}

	public void setDateLastUpdated(LocalDateTime dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

}