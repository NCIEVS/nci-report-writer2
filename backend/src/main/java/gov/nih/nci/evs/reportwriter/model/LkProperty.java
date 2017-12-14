package gov.nih.nci.evs.reportwriter.model;


import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the lk_property database table.
 * 
 */
@Entity
@Table(name="lk_property")
@NamedQuery(name="LkProperty.findAll", query="SELECT l FROM LkProperty l")
public class LkProperty implements Serializable {
	private static final long serialVersionUID = 1L;
	private String code;
	private String label;
	private List<ReportTemplateColumn> reportTemplateColumns;

	public LkProperty() {
	}


	@Id
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


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

}