// import { Component, OnInit, OnDestroy } from '@angular/core';
// import { Subscription } from 'rxjs/Subscription';

// import { LoaderService } from './loader.service';
// import { LoaderState } from './loader';

// @Component({
//     selector: 'angular-loader',
//     templateUrl: 'loader.component.html',
//     styleUrls: ['loader.component.css']
// })
// export class LoaderComponent implements OnInit {
//     show = false;
//     private subscription: Subscription;

//     constructor(
//         private loaderService: LoaderService
//     ) { 
//         //this.subscription = this.loaderService.getLoaderSubject().subscribe(show => {this.show = show; });
//     }

//     ngOnInit() { 
//         this.subscription = this.loaderService.loaderState
//             .subscribe((state: LoaderState) => {
//                 this.show = state.show;
//             });
//     }

//     ngOnDestroy() {
//         this.subscription.unsubscribe();
//     }
// }

import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';

import { LoaderService } from './../../service/loader.service';

@Component({
    selector: 'angular-loader',
    templateUrl: 'loader.component.html',
    styleUrls: ['loader.component.css']
})
export class LoaderComponent implements OnDestroy {
    show: boolean;
    private subscription: Subscription;

    constructor(
        private loaderService: LoaderService
    ) { 
        
    }

    ngOnInit() {
        this.loaderService.getLoaderSubject().subscribe(show => this.show = show)
      }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}