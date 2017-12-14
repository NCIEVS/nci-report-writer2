package gov.nih.nci.evs.reportwriter.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the lk_group database table.
 * 
 */
@Entity
@Table(name="lk_group")
@NamedQuery(name="LkGroup.findAll", query="SELECT l FROM LkGroup l")
public class LkGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private List<ReportTemplateColumn> reportTemplateColumns;

	public LkGroup() {
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


	//bi-directional many-to-one association to ReportTemplateColumn
	@OneToMany(mappedBy="lkGroup")
	public List<ReportTemplateColumn> getReportTemplateColumns() {
		return this.reportTemplateColumns;
	}

	public void setReportTemplateColumns(List<ReportTemplateColumn> reportTemplateColumns) {
		this.reportTemplateColumns = reportTemplateColumns;
	}

	public ReportTemplateColumn addReportTemplateColumn(ReportTemplateColumn reportTemplateColumn) {
		getReportTemplateColumns().add(reportTemplateColumn);
		reportTemplateColumn.setLkGroup(this);

		return reportTemplateColumn;
	}

	public ReportTemplateColumn removeReportTemplateColumn(ReportTemplateColumn reportTemplateColumn) {
		getReportTemplateColumns().remove(reportTemplateColumn);
		reportTemplateColumn.setLkGroup(null);

		return reportTemplateColumn;
	}

}