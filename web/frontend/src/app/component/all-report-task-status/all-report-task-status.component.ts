import { Component, OnInit, Inject } from "@angular/core";

import { ReportTaskService } from "./../../service/report-task.service";
import { Task } from "../../model/task";
import { Lookup } from "./../../model/lookup";
import { DOCUMENT } from "@angular/common";
import { ConfirmationService } from "primeng/api";

import { getBaseLocation } from "./../../service/common-functions";
import { ViewChild } from "@angular/core";

import { Table } from "primeng/table";

@Component({
  selector: "app-all-report-task-status",
  templateUrl: "./all-report-task-status.component.html",
  styleUrls: ["./all-report-task-status.component.css"],
  providers: [ConfirmationService]
})
export class AllReportTaskStatusComponent implements OnInit {
  @ViewChild("dtTasks") table: Table;

  tasks: Task[];
  taskStatuses: Lookup[];
  lookupNone: Lookup;
  task: Task;
  detailedReportXLSPath: string;

  totalRecordsCount: number;

  public getBaseLocation = getBaseLocation;

  pageinationcount: string;

  detailedReportPath: string;
  reportXLS: string;
  reportTxt: string;
  reportTemplate: string;
  reportLog: string;
  reportProcess: string;
  globalFilter: string;
  globalFilter1: string;
  cols: any[];
  selectedProcesstype: string;
  columnNoToProcess: number;
  displayProcessingType = false;
  processReportUrl:string;
  showprocessReportUrl = false;
  processingTypes: Lookup[] = [
    { label: "Maps_To", value: "Maps_To" },
    { label: "Full_syn", value: "Full_syn" }
  ];

  constructor(
    private confirmationService: ConfirmationService,
    private reportTaskService: ReportTaskService,
    @Inject(DOCUMENT) private document: any
  ) {}

  onFilter(e) {
    //saving the filters
    console.log(this.globalFilter);
    if (!(this.globalFilter == null || this.globalFilter == undefined)) {
      localStorage.setItem("globalfilters-task", this.globalFilter);
    }
    console.log("filters - " + JSON.stringify(this.table.filters));
    localStorage.setItem("filters-task", JSON.stringify(this.table.filters));
  }

  ngOnInit() {
    this.cols = [
      {
        field: "id",
        header: "Task Id",
        filterMatchMode: "contains",
        width: "7%"
      },
      {
        field: "reportTemplateName",
        header: "Name",
        filterMatchMode: "contains",
        width: "30%"
      },
      {
        field: "status",
        header: "Status",
        filterMatchMode: "equals",
        width: "13%"
      },
      {
        field: "dateCreated",
        header: "Date Created",
        filterMatchMode: "contains",
        width: "15%"
      },
      {
        field: "dateStarted",
        header: "Date Started",
        filterMatchMode: "contains",
        width: "15%"
      },
      {
        field: "dateCompleted",
        header: "Date Completed",
        filterMatchMode: "contains",
        width: "15%"
      },
      {
        field: "version",
        header: "Version",
        filterMatchMode: "contains",
        width: "10%"
      },
      {
        field: "databaseType",
        header: "Database Type",
        filterMatchMode: "contains",
        width: "10%"
      },
      {
        field: "id",
        header: "Action",
        filterMatchMode: "contains",
        width: "18%"
      },
      {
        field: "id",
        header: "Download",
        filterMatchMode: "contains",
        width: "18%"
      }
    ];

    this.lookupNone = new Lookup();
    this.lookupNone.label = "All";
    this.lookupNone.value = null;
    this.getReportTasks();
    this.getTaskStatuses();
    this.detailedReportPath =
      "http://" +
      this.document.location.hostname +
      ":" +
      this.document.location.port +
      "/" +
      getBaseLocation() +
      "/reportwriter/";
    //this.detailedReportPath = "http://localhost:8080/reportwriter/";
    this.reportXLS = this.detailedReportPath + "getXLSReport";
    this.reportTxt = this.detailedReportPath + "getTxtReport";
    this.reportTemplate = this.detailedReportPath + "getTemplateReport";
    this.reportLog = this.detailedReportPath + "getLogReport";
    this.reportProcess = this.detailedReportPath + "convertReport";

    //setting the filters
    const filters = localStorage.getItem("filters-task");
    if (filters) {
      console.log("filters in init - " + filters);
      this.table.filters = JSON.parse(filters);
      if (
        this.table.filters.global != undefined &&
        this.table.filters.global != null
      ) {
        this.table.filterGlobal(this.table.filters.global.value, "contains");
      }
    }
    const globalfilters = localStorage.getItem("globalfilters-task");
    if (globalfilters != undefined || globalfilters != null) {
      console.log("global filters in init - " + globalfilters);
      this.globalFilter = globalfilters;
    }
  }

  getXLSReport(templateTask) {
    this.reportTaskService.getXLSReport(templateTask.id).subscribe();
  }

  getReportTasks(): void {
    this.reportTaskService.getReportTasks().subscribe(tasks => {
      this.tasks = tasks;
      this.totalRecordsCount = this.tasks.length;
      let currentpageCount = 0 + 1;
      let currentPageRows = 0 + 10;
      if (currentPageRows > this.tasks.length) {
        currentPageRows = this.tasks.length;
      }
      this.pageinationcount =
        "Showing " +
        currentpageCount +
        " to " +
        currentPageRows +
        " of " +
        this.tasks.length;
    });
  }

  deleteReportTask(templateTask): void {
    this.confirmationService.confirm({
      message: "Are you sure that you want to delete?",
      accept: () => {
        this.reportTaskService
          .deleteReportTask(templateTask.id)
          .subscribe(task => {
            this.task = task;
            console.log("Task id -" + task.id + " status - " + task.status);
            this.getReportTasks();
          });
      }
    });
  }

  processTask(task) {
    this.task = task;
    console.log("task - " + JSON.stringify(task));
    this.showprocessReportUrl = false;
    this.displayProcessingType = true;
    
  }

  closeDialog(event){
    // If we are clicking the close button and not something else
    if (event.target.className === "fa fa-fw fa-close") {
      this.cancelSelectProcessTask();
    }
  }

  cancelSelectProcessTask() {
    this.displayProcessingType = false;
    this.showprocessReportUrl = false;
    this.selectedProcesstype = null;
    this.columnNoToProcess = null;
    this.task = null;
  }
  onSubmitProcessType() {
    console.log(
      "selectedProcesstype- " +
        this.selectedProcesstype +
        " columnNoToProcess- " +
        this.columnNoToProcess +
        " task.id- " +
        this.task.id
    );
    this.processReportUrl = this.reportProcess + "/" + this.task.id + "/" + this.selectedProcesstype + "/" + this.columnNoToProcess;
    this.showprocessReportUrl = true;
  }

  getTaskStatuses(): void {
    this.reportTaskService.getTaskStatuses().subscribe(taskStatuses => {
      this.taskStatuses = taskStatuses.filter(function(obj) {
        return obj.label !== "Deleted";
      });
      this.taskStatuses.push(this.lookupNone);
    });
  }

  onPageChange(e) {
    let currentpageCount = e.first + 1;
    let currentPageRows = e.first + e.rows;
    if (currentPageRows > this.tasks.length) {
      currentPageRows = this.tasks.length;
    }
    this.pageinationcount =
      "Showing " +
      currentpageCount +
      " to " +
      currentPageRows +
      " of " +
      this.tasks.length;
  }
}
