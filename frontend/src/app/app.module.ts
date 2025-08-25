import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgModule, APP_INITIALIZER, ErrorHandler } from "@angular/core";
import {
  Location,
  LocationStrategy,
  PathLocationStrategy
} from "@angular/common";

import { AppComponent } from "./app.component";

import { MultiSelectModule } from "primeng/multiselect";
import { DropdownModule } from "primeng/dropdown";
// import { SharedModule } from "primeng/primeng";
import { DialogModule } from "primeng/dialog";
import { ButtonModule } from "primeng/button";
import { MenubarModule } from "primeng/menubar";
import { MenuItem } from "primeng/api";
import { ConfirmDialogModule } from "primeng/confirmdialog";
import { SplitButtonModule } from "primeng/splitbutton";
import { TooltipModule } from "primeng/tooltip";
import { BlockUIModule } from "primeng/blockui";
import { ProgressBarModule } from "primeng/progressbar";
import { ProgressSpinnerModule } from "primeng/progressspinner";
import { TableModule } from "primeng/table";
import { InputTextModule } from "primeng/inputtext";
import { FileUploadModule } from "primeng/fileupload";
import { InputMaskModule } from "primeng/inputmask";

import { MessageService } from "primeng/api";

import { ToastrModule } from "ngx-toastr";
import { ToastrService } from "ngx-toastr";

import { ToastModule } from "primeng/toast";

import { NgbModule } from "@ng-bootstrap/ng-bootstrap";

import { FormsModule } from "@angular/forms";

import { CreateTemplateComponent } from "./component/create-template/create-template.component";

import { LookupvaluesTemplateService } from "./service/lookupvalues-template.service";
import { LookupvaluesTemplaterowService } from "./service/lookupvalues-templaterow.service";
import { ReportTemplateService } from "./service/report-template.service";
import { ReportTaskService } from "./service/report-task.service";
import { GlobalErrorHandler } from "./service/GlobalErrorHandler";

import { LoaderService } from "./service/loader.service";
import { HttpService } from "./service/http.interceptor";

import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from "@angular/common/http";

import { AppRoutingModule } from "./app-routing.module";
import { ReportTemplateViewComponent } from "./component/report-template-view/report-template-view.component";
import { AllReportTaskStatusComponent } from "./component/all-report-task-status/all-report-task-status.component";
import { ReportTemplateComponent } from "./component/report-template/report-template.component";
import { ReportTaskOutputComponent } from "./component/report-task-output/report-task-output.component";
import { ReportwriterHomeComponent } from "./component/reportwriter-home/reportwriter-home.component";
import { HeaderComponent } from "./component/header/header.component";
import { FooterComponent } from "./component/footer/footer.component";
import { LoaderComponent } from "./component/loader/loader.component";

import { APP_BASE_HREF } from "@angular/common";
import { getBaseLocation } from "./service/common-functions";
import { CloneTemplateComponent } from "./component/clone-template/clone-template.component";

@NgModule({ declarations: [
        AppComponent,
        CreateTemplateComponent,
        ReportTemplateComponent,
        ReportTemplateViewComponent,
        AllReportTaskStatusComponent,
        ReportTaskOutputComponent,
        ReportwriterHomeComponent,
        HeaderComponent,
        FooterComponent,
        LoaderComponent,
        CloneTemplateComponent
    ],
    bootstrap: [AppComponent], imports: [BrowserModule,
        MultiSelectModule,
        DropdownModule,
        FormsModule,
        BrowserAnimationsModule,
        TableModule,
        // SharedModule,
        NgbModule,
        DialogModule,
        ButtonModule,
        AppRoutingModule,
        MenubarModule,
        ConfirmDialogModule,
        SplitButtonModule,
        TooltipModule,
        BlockUIModule,
        ProgressBarModule,
        ProgressSpinnerModule,
        InputTextModule,
        FileUploadModule,
        ToastModule,
        InputMaskModule,
        ToastrModule.forRoot({
            timeOut: 10000,
            positionClass: "toast-top-right",
            preventDuplicates: true,
            maxOpened: 5,
            autoDismiss: true,
            newestOnTop: true
        })], providers: [
        LookupvaluesTemplateService,
        LookupvaluesTemplaterowService,
        ReportTemplateService,
        ReportTaskService,
        LoaderService,
        MessageService,
        ToastrService,
        GlobalErrorHandler,
        {
            provide: ErrorHandler,
            useClass: GlobalErrorHandler
        },
        Location,
        {
            provide: LocationStrategy,
            useClass: PathLocationStrategy
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: HttpService,
            multi: true
        },
        {
            provide: APP_BASE_HREF,
            useFactory: getBaseLocation
        },
        provideHttpClient(withInterceptorsFromDi())
    ] })
export class AppModule {}
