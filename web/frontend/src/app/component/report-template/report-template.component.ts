import { EvsVersionInfo } from './../../model/evsVersionInfo';
import { Task } from "./../../model/task";
import { Template } from "./../../model/template";
import { Component, OnInit } from "@angular/core";
import { ReportTemplateService } from "./../../service/report-template.service";
import { getBaseLocation } from "./../../service/common-functions";
import { LookupvaluesTemplateService } from "./../../service/lookupvalues-template.service";

import { ViewChild } from "@angular/core";

import { Table } from "primeng/table";
import { InputText } from "primeng/inputtext";
import { ElementRef } from "@angular/core";
import { forEach } from "@angular/router/src/utils/collection";

import { Lookup } from "./../../model/lookup";
import { RunReportTemplateInfo } from "./../../model/runReportTemplateInfo";

import { FileUploadModule, FileUpload } from "primeng/primeng";

@Component({
  selector: "app-report-template",
  templateUrl: "./report-template.component.html",
  styleUrls: ["./report-template.component.css"]
})
export class ReportTemplateComponent implements OnInit {
  //@ViewChild("dtTemplate") dataTable: DataTable;
  @ViewChild("dtTemplates")
  table: Table;
  @ViewChild("fileUpload")
  fileUpload: FileUpload;

  constructor(
    private reportTemplateService: ReportTemplateService,
    private lookupvaluesTemplateService: LookupvaluesTemplateService
  ) {
    this.getStatuses();
    this.getMonthlyVersionInfo();

    this.getWeeklyVersionInfo();
    
  }

  templates: Template[];
  selectedTemplates: Template[];
  totalRecordsCount: number;
  pageinationcount: string;
  selectedTemplate: Template;
  task: Task;
  tasks: Task[];
  staticAlertClosed: boolean;
  displayMessage: string;
  taskRun: boolean;
  public getBaseLocation = getBaseLocation;
  basePath: string;

  statuses: Lookup[];
  evsVersionInfo : EvsVersionInfo;
  

  selectedStatus: string;
  selectedName: string;
  selectedId: string;
  selectedDateCreated: string;
  selectedDateUpdated: string;
  //selectedStatus1: string;
  filterObject: any;
  //filterObject1: any;
  globalFilter: string;
  //globalFilter1: string;

  databaseTypes: Lookup[] =  [
    { label: "", value: "monthly"},
    { label: "", value: "weekly"}
  ];
  selectedDatabasetype: string = null;
  displayDatabaseType: boolean = false;
  isSingleTemplateRun: boolean = true;
  runReportTemplateInfo: RunReportTemplateInfo = null;
  cols: any[];
  showFileUpload: boolean = false;
  uploadUrl: string = "/reportwriter/uploadConceptList";

  onFilter(e) {
    //saving the filters
    console.log(this.globalFilter);
    if (!(this.globalFilter == null || this.globalFilter == undefined)) {
      localStorage.setItem("globalfilters-template", this.globalFilter);
    }
    if (this.table != undefined) {
      console.log("filters - " + JSON.stringify(this.table.filters));
      localStorage.setItem(
        "filters-template",
        JSON.stringify(this.table.filters)
      );
    }
  }

  UploadFile(event): void {
    console.log("UploadFile", event);

    if (event.files.length == 0) {
      alert("No File is selected. Please select a File.");
      return;
    }
    let input = new FormData();
    var fileToUpload = event.files[0];
    input.append("conceptList", fileToUpload);
    console.log("In BeforeSend -" + JSON.stringify(this.runReportTemplateInfo));
    input.append(
      "runReportTemplateInfo",
      new Blob([JSON.stringify(this.runReportTemplateInfo)], {
        type: "application/json"
      })
    );

    this.reportTemplateService
      .runReportTemplateConceptList(input)
      .subscribe(tasks => {
        this.tasks = tasks;
        console.log("tasks" + JSON.stringify(this.tasks));
        this.processReportTasks();
      });
  }

