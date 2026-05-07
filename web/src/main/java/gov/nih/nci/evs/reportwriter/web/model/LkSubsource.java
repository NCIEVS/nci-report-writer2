package gov.nih.nci.evs.reportwriter.web.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/** The persistent class for the lk_subsource database table. */
@Entity
@Table(name = "lk_subsource")
@NamedQuery(name = "LkSubsource.findAll", query = "SELECT l FROM LkSubsource l")
public class LkSubsource implements LkGeneric, Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The name. */
  private String name;

  /** The description. */
  private String description;

  /** The report template columns. */
  //  private List<ReportTemplateColumn> reportTemplateColumns;

  /** Instantiates a new lk subsource. */
  public LkSubsource() {}

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
  //bi-directional many-to-one association to ReportTemplateColumn
  @OneToMany(mappedBy="lkSubsource")
  public List<ReportTemplateColumn> getReportTemplateColumns() {
  	return this.reportTemplateColumns;
  }

  public void setReportTemplateColumns(List<ReportTemplateColumn> reportTemplateColumns) {
  	this.reportTemplateColumns = reportTemplateColumns;
  }

  public ReportTemplateColumn addReportTemplateColumn(ReportTemplateColumn reportTemplateColumn) {
  	getReportTemplateColumns().add(reportTemplateColumn);
  	reportTemplateColumn.setLkSubsource(this);

  	return reportTemplateColumn;
  }

  public ReportTemplateColumn removeReportTemplateColumn(ReportTemplateColumn reportTemplateColumn) {
  	getReportTemplateColumns().remove(reportTemplateColumn);
  	reportTemplateColumn.setLkSubsource(null);

  	return reportTemplateColumn;
  }
  */

}
