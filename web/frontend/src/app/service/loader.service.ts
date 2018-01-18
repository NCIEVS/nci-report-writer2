// import { Injectable } from '@angular/core';
// import { Observable } from 'rxjs';
// import { Subject } from 'rxjs/Subject';
// import { LoaderState } from './loader';


// @Injectable()

// export class LoaderService {

//     private loaderSubject = new Subject<LoaderState>();

//     loaderState = this.loaderSubject.asObservable();

//     constructor() { }

//     show() {
//         this.loaderSubject.next(<LoaderState>{show: true});
//     }

//     hide() {
//         this.loaderSubject.next(<LoaderState>{show: false});
//     }
// }

import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Injectable()

export class LoaderService {

    private loaderSubject = new BehaviorSubject<boolean>(false);    

    constructor() { }

    showLoader() {
        this.loaderSubject.next(true);
        console.log("show loader");
    }

    hideLoader() {
        this.loaderSubject.next(false);
        console.log("hide loader");
    }

    getLoaderSubject(): Observable<any> {
        return this.loaderSubject.asObservable();
    }
}