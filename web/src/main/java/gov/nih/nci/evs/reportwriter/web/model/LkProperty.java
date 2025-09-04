package gov.nih.nci.evs.reportwriter.web.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/** The persistent class for the lk_property database table. */
@Entity
@Table(name = "lk_property")
@NamedQuery(name = "LkProperty.findAll", query = "SELECT l FROM LkProperty l")
public class LkProperty implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The code. */
  private String code;

  /** The label. */
  private String label;

  /** The report template columns. */
  //  private List<ReportTemplateColumn> reportTemplateColumns;

  /** Instantiates a new lk property. */
  public LkProperty() {}

  /**
   * Gets the code.
   *
   * @return the code
   */
  @Id
  public String getCode() {
    return this.code;
  }

  /**
   * Sets the code.
   *
   * @param code the new code
   */
  public void setCode(final String code) {
    this.code = code;
  }

  /**
   * Gets the label.
   *
   * @return the label
   */
  public String getLabel() {
    return this.label;
  }

  /**
   * Sets the label.
   *
   * @param label the new label
   */
  public void setLabel(final String label) {
    this.label = label;
  }

  /*
  //bi-directional many-to-one association to ReportTemplateColumn
  @OneToMany(mappedBy="lkProperty")
  public List<ReportTemplateColumn> getReportTemplateColumns() {
  	return this.reportTemplateColumns;
  }

  public void setReportTemplateColumns(List<ReportTemplateColumn> reportTemplateColumns) {
  	this.reportTemplateColumns = reportTemplateColumns;
  }

  public ReportTemplateColumn addReportTemplateColumn(ReportTemplateColumn reportTemplateColumn) {
  	getReportTemplateColumns().add(reportTemplateColumn);
  	reportTemplateColumn.setLkProperty(this);

  	return reportTemplateColumn;
  }

  public ReportTemplateColumn removeReportTemplateColumn(ReportTemplateColumn reportTemplateColumn) {
  	getReportTemplateColumns().remove(reportTemplateColumn);
  	reportTemplateColumn.setLkProperty(null);

  	return reportTemplateColumn;
  }
  */

}
