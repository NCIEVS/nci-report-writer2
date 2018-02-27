import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { Location, LocationStrategy, PathLocationStrategy } from '@angular/common';

import { AppComponent } from './app.component';


import {MultiSelectModule} from 'primeng/primeng';
import {DropdownModule} from 'primeng/primeng';
import {DataTableModule,SharedModule} from 'primeng/primeng';
import {DialogModule} from 'primeng/primeng';
import {ButtonModule} from 'primeng/primeng';
import {MenubarModule,MenuItem} from 'primeng/primeng';
import {ConfirmDialogModule,ConfirmationService} from 'primeng/primeng';
import {SplitButtonModule} from 'primeng/primeng';
import {TooltipModule} from 'primeng/primeng';
import {BlockUIModule} from 'primeng/primeng';
import {ProgressBarModule} from 'primeng/primeng';
import {ProgressSpinnerModule} from 'primeng/primeng';
import {TableModule} from 'primeng/table';
import {InputTextModule} from 'primeng/inputtext';



import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import { FormsModule } from '@angular/forms';


import { CreateTemplateComponent } from './component/create-template/create-template.component';


import { LookupvaluesTemplateService } from './service/lookupvalues-template.service';
import { LookupvaluesTemplaterowService } from './service/lookupvalues-templaterow.service';
import { ReportTemplateService } from './service/report-template.service';
import { ReportTaskService } from './service/report-task.service';
import { LoaderService } from './service/loader.service';
import { HttpService } from './service/http.interceptor';



import { HttpClientModule, HTTP_INTERCEPTORS}  from '@angular/common/http';

import { AppRoutingModule }     from './app-routing.module';
import { ReportTemplateViewComponent } from './component/report-template-view/report-template-view.component';
import { AllReportTaskStatusComponent } from './component/all-report-task-status/all-report-task-status.component';
import { ReportTemplateComponent } from './component/report-template/report-template.component';
import { ReportTaskOutputComponent } from './component/report-task-output/report-task-output.component';
import { ReportwriterHomeComponent } from './component/reportwriter-home/reportwriter-home.component';
import { HeaderComponent } from './component/header/header.component';
import { FooterComponent } from './component/footer/footer.component';
import { LoaderComponent } from './component/loader/loader.component';

import { APP_BASE_HREF } from '@angular/common';
import { getBaseLocation } from './service/common-functions';

@NgModule({
  declarations: [
    AppComponent,
    CreateTemplateComponent,
    ReportTemplateComponent,
    ReportTemplateViewComponent,
    AllReportTaskStatusComponent,
    ReportTaskOutputComponent,
    ReportwriterHomeComponent,
    HeaderComponent,
    FooterComponent,
    LoaderComponent
  ],
  imports: [
    BrowserModule,
    MultiSelectModule,
    DropdownModule,
    FormsModule,
    BrowserAnimationsModule,
    DataTableModule,
    SharedModule,
    NgbModule.forRoot(),
    DialogModule,
    ButtonModule,
    AppRoutingModule,
    MenubarModule,
    HttpClientModule,
    ConfirmDialogModule,
    SplitButtonModule,
    TooltipModule,
    BlockUIModule,
    ProgressBarModule,
    ProgressSpinnerModule,
    InputTextModule
  ],
  providers: [LookupvaluesTemplateService,LookupvaluesTemplaterowService,ReportTemplateService,ReportTaskService,LoaderService,Location, 
    {
      provide: LocationStrategy, 
      useClass: PathLocationStrategy
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpService,
      multi: true,
    },
    {
      provide: APP_BASE_HREF,
      useFactory: getBaseLocation
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
