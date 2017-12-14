package gov.nih.nci.evs.reportwriter.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the lk_property_type database table.
 * 
 */
@Entity
@Table(name="lk_property_type")
@NamedQuery(name="LkPropertyType.findAll", query="SELECT l FROM LkPropertyType l")
public class LkPropertyType implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private List<ReportTemplateColumn> reportTemplateColumns;

	public LkPropertyType() {
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
	@OneToMany(mappedBy="lkPropertyType")
	public List<ReportTemplateColumn> getReportTemplateColumns() {
		return this.reportTemplateColumns;
	}

	public void setReportTemplateColumns(List<ReportTemplateColumn> reportTemplateColumns) {
		this.reportTemplateColumns = reportTemplateColumns;
	}

	public ReportTemplateColumn addReportTemplateColumn(ReportTemplateColumn reportTemplateColumn) {
		getReportTemplateColumns().add(reportTemplateColumn);
		reportTemplateColumn.setLkPropertyType(this);

		return reportTemplateColumn;
	}

	public ReportTemplateColumn removeReportTemplateColumn(ReportTemplateColumn reportTemplateColumn) {
		getReportTemplateColumns().remove(reportTemplateColumn);
		reportTemplateColumn.setLkPropertyType(null);

		return reportTemplateColumn;
	}

}