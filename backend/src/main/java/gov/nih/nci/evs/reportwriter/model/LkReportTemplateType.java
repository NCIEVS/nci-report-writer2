package gov.nih.nci.evs.reportwriter.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the lk_report_template_type database table.
 * 
 */
@Entity
@Table(name="lk_report_template_type")
@NamedQuery(name="LkReportTemplateType.findAll", query="SELECT l FROM LkReportTemplateType l")
public class LkReportTemplateType implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private List<ReportTemplate> reportTemplates;

	public LkReportTemplateType() {
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
	@OneToMany(mappedBy="lkReportTemplateType")
	public List<ReportTemplate> getReportTemplates() {
		return this.reportTemplates;
	}

	public void setReportTemplates(List<ReportTemplate> reportTemplates) {
		this.reportTemplates = reportTemplates;
	}

	public ReportTemplate addReportTemplate(ReportTemplate reportTemplate) {
		getReportTemplates().add(reportTemplate);
		reportTemplate.setLkReportTemplateType(this);

		return reportTemplate;
	}

	public ReportTemplate removeReportTemplate(ReportTemplate reportTemplate) {
		getReportTemplates().remove(reportTemplate);
		reportTemplate.setLkReportTemplateType(null);

		return reportTemplate;
	}

}