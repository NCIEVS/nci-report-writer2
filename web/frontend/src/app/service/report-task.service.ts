import { ReportTaskOutput } from './../model/reportTaskOutput';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { catchError, map, tap } from 'rxjs/operators';
import {Lookup} from './../model/lookup';

import {Task} from './../model/task';

@Injectable()
export class ReportTaskService {

  constructor(private http: HttpClient) { }


  getReportTasks(): Observable<Task[]> {    
    //return of(this.statuses);
   
    return this.http.get<Task[]>("/reportwriter/reporttasks").pipe(
      catchError(this.handleError(' reporttasks', []))
    );

  }

  deleteReportTask(taskId):Observable<Task>{
    return this.http.get<Task>("/reportwriter/deleteReportTask/" + taskId).pipe(
      catchError(this.handleError(' reporttasks', null))
    );
  }

  getXLSReport(taskId){
    return this.http.get("/reportwriter/getXLSReport/" + taskId).pipe(
      catchError(this.handleError(' getXLSReport', null))
    );
  }

  getTaskStatuses(): Observable<Lookup[]> {  
    
     //return of(this.groups);
     return this.http.get<Lookup[]>("/reportwriter/lkreportstatus").pipe(
       catchError(this.handleError('lkreportstatus', []))
     );
   }

   getReportTaskData(taskId): Observable<ReportTaskOutput> {  
    
     //return of(this.groups);
     return this.http.get<ReportTaskOutput>("/reportwriter/getReportTaskData/" + taskId).pipe(
       catchError(this.handleError('getReportTaskData', null))
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
