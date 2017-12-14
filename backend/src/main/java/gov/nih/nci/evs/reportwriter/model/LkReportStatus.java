package gov.nih.nci.evs.reportwriter.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the lk_report_status database table.
 * 
 */
@Entity
@Table(name="lk_report_status")
@NamedQuery(name="LkReportStatus.findAll", query="SELECT l FROM LkReportStatus l")
public class LkReportStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private List<ReportTask> reportTasks;

	public LkReportStatus() {
	}


	@Id
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	/*
	//bi-directional many-to-one association to ReportTask
	@OneToMany(mappedBy="lkReportStatus")
	public List<ReportTask> getReportTasks() {
		return this.reportTasks;
	}

	public void setReportTasks(List<ReportTask> reportTasks) {
		this.reportTasks = reportTasks;
	}

	public ReportTask addReportTask(ReportTask reportTask) {
		getReportTasks().add(reportTask);
		reportTask.setLkReportStatus(this);

		return reportTask;
	}

	public ReportTask removeReportTask(ReportTask reportTask) {
		getReportTasks().remove(reportTask);
		reportTask.setLkReportStatus(null);

		return reportTask;
	}
	*/

}