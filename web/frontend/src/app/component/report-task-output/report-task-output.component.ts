import { Template } from './../../model/template';
import { ReportTaskOutput } from './../../model/reportTaskOutput';
import { ReportData } from './../../model/reportData';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ReportTaskService } from './../../service/report-task.service';
import { Lookup } from '../../model/lookup';
import { Task } from '../../model/task';

@Component({
  selector: 'app-report-task-output',
  templateUrl: './report-task-output.component.html',
  styleUrls: ['./report-task-output.component.css']
})
export class ReportTaskOutputComponent implements OnInit {
  taskId:number;
  reportTaskOutput:ReportTaskOutput;
  cols: any[];
  colsOrig:any[];
  reportData:ReportData[];
  template:Template;
  reportName:string;
  pageinationcount:string;
  globalFilterFields:string[];
 
  columnOptions: Lookup[];

  constructor(private route: ActivatedRoute,private reportTaskService:ReportTaskService,) { 
    this.taskId = route.snapshot.params['id'];
    this.getReportTask(this.taskId);
  }

  ngOnInit() {
    
  }

  getReportTaskOutput(): void {
    this.reportTaskService.getReportTaskData(this.taskId).
    subscribe(reportTaskOutput => {this.reportTaskOutput = reportTaskOutput;  
      console.log("task output -" + JSON.stringify(this.reportTaskOutput.header));
      this.cols= this.reportTaskOutput.header;this.colsOrig= this.reportTaskOutput.header;this.reportData = this.reportTaskOutput.data
     
      this.columnOptions = [];
      this.globalFilterFields = [];
      for(let i = 0; i < this.cols.length; i++) {
          this.columnOptions.push({label: this.cols[i].header, value: this.cols[i]});
          this.globalFilterFields.push(this.cols[i].field);
      }
    
      let currentpageCount = 0 + 1;
      let currentPageRows = 0 + 20;
      if (currentPageRows > this.reportData.length){
        currentPageRows = this.reportData.length;
      }
      this.pageinationcount = 'Showing ' +  currentpageCount + ' to ' + currentPageRows + ' of ' + this.reportData.length;

    
    });
  }


  onPageChange(e) {    
    let currentpageCount = e.first + 1;
    let currentPageRows = e.first + e.rows;
    if (currentPageRows > this.reportData.length){
      currentPageRows = this.reportData.length;
    }
    this.pageinationcount = 'Showing ' +  currentpageCount + ' to ' + currentPageRows + ' of ' + this.reportData.length;
}


  getReportTask(taskId): void {
    console.log("calling getReportTask" + taskId );
    this.reportTaskService.getReportNameByTaskId(taskId).
    subscribe(template => {this.template = template;this.reportName = template.name; 
      this.getReportTaskOutput();
    });
  }
}
