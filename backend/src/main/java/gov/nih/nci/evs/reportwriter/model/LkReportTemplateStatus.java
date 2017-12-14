package gov.nih.nci.evs.reportwriter.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the lk_report_template_status database table.
 * 
 */
@Entity
@Table(name="lk_report_template_status")
@NamedQuery(name="LkReportTemplateStatus.findAll", query="SELECT l FROM LkReportTemplateStatus l")
public class LkReportTemplateStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private List<ReportTemplate> reportTemplates;

	public LkReportTemplateStatus() {
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
	@OneToMany(mappedBy="lkReportTemplateStatus")
	public List<ReportTemplate> getReportTemplates() {
		return this.reportTemplates;
	}

	public void setReportTemplates(List<ReportTemplate> reportTemplates) {
		this.reportTemplates = reportTemplates;
	}

	public ReportTemplate addReportTemplate(ReportTemplate reportTemplate) {
		getReportTemplates().add(reportTemplate);
		reportTemplate.setLkReportTemplateStatus(this);

		return reportTemplate;
	}

	public ReportTemplate removeReportTemplate(ReportTemplate reportTemplate) {
		getReportTemplates().remove(reportTemplate);
		reportTemplate.setLkReportTemplateStatus(null);

		return reportTemplate;
	}

}