package gov.nih.nci.evs.reportwriter.web.support;

import java.util.List;

import gov.nih.nci.evs.reportwriter.web.model.ReportTemplateColumn;

/** The Class ReportTemplateUI. */
public class ReportTemplateUI {

  /** The id. */
  private Integer id;

  /** The level. */
  private Integer level;

  /** The name. */
  private String name;

  /** The root concept code. */
  private String rootConceptCode;

  /** The sort column. */
  private Integer sortColumn;

  /** The status. */
  private String status;

  /** The type. */
  private String type;

  /** The association. */
  private String association;

  /** The columns. */
  private List<ReportTemplateColumn> columns;

  /** The date created. */
  private String dateCreated;

  /** The date last updated. */
  private String dateLastUpdated;

  /**
   * Gets the type.
   *
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the type.
   *
   * @param type the new type
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Gets the association.
   *
   * @return the association
   */
  public String getAssociation() {
    return association;
  }

  /**
   * Sets the association.
   *
   * @param association the new association
   */
  public void setAssociation(String association) {
    this.association = association;
  }

  /**
   * Gets the root concept code.
   *
   * @return the root concept code
   */
  public String getRootConceptCode() {
    return rootConceptCode;
  }

  /**
   * Sets the root concept code.
   *
   * @param rootConceptCode the new root concept code
   */
  public void setRootConceptCode(String rootConceptCode) {
    this.rootConceptCode = rootConceptCode;
  }

  /**
   * Gets the level.
   *
   * @return the level
   */
  public Integer getLevel() {
    return level;
  }

  /**
   * Sets the level.
   *
   * @param level the new level
   */
  public void setLevel(Integer level) {
    this.level = level;
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
   * Gets the sort column.
   *
   * @return the sort column
   */
  public Integer getSortColumn() {
    return sortColumn;
  }

  /**
   * Sets the sort column.
   *
   * @param sortColumn the new sort column
   */
  public void setSortColumn(Integer sortColumn) {
    this.sortColumn = sortColumn;
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
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the columns.
   *
   * @return the columns
   */
  public List<ReportTemplateColumn> getColumns() {
    return columns;
  }

  /**
   * Sets the columns.
   *
   * @param columns the new columns
   */
  public void setColumns(List<ReportTemplateColumn> columns) {
    this.columns = columns;
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
   * Gets the date last updated.
   *
   * @return the date last updated
   */
  public String getDateLastUpdated() {
    return dateLastUpdated;
  }

  /**
   * Sets the date last updated.
   *
   * @param dateLastUpdated the new date last updated
   */
  public void setDateLastUpdated(String dateLastUpdated) {
    this.dateLastUpdated = dateLastUpdated;
  }
}
