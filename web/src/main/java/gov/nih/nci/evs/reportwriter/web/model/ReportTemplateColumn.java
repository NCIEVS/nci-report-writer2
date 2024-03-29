package gov.nih.nci.evs.reportwriter.web.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the report_template_column database table.
 * 
 */
@Entity
@Table(name="report_template_column")
@NamedQueries({   
    @NamedQuery(name = "ReportTemplateColumn.getReportColumnsByReportTemplateId", 
    		query="SELECT u FROM ReportTemplateColumn u where u.reportTemplate.id = :reportTemplateId")
})
@JsonFilter("yamlFilter")
public class ReportTemplateColumn implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer columnNumber;
	private String label;
	private ReportTemplate reportTemplate;
	private String display;
	private String propertyType;
	private String property;
	private String source;
	private String group;
	private String subsource;
	private String attr;
	private LocalDateTime dateCreated;
	private LocalDateTime dateLastUpdated;
	private String createdBy;
	private String lastUpdatedBy;

	public ReportTemplateColumn() {
	}


	@Id
	@Column(name="id")	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Column(name="column_number")
	public Integer getColumnNumber() {
		return this.columnNumber;
	}

	public void setColumnNumber(Integer columnNumber) {
		this.columnNumber = columnNumber;
	}

	@Column(name="label")
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	//bi-directional many-to-one association to ReportTemplate
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="report_template_id")
	public ReportTemplate getReportTemplate() {
		return this.reportTemplate;
	}

	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
	}


	@Column(name="display")
	public String getDisplay() {
		return this.display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}


	@Column(name="property_type")
	public String getPropertyType() {
		return this.propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}


	@Column(name="property")
	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}


	@Column(name="source")
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}


	@Column(name="source_group")
	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}


	@Column(name="subsource")
	public String getSubsource() {
		return this.subsource;
	}

	public void setSubsource(String subsource) {
		this.subsource = subsource;
	}

	@Column(name="attr")
	public String getAttr() {
		return this.attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}


	@JsonIgnore
	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	@JsonIgnore
	public LocalDateTime getDateLastUpdated() {
		return dateLastUpdated;
	}

	public void setDateLastUpdated(LocalDateTime dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}

	@JsonIgnore
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@JsonIgnore
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
}