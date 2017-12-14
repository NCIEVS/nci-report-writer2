package gov.nih.nci.evs.reportwriter.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the report_template database table.
 * 
 */
@Entity
@Table(name="report_template")
@NamedQuery(name="ReportTemplate.findAll", query="SELECT r FROM ReportTemplate r")
public class ReportTemplate implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int level;
	private String reportName;
	private String rootConceptCode;
	private int sortColumn;
	private LkReportTemplateStatus lkReportTemplateStatus;
	private LkReportTemplateType lkReportTemplateType;
	private LkAssociation lkAssociation;
	private List<ReportTemplateColumn> reportTemplateColumns;
	private List<ReportTemplateConceptList> reportTemplateConceptLists;

	public ReportTemplate() {
	}


	@Id
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}


	@Column(name="report_name")
	public String getReportName() {
		return this.reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}


	@Column(name="root_concept_code")
	public String getRootConceptCode() {
		return this.rootConceptCode;
	}

	public void setRootConceptCode(String rootConceptCode) {
		this.rootConceptCode = rootConceptCode;
	}


	@Column(name="sort_column")
	public int getSortColumn() {
		return this.sortColumn;
	}

	public void setSortColumn(int sortColumn) {
		this.sortColumn = sortColumn;
	}


	//bi-directional many-to-one association to LkReportTemplateStatus
	@ManyToOne
	@JoinColumn(name="status")
	public LkReportTemplateStatus getLkReportTemplateStatus() {
		return this.lkReportTemplateStatus;
	}

	public void setLkReportTemplateStatus(LkReportTemplateStatus lkReportTemplateStatus) {
		this.lkReportTemplateStatus = lkReportTemplateStatus;
	}


	//bi-directional many-to-one association to LkReportTemplateType
	@ManyToOne
	@JoinColumn(name="type")
	public LkReportTemplateType getLkReportTemplateType() {
		return this.lkReportTemplateType;
	}

	public void setLkReportTemplateType(LkReportTemplateType lkReportTemplateType) {
		this.lkReportTemplateType = lkReportTemplateType;
	}


	//bi-directional many-to-one association to LkAssociation
	@ManyToOne
	@JoinColumn(name="association")
	public LkAssociation getLkAssociation() {
		return this.lkAssociation;
	}

	public void setLkAssociation(LkAssociation lkAssociation) {
		this.lkAssociation = lkAssociation;
	}


	//bi-directional many-to-one association to ReportTemplateColumn
	@OneToMany(mappedBy="reportTemplate")
	public List<ReportTemplateColumn> getReportTemplateColumns() {
		return this.reportTemplateColumns;
	}

	public void setReportTemplateColumns(List<ReportTemplateColumn> reportTemplateColumns) {
		this.reportTemplateColumns = reportTemplateColumns;
	}

	public ReportTemplateColumn addReportTemplateColumn(ReportTemplateColumn reportTemplateColumn) {
		getReportTemplateColumns().add(reportTemplateColumn);
		reportTemplateColumn.setReportTemplate(this);

		return reportTemplateColumn;
	}

	public ReportTemplateColumn removeReportTemplateColumn(ReportTemplateColumn reportTemplateColumn) {
		getReportTemplateColumns().remove(reportTemplateColumn);
		reportTemplateColumn.setReportTemplate(null);

		return reportTemplateColumn;
	}


	//bi-directional many-to-one association to ReportTemplateConceptList
	@OneToMany(mappedBy="reportTemplate")
	public List<ReportTemplateConceptList> getReportTemplateConceptLists() {
		return this.reportTemplateConceptLists;
	}

	public void setReportTemplateConceptLists(List<ReportTemplateConceptList> reportTemplateConceptLists) {
		this.reportTemplateConceptLists = reportTemplateConceptLists;
	}

	public ReportTemplateConceptList addReportTemplateConceptList(ReportTemplateConceptList reportTemplateConceptList) {
		getReportTemplateConceptLists().add(reportTemplateConceptList);
		reportTemplateConceptList.setReportTemplate(this);

		return reportTemplateConceptList;
	}

	public ReportTemplateConceptList removeReportTemplateConceptList(ReportTemplateConceptList reportTemplateConceptList) {
		getReportTemplateConceptLists().remove(reportTemplateConceptList);
		reportTemplateConceptList.setReportTemplate(null);

		return reportTemplateConceptList;
	}

}