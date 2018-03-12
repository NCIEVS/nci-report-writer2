import { Task } from './../../model/task';
import { Template } from './../../model/template';
import { Component, OnInit } from '@angular/core';
import { ReportTemplateService } from './../../service/report-template.service';
import { getBaseLocation } from './../../service/common-functions';

import {ViewChild} from '@angular/core';
import {DataTable} from 'primeng/datatable';
import {InputText} from  'primeng/inputtext';
import {ElementRef } from '@angular/core';

@Component({
  selector: 'app-report-template',
  templateUrl: './report-template.component.html',
  styleUrls: ['./report-template.component.css']
})
export class ReportTemplateComponent implements OnInit {

  @ViewChild('dtTemplate') dataTable: DataTable;
 
 

  constructor(private reportTemplateService:ReportTemplateService) { }

  templates: Template[];
  selectedTemplates: Template[];
  totalRecordsCount:number;
  pageinationcount:string;
  task:Task;
  tasks:Task[];
  staticAlertClosed:boolean;
  displayMessage:string;
  taskRun:boolean;
  public getBaseLocation = getBaseLocation;
  basePath:string;
  globalFilter:string;

  onFilter(e) {
    //saving the filters
    console.log(this.globalFilter);
    if (!(this.globalFilter == null || this.globalFilter == undefined )){
      localStorage.setItem("globalfilters-template", this.globalFilter);
    }
    console.log("filters - " +  JSON.stringify(this.dataTable.filters));
    localStorage.setItem("filters-template", JSON.stringify(this.dataTable.filters));
  }
  
  ngOnInit() {
    this.getReportTemplates();
    this.staticAlertClosed = true;
    this.taskRun = false;
    this.basePath = "/" + getBaseLocation() + "/reportTask"; 
    console.log("basePath - " +  this.basePath);

    //setting the filters
    const filters = localStorage.getItem("filters-template");
    if (filters) {
      console.log("filters-template in init - " + JSON.parse(filters));
      this.dataTable.filters = JSON.parse(filters);
    }
    const globalfilters = localStorage.getItem("globalfilters-template");
    if (globalfilters != undefined || globalfilters != null) {
      console.log("globalfilters-template in init - " + globalfilters);
      this.globalFilter = globalfilters;
    }


  }


  runTemplates(): void {

    console.log("In runTemplates - " + JSON.stringify(this.selectedTemplates));
    
    this.reportTemplateService.runReportTemplates(this.selectedTemplates).
    subscribe(tasks => {this.tasks = tasks;console.log(JSON.stringify(tasks));  
      this.displayMessage = ""; 
      for (let task of tasks) {
        this.displayMessage = this.displayMessage + " A task with Id - " + task.reportTemplateId + " has been created for the template - " + task.reportTemplateName + ".<br>";
      }
     
      this.displayMessage =  this.displayMessage + "Please check the status of the report in the All Report Tasks page.";
      this.taskRun = true;
      this.staticAlertClosed = false;
      window.scrollTo(0,0);
      setTimeout(() => {this.staticAlertClosed = true; console.log("setting staticAlertClosed to true") }, 50000);
    });

  }

  getReportTemplates(): void {
    this.reportTemplateService.getReportTemplates().
    subscribe(templates => {this.templates = templates; console.log(JSON.stringify(this.templates));this.totalRecordsCount = this.templates.length;
      let currentpageCount = 0 + 1;
      let currentPageRows = 0 + 20;
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
    this.displayMessage = " A task with Id - " + task.id + " has been created for the template " + templateRow.name + ". <br>"
    "Please check the status of the report in the All Report Tasks page.";
    this.taskRun = true;
    this.staticAlertClosed = false;
    window.scrollTo(0,0);
    setTimeout(() => {this.staticAlertClosed = true; console.log("setting staticAlertClosed to true") }, 50000);
  });

}


}
