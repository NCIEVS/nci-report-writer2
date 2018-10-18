import { Task } from './../model/task';
import { Injectable } from '@angular/core';
import { Observable ,  of,throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { catchError, map, tap } from 'rxjs/operators';
import {Template} from './../model/template';
import {RunReportTemplateInfo} from './../model/runReportTemplateInfo';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class ReportTemplateService {

  url:string;

  constructor( private http: HttpClient) { }

    /** POST: add a new template to the server */
    addReportTemplate (template: Template): Observable<Template> {
      this.url = "/reportwriter/createTemplate";
      console.log("addReportTemplate ----- " + JSON.stringify(template));
      return this.http.post<Template>(this.url, template, httpOptions)
      .pipe(
        catchError(err => {
          console.log(
            "Handling error locally and rethrowing it...",
            JSON.stringify(err)
          );
          err.message =
            "Error adding report template - " + template.name;
          return throwError(err);
        })
      );
    }

    /** POST: save changes to the template to the server */
    saveReportTemplate (template: Template): Observable<Template> {
      this.url = "/reportwriter/saveTemplate";
      console.log("saveReportTemplate ----- " + JSON.stringify(template));
      return this.http.post<Template>(this.url, template, httpOptions) .pipe(
        catchError(err => {
          console.log(
            "Handling error locally and rethrowing it...",
            JSON.stringify(err)
          );
          err.message =
            "Error running saving template - " + template.name;
          return throwError(err);
        })
      );
    }


    runReportTemplates(runReportTemplateInfo: RunReportTemplateInfo): Observable<Task[]>{
      this.url = "/reportwriter/runReportTemplates";
      return this.http.post<Task[]>( this.url, runReportTemplateInfo, httpOptions)
      .pipe(
        catchError(err => {
          console.log(
            "Handling error locally and rethrowing it...",
            JSON.stringify(err)
          );
          err.message =
            "Error running report templates";
          return throwError(err);
        })
      );
      
    }

    runReportTemplateConceptList(formData: FormData): Observable<Task[]>{
      this.url = "/reportwriter/uploadConceptList";
      return this.http.post<Task[]>( this.url, formData)
      .pipe(
        catchError(err => {
          console.log(
            "Handling error locally and rethrowing it...",
            JSON.stringify(err)
          );
          err.message =
            "Error running report templates";
          return throwError(err);
        })
      );
      
    }

    cloneTemplate(template:Template):  Observable<Template>{
      console.log("cloneTemplate ----- " + JSON.stringify(template));
      return this.http.post<Template>("/reportwriter/cloneTemplate" ,template, httpOptions)
      .pipe(
        catchError(err => {
          console.log(
            "Handling error locally and rethrowing it...",
            JSON.stringify(err)
          );
          err.message =
            "Error cloning report template";
          return throwError(err);
        })
      );
    }

    getReportTemplates(): Observable<Template[]> {    
      //return of(this.statuses);
      this.url = "/reportwriter/reporttemplates";
      return this.http.get<Template[]>(this.url)
      .pipe(
        catchError(err => {
          console.log(
            "Handling error locally and rethrowing it...",
            JSON.stringify(err)
          );
          err.message =
            "Error getting report templates from the backend";
          return throwError(err);
        })
      );
  
    }

    getReportTemplate(reportTemplateId): Observable<Template> {    
      //return of(this.statuses);
      this.url = "/reportwriter/reporttemplate/" + reportTemplateId;
      return this.http.get<Template>(this.url).pipe(
        catchError(err => {
          console.log(
            "Handling error locally and rethrowing it...",
            JSON.stringify(err)
          );
          err.message =
            "Error getting report template for template id " + reportTemplateId + "from the backend";
          return throwError(err);
        })
      );
    
  
    }

    

}
