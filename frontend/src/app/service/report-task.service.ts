import { ReportTaskOutput } from './../model/reportTaskOutput';
import { Injectable } from '@angular/core';


import { HttpClient, HttpHeaders } from '@angular/common/http';

import {Lookup} from './../model/lookup';

import {Task} from './../model/task';
import {Template} from './../model/template';

import { Observable, of, throwError } from "rxjs";
import { catchError, map, tap } from "rxjs/operators";

@Injectable()
export class ReportTaskService {

  constructor(private http: HttpClient) { }


  

  getReportTasks(): Observable<Task[]> {    
    //return of(this.statuses);
   
    return this.http.get<Task[]>("/reportwriter/reporttasks").pipe(
      catchError(err => {
        console.log(
          "Handling error locally and rethrowing it...",
          JSON.stringify(err)
        );
        err.message =
          "Error getting report tasks";
        return throwError(err);
      })
    );

  }

  getReportNameByTaskId(taskId): Observable<Template> {    
    //return of(this.statuses);
    console.log("calling getReportTask - service method - " + taskId );
    return this.http.get<Template>("/reportwriter/reportNameByTaskId/" + taskId).pipe(
      catchError(err => {
        console.log(
          "Handling error locally and rethrowing it...",
          JSON.stringify(err)
        );
        err.message =
          "Error getting report task for task Id -" + taskId;
        return throwError(err);
      })
    );

  }

  deleteReportTask(taskId):Observable<Task>{
    return this.http.get<Task>("/reportwriter/deleteReportTask/" + taskId).pipe(
      catchError(err => {
        console.log(
          "Handling error locally and rethrowing it...",
          JSON.stringify(err)
        );
        err.message =
          "Error deleting task Id -" + taskId;
        return throwError(err);
      })
    );
  }

  getXLSReport(taskId){
    return this.http.get("/reportwriter/getXLSReport/" + taskId).pipe(
      catchError(err => {
        console.log(
          "Handling error locally and rethrowing it...",
          JSON.stringify(err)
        );
        err.message =
          "Error getting report xls report from the backend for task Id -" + taskId;
        return throwError(err);
      })
    );
  }

  getTaskStatuses(): Observable<Lookup[]> {  
    
     //return of(this.groups);
     return this.http.get<Lookup[]>("/reportwriter/lkreportstatus").pipe(
      catchError(err => {
        console.log(
          "Handling error locally and rethrowing it...",
          JSON.stringify(err)
        );
        err.message =
          "Error getting report task statuses from the backend";
        return throwError(err);
      })
    );
   }

   getReportTaskData(taskId): Observable<ReportTaskOutput> {  
    
     //return of(this.groups);
     return this.http.get<ReportTaskOutput>("/reportwriter/getReportTaskData/" + taskId).pipe(
      catchError(err => {
        console.log(
          "Handling error locally and rethrowing it...",
          JSON.stringify(err)
        );
        err.message =
          "Error getting report task data from the backend for task Id - " + taskId;
        return throwError(err);
      })
    );
   }


    
}
