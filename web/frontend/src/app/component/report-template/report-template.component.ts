import { Task } from './../../model/task';
import { Template } from './../../model/template';
import { Component, OnInit } from '@angular/core';
import { ReportTemplateService } from './../../service/report-template.service';
import { getBaseLocation } from './../../service/common-functions';

@Component({
  selector: 'app-report-template',
  templateUrl: './report-template.component.html',
  styleUrls: ['./report-template.component.css']
})
export class ReportTemplateComponent implements OnInit {

  constructor(private reportTemplateService:ReportTemplateService) { }

  templates: Template[];
  totalRecordsCount:number;
  pageinationcount:string;
  task:Task;
  staticAlertClosed:boolean;
  displayMessage:string;
  taskRun:boolean;
  public getBaseLocation = getBaseLocation;
  basePath:string;
  
  ngOnInit() {
    this.getReportTemplates();
    this.staticAlertClosed = true;
    this.taskRun = false;
    this.basePath = "/" + getBaseLocation() + "/reportTask"; 
    console.log("basePath - " +  this.basePath);
  }


  getReportTemplates(): void {
    this.reportTemplateService.getReportTemplates().
    subscribe(templates => {this.templates = templates; console.log(JSON.stringify(this.templates));this.totalRecordsCount = this.templates.length;
      let currentpageCount = 0 + 1;
      let currentPageRows = 0 + 5;
      if (currentPageRows > this.templates.length){
        currentPageRows = this.templates.length;
      }
      this.pageinationcount = 'Showing ' +  currentpageCount + ' to ' + currentPageRows + ' of ' + this.templates.length;
  
    });
  }



  editTemplate(template) {
    console.log("In editTemplate - " + JSON.stringify(template));
    
  }

  onPageChange(e) {    
    let currentpageCount = e.first + 1;
    let currentPageRows = e.first + e.rows;
    if (currentPageRows > this.templates.length){
      currentPageRows = this.templates.length;
    }
    this.pageinationcount = 'Showing ' +  currentpageCount + ' to ' + currentPageRows + ' of ' + this.templates.length;
}

runTemplate(templateRow){
  console.log("In runTemplate - " + JSON.stringify(templateRow));
  
  this.reportTemplateService.runReportTemplate(templateRow.id).
  subscribe(task => {this.task = task;console.log(JSON.stringify(task));    
    this.displayMessage = " A task with id - " + task.id + " has been created for the template " + templateRow.name + 
    ". Please check the status of the report in the All Report Tasks page.";
    this.taskRun = true;
    this.staticAlertClosed = false;
    setTimeout(() => {this.staticAlertClosed = true; console.log("setting staticAlertClosed to true") }, 50000);
  });

}


}
