import { Task } from './../model/task';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { catchError, map, tap } from 'rxjs/operators';
import {Template} from './../model/template';
import {RunReportTemplateInfo} from './../model/runReportTemplateInfo';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class ReportTemplateService {

  constructor( private http: HttpClient) { }

    /** POST: add a new template to the server */
    addReportTemplate (template: Template): Observable<Template> {
      console.log("addReportTemplate ----- " + JSON.stringify(template));
      return this.http.post<Template>("/reportwriter/createTemplate", template, httpOptions)
      .pipe(tap((template: Template) => console.log("Template Created")),
            catchError(this.handleError<Template>('addReportTemplate'))
      );
    }

    /** POST: save changes to the template to the server */
    saveReportTemplate (template: Template): Observable<Template> {
      console.log("saveReportTemplate ----- " + JSON.stringify(template));
      return this.http.post<Template>("/reportwriter/saveTemplate", template, httpOptions).pipe(
        tap((template: Template) => console.log("Template saved")),
        catchError(this.handleError<Template>('saveReportTemplate'))
      );
    }

   /* runReportTemplate(templateId:number): Observable<Task>{

      return this.http.get<Task>("/reportwriter/runReport/" + templateId).pipe(
        catchError(this.handleError('runReportTemplate', null))
      );

    }


    runReportTemplates(templates: Template[]): Observable<Task[]>{
      
      return this.http.post<Task[]>("/reportwriter/runReportTemplates", templates, httpOptions)
      .pipe(tap((tasks: Task[]) => console.log("runReportTemplates")),
            catchError(this.handleError<Task[]>('runReportTemplates'))
      );
      
    }*/

    runReportTemplates(runReportTemplateInfo: RunReportTemplateInfo): Observable<Task[]>{
      
      return this.http.post<Task[]>("/reportwriter/runReportTemplates", runReportTemplateInfo, httpOptions)
      .pipe(tap((tasks: Task[]) => console.log("runAllReportTemplates")),
            catchError(this.handleError<Task[]>('runAllReportTemplates'))
      );
      
    }

    cloneTemplate(template:Template):  Observable<Template>{
      console.log("cloneTemplate ----- " + JSON.stringify(template));
      return this.http.post<Template>("/reportwriter/cloneTemplate" ,template, httpOptions).pipe(
        tap((template: Template) => console.log("New Template created")),
        catchError(this.handleError<Template>('cloneTemplate'))
      );
    }

    getReportTemplates(): Observable<Template[]> {    
      //return of(this.statuses);
     
      return this.http.get<Template[]>("/reportwriter/reporttemplates").pipe(
        catchError(this.handleError(' reporttemplates', []))
      );
  
    }

    getReportTemplate(reportTemplateId): Observable<Template> {    
      //return of(this.statuses);
     
      return this.http.get<Template>("/reportwriter/reporttemplate/" + reportTemplateId).pipe(
        catchError(this.handleError(' getReportTemplate', null))
      );
  
    }

     /**
 * Handle Http operation that failed.
 * Let the app continue.
 * @param operation - name of the operation that failed
 * @param result - optional value to return as the observable result
 */
private handleError<T> (operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {
 
    // TODO: send the error to remote logging infrastructure
    console.error(error); // log to console instead
 
    // Let the app keep running by returning an empty result.
    return of(result as T);
  };
}

}
