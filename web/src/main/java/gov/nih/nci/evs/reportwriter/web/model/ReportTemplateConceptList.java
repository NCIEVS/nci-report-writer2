package gov.nih.nci.evs.reportwriter.web.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/** The persistent class for the report_template_concept_list database table. */
@Entity
@Table(name = "report_template_concept_list")
@NamedQuery(
    name = "ReportTemplateConceptList.findAll",
    query = "SELECT r FROM ReportTemplateConceptList r")
public class ReportTemplateConceptList implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The id. */
  private int id;

  /** The concept code. */
  private String conceptCode;

  /** The report template. */
  private ReportTemplate reportTemplate;

  /** The date created. */
  //  private LocalDateTime dateCreated;

  /** The date last updated. */
  private LocalDateTime dateLastUpdated;

  /** The created by. */
  private String createdBy;

  /** The last updated by. */
  private String lastUpdatedBy;

  /** Instantiates a new report template concept list. */
  public ReportTemplateConceptList() {}

  /**
   * Gets the id.
   *
   * @return the id
   */
  @Id
  public int getId() {
    return this.id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(final int id) {
    this.id = id;
  }

  /**
   * Gets the concept code.
   *
   * @return the concept code
   */
  @Column(name = "concept_code")
  public String getConceptCode() {
    return this.conceptCode;
  }

  /**
   * Sets the concept code.
   *
   * @param conceptCode the new concept code
   */
  public void setConceptCode(final String conceptCode) {
    this.conceptCode = conceptCode;
  }

  /**
   * Gets the report template.
   *
   * @return the report template
   */
  // bi-directional many-to-one association to ReportTemplate
  @ManyToOne
  @JoinColumn(name = "report_template_id")
  public ReportTemplate getReportTemplate() {
    return this.reportTemplate;
  }

  /**
   * Sets the report template.
   *
   * @param reportTemplate the new report template
   */
  public void setReportTemplate(final ReportTemplate reportTemplate) {
    this.reportTemplate = reportTemplate;
  }

  /**
   * Gets the date last updated.
   *
   * @return the date last updated
   */
  public LocalDateTime getDateLastUpdated() {
    return dateLastUpdated;
  }

  /**
   * Sets the date last updated.
   *
   * @param dateLastUpdated the new date last updated
   */
  public void setDateLastUpdated(final LocalDateTime dateLastUpdated) {
    this.dateLastUpdated = dateLastUpdated;
  }

  /**
   * Gets the created by.
   *
   * @return the created by
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * Sets the created by.
   *
   * @param createdBy the new created by
   */
  public void setCreatedBy(final String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * Gets the last updated by.
   *
   * @return the last updated by
   */
  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  /**
   * Sets the last updated by.
   *
   * @param lastUpdatedBy the new last updated by
   */
  public void setLastUpdatedBy(final String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }
}
