package gov.nih.nci.evs.reportwriter.web.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/** The persistent class for the lk_association database table. */
@Entity
@Table(name = "lk_association")
@NamedQuery(name = "LkAssociation.findAll", query = "SELECT l FROM LkAssociation l")
public class LkAssociation implements LkGeneric, Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The name. */
  private String name;

  /** The description. */
  private String description;

  /** The report templates. */
  //  private List<ReportTemplate> reportTemplates;

  /** Instantiates a new lk association. */
  public LkAssociation() {}

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
  //bi-directional many-to-one association to ReportTemplate
  @OneToMany(mappedBy="lkAssociation")
  public List<ReportTemplate> getReportTemplates() {
  	return this.reportTemplates;
  }

  public void setReportTemplates(List<ReportTemplate> reportTemplates) {
  	this.reportTemplates = reportTemplates;
  }

  public ReportTemplate addReportTemplate(ReportTemplate reportTemplate) {
  	getReportTemplates().add(reportTemplate);
  	reportTemplate.setLkAssociation(this);

  	return reportTemplate;
  }

  public ReportTemplate removeReportTemplate(ReportTemplate reportTemplate) {
  	getReportTemplates().remove(reportTemplate);
  	reportTemplate.setLkAssociation(null);

  	return reportTemplate;
  }
  */

}
