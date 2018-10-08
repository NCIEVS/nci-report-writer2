import { Injectable } from '@angular/core';
import { Observable ,  of } from 'rxjs';
import {Lookup} from './../model/lookup';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { catchError, map, tap } from 'rxjs/operators';

@Injectable()
export class LookupvaluesTemplateService {

  types:Lookup[]= [{label:'Association',value:'Association'},{ label:'Concept List',value:'Concept List'}];

 
  associations:Lookup[] = [{label:'Concept_In_Subset',value:'Concept_In_Subset'},{ label:'Children',value:'Children'}];
  statuses:Lookup[] = [{label:'Pending',value:'P'},{ label:'Active',value:'A'}];

  constructor( private http: HttpClient) { }

   getTypes(): Observable<Lookup[]> {    
    //return of(this.types);
    return this.http.get<Lookup[]>("/reportwriter/lkreporttemplatetype").pipe(
      catchError(this.handleError('lkreporttemplatetype', []))
    );
    
  }

  getAssociations(): Observable<Lookup[]> {    
    //return of(this.associations);
    return this.http.get<Lookup[]>("/reportwriter/lkassociation").pipe(
      catchError(this.handleError('lkpropertytype', []))
    );
  }

  getStatuses(): Observable<Lookup[]> {    
    //return of(this.statuses);
   
    return this.http.get<Lookup[]>("/reportwriter/lkreporttemplatestatus").pipe(
      catchError(this.handleError(' lkreporttemplatestatus', []))
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
