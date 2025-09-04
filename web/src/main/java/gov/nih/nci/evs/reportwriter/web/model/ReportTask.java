package gov.nih.nci.evs.reportwriter.web.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/** The persistent class for the report_task database table. */
@Entity
@Table(name = "report_task")
@NamedQueries({@NamedQuery(name = "ReportTask.findAll", query = "SELECT r FROM ReportTemplate r")})
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
  private String version;
  private String graphName;
  private String databaseUrl;
  private String databaseType;

  public ReportTask() {}

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer getId() {
    return this.id;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  // @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_completed")
  public LocalDateTime getDateCompleted() {
    return this.dateCompleted;
  }

  public void setDateCompleted(final LocalDateTime dateCompleted) {
    this.dateCompleted = dateCompleted;
  }

  // @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_created")
  public LocalDateTime getDateCreated() {
    return this.dateCreated;
  }

  public void setDateCreated(final LocalDateTime dateCreated) {
    this.dateCreated = dateCreated;
  }

  // @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "date_started")
  public LocalDateTime getDateStarted() {
    return this.dateStarted;
  }

  public void setDateStarted(final LocalDateTime dateStarted) {
    this.dateStarted = dateStarted;
  }

  @Column(name = "date_last_updated")
  public LocalDateTime getDateLastUpdated() {
    return this.dateLastUpdated;
  }

  public void setDateLastUpdated(final LocalDateTime dateLastUpdated) {
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

  public void setCreatedBy(final String createdBy) {
    this.createdBy = createdBy;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public void setLastUpdatedBy(final String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  // bi-directional many-to-one associion to ReportTemplate
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "report_template_id")
  public ReportTemplate getReportTemplate() {
    return this.reportTemplate;
  }

  public void setReportTemplate(final ReportTemplate reportTemplate) {
    this.reportTemplate = reportTemplate;
  }

  @Column(name = "status")
  public String getStatus() {
    return this.status;
  }

  public void setStatus(final String status) {
    this.status = status;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(final String version) {
    this.version = version;
  }

  @Column(name = "graph_name")
  public String getGraphName() {
    return graphName;
  }

  public void setGraphName(final String graphName) {
    this.graphName = graphName;
  }

  @Column(name = "database_url")
  public String getDatabaseUrl() {
    return databaseUrl;
  }

  public void setDatabaseUrl(final String databaseUrl) {
    this.databaseUrl = databaseUrl;
  }

  @Column(name = "database_type")
  public String getDatabaseType() {
    return databaseType;
  }

  public void setDatabaseType(final String databaseType) {
    this.databaseType = databaseType;
  }
}
