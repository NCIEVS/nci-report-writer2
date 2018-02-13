import { Component, OnInit,Inject } from '@angular/core';

import { ReportTaskService } from './../../service/report-task.service';
import { Task } from '../../model/task';
import { Lookup } from './../../model/lookup';
import { DOCUMENT } from '@angular/platform-browser';
import {ConfirmationService} from 'primeng/api';

import { getBaseLocation } from './../../service/common-functions';




@Component({
  selector: 'app-all-report-task-status',
  templateUrl: './all-report-task-status.component.html',
  styleUrls: ['./all-report-task-status.component.css'],
  providers: [ConfirmationService]
  
})
export class AllReportTaskStatusComponent implements OnInit {

  tasks: Task[];
  taskStatuses: Lookup[];
  lookupNone:Lookup;
  task:Task;
  detailedReportXLSPath:string;
  
  totalRecordsCount:number;

  public getBaseLocation = getBaseLocation;

  pageinationcount:string;

  detailedReportPath:string;
  reportXLS:string;
  reportTxt:string;
  reportTemplate:string;
  reportLog:string;

  constructor(private confirmationService: ConfirmationService,private reportTaskService:ReportTaskService,@Inject(DOCUMENT) private document: any) { }

  ngOnInit() {
    this.lookupNone = new Lookup();
    this.lookupNone.label = "All";
    this.lookupNone.value = null;
    this.getReportTasks();
    this.getTaskStatuses();
    this.detailedReportPath = 'http://' + this.document.location.hostname + ":" + this.document.location.port+ "/" + getBaseLocation() + "/reportwriter/";
    //this.detailedReportPath = "http://localhost:8080/ncreportwriter/reportwriter";
    this.reportXLS = this.detailedReportPath + "getXLSReport";
    this.reportTxt = this.detailedReportPath + "getTxtReport";
    this.reportTemplate = this.detailedReportPath + "getTemplateReport";
    this.reportLog = this.detailedReportPath + "getLogReport";
 
    
  
  }

  getXLSReport(templateTask){
    this.reportTaskService.getXLSReport(templateTask.id).
    subscribe();
  }

  getReportTasks(): void {
    this.reportTaskService.getReportTasks().
    subscribe(tasks => {this.tasks = tasks; this.totalRecordsCount = this.tasks.length;
      let currentpageCount = 0 + 1;
      let currentPageRows = 0 + 10;
      if (currentPageRows > this.tasks.length){
        currentPageRows = this.tasks.length;
      }
      this.pageinationcount = 'Showing ' +  currentpageCount + ' to ' + currentPageRows + ' of ' + this.tasks.length;
  
    
    });
  }


  deleteReportTask(templateTask):void{

    this.confirmationService.confirm({
      message: 'Are you sure that you want to delete?',
      accept: () => {
        this.reportTaskService.deleteReportTask(templateTask.id).
        subscribe(task => {this.task = task; console.log("Task id -" + task.id + " status - " + task.status); this.getReportTasks()});
      }
  });

    

  }


  

 
  getTaskStatuses(): void {
    this.reportTaskService.getTaskStatuses().subscribe(taskStatuses => 
      {this.taskStatuses =  taskStatuses.filter(function( obj ) {
        return obj.label !== 'Deleted';
    });  
        this.taskStatuses.push(this.lookupNone)});
  }


  onPageChange(e) {    
    let currentpageCount = e.first + 1;
    let currentPageRows = e.first + e.rows;
    if (currentPageRows > this.tasks.length){
      currentPageRows = this.tasks.length;
    }
    this.pageinationcount = 'Showing ' +  currentpageCount + ' to ' + currentPageRows + ' of ' + this.tasks.length;
}

}
