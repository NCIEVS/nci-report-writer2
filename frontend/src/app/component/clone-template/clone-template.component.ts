import { Component, OnInit } from '@angular/core';

import { ReportTemplateService } from './../../service/report-template.service';
import { TemplateRow } from './../../model/template-row';
import { Template } from './../../model/template';

import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-clone-template',
  templateUrl: './clone-template.component.html',
  styleUrls: ['./clone-template.component.css']
})
export class CloneTemplateComponent implements OnInit {
  templateId: number;
  title: string;
  templateName:string;
  template: Template;
  templateRows: TemplateRow[];
  clonetemplate: Template;
  isCloned:boolean;
  displayMessage:string;

  constructor(
    private reportTemplateService: ReportTemplateService, private route: ActivatedRoute) {
    this.isCloned = false;
    this.templateId = route.snapshot.params['id'];
    console.log("templateId -" + this.templateId);
    this.reportTemplateService.getReportTemplate(this.templateId).subscribe(template => {
      this.template = template;
      this.templateRows = template.columns;
      console.log("after getReportTemplate ----- " + JSON.stringify(this.template));
      console.log("after getReportTemplate ----- " + JSON.stringify(this.templateRows));
      this.title = "Clone Template Id - " + this.templateId + " with Template Name - " + this.template.name;
    });
    


  }



  

  ngOnInit() {

    
  }


  onSubmitCloneTemplate() {
    console.log("** - *** " + this.templateName);
    this.template.name = this.templateName;

    console.log("Before calling cloneTemplate " +  JSON.stringify(this.template));
    this.reportTemplateService.cloneTemplate(this.template).subscribe(template => {
      console.log("Returned from cloneTemplate " +  JSON.stringify(template));
      this.clonetemplate = template;
      this.displayMessage = "The Template has been cloned. The new Template id is " + this.clonetemplate.id 
      + " and name is "+ this.clonetemplate.name + ". Click on the icon below to access it.  ";
      this.isCloned = true;
    });

  }

}