  ngOnInit() {
    this.cols = [
      {
        field: "id",
        header: "Action",
        filterMatchMode: "contains",
        width: "15%"
      },
      { field: "id", header: "Id", filterMatchMode: "contains", width: "5%" },
      {
        field: "name",
        header: "Name",
        filterMatchMode: "contains",
        width: "34%"
      },
      {
        field: "status",
        header: "Status",
        filterMatchMode: "equals",
        width: "10%"
      },
      {
        field: "type",
        header: "Type",
        filterMatchMode: "contains",
        width: "10%"
      },
      {
        field: "dateCreated",
        header: "Date Created",
        filterMatchMode: "contains",
        width: "18%"
      },
      {
        field: "dateLastUpdated",
        header: "Date Last Updated",
        filterMatchMode: "contains",
        width: "18%"
      }
    ];

    this.getReportTemplates();
   
    

    console.log(
      "In ReportTemplateComponent - ngOnInit - " +
        JSON.stringify(this.databaseTypes)
    );

    this.staticAlertClosed = true;
    this.taskRun = false;
    this.basePath = "/" + getBaseLocation() + "/reportTask";
    console.log("basePath - " + this.basePath);

    //setting the filters
    const filters = localStorage.getItem("filters-template");
    if (filters) {
      console.log("filters-template in init - " + filters);
      this.filterObject = JSON.parse(filters);

      if (
        this.filterObject.global != undefined &&
        this.filterObject.global != null
      ) {
        this.table.filterGlobal(this.filterObject.global.value, "contains");
      }

      if (
        this.filterObject.status == undefined ||
        this.filterObject.status == null
      ) {
        this.filterObject.status = {};
        this.filterObject.status.value = "Active";
        this.filterObject.status.matchMode = "equals";
        this.selectedStatus = "Active";
        this.table.filter(this.filterObject.status.value, "status", "equals");
      } else {
        this.selectedStatus = this.filterObject.status.value;
        this.table.filter(this.selectedStatus, "status", "equals");
      }

      if (
        this.filterObject.name != undefined &&
        this.filterObject.name != null
      ) {
        this.selectedName = this.filterObject.name.value;
        this.table.filter(this.selectedName, "name", "contains");
      }

      if (this.filterObject.id != undefined && this.filterObject.id != null) {
        this.selectedId = this.filterObject.id.value;
        this.table.filter(this.selectedId, "id", "contains");
      }

      if (
        this.filterObject.dateCreated != undefined &&
        this.filterObject.dateCreated != null
      ) {
        this.selectedDateCreated = this.filterObject.dateCreated.value;
        this.table.filter(this.selectedDateCreated, "dateCreated", "contains");
      }

      if (
        this.filterObject.dateLastUpdated != undefined &&
        this.filterObject.dateLastUpdated != null
      ) {
        this.selectedDateUpdated = this.filterObject.dateLastUpdated.value;
        this.table.filter(
          this.selectedDateUpdated,
          "dateLastUpdated",
          "contains"
        );
      }

      this.table.filters = this.filterObject;
    } else {
      this.filterObject = {};
      this.filterObject.status = {};
      this.filterObject.status.value = "Active";
      this.selectedStatus = "Active";
      this.filterObject.status.matchMode = "equals";
      this.table.filters = this.filterObject;
      this.selectedStatus = this.filterObject.status.value;
      this.table.filter(this.selectedStatus, "status", "equals");
    }

    const globalfilters = localStorage.getItem("globalfilters-template");
    if (globalfilters != undefined || globalfilters != null) {
      console.log("globalfilters-template in init - " + globalfilters);
      this.globalFilter = globalfilters;
    }
  }

  onRowSelect(event) {
    console.log(event.data.status);
    return true;
  }

  executeMultipleTemplates() {
    this.runReportTemplateInfo = new RunReportTemplateInfo(
      this.selectedDatabasetype,
      this.selectedTemplates
    );
  }

  getReportTemplates(): void {
    this.reportTemplateService.getReportTemplates().subscribe(templates => {
      this.templates = templates;
      //console.log(JSON.stringify(this.templates));
      this.totalRecordsCount = this.templates.length;
      let currentpageCount = 0 + 1;
      let currentPageRows = 0 + 20;
      if (currentPageRows > this.templates.length) {
        currentPageRows = this.templates.length;
      }
      this.pageinationcount =
        "Showing " +
        currentpageCount +
        " to " +
        currentPageRows +
        " of " +
        this.templates.length;
    });
  }

  editTemplate(template) {
    console.log("In editTemplate - " + JSON.stringify(template));
  }

