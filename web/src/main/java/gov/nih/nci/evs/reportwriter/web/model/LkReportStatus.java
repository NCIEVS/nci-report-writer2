package gov.nih.nci.evs.reportwriter.web.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/** The persistent class for the lk_report_status database table. */
@Entity
@Table(name = "lk_report_status")
@NamedQuery(name = "LkReportStatus.findAll", query = "SELECT l FROM LkReportStatus l")
public class LkReportStatus implements LkGeneric, Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The name. */
  private String name;

  /** The description. */
  private String description;

  /** The report tasks. */
  // private List<ReportTask> reportTasks;

  /** Instantiates a new lk report status. */
  public LkReportStatus() {}

  /* see superclass */
  @Override
  @Id
  public String getName() {
    return this.name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /* see superclass */
  @Override
  public String getDescription() {
    return this.description;
  }

  /**
   * Sets the description.
   *
   * @param description the new description
   */
  public void setDescription(final String description) {
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
