package gov.nih.nci.evs.reportwriter.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the report_template_column database table.
 * 
 */
@Entity
@Table(name="report_template_column")
@NamedQuery(name="ReportTemplateColumn.findAll", query="SELECT r FROM ReportTemplateColumn r")
public class ReportTemplateColumn implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int columnNumber;
	private String label;
	private ReportTemplate reportTemplate;
	private LkDisplay lkDisplay;
	private LkPropertyType lkPropertyType;
	private LkProperty lkProperty;
	private LkSource lkSource;
	private LkGroup lkGroup;
	private LkSubsource lkSubsource;

	public ReportTemplateColumn() {
	}


	@Id
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name="column_number")
	public int getColumnNumber() {
		return this.columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}


	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	//bi-directional many-to-one association to ReportTemplate
	@ManyToOne
	@JoinColumn(name="report_template_id")
	public ReportTemplate getReportTemplate() {
		return this.reportTemplate;
	}

	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
	}


	//bi-directional many-to-one association to LkDisplay
	@ManyToOne
	@JoinColumn(name="display")
	public LkDisplay getLkDisplay() {
		return this.lkDisplay;
	}

	public void setLkDisplay(LkDisplay lkDisplay) {
		this.lkDisplay = lkDisplay;
	}


	//bi-directional many-to-one association to LkPropertyType
	@ManyToOne
	@JoinColumn(name="property_type")
	public LkPropertyType getLkPropertyType() {
		return this.lkPropertyType;
	}

	public void setLkPropertyType(LkPropertyType lkPropertyType) {
		this.lkPropertyType = lkPropertyType;
	}


	//bi-directional many-to-one association to LkProperty
	@ManyToOne
	@JoinColumn(name="property")
	public LkProperty getLkProperty() {
		return this.lkProperty;
	}

	public void setLkProperty(LkProperty lkProperty) {
		this.lkProperty = lkProperty;
	}


	//bi-directional many-to-one association to LkSource
	@ManyToOne
	@JoinColumn(name="source")
	public LkSource getLkSource() {
		return this.lkSource;
	}

	public void setLkSource(LkSource lkSource) {
		this.lkSource = lkSource;
	}


	//bi-directional many-to-one association to LkGroup
	@ManyToOne
	@JoinColumn(name="source_group")
	public LkGroup getLkGroup() {
		return this.lkGroup;
	}

	public void setLkGroup(LkGroup lkGroup) {
		this.lkGroup = lkGroup;
	}


	//bi-directional many-to-one association to LkSubsource
	@ManyToOne
	@JoinColumn(name="subsource")
	public LkSubsource getLkSubsource() {
		return this.lkSubsource;
	}

	public void setLkSubsource(LkSubsource lkSubsource) {
		this.lkSubsource = lkSubsource;
	}

}