  onPageChange(e) {
    let currentpageCount = e.first + 1;
    let currentPageRows = e.first + e.rows;
    if (currentPageRows > this.templates.length) {
      currentPageRows = this.templates.length;
    }
    this.pageinationcount =
      "Showing " +
      currentpageCount +
      " to " +
      currentPageRows +
      " of " +
      this.templates.length;
  }

  runTemplate(templateRow) {
    console.log("In runTemplate - " + JSON.stringify(templateRow));
    this.selectedTemplate = templateRow;
    if (this.selectedTemplate.type === "ConceptList") {
      this.showFileUpload = true;
    } else {
      this.showFileUpload = false;
    }
    this.isSingleTemplateRun = true;
    this.displayDatabaseType = true;
  }

  runTemplates(): void {
    console.log("In runTemplates - " + JSON.stringify(this.selectedTemplates));
    if (
      this.selectedTemplates == undefined ||
      this.selectedTemplates.length < 1
    ) {
      alert("Please select one or more templates to run");
      return;
    }
    this.showFileUpload = false;
    for (let template of this.selectedTemplates) {
      if (template.type === "ConceptList") {
        alert(
          "Please de-select the template of type ConceptList - " +
            template.name +
            ". The templates of type ConceptList need to be run individually."
        );
        return;
      }
    }

    this.isSingleTemplateRun = false;
    this.displayDatabaseType = true;
  }

  getStatuses(): void {
    this.lookupvaluesTemplateService
      .getStatuses()
      .subscribe(statuses => (this.statuses = statuses));
  }


  getMonthlyVersionInfo(): void {
    this.lookupvaluesTemplateService
      .getVersionInfo("monthly")
      .subscribe(evsVersionInfo => (this.databaseTypes[0].label = "monthly (" + evsVersionInfo.version + ")"));
  }

  getWeeklyVersionInfo(): void {
    this.lookupvaluesTemplateService
      .getVersionInfo("weekly")
      .subscribe(evsVersionInfo => (this.databaseTypes[1].label = "weekly (" + evsVersionInfo.version + ")"));
  }

  onSubmitDatabaseType() {
    if (this.showFileUpload && this.fileUpload.files.length == 0) {
      alert(
        "Please select a file that contains a list of concept codes. Each concept code should be on a new line."
      );
    } else {
      this.displayDatabaseType = false;
      console.log(
        "onSubmitDatabaseType - " + JSON.stringify(this.selectedDatabasetype)
      );

      if (this.isSingleTemplateRun) {
        this.executeSelectedTemplate();
      } else {
        this.executeSelectedTemplates();
      }
    }
  }

  executeSelectedTemplate() {
    console.log(
      "In executeSelectedTemplate - " + JSON.stringify(this.selectedTemplate)
    );
    this.selectedTemplates = [];
    this.selectedTemplates.push(this.selectedTemplate);
    this.runReportTemplateInfo = new RunReportTemplateInfo(
      this.selectedDatabasetype,
      this.selectedTemplates
    );
    if (this.showFileUpload) {
      this.fileUpload.upload();
    } else {
      this.runReportTemplates();
    }
  }

  removeFile(file: File, uploader: FileUpload) {
    const index = uploader.files.indexOf(file);
    uploader.remove(null, index);
  }

  executeSelectedTemplates() {
    this.runReportTemplateInfo = new RunReportTemplateInfo(
      this.selectedDatabasetype,
      this.selectedTemplates
    );
    this.runReportTemplates();
  }

  runReportTemplates() {
    this.reportTemplateService
      .runReportTemplates(this.runReportTemplateInfo)
      .subscribe(tasks => {
        this.tasks = tasks;
        this.processReportTasks();
      });
  }

  cancelSelectDatabaseType() {
    this.displayDatabaseType = false;
  }

  processReportTasks() {
    console.log(JSON.stringify(this.tasks));
    this.displayMessage = "";
    for (let task of this.tasks) {
      this.displayMessage =
        this.displayMessage +
        " A task with Id - " +
        task.id +
        " has been created for the template - " +
        task.reportTemplateName +
        ".<br>";
    }

    this.displayMessage =
      this.displayMessage +
      "Please check the status of the report in the All Report Tasks page.";
    this.taskRun = true;
    this.staticAlertClosed = false;
    window.scrollTo(0, 0);
    setTimeout(() => {
      this.staticAlertClosed = true;
      console.log("setting staticAlertClosed to true");
    }, 50000);
  }
}
