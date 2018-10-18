import { Injectable } from "@angular/core";



import { Lookup } from "./../model/lookup";
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse
} from "@angular/common/http";

import { Observable, of, throwError } from "rxjs";
import { catchError, map, tap } from "rxjs/operators";

@Injectable()
export class LookupvaluesTemplateService {
  url: string;

  types: Lookup[] = [
    { label: "Association", value: "Association" },
    { label: "Concept List", value: "Concept List" }
  ];

  associations: Lookup[] = [
    { label: "Concept_In_Subset", value: "Concept_In_Subset" },
    { label: "Children", value: "Children" }
  ];
  statuses: Lookup[] = [
    { label: "Pending", value: "P" },
    { label: "Active", value: "A" }
  ];

  constructor(private http: HttpClient) {}

  getTypes(): Observable<Lookup[]> {
    this.url = "/reportwriter/lkreporttemplatetype";
    //return of(this.types);
    return this.http.get<Lookup[]>(this.url).pipe(
      catchError(err => {
        console.log(
          "Handling error locally and rethrowing it...",
          JSON.stringify(err)
        );
        err.message =
          "Error getting lookup values for template types from the backend";
        return throwError(err);
      })
    );
  }

  getAssociations(): Observable<Lookup[]> {
    this.url = "/reportwriter/lkassociation";
    //return of(this.associations);
    return this.http.get<Lookup[]>(this.url).pipe(
      catchError(err => {
        console.log(
          "Handling error locally and rethrowing it...",
          JSON.stringify(err)
        );
        err.message =
          "Error getting lookup values for associations from the backend";
        return throwError(err);
      })
    );
  }

  getStatuses(): Observable<Lookup[]> {
    //return of(this.statuses);
    this.url = "/reportwriter/lkreporttemplatestatus";
    return this.http.get<Lookup[]>(this.url).pipe(
      catchError(err => {
        console.log(
          "Handling error locally and rethrowing it...",
          JSON.stringify(err)
        );
        err.message =
          "Error getting lookup values for template status from the backend";
        return throwError(err);
      })
    );
  }

  p;
}
