package gov.nih.nci.evs.reportwriter.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the lk_association database table.
 * 
 */
@Entity
@Table(name="lk_association")
@NamedQuery(name="LkAssociation.findAll", query="SELECT l FROM LkAssociation l")
public class LkAssociation implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private List<ReportTemplate> reportTemplates;

	public LkAssociation() {
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

}