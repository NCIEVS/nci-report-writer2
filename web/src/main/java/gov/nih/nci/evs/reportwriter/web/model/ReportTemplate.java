package gov.nih.nci.evs.reportwriter.web.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * The persistent class for the report_template database table.
 * 
 */
@Entity
@Table(name = "report_template")
@NamedQuery(name = "ReportTemplate.findAll", query = "SELECT r FROM ReportTemplate r")
@JsonFilter("yamlFilter")
public class ReportTemplate implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer level;
	private String name;
	private String rootConceptCode;
	private Integer sortColumn;
	private String status;
	private String type;
	private String association;
	private List<ReportTemplateColumn> columns;
	private List<ReportTemplateConceptList> reportTemplateConceptLists;
	private List<ReportTask> tasks;
	private LocalDateTime dateCreated;
	private LocalDateTime dateLastUpdated;
	private String createdBy;
	private String lastUpdatedBy;

	public ReportTemplate() {
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "root_concept_code")
	public String getRootConceptCode() {
		return this.rootConceptCode;
	}

	public void setRootConceptCode(String rootConceptCode) {
		this.rootConceptCode = rootConceptCode;
	}

	@Column(name = "sort_column")
	public Integer getSortColumn() {
		return this.sortColumn;
	}

	public void setSortColumn(Integer sortColumn) {
		this.sortColumn = sortColumn;
	}

	@Column(name = "status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "type")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "association")
	public String getAssociation() {
		return this.association;
	}

	public void setAssociation(String association) {
		this.association = association;
	}

	// bi-directional many-to-one association to ReportTemplateColumn

	@OneToMany(mappedBy = "reportTemplate", fetch = FetchType.EAGER, cascade = {
			CascadeType.ALL }, orphanRemoval = true)
	public List<ReportTemplateColumn> getColumns() {
		return this.columns;
	}

	public void setColumns(List<ReportTemplateColumn> columns) {
		this.columns = columns;

	}

	// bi-directional many-to-one association to ReportTask
	@JsonIgnore
	@OneToMany(mappedBy = "reportTemplate", fetch = FetchType.LAZY)
	public List<ReportTask> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<ReportTask> tasks) {
		this.tasks = tasks;

	}

	// bi-directional many-to-one association to ReportTemplateConceptList
	@JsonIgnore
	@OneToMany(mappedBy = "reportTemplate")
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

	public ReportTemplateConceptList removeReportTemplateConceptList(
			ReportTemplateConceptList reportTemplateConceptList) {
		getReportTemplateConceptLists().remove(reportTemplateConceptList);
		reportTemplateConceptList.setReportTemplate(null);

		return reportTemplateConceptList;
	}


	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}


	public LocalDateTime getDateLastUpdated() {
		return dateLastUpdated;
	}

	public void setDateLastUpdated(LocalDateTime dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

}