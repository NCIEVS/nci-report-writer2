import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import {Lookup} from './../model/lookup';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { catchError, map, tap } from 'rxjs/operators';

@Injectable()
export class LookupvaluesTemplaterowService {



  displays:Lookup[]= [{label:'code',value:'code'},{ label:'property',value:'property'},{ label:'source-code',value:'source-code'}];
  
   
  propertyTypes:Lookup[] = [{label:'DEFINITION',value:'DEFINITION'},{ label:'ALT_DEFINITION',value:'ALT_DEFINITION'},{ label:'FULL_SYN',value:'FULL_SYN'}];
  propertyTargets:Lookup[] = [{label:'Preferred_Name',value:'Preferred_Name'},{ label:'Contributing_Source',value:'Contributing_Source'}];
  sources:Lookup[] = [{label:'DTP',value:'DTP'},{ label:'FDA',value:'FDA'}];
  groups:Lookup[] = [{label:'AB',value:'AB'},{ label:'AD',value:'AD'}];
  subsources:Lookup[] = [{label:'CDRH',value:'CDRH'},{ label:'CTRP',value:'CTRP'}];
  
  constructor(private http: HttpClient) { 
    

  }
  
  getDisplays(): Observable<Lookup[]> {    
      //return of(this.displays);
      return this.http.get<Lookup[]>("/reportwriter/lkdisplay").pipe(
        catchError(this.handleError('lkdisplay', []))
      );
  }
  
  getPropertyTypes(): Observable<Lookup[]> {    
      //return of(this.propertyTypes);
      return this.http.get<Lookup[]>("/reportwriter/lkpropertytype").pipe(
        catchError(this.handleError('lkpropertytype', []))
      );
  }
  
  getPropertyTargets(): Observable<Lookup[]> {    
     //return of(this.propertyTargets);
      return this.http.get<Lookup[]>("/reportwriter/lkproperty").pipe(
        catchError(this.handleError('lkpropertytype', []))
      );
  }

  getSources(): Observable<Lookup[]> { 
    
    //return of(this.sources);
    return this.http.get<Lookup[]>("/reportwriter/lksource").pipe(
      catchError(this.handleError('lkpropertytype', []))
    );
  }

  getGroups(): Observable<Lookup[]> {  
   
    //return of(this.groups);
    return this.http.get<Lookup[]>("/reportwriter/lkgroup").pipe(
      catchError(this.handleError('lkpropertytype', []))
    );
  }

  getSubsources(): Observable<Lookup[]> {  
   
    //return of(this.subsources);
    return this.http.get<Lookup[]>("/reportwriter/lksubsource").pipe(
      catchError(this.handleError('lkpropertytype', []))
    );
  }

  getAttrs(): Observable<Lookup[]> {  
    return this.http.get<Lookup[]>("/reportwriter/lkattr").pipe(
      catchError(this.handleError('lkattr', []))
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
