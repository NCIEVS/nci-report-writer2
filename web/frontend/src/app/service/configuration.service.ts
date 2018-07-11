import { Template } from './../model/template';
import { Injectable } from "@angular/core";

import {Lookup} from './../model/lookup';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class ConfigurationService {
  static graphNames:Lookup[] = null;
  private static instance: ConfigurationService = null;

  // Return the instance of the service
  public static getInstance(
    http: HttpClient
  
  ): ConfigurationService {
    if (ConfigurationService.instance === null) {
      ConfigurationService.instance = new ConfigurationService(
        http
      );
    }
    return ConfigurationService.instance;
  }

  constructor(private http: HttpClient) {}

  loadConfiguration(url): Promise<any> {
    return new Promise((resolve, reject) => {
          this.http.get(url).toPromise()
          .then (response => {
            ConfigurationService.graphNames = response['graphNames'];
            
            resolve(true);
          }).catch (error => {
            Observable.throw(error);
          });
        });
  }


}
