package gov.nih.nci.evs.reportwriter.web.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * The persistent class for the report_task database table.
 * 
 */
@Entity
@Table(name = "report_task")
@NamedQueries({   
@NamedQuery(name = "ReportTask.findAll", query = "SELECT r FROM ReportTemplate r")

})
public class ReportTask implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private LocalDateTime dateCompleted;
	private LocalDateTime dateCreated;
	private LocalDateTime dateStarted;
	private LocalDateTime dateLastUpdated;
	private String createdBy;
	private String lastUpdatedBy;
	private ReportTemplate reportTemplate;
	private String status;

	public ReportTask() {
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_completed")
	public LocalDateTime getDateCompleted() {
		return this.dateCompleted;
	}

	public void setDateCompleted(LocalDateTime dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	public LocalDateTime getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_started")
	public LocalDateTime getDateStarted() {
		return this.dateStarted;
	}

	public void setDateStarted(LocalDateTime dateStarted) {
		this.dateStarted = dateStarted;
	}

	@Column(name = "date_last_updated")
	public LocalDateTime getDateLastUpdated() {
		return this.dateLastUpdated;
	}

	public void setDateLastUpdated(LocalDateTime dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}


	/*
	 * @Column(name="report_template_id") public int getReportTemplateId() { return
	 * this.reportTemplateId; }
	 * 
	 * public void setReportTemplateId(int reportTemplateId) { this.reportTemplateId
	 * = reportTemplateId; }
	 */

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

	// bi-directional many-to-one associion to ReportTemplate
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "report_template_id")
	public ReportTemplate getReportTemplate() {
		return this.reportTemplate;
	}

	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	@Column(name = "status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}