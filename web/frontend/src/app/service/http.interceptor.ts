import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpResponse
} from "@angular/common/http";

import { LoaderService } from "./loader.service";
import { getBaseLocation } from "./common-functions";
import { map, takeUntil, tap } from "rxjs/operators";

@Injectable()
export class HttpService implements HttpInterceptor {
  constructor(private loaderService: LoaderService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    //Use the below two lines of code for a war file
    const url = getBaseLocation();
    //console.log("url - " + url);
    //Use the below two lines of code for a jar file
    //const url = "";
    //console.log("url - " + url);

    req = req.clone({
      url: url + req.url
    });
    // start our loader here
    this.loaderService.showLoader();

    return next.handle(req).pipe(
      tap(
        event => {
          // if the event is for http response
          if (event instanceof HttpResponse) {
            // stop our loader here
            this.loaderService.hideLoader();
          }
        },
        error => {
          // if any error (not for just HttpResponse) we stop our loader bar
          this.loaderService.hideLoader();
          console.log("----response----");
          console.error("status code:");
          console.error(error.status);
          console.error(error.message);
          console.log("--- end of response---");
        }
      )
    );
  }
}
