package gov.nih.nci.evs.reportwriter.web.support;

/** The Class ReportTaskUI. */
public class ReportTaskUI {

  /** The id. */
  private Integer id;

  /** The date completed. */
  private String dateCompleted;

  /** The date created. */
  private String dateCreated;

  /** The date started. */
  private String dateStarted;

  /** The report template name. */
  private String reportTemplateName;

  /** The report template id. */
  private Integer reportTemplateId;

  /** The status. */
  private String status;

  /** The version. */
  private String version;

  /** The graph name. */
  private String graphName;

  /** The database url. */
  private String databaseUrl;

  /** The database type. */
  private String databaseType;

  /** Instantiates a new report task UI. */
  public ReportTaskUI() {}

  /**
   * Instantiates a new report task UI.
   *
   * @param id the id
   * @param dateCompleted the date completed
   * @param dateCreated the date created
   * @param dateStarted the date started
   * @param reportTemplateName the report template name
   * @param reportTemplateId the report template id
   * @param status the status
   * @param version the version
   */
  public ReportTaskUI(
      Integer id,
      String dateCompleted,
      String dateCreated,
      String dateStarted,
      String reportTemplateName,
      Integer reportTemplateId,
      String status,
      String version) {
    this.id = id;
    this.dateCompleted = dateCompleted;
    this.dateCreated = dateCreated;
    this.dateStarted = dateStarted;
    this.reportTemplateName = reportTemplateName;
    this.reportTemplateId = reportTemplateId;
    this.status = status;
    this.version = version;
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public Integer getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * Gets the date completed.
   *
   * @return the date completed
   */
  public String getDateCompleted() {
    return dateCompleted;
  }

  /**
   * Sets the date completed.
   *
   * @param dateCompleted the new date completed
   */
  public void setDateCompleted(String dateCompleted) {
    this.dateCompleted = dateCompleted;
  }

  /**
   * Gets the date created.
   *
   * @return the date created
   */
  public String getDateCreated() {
    return dateCreated;
  }

  /**
   * Sets the date created.
   *
   * @param dateCreated the new date created
   */
  public void setDateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
  }

  /**
   * Gets the date started.
   *
   * @return the date started
   */
  public String getDateStarted() {
    return dateStarted;
  }

  /**
   * Sets the date started.
   *
   * @param dateStarted the new date started
   */
  public void setDateStarted(String dateStarted) {
    this.dateStarted = dateStarted;
  }

  /**
   * Gets the report template name.
   *
   * @return the report template name
   */
  public String getReportTemplateName() {
    return reportTemplateName;
  }

  /**
   * Sets the report template name.
   *
   * @param reportTemplateName the new report template name
   */
  public void setReportTemplateName(String reportTemplateName) {
    this.reportTemplateName = reportTemplateName;
  }

  /**
   * Gets the report template id.
   *
   * @return the report template id
   */
  public Integer getReportTemplateId() {
    return reportTemplateId;
  }

  /**
   * Sets the report template id.
   *
   * @param reportTemplateId the new report template id
   */
  public void setReportTemplateId(Integer reportTemplateId) {
    this.reportTemplateId = reportTemplateId;
  }

  /**
   * Gets the status.
   *
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * Sets the status.
   *
   * @param status the new status
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Gets the version.
   *
   * @return the version
   */
  public String getVersion() {
    return version;
  }

  /**
   * Sets the version.
   *
   * @param version the new version
   */
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * Gets the graph name.
   *
   * @return the graph name
   */
  public String getGraphName() {
    return graphName;
  }

  /**
   * Sets the graph name.
   *
   * @param graphName the new graph name
   */
  public void setGraphName(String graphName) {
    this.graphName = graphName;
  }

  /**
   * Gets the database url.
   *
   * @return the database url
   */
  public String getDatabaseUrl() {
    return databaseUrl;
  }

  /**
   * Sets the database url.
   *
   * @param databaseUrl the new database url
   */
  public void setDatabaseUrl(String databaseUrl) {
    this.databaseUrl = databaseUrl;
  }

  /**
   * Gets the database type.
   *
   * @return the database type
   */
  public String getDatabaseType() {
    return databaseType;
  }

  /**
   * Sets the database type.
   *
   * @param databaseType the new database type
   */
  public void setDatabaseType(String databaseType) {
    this.databaseType = databaseType;
  }
}
