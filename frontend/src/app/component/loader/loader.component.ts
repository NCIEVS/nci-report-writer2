

import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

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