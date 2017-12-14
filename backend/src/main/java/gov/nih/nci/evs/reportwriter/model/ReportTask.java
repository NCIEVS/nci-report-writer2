package gov.nih.nci.evs.reportwriter.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the report_task database table.
 * 
 */
@Entity
@Table(name="report_task")
@NamedQuery(name="ReportTask.findAll", query="SELECT r FROM ReportTask r")
public class ReportTask implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Date dateCompleted;
	private Date dateCreated;
	private Date dateStarted;
	private int reportTemplateId;
	private LkReportStatus lkReportStatus;

	public ReportTask() {
	}


	@Id
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_completed")
	public Date getDateCompleted() {
		return this.dateCompleted;
	}

	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_created")
	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_started")
	public Date getDateStarted() {
		return this.dateStarted;
	}

	public void setDateStarted(Date dateStarted) {
		this.dateStarted = dateStarted;
	}


	@Column(name="report_template_id")
	public int getReportTemplateId() {
		return this.reportTemplateId;
	}

	public void setReportTemplateId(int reportTemplateId) {
		this.reportTemplateId = reportTemplateId;
	}


	//bi-directional many-to-one association to LkReportStatus
	@ManyToOne
	@JoinColumn(name="status")
	public LkReportStatus getLkReportStatus() {
		return this.lkReportStatus;
	}

	public void setLkReportStatus(LkReportStatus lkReportStatus) {
		this.lkReportStatus = lkReportStatus;
	}

}