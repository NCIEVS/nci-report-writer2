import { Component, OnInit } from '@angular/core';

import { ActivatedRoute } from '@angular/router';
import { ReportTemplateService } from './../../service/report-template.service';

import { TemplateRow } from './../../model/template-row';
import { Template } from './../../model/template';

@Component({
  selector: 'app-report-template-view',
  templateUrl: './report-template-view.component.html',
  styleUrls: ['./report-template-view.component.css']
})
export class ReportTemplateViewComponent implements OnInit {
  templateId:number;
  template:Template;
  templateRows:TemplateRow[];
  templateFetched: boolean = false;

  constructor(private route: ActivatedRoute ,private reportTemplateService: ReportTemplateService) { 

    this.templateId = route.snapshot.params['id'];
    console.log("templateId -" + this.templateId);
    this.reportTemplateService.getReportTemplate(this.templateId).subscribe(template => {
      this.template = template;
      this.templateRows = template.columns;
      this.templateFetched = true;
      console.log("after getReportTemplate ----- " + JSON.stringify(this.template));
      console.log("after getReportTemplate ----- " + JSON.stringify(this.templateRows));
    });
  
  }

  ngOnInit() {

    
  }

}
