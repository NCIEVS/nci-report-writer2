import { Injectable } from "@angular/core";

import { Lookup } from "./../model/lookup";
import { HttpClient, HttpHeaders } from "@angular/common/http";

import { Observable, of, throwError } from "rxjs";
import { catchError, map, tap } from "rxjs/operators";

@Injectable()
export class LookupvaluesTemplaterowService {
  displays: Lookup[] = [
    { label: "code", value: "code" },
    { label: "property", value: "property" },
    { label: "source-code", value: "source-code" }
  ];

  propertyTypes: Lookup[] = [
    { label: "DEFINITION", value: "DEFINITION" },
    { label: "ALT_DEFINITION", value: "ALT_DEFINITION" },
    { label: "FULL_SYN", value: "FULL_SYN" }
  ];
  propertyTargets: Lookup[] = [
    { label: "Preferred_Name", value: "Preferred_Name" },
    { label: "Contributing_Source", value: "Contributing_Source" }
  ];
  sources: Lookup[] = [
    { label: "DTP", value: "DTP" },
    { label: "FDA", value: "FDA" }
  ];
  groups: Lookup[] = [
    { label: "AB", value: "AB" },
    { label: "AD", value: "AD" }
  ];
  subsources: Lookup[] = [
    { label: "CDRH", value: "CDRH" },
    { label: "CTRP", value: "CTRP" }
  ];

  constructor(private http: HttpClient) {}

  getDisplays(): Observable<Lookup[]> {
    //return of(this.displays);
    return this.http.get<Lookup[]>("/reportwriter/lkdisplay").pipe(
      catchError(err => {
        console.log(
          "Handling error locally and rethrowing it...",
          JSON.stringify(err)
        );
        err.message =
          "Error getting lookup values for display types from the backend";
        return throwError(err);
      })
    );
  }

  getPropertyTypes(): Observable<Lookup[]> {
    //return of(this.propertyTypes);
    return this.http.get<Lookup[]>("/reportwriter/lkpropertytype").pipe(
      catchError(err => {
        console.log(
          "Handling error locally and rethrowing it...",
          JSON.stringify(err)
        );
        err.message =
          "Error getting lookup values for property types from the backend";
        return throwError(err);
      })
    );
  }

  getPropertyTargets(): Observable<Lookup[]> {
    //return of(this.propertyTargets);
    return this.http.get<Lookup[]>("/reportwriter/lkproperty").pipe(
      catchError(err => {
        console.log(
          "Handling error locally and rethrowing it...",
          JSON.stringify(err)
        );
        err.message =
          "Error getting lookup values for property target types from the backend";
        return throwError(err);
      })
    );
  }

  getSources(): Observable<Lookup[]> {
    //return of(this.sources);
    return this.http.get<Lookup[]>("/reportwriter/lksource").pipe(
      catchError(err => {
        console.log(
          "Handling error locally and rethrowing it...",
          JSON.stringify(err)
        );
        err.message =
          "Error getting lookup values for sources types from the backend";
        return throwError(err);
      })
    );
  }

  getGroups(): Observable<Lookup[]> {
    //return of(this.groups);
    return this.http.get<Lookup[]>("/reportwriter/lkgroup").pipe(
      catchError(err => {
        console.log(
          "Handling error locally and rethrowing it...",
          JSON.stringify(err)
        );
        err.message =
          "Error getting lookup values for groups types from the backend";
        return throwError(err);
      })
    );
  }

  getSubsources(): Observable<Lookup[]> {
    //return of(this.subsources);
    return this.http.get<Lookup[]>("/reportwriter/lksubsource").pipe(
      catchError(err => {
        console.log(
          "Handling error locally and rethrowing it...",
          JSON.stringify(err)
        );
        err.message =
          "Error getting lookup values for sub sources types from the backend";
        return throwError(err);
      })
    );
  }

  getAttrs(): Observable<Lookup[]> {
    return this.http.get<Lookup[]>("/reportwriter/lkattr").pipe(
      catchError(err => {
        console.log(
          "Handling error locally and rethrowing it...",
          JSON.stringify(err)
        );
        err.message =
          "Error getting lookup values for attr types from the backend";
        return throwError(err);
      })
    );
  }
}
