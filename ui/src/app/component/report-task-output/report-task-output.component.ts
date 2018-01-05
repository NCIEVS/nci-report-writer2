import { ReportTaskOutput } from './../../model/reportTaskOutput';
import { ReportData } from './../../model/reportData';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ReportTaskService } from './../../service/report-task.service';

@Component({
  selector: 'app-report-task-output',
  templateUrl: './report-task-output.component.html',
  styleUrls: ['./report-task-output.component.css']
})
export class ReportTaskOutputComponent implements OnInit {
  taskId:number;
  reportTaskOutput:ReportTaskOutput;
  cols: any[];
  reportData:ReportData[];

  constructor(private route: ActivatedRoute,private reportTaskService:ReportTaskService,) { 
    this.taskId = route.snapshot.params['id'];
  }

  ngOnInit() {
    this.getReportTaskOutput();
    

  }


  getReportTaskOutput(): void {
    this.reportTaskService.getReportTaskData(this.taskId).
    subscribe(reportTaskOutput => {this.reportTaskOutput = reportTaskOutput;  
      console.log("templateRowUI - " + JSON.stringify(this.reportTaskOutput));
      this.cols= this.reportTaskOutput.header;this.reportData = this.reportTaskOutput.data});
  }

 

}
