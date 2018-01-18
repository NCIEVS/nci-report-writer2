webpackJsonp(["main"],{

/***/ "../../../../../src/$$_lazy_route_resource lazy recursive":
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncatched exception popping up in devtools
	return Promise.resolve().then(function() {
		throw new Error("Cannot find module '" + req + "'.");
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "../../../../../src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "../../../../../src/app/app-routing.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppRoutingModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__component_create_template_create_template_component__ = __webpack_require__("../../../../../src/app/component/create-template/create-template.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__component_report_template_report_template_component__ = __webpack_require__("../../../../../src/app/component/report-template/report-template.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__component_report_template_view_report_template_view_component__ = __webpack_require__("../../../../../src/app/component/report-template-view/report-template-view.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__component_all_report_task_status_all_report_task_status_component__ = __webpack_require__("../../../../../src/app/component/all-report-task-status/all-report-task-status.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__component_report_task_output_report_task_output_component__ = __webpack_require__("../../../../../src/app/component/report-task-output/report-task-output.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__component_reportwriter_home_reportwriter_home_component__ = __webpack_require__("../../../../../src/app/component/reportwriter-home/reportwriter-home.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};








var routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: 'createTemplate/:id', component: __WEBPACK_IMPORTED_MODULE_2__component_create_template_create_template_component__["a" /* CreateTemplateComponent */] },
    { path: 'reportTemplate', component: __WEBPACK_IMPORTED_MODULE_3__component_report_template_report_template_component__["a" /* ReportTemplateComponent */] },
    { path: 'reportTemplateView/:id', component: __WEBPACK_IMPORTED_MODULE_4__component_report_template_view_report_template_view_component__["a" /* ReportTemplateViewComponent */] },
    { path: 'reportTask', component: __WEBPACK_IMPORTED_MODULE_5__component_all_report_task_status_all_report_task_status_component__["a" /* AllReportTaskStatusComponent */] },
    { path: 'reportTaskOutput/:id', component: __WEBPACK_IMPORTED_MODULE_6__component_report_task_output_report_task_output_component__["a" /* ReportTaskOutputComponent */] },
    { path: 'home', component: __WEBPACK_IMPORTED_MODULE_7__component_reportwriter_home_reportwriter_home_component__["a" /* ReportwriterHomeComponent */] }
];
var AppRoutingModule = (function () {
    function AppRoutingModule() {
    }
    AppRoutingModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["NgModule"])({
            imports: [__WEBPACK_IMPORTED_MODULE_1__angular_router__["RouterModule"].forRoot(routes)],
            exports: [__WEBPACK_IMPORTED_MODULE_1__angular_router__["RouterModule"]]
        })
    ], AppRoutingModule);
    return AppRoutingModule;
}());



/***/ }),

/***/ "../../../../../src/app/app.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/app.component.html":
/***/ (function(module, exports) {

module.exports = "<!--<div class=\"container\">\n    <nav>\n        <a routerLink=\"/createTemplate\">Create Template</a>\n        <a routerLink=\"/reportTemplate\">Report Template</a>\n    </nav>\n    <router-outlet></router-outlet>\n</div>-->\n<angular-loader></angular-loader>\n<nci-header></nci-header>\n<div class=\"container\" style=\"padding-top:15px;\">\n    <!-- <p-menubar [model]=\"items\"></p-menubar> -->\n    <br>\n    <router-outlet></router-outlet>\n    <nci-footer></nci-footer>\n</div>\n"

/***/ }),

/***/ "../../../../../src/app/app.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var AppComponent = (function () {
    function AppComponent() {
        this.title = 'app';
    }
    AppComponent.prototype.ngOnInit = function () {
        this.items = [
            {
                icon: 'fa-home',
                routerLink: ['/home']
            },
            {
                label: 'Template',
                items: [{
                        label: 'Create Template',
                        icon: 'fa-plus',
                        routerLink: ['/createTemplate/0']
                    },
                    {
                        label: 'View All Templates',
                        icon: 'fa-eye',
                        routerLink: ['/reportTemplate']
                    }
                ]
            },
            {
                label: 'All Report Tasks',
                icon: 'fa-file-text-o',
                routerLink: ['/reportTask']
            }
        ];
    };
    AppComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-root',
            template: __webpack_require__("../../../../../src/app/app.component.html"),
            styles: [__webpack_require__("../../../../../src/app/app.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "../../../../../src/app/app.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__ = __webpack_require__("../../../platform-browser/esm5/platform-browser.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_animations__ = __webpack_require__("../../../platform-browser/esm5/animations.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_common__ = __webpack_require__("../../../common/esm5/common.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__app_component__ = __webpack_require__("../../../../../src/app/app.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_primeng_primeng__ = __webpack_require__("../../../../primeng/primeng.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_primeng_primeng___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_5_primeng_primeng__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__ng_bootstrap_ng_bootstrap__ = __webpack_require__("../../../../@ng-bootstrap/ng-bootstrap/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__angular_forms__ = __webpack_require__("../../../forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__component_create_template_create_template_component__ = __webpack_require__("../../../../../src/app/component/create-template/create-template.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__service_lookupvalues_template_service__ = __webpack_require__("../../../../../src/app/service/lookupvalues-template.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__service_lookupvalues_templaterow_service__ = __webpack_require__("../../../../../src/app/service/lookupvalues-templaterow.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__service_report_template_service__ = __webpack_require__("../../../../../src/app/service/report-template.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__service_report_task_service__ = __webpack_require__("../../../../../src/app/service/report-task.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__service_loader_service__ = __webpack_require__("../../../../../src/app/service/loader.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__service_http_interceptor__ = __webpack_require__("../../../../../src/app/service/http.interceptor.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_16__app_routing_module__ = __webpack_require__("../../../../../src/app/app-routing.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_17__component_report_template_view_report_template_view_component__ = __webpack_require__("../../../../../src/app/component/report-template-view/report-template-view.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_18__component_all_report_task_status_all_report_task_status_component__ = __webpack_require__("../../../../../src/app/component/all-report-task-status/all-report-task-status.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_19__component_report_template_report_template_component__ = __webpack_require__("../../../../../src/app/component/report-template/report-template.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_20__component_report_task_output_report_task_output_component__ = __webpack_require__("../../../../../src/app/component/report-task-output/report-task-output.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_21__component_reportwriter_home_reportwriter_home_component__ = __webpack_require__("../../../../../src/app/component/reportwriter-home/reportwriter-home.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_22__component_header_header_component__ = __webpack_require__("../../../../../src/app/component/header/header.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_23__component_footer_footer_component__ = __webpack_require__("../../../../../src/app/component/footer/footer.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_24__component_loader_loader_component__ = __webpack_require__("../../../../../src/app/component/loader/loader.component.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




































var AppModule = (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_2__angular_core__["NgModule"])({
            declarations: [
                __WEBPACK_IMPORTED_MODULE_4__app_component__["a" /* AppComponent */],
                __WEBPACK_IMPORTED_MODULE_8__component_create_template_create_template_component__["a" /* CreateTemplateComponent */],
                __WEBPACK_IMPORTED_MODULE_19__component_report_template_report_template_component__["a" /* ReportTemplateComponent */],
                __WEBPACK_IMPORTED_MODULE_17__component_report_template_view_report_template_view_component__["a" /* ReportTemplateViewComponent */],
                __WEBPACK_IMPORTED_MODULE_18__component_all_report_task_status_all_report_task_status_component__["a" /* AllReportTaskStatusComponent */],
                __WEBPACK_IMPORTED_MODULE_20__component_report_task_output_report_task_output_component__["a" /* ReportTaskOutputComponent */],
                __WEBPACK_IMPORTED_MODULE_21__component_reportwriter_home_reportwriter_home_component__["a" /* ReportwriterHomeComponent */],
                __WEBPACK_IMPORTED_MODULE_22__component_header_header_component__["a" /* HeaderComponent */],
                __WEBPACK_IMPORTED_MODULE_23__component_footer_footer_component__["a" /* FooterComponent */],
                __WEBPACK_IMPORTED_MODULE_24__component_loader_loader_component__["a" /* LoaderComponent */]
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__["BrowserModule"],
                __WEBPACK_IMPORTED_MODULE_5_primeng_primeng__["MultiSelectModule"],
                __WEBPACK_IMPORTED_MODULE_5_primeng_primeng__["DropdownModule"],
                __WEBPACK_IMPORTED_MODULE_7__angular_forms__["FormsModule"],
                __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_animations__["a" /* BrowserAnimationsModule */],
                __WEBPACK_IMPORTED_MODULE_5_primeng_primeng__["DataTableModule"],
                __WEBPACK_IMPORTED_MODULE_5_primeng_primeng__["SharedModule"],
                __WEBPACK_IMPORTED_MODULE_6__ng_bootstrap_ng_bootstrap__["c" /* NgbModule */].forRoot(),
                __WEBPACK_IMPORTED_MODULE_5_primeng_primeng__["DialogModule"],
                __WEBPACK_IMPORTED_MODULE_5_primeng_primeng__["ButtonModule"],
                __WEBPACK_IMPORTED_MODULE_16__app_routing_module__["a" /* AppRoutingModule */],
                __WEBPACK_IMPORTED_MODULE_5_primeng_primeng__["MenubarModule"],
                __WEBPACK_IMPORTED_MODULE_15__angular_common_http__["c" /* HttpClientModule */],
                __WEBPACK_IMPORTED_MODULE_5_primeng_primeng__["ConfirmDialogModule"],
                __WEBPACK_IMPORTED_MODULE_5_primeng_primeng__["SplitButtonModule"],
                __WEBPACK_IMPORTED_MODULE_5_primeng_primeng__["TooltipModule"],
                __WEBPACK_IMPORTED_MODULE_5_primeng_primeng__["BlockUIModule"],
                __WEBPACK_IMPORTED_MODULE_5_primeng_primeng__["ProgressBarModule"],
                __WEBPACK_IMPORTED_MODULE_5_primeng_primeng__["ProgressSpinnerModule"]
            ],
            providers: [__WEBPACK_IMPORTED_MODULE_9__service_lookupvalues_template_service__["a" /* LookupvaluesTemplateService */], __WEBPACK_IMPORTED_MODULE_10__service_lookupvalues_templaterow_service__["a" /* LookupvaluesTemplaterowService */], __WEBPACK_IMPORTED_MODULE_11__service_report_template_service__["a" /* ReportTemplateService */], __WEBPACK_IMPORTED_MODULE_12__service_report_task_service__["a" /* ReportTaskService */], __WEBPACK_IMPORTED_MODULE_13__service_loader_service__["a" /* LoaderService */], __WEBPACK_IMPORTED_MODULE_3__angular_common__["Location"],
                {
                    provide: __WEBPACK_IMPORTED_MODULE_3__angular_common__["LocationStrategy"],
                    useClass: __WEBPACK_IMPORTED_MODULE_3__angular_common__["PathLocationStrategy"]
                },
                {
                    provide: __WEBPACK_IMPORTED_MODULE_15__angular_common_http__["a" /* HTTP_INTERCEPTORS */],
                    useClass: __WEBPACK_IMPORTED_MODULE_14__service_http_interceptor__["a" /* HttpService */],
                    multi: true,
                }],
            bootstrap: [__WEBPACK_IMPORTED_MODULE_4__app_component__["a" /* AppComponent */]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "../../../../../src/app/component/all-report-task-status/all-report-task-status.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".col-header {\n    text-align: top;\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/component/all-report-task-status/all-report-task-status.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\n        <div class=\"page-header\">\n            <span> All Report Writer Tasks</span>\n        </div>\n<!-- \n            <div class=\"ui-widget-header\" style=\"padding:4px 10px;border-bottom: 0 none\">\n                <i class=\"fa fa-search\" style=\"margin:4px 4px 0 0\"></i>\n                <input #gb type=\"text\" pInputText size=\"50\" placeholder=\"Global Filter\">\n            </div> -->\n\n            <div class=\"ui-widget-header row\">\n                    <div class=\"col-sm-6\">\n                        <div class=\"input-group \">\n                            <span class=\"input-group-addon\" id=\"basic-addon1\"><i class=\"fa fa-search\"></i></span>\n                            <input #gb type=\"text\" pInputText size=\"50\" placeholder=\"Global Filter\" class=\"form-control\" aria-describedby=\"basic-addon1\">\n                        </div>\n                    </div>\n                    <div class=\"col-sm-6 \">\n                        <div class=\"pull-right paginationCount\">{{pageinationcount}}</div>\n                    </div>   \n            </div>            \n\n            <p-dataTable (onPage)=\"onPageChange($event)\" reorderableColumns=\"true\" sortField=\"id\" sortOrder=\"-1\" [value]=\"tasks\" [responsive]=\"true\" [rows]=\"5\" [paginator]=\"true\" [alwaysShowPaginator]=\"true\" [pageLinks]=\"3\" [rowsPerPageOptions]=\"[5,10,20]\" [globalFilter]=\"gb\"\n                #dt>\n                <p-column field=\"id\" [sortable]=\"true\" [style]=\"{'width':'6%'}\" header=\"Task Id\" [filter]=\"true\" filterPlaceholder=\"Search\" filterMatchMode=\"contains\"> </p-column>\n                <p-column field=\"reportTemplateName\" [sortable]=\"true\" header=\"Name\" [filter]=\"true\" filterPlaceholder=\"Search\" filterMatchMode=\"contains\"> </p-column>\n                <p-column field=\"status\" [sortable]=\"true\" [style]=\"{'width':'15%'}\" header=\"Status\" [filter]=\"true\" filterMatchMode=\"equals\">\n                    <ng-template pTemplate=\"filter\" let-col>\n                        <p-dropdown [options]=\"taskStatuses\" [style]=\"{'width':'100%'}\" (onChange)=\"dt.filter($event.value,col.field,col.filterMatchMode)\" styleClass=\"ui-column-filter\"></p-dropdown>\n                    </ng-template>\n                </p-column>\n                <p-column field=\"dateCreated\" [sortable]=\"true\" header=\"Date Created\" [filter]=\"true\" filterPlaceholder=\"Search\" filterMatchMode=\"contains\"></p-column>\n                <p-column field=\"dateStarted\" [sortable]=\"true\" header=\"Date Started\" [filter]=\"true\" filterPlaceholder=\"Search\" filterMatchMode=\"contains\"></p-column>\n                <p-column field=\"dateCompleted\" [sortable]=\"true\" header=\"Date Completed\" [filter]=\"true\" filterPlaceholder=\"Search\" filterMatchMode=\"contains\"></p-column>\n                <p-column field=\"id\" styleClass=\"col-header\" header=\"Action\">\n                    <ng-template let-templateTask=\"rowData\" pTemplate=\"body\">\n                        <!--<button type=\"button\" *ngIf=\"templateTask.status == 'Completed'\" pButton [routerLink]=\"['/createTemplate', templateTask.id]\" icon=\"fa-file-text-o\"></button>-->\n                        <!-- <a class=\"ui-pbutton-link\" href=\"{{reportXLS}}/{{templateTask.id}}\" *ngIf=\"templateTask.status == 'Completed'\"><i class=\"fa fa-download\" aria-hidden=\"true\"></i> .xls</a>\n                        <a class=\"ui-pbutton-link\" href=\"{{reportTxt}}/{{templateTask.id}}\" *ngIf=\"templateTask.status == 'Completed'\"><i class=\"fa fa-download\" aria-hidden=\"true\"></i> .txt</a>\n                        <a class=\"ui-pbutton-link\" href=\"{{reportTemplate}}/{{templateTask.id}}\" *ngIf=\"templateTask.status == 'Completed'\"><i class=\"fa fa-download\" aria-hidden=\"true\"></i> .template</a><br> -->\n                        <!-- <button type=\"button\" *ngIf=\"templateTask.status == 'Completed'\" pButton (click)=\"getXLSReport(templateTask)\" icon=\"fa fa-file-excel-o\"></button>-->\n                        <button type=\"button\" *ngIf=\"templateTask.status == 'Completed'\" pButton [routerLink]=\"['/reportTaskOutput', templateTask.id]\" icon=\"fa-eye\"></button>\n                        <button type=\"button\" *ngIf=\"templateTask.status == 'Completed'\" pButton (click)=\"deleteReportTask(templateTask)\" icon=\"fa fa-trash-o\"></button>\n\n\n                    </ng-template>\n                </p-column>\n                <p-column field=\"id\" styleClass=\"col-header\" header=\"Download\">\n                    <ng-template let-templateTask=\"rowData\" pTemplate=\"body\">\n                        <a class=\"ui-pbutton-link\" href=\"{{reportXLS}}/{{templateTask.id}}\" *ngIf=\"templateTask.status == 'Completed'\"><i class=\"fa fa-download\" aria-hidden=\"true\"></i> .xls</a>\n                        <a class=\"ui-pbutton-link\" href=\"{{reportTxt}}/{{templateTask.id}}\" *ngIf=\"templateTask.status == 'Completed'\"><i class=\"fa fa-download\" aria-hidden=\"true\"></i> .txt</a><br>\n                        <a class=\"ui-pbutton-link\" href=\"{{reportTemplate}}/{{templateTask.id}}\" *ngIf=\"templateTask.status == 'Completed'\"><i class=\"fa fa-download\" aria-hidden=\"true\"></i> .template</a><br>\n                    </ng-template>\n                </p-column>\n\n            </p-dataTable>\n\n</div>"

/***/ }),

/***/ "../../../../../src/app/component/all-report-task-status/all-report-task-status.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AllReportTaskStatusComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__service_report_task_service__ = __webpack_require__("../../../../../src/app/service/report-task.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__model_lookup__ = __webpack_require__("../../../../../src/app/model/lookup.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_platform_browser__ = __webpack_require__("../../../platform-browser/esm5/platform-browser.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};




var AllReportTaskStatusComponent = (function () {
    function AllReportTaskStatusComponent(reportTaskService, document) {
        this.reportTaskService = reportTaskService;
        this.document = document;
    }
    AllReportTaskStatusComponent.prototype.ngOnInit = function () {
        this.lookupNone = new __WEBPACK_IMPORTED_MODULE_2__model_lookup__["a" /* Lookup */]();
        this.lookupNone.label = "All";
        this.lookupNone.value = null;
        this.getReportTasks();
        this.getTaskStatuses();
        //this.detailedReportPath = 'http://' + this.document.location.hostname + ":" + this.document.location.port+ "/reportwriter/";
        this.detailedReportPath = "http://localhost:8080/reportwriter/";
        this.reportXLS = this.detailedReportPath + "getXLSReport";
        this.reportTxt = this.detailedReportPath + "getTxtReport";
        this.reportTemplate = this.detailedReportPath + "getTemplateReport";
    };
    AllReportTaskStatusComponent.prototype.getXLSReport = function (templateTask) {
        this.reportTaskService.getXLSReport(templateTask.id).
            subscribe();
    };
    AllReportTaskStatusComponent.prototype.getReportTasks = function () {
        var _this = this;
        this.reportTaskService.getReportTasks().
            subscribe(function (tasks) {
            _this.tasks = tasks;
            _this.totalRecordsCount = _this.tasks.length;
            var currentpageCount = 0 + 1;
            var currentPageRows = 0 + 5;
            if (currentPageRows > _this.tasks.length) {
                currentPageRows = _this.tasks.length;
            }
            _this.pageinationcount = 'Showing ' + currentpageCount + ' to ' + currentPageRows + ' of ' + _this.tasks.length;
        });
    };
    AllReportTaskStatusComponent.prototype.deleteReportTask = function (templateTask) {
        var _this = this;
        this.reportTaskService.deleteReportTask(templateTask.id).
            subscribe(function (task) { _this.task = task; console.log("Task id -" + task.id + " status - " + task.status); _this.getReportTasks(); });
    };
    AllReportTaskStatusComponent.prototype.getTaskStatuses = function () {
        var _this = this;
        this.reportTaskService.getTaskStatuses().subscribe(function (taskStatuses) {
            _this.taskStatuses = taskStatuses.filter(function (obj) {
                return obj.label !== 'Deleted';
            });
            _this.taskStatuses.push(_this.lookupNone);
        });
    };
    AllReportTaskStatusComponent.prototype.onPageChange = function (e) {
        var currentpageCount = e.first + 1;
        var currentPageRows = e.first + e.rows;
        if (currentPageRows > this.tasks.length) {
            currentPageRows = this.tasks.length;
        }
        this.pageinationcount = 'Showing ' + currentpageCount + ' to ' + currentPageRows + ' of ' + this.tasks.length;
    };
    AllReportTaskStatusComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-all-report-task-status',
            template: __webpack_require__("../../../../../src/app/component/all-report-task-status/all-report-task-status.component.html"),
            styles: [__webpack_require__("../../../../../src/app/component/all-report-task-status/all-report-task-status.component.css")],
        }),
        __param(1, Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Inject"])(__WEBPACK_IMPORTED_MODULE_3__angular_platform_browser__["DOCUMENT"])),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__service_report_task_service__["a" /* ReportTaskService */], Object])
    ], AllReportTaskStatusComponent);
    return AllReportTaskStatusComponent;
}());



/***/ }),

/***/ "../../../../../src/app/component/create-template/create-template.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".col-button {\n    vertical-align: \"center\"\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/component/create-template/create-template.component.html":
/***/ (function(module, exports) {

module.exports = "    <div class=\"container\">\n        <div class=\"page-header\">\n            <span> {{title}}</span>\n        </div>\n\n            <form class=\"form-horizontal\" #templateForm=\"ngForm\" (ngSubmit)=\"onSubmitTemplate('Template')\">\n\n                <div class=\"form-group\">\n                    <label for=\"name\" class=\"col-sm-2 control-label\">Name <span class=\"required-field\">*</span></label>\n                    <div class=\"col-sm-10\">\n                        <input class=\"form-control\" type=\"text\" value=\"Name\" [(ngModel)]=\"template.name\" id=\"name\" name=\"name\" required #name=\"ngModel\">\n                        <div [hidden]=\"name.valid || name.pristine\">\n                            <span class=\"form-error-message\">Name is required</span>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"form-group\">\n                    <label for=\"rootConceptCode\" class=\"col-sm-2 control-label\">Root Concept Code <span class=\"required-field\">*</span></label>\n                    <div class=\"col-sm-10\">\n                        <input class=\"form-control\" type=\"text\" value=\"Root Concept Code\" [(ngModel)]=\"template.rootConceptCode\" id=\"rootConceptCode\" name=\"rootConceptCode\" #rootConceptCode=\"ngModel\" required>\n                        <div [hidden]=\"rootConceptCode.valid || rootConceptCode.pristine\">\n                            <span class=\"form-error-message\">Root Concept Code is required</span>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"form-group\">\n                    <label for=\"type\" class=\"col-sm-2 control-label\">Type <span class=\"required-field\">*</span></label>\n                    <div class=\"col-sm-4\">\n\n                        <p-dropdown [style]=\"{'width':'100%'}\" [options]=\"types\" [(ngModel)]=\"template.type\" id=\"type\" name=\"type\" #type=\"ngModel\" placeholder=\"Select a type\" required></p-dropdown>\n                        <div [hidden]=\"type.valid || type.pristine\">\n                            <span class=\"form-error-message\">Type is required</span>\n                        </div>\n                    </div>\n\n                    <label for=\"association\" class=\"col-sm-2 control-label\">Association Name <span class=\"required-field\">*</span></label>\n                    <div class=\"col-sm-4\">\n\n                        <p-dropdown [style]=\"{'width':'100%'}\" [options]=\"associations\" [(ngModel)]=\"template.association\" id=\"association\" name=\"association\" placeholder=\"Select a value\" #association=\"ngModel\" required></p-dropdown>\n                        <div [hidden]=\"association.valid || association.pristine\">\n                            <span class=\"form-error-message\">Association Name is required</span>\n                        </div>\n                    </div>\n                </div>\n\n                <div class=\"form-group\">\n                    <label for=\"level\" class=\"col-sm-2 control-label\">Level <span class=\"required-field\">*</span></label>\n                    <div class=\"col-sm-4\">\n                        <input class=\"form-control\" type=\"number\" value=\"Level\" id=\"level\" name=\"level\" [(ngModel)]=\"template.level\" #level=\"ngModel\" required>\n                        <div [hidden]=\"level.valid || level.pristine\">\n                            <span class=\"form-error-message\">Level is required</span>\n                        </div>\n                    </div>\n\n                    <label for=\"sortColumn\" class=\"col-sm-2 control-label\">Sort Column <span class=\"required-field\">*</span></label>\n                    <div class=\"col-sm-4\">\n                        <input class=\"form-control\" type=\"number\" value=\"Sort Column\" [(ngModel)]=\"template.sortColumn\" id=\"sortColumn\" name=\"sortColumn\" required #sortColumn=\"ngModel\">\n                        <div [hidden]=\"sortColumn.valid || sortColumn.pristine\">\n                            <span class=\"form-error-message\">Sort Column is required</span>\n                        </div>\n                    </div>\n                </div>\n                <div class=\"form-group\">\n                    <label for=\"status\" class=\"col-sm-2 control-label\">Status <span class=\"required-field\">*</span></label>\n                    <div class=\"col-sm-4\">\n\n                        <p-dropdown [style]=\"{'width':'100%'}\" [options]=\"statuses\" placeholder=\"Select a status\" [(ngModel)]=\"template.status\" id=\"status\" #status=\"ngModel\" name=\"status\" required></p-dropdown>\n                        <div [hidden]=\"status.valid || status.pristine\">\n                            <span class=\"form-error-message\">Status is required</span>\n                        </div>\n                    </div>\n                </div>\n\n                <button type=\"button\" (click)=\"showAddRow()\" pButton icon=\"fa-external-link-square\" label=\"Add Row\"></button>\n                <br>\n                <br>\n\n                <p-dataTable [value]=\"templateRows\" [editable]=\"true\" expandableRows=\"true\">\n                    <p-column styleClass=\"col-button\" [style]=\"{'width':'50px'}\">\n                        <ng-template pTemplate=\"header\">\n                            <button type=\"button\" pButton icon=\"fa fa-trash\"></button>\n                        </ng-template>\n                        <ng-template let-templateRow=\"rowData\" pTemplate=\"body\">\n                            <button type=\"button\" pButton (click)=\"deleteTemplateRow(templateRow)\" icon=\"fa fa-trash\"></button>\n                        </ng-template>\n                    </p-column>\n                    <p-column field=\"label\" header=\"Label\" [editable]=\"true\"></p-column>\n                    <p-column field=\"display\" header=\"Display\" [editable]=\"true\">\n                        <ng-template let-col let-templateRow=\"rowData\" pTemplate=\"editor\">\n                            <p-dropdown [(ngModel)]=\"templateRow[col.field]\" name=\"display\" [ngModelOptions]=\"{standalone: true}\" [options]=\"displays\" [autoWidth]=\"false\" [style]=\"{'width':'95%'}\" required=\"true\" appendTo=\"body\"></p-dropdown>\n                        </ng-template>\n                    </p-column>\n                    <p-column field=\"propertyType\" header=\"Property Type\" [editable]=\"true\" [style]=\"{'width':'170px'}\">\n                        <ng-template let-col let-templateRow=\"rowData\" pTemplate=\"editor\">\n                            <p-dropdown [filter]=\"true\" [(ngModel)]=\"templateRow[col.field]\" name=\"propertyType\" [ngModelOptions]=\"{standalone: true}\" [options]=\"propertyTypes\" [autoWidth]=\"false\" [style]=\"{'width':'95%'}\" required=\"true\" appendTo=\"body\"></p-dropdown>\n                        </ng-template>\n                    </p-column>\n                    <p-column field=\"property\" header=\"Property Target\" [editable]=\"true\" [style]=\"{'width':'270px'}\">\n                        <ng-template let-col let-templateRow=\"rowData\" pTemplate=\"editor\">\n                            <p-dropdown [filter]=\"true\" [(ngModel)]=\"templateRow[col.field]\" name=\"propertyTarget\" [ngModelOptions]=\"{standalone: true}\" [options]=\"propertyTargets\" [autoWidth]=\"false\" [style]=\"{'width':'95%'}\" required=\"true\" appendTo=\"body\"></p-dropdown>\n                        </ng-template>\n                    </p-column>\n\n                    <p-column field=\"source\" header=\"Source\" [editable]=\"true\" [style]=\"{'width':'100px'}\">\n                        <ng-template let-col let-templateRow=\"rowData\" pTemplate=\"editor\">\n                            <p-dropdown [filter]=\"true\" [(ngModel)]=\"templateRow[col.field]\" name=\"source\" [ngModelOptions]=\"{standalone: true}\" [options]=\"sources\" [autoWidth]=\"false\" [style]=\"{'width':'95%'}\" appendTo=\"body\"></p-dropdown>\n                        </ng-template>\n                    </p-column>\n\n                    <p-column field=\"group\" header=\"Group\" [editable]=\"true\" [style]=\"{'width':'60px'}\">\n                        <ng-template let-col let-templateRow=\"rowData\" pTemplate=\"editor\">\n                            <p-dropdown [filter]=\"true\" [filter]=\"true\" [(ngModel)]=\"templateRow[col.field]\" [options]=\"groups\" name=\"group\" [autoWidth]=\"false\" [ngModelOptions]=\"{standalone: true}\" [style]=\"{'width':'95%'}\" appendTo=\"body\"></p-dropdown>\n                        </ng-template>\n                    </p-column>\n\n                    <p-column field=\"subsource\" header=\"Subsource\" [editable]=\"true\">\n                        <ng-template let-col let-templateRow=\"rowData\" pTemplate=\"editor\">\n                            <p-dropdown [filter]=\"true\" [(ngModel)]=\"templateRow[col.field]\" name=\"subsource\" [ngModelOptions]=\"{standalone: true}\" [options]=\"subsources\" [autoWidth]=\"false\" [style]=\"{'width':'95%'}\" appendTo=\"body\"></p-dropdown>\n                        </ng-template>\n                    </p-column>\n                </p-dataTable>\n                <br>\n                <br>\n                <div class=\"form-group\">\n\n                    <div class=\"col-sm-10\">\n                        <button type=\"submit\" pButton [disabled]=\"!templateForm.form.valid\" label=\"{{templateButtonName}}\"></button>\n                        <button type=\"button\" pButton label=\"Clear Form\" *ngIf=\"!templateCreated\" (click)=\"clearTemplateRows();templateForm.reset()\"></button>\n                        <button type=\"button\" pButton label=\"Cancel\" [routerLink]=\"['/home']\"></button>\n                    </div>\n\n\n\n                </div>\n                <div class=\"form-group\" *ngIf=\"templateCreated\">\n                    <p>\n                        <ngb-alert [dismissible]=\"false\" [type]=\"'success'\" *ngIf=\"!staticAlertClosed\" (close)=\"staticAlertClosed = true\">\n                            {{displayMessage}}\n                        </ngb-alert>\n                    </p>\n                </div>\n\n            </form>\n</div>\n\n\n<p-dialog header=\"Add Template Row\" [(visible)]=\"displayAddRow\" [width]=\"850\">\n    <div class=\"\">\n        <form class=\"form-horizontal\" #templateRowForm=\"ngForm\" (ngSubmit)=\"onSubmitAddRow();templateRowForm.reset()\">\n            \n            <div class=\"form-group\">\n                <label for=\"label\" class=\"col-sm-3 control-label\">Label <span class=\"required-field\">*</span></label>\n                <div class=\"col-sm-8\">\n                    <input class=\"form-control\" type=\"text\" value=\"Label\" [(ngModel)]=\"templateRowUI.label\" id=\"label\" name=\"label\" required #label=\"ngModel\">\n                    <div [hidden]=\"label.valid || label.pristine\">\n                        <span class=\"form-error-message\">Label is required</span>\n                    </div>\n                </div>\n            </div>\n\n            <div class=\"form-group\">\n                <label for=\"type\" class=\"col-sm-3 control-label\">Display <span class=\"required-field\">*</span></label>\n                <div class=\"col-sm-8\">\n\n                    <p-dropdown appendTo=\"body\" [style]=\"{'width':'250px'}\" [options]=\"displays\" [(ngModel)]=\"templateRowUI.display\" id=\"display\" name=\"display\" #display=\"ngModel\" placeholder=\"Select a display\" required></p-dropdown>\n                    <div [hidden]=\"display.valid || display.pristine\">\n                        <span class=\"form-error-message\">Display is required</span>\n                    </div>\n                </div>\n            </div>\n            <div class=\"form-group\">\n                <label for=\"type\" class=\"col-sm-3 control-label\">Property Type <span class=\"required-field\">*</span></label>\n                <div class=\"col-sm-8\">\n\n                    <p-dropdown [filter]=\"true\" appendTo=\"body\" [style]=\"{'width':'250px'}\" [options]=\"propertyTypes\" [(ngModel)]=\"templateRowUI.propertyType\" id=\"propertyType\" name=\"propertyType\" #propertyType=\"ngModel\" placeholder=\"Select a Property Type\" required></p-dropdown>\n                    <div [hidden]=\"propertyType.valid || propertyType.pristine\">\n                        <span class=\"form-error-message\">Property Type is required</span>\n                    </div>\n                </div>\n            </div>\n            <div class=\"form-group\">\n                <label for=\"type\" class=\"col-sm-3 control-label\">Property Target <span class=\"required-field\">*</span></label>\n                <div class=\"col-sm-8\">\n\n                    <p-dropdown [filter]=\"true\" appendTo=\"body\" [style]=\"{'width':'250px'}\" [options]=\"propertyTargets\" [(ngModel)]=\"templateRowUI.property\" id=\"propertyTarget\" name=\"propertyTarget\" #propertyTarget=\"ngModel\" placeholder=\"Select a Property Target\" required></p-dropdown>\n                    <div [hidden]=\"propertyTarget.valid || propertyTarget.pristine\">\n                        <span class=\"form-error-message\">Property Target is required</span>\n                    </div>\n                </div>\n            </div>\n            <div class=\"form-group\">\n                <label for=\"type\" class=\"col-sm-3 control-label\">Source:</label>\n                <div class=\"col-sm-8\">\n\n                    <p-dropdown [filter]=\"true\" appendTo=\"body\" [style]=\"{'width':'250px'}\" [options]=\"sources\" [(ngModel)]=\"templateRowUI.source\" id=\"source\" name=\"source\" placeholder=\"Select a Source\"></p-dropdown>\n\n                </div>\n            </div>\n            <div class=\"form-group\">\n                <label for=\"type\" class=\"col-sm-3 control-label\">Group:</label>\n                <div class=\"col-sm-8\">\n\n                    <p-dropdown [filter]=\"true\" appendTo=\"body\" [style]=\"{'width':'250px'}\" [options]=\"groups\" [(ngModel)]=\"templateRowUI.group\" id=\"group\" name=\"group\" placeholder=\"Select a Group\"></p-dropdown>\n\n                </div>\n            </div>\n            <div class=\"form-group\">\n                <label for=\"type\" class=\"col-sm-3 control-label\">Subsource:</label>\n                <div class=\"col-sm-8\">\n\n                    <p-dropdown [filter]=\"true\" appendTo=\"body\" [style]=\"{'width':'250px'}\" [options]=\"subsources\" [(ngModel)]=\"templateRowUI.subsource\" id=\"subsource\" name=\"subsource\" placeholder=\"Select a Subsource\"></p-dropdown>\n\n                </div>\n            </div>\n            <!--  <div class=\"form-group\">\n                <label for=\"type\" class=\"col-sm-3 control-label\">Source:</label>\n                <div class=\"col-sm-8\">\n\n                    <p-dropdown appendTo=\"body\" [style]=\"{'width':'250px'}\" [options]=\"sources\" [(ngModel)]=\"templateRowUI.source\" id=\"source\" name=\"source\" #source=\"ngModel\" placeholder=\"Select a Source\" required></p-dropdown>\n                    <div [hidden]=\"source.valid || source.pristine\" class=\"alert form-error-message\">\n                        Source is required\n                    </div>\n                </div>\n            </div>\n            <div class=\"form-group\">\n                <label for=\"type\" class=\"col-sm-3 control-label\">Group:</label>\n                <div class=\"col-sm-8\">\n\n                    <p-dropdown appendTo=\"body\" [style]=\"{'width':'250px'}\" [options]=\"groups\" [(ngModel)]=\"templateRowUI.group\" id=\"group\" name=\"group\" #group=\"ngModel\" placeholder=\"Select a Group\" required></p-dropdown>\n                    <div [hidden]=\"group.valid || group.pristine\" class=\"alert form-error-message\">\n                        Group is required\n                    </div>\n                </div>\n            </div>\n            <div class=\"form-group\">\n                <label for=\"type\" class=\"col-sm-3 control-label\">Subsource:</label>\n                <div class=\"col-sm-8\">\n\n                    <p-dropdown appendTo=\"body\" [style]=\"{'width':'250px'}\" [options]=\"subsources\" [(ngModel)]=\"templateRowUI.subsource\" id=\"subsource\" name=\"subsource\" #subsource=\"ngModel\" placeholder=\"Select a Subsource\" required></p-dropdown>\n                    <div [hidden]=\"subsource.valid || subsource.pristine\" class=\"alert form-error-message\">\n                        Subsource is required\n                    </div>\n                </div>\n            </div>-->\n            <div class=\"form-group\">\n                <label for=\"submit\" class=\"col-sm-3 control-label\"></label>\n                <div class=\"col-sm-8\">\n                    <button type=\"submit\" pButton [disabled]=\"!templateRowForm.form.valid\" label=\"Save\"></button>\n                    <button type=\"button\" pButton (click)=\"cancelAddRow();templateRowForm.reset()\" label=\"Cancel\"></button>\n                </div>\n            </div>\n        </form>\n    </div>\n</p-dialog>"

/***/ }),

/***/ "../../../../../src/app/component/create-template/create-template.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CreateTemplateComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__service_lookupvalues_templaterow_service__ = __webpack_require__("../../../../../src/app/service/lookupvalues-templaterow.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__model_template_row__ = __webpack_require__("../../../../../src/app/model/template-row.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__model_template__ = __webpack_require__("../../../../../src/app/model/template.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__service_lookupvalues_template_service__ = __webpack_require__("../../../../../src/app/service/lookupvalues-template.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__service_report_template_service__ = __webpack_require__("../../../../../src/app/service/report-template.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__model_lookup__ = __webpack_require__("../../../../../src/app/model/lookup.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__ng_bootstrap_ng_bootstrap__ = __webpack_require__("../../../../@ng-bootstrap/ng-bootstrap/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};









var CreateTemplateComponent = (function () {
    function CreateTemplateComponent(lookupvaluesTemplateService, modalService, lookupvaluesTemplaterowService, reportTemplateService, route) {
        this.lookupvaluesTemplateService = lookupvaluesTemplateService;
        this.modalService = modalService;
        this.lookupvaluesTemplaterowService = lookupvaluesTemplaterowService;
        this.reportTemplateService = reportTemplateService;
        this.route = route;
        this.template = new __WEBPACK_IMPORTED_MODULE_2__model_template__["a" /* Template */]();
        this.templateRowUI = new __WEBPACK_IMPORTED_MODULE_1__model_template_row__["a" /* TemplateRow */]();
        this.templateRowAdd = new __WEBPACK_IMPORTED_MODULE_1__model_template_row__["a" /* TemplateRow */]();
        this.staticAlertClosed = false;
        this.displayAddRow = false;
        this.templateId = route.snapshot.params['id'];
        console.log("templateId -" + this.templateId);
        /* if (this.templateId != null || this.templateId!= undefined || this.templateId != 0){
             this.reportTemplateService.getReportTemplate(this.templateId).subscribe(template => {
             this.template = template;
             this.templateRows = template.columns;
             console.log("after getReportTemplate ----- " + JSON.stringify(this.template));
             console.log("after getReportTemplate ----- " + JSON.stringify(this.templateRows));
             this.templateCreated = true;
             this.templateButtonName = "Save Changes";
            
            
           });
         }*/
    }
    CreateTemplateComponent.prototype.showAddRow = function () {
        this.displayAddRow = true;
    };
    CreateTemplateComponent.prototype.deleteTemplateRow = function (templateRow) {
        console.log("In deleteTemplateRow - templateRow" + JSON.stringify(templateRow));
        console.log("In deleteTemplateRow - templateRows" + JSON.stringify(this.templateRows));
        this.templateRows = this.templateRows.filter(function (templateRowIn) { return templateRowIn.columnNumber != templateRow.columnNumber; });
        console.log("In deleteTemplateRow - templateRows" + JSON.stringify(this.templateRows));
    };
    CreateTemplateComponent.prototype.onSubmitAddRow = function () {
        this.displayAddRow = false;
        console.log("templateRowUI - " + JSON.stringify(this.templateRowUI));
        this.templateRowAdd = new __WEBPACK_IMPORTED_MODULE_1__model_template_row__["a" /* TemplateRow */]();
        if (this.templateRows == undefined || this.templateRows == null || this.templateRows.length == 0) {
            this.templateRowAdd.columnNumber = 1;
        }
        else {
            this.templateRowAdd.columnNumber = this.templateRows[this.templateRows.length - 1].columnNumber + 1;
        }
        this.templateRowAdd.label = this.templateRowUI.label;
        this.templateRowAdd.display = this.templateRowUI.display;
        this.templateRowAdd.propertyType = this.templateRowUI.propertyType;
        this.templateRowAdd.property = this.templateRowUI.property;
        if (this.templateRowUI.source == undefined || this.templateRowUI.source == null) {
            this.templateRowAdd.source = null;
        }
        else {
            this.templateRowAdd.source = this.templateRowUI.source;
        }
        if (this.templateRowUI.group == undefined || this.templateRowUI.group == null) {
            this.templateRowAdd.group = null;
        }
        else {
            this.templateRowAdd.group = this.templateRowUI.group;
        }
        if (this.templateRowUI.subsource == undefined || this.templateRowUI.subsource == null) {
            this.templateRowAdd.subsource = null;
        }
        else {
            this.templateRowAdd.subsource = this.templateRowUI.subsource;
        }
        console.log("templateRowAdd - " + JSON.stringify(this.templateRowAdd));
        if (this.templateRows == undefined || this.templateRows == null || this.templateRows.length == 0) {
            this.templateRows = [this.templateRowAdd];
        }
        else {
            this.templateRows = this.templateRows.concat([this.templateRowAdd]);
        }
        console.log("templateRows" + JSON.stringify(this.templateRows));
    };
    CreateTemplateComponent.prototype.cancelAddRow = function () {
        this.displayAddRow = false;
    };
    CreateTemplateComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.lookupNone = new __WEBPACK_IMPORTED_MODULE_6__model_lookup__["a" /* Lookup */]();
        this.lookupNone.label = "None";
        this.lookupNone.value = "None";
        this.getTypes();
        this.getAssociations();
        this.getStatuses();
        this.getDisplays();
        this.getPropertyTypes();
        this.getPropertyTargets();
        this.getSources();
        this.getGroups();
        this.getSubsources();
        //this.sources.push(this.lookupNone);
        //this.groups.push(this.lookupNone);
        //this.subsources.push(this.lookupNone);
        if (this.templateId != null && this.templateId != undefined && this.templateId != 0) {
            this.templateCreated = true;
            this.templateButtonName = "Save Changes";
            this.title = "Edit Template";
            this.staticAlertClosed = true;
            this.reportTemplateService.getReportTemplate(this.templateId).subscribe(function (template) {
                _this.template = template;
                _this.templateRows = template.columns;
                console.log("after getReportTemplate ----- " + JSON.stringify(_this.template));
                console.log("after getReportTemplate ----- " + JSON.stringify(_this.templateRows));
            });
        }
        else {
            this.title = "Create Template";
            this.templateCreated = false;
            this.templateButtonName = "Create Template";
            this.template = new __WEBPACK_IMPORTED_MODULE_2__model_template__["a" /* Template */]();
            this.templateRows = [];
        }
        this.templateRowcols = [
            { field: 'label', header: 'Label', editable: true },
            { field: 'display', header: 'Display', editable: true },
            { field: 'propertyType', header: 'Property Type', editable: true },
            { field: 'property', header: 'Property', editable: true },
            { field: 'source', header: 'Source', editable: true },
            { field: 'group', header: 'Group', editable: true },
            { field: 'subsource', header: 'Subsource', editable: true }
        ];
    };
    CreateTemplateComponent.prototype.getTypes = function () {
        var _this = this;
        this.lookupvaluesTemplateService.getTypes().subscribe(function (types) { return _this.types = types; });
    };
    CreateTemplateComponent.prototype.getAssociations = function () {
        var _this = this;
        this.lookupvaluesTemplateService.getAssociations().subscribe(function (associations) { return _this.associations = associations; });
    };
    CreateTemplateComponent.prototype.getStatuses = function () {
        var _this = this;
        this.lookupvaluesTemplateService.getStatuses().subscribe(function (statuses) { return _this.statuses = statuses; });
    };
    CreateTemplateComponent.prototype.getDisplays = function () {
        var _this = this;
        this.lookupvaluesTemplaterowService.getDisplays().subscribe(function (displays) { return _this.displays = displays; });
    };
    CreateTemplateComponent.prototype.getPropertyTypes = function () {
        var _this = this;
        this.lookupvaluesTemplaterowService.getPropertyTypes().subscribe(function (propertyTypes) { _this.propertyTypes = propertyTypes; });
    };
    CreateTemplateComponent.prototype.getPropertyTargets = function () {
        var _this = this;
        this.lookupvaluesTemplaterowService.getPropertyTargets().subscribe(function (propertyTargets) { _this.propertyTargets = propertyTargets; });
    };
    CreateTemplateComponent.prototype.getSources = function () {
        var _this = this;
        this.lookupvaluesTemplaterowService.getSources().subscribe(function (sources) { _this.sources = sources; _this.sources.push(_this.lookupNone); });
    };
    CreateTemplateComponent.prototype.getGroups = function () {
        var _this = this;
        this.lookupvaluesTemplaterowService.getGroups().subscribe(function (groups) { _this.groups = groups; _this.groups.push(_this.lookupNone); });
    };
    CreateTemplateComponent.prototype.getSubsources = function () {
        var _this = this;
        this.lookupvaluesTemplaterowService.getSubsources().subscribe(function (subsources) { _this.subsources = subsources; _this.subsources.push(_this.lookupNone); });
    };
    CreateTemplateComponent.prototype.clearTemplateRows = function () {
        this.templateRows = [];
    };
    CreateTemplateComponent.prototype.onSubmitTemplate = function (value) {
        var _this = this;
        console.log("** - *** " + value);
        var count = 1;
        for (var _i = 0, _a = this.templateRows; _i < _a.length; _i++) {
            var templateRow = _a[_i];
            templateRow.columnNumber = count;
            templateRow.id = null;
            if (templateRow.source == 'None')
                templateRow.source = null;
            if (templateRow.group == 'None')
                templateRow.group = null;
            if (templateRow.subsource == 'None')
                templateRow.subsource = null;
            count++;
        }
        this.template.columns = this.templateRows;
        console.log("before onSubmitTemplate ----- " + JSON.stringify(this.template));
        if (!this.templateCreated) {
            this.reportTemplateService.addReportTemplate(this.template).subscribe(function (template) {
                console.log("Returned from addReportTemplate " + JSON.stringify(template));
                _this.template = template;
                _this.templateRows = template.columns;
                console.log("after onSubmitTemplate addReportTemplate----- " + JSON.stringify(_this.template));
                console.log("after onSubmitTemplate addReportTemplate----- " + JSON.stringify(_this.templateRows));
                _this.templateCreated = true;
                _this.templateButtonName = "Save Changes";
                _this.displayMessage = " Template has been created. Click on Cancel to exit. Click on Reset to create a new Template";
                setTimeout(function () { _this.staticAlertClosed = true; console.log("setting staticAlertClosed to true"); }, 8000);
            });
        }
        else {
            this.reportTemplateService.saveReportTemplate(this.template).subscribe(function (template) {
                console.log("Returned from addReportTemplate " + JSON.stringify(template));
                _this.template = template;
                _this.templateRows = template.columns;
                console.log("after onSubmitTemplate saveReportTemplate----- " + JSON.stringify(_this.template));
                console.log("after onSubmitTemplate saveReportTemplate ----- " + JSON.stringify(_this.templateRows));
                _this.staticAlertClosed = false;
                _this.displayMessage = " The changes made to the Template have been saved. You can continue to edit the template or click on Cancel to exit.";
                setTimeout(function () { _this.staticAlertClosed = true; console.log("setting staticAlertClosed to true"); }, 8000);
            });
        }
    };
    CreateTemplateComponent.prototype.open = function (content) {
        var _this = this;
        this.modalService.open(content).result.then(function (result) {
            _this.closeResult = "Closed with: " + result;
        }, function (reason) {
            _this.templateRowUI = new __WEBPACK_IMPORTED_MODULE_1__model_template_row__["a" /* TemplateRow */]();
            _this.closeResult = "Dismissed " + _this.getDismissReason(reason);
        });
    };
    CreateTemplateComponent.prototype.getDismissReason = function (reason) {
        if (reason === __WEBPACK_IMPORTED_MODULE_7__ng_bootstrap_ng_bootstrap__["a" /* ModalDismissReasons */].ESC) {
            return 'by pressing ESC';
        }
        else if (reason === __WEBPACK_IMPORTED_MODULE_7__ng_bootstrap_ng_bootstrap__["a" /* ModalDismissReasons */].BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        }
        else {
            return "with: " + reason;
        }
    };
    CreateTemplateComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_5__angular_core__["Component"])({
            selector: 'app-create-template',
            template: __webpack_require__("../../../../../src/app/component/create-template/create-template.component.html"),
            styles: [__webpack_require__("../../../../../src/app/component/create-template/create-template.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_3__service_lookupvalues_template_service__["a" /* LookupvaluesTemplateService */],
            __WEBPACK_IMPORTED_MODULE_7__ng_bootstrap_ng_bootstrap__["b" /* NgbModal */],
            __WEBPACK_IMPORTED_MODULE_0__service_lookupvalues_templaterow_service__["a" /* LookupvaluesTemplaterowService */],
            __WEBPACK_IMPORTED_MODULE_4__service_report_template_service__["a" /* ReportTemplateService */], __WEBPACK_IMPORTED_MODULE_8__angular_router__["ActivatedRoute"]])
    ], CreateTemplateComponent);
    return CreateTemplateComponent;
}());



/***/ }),

/***/ "../../../../../src/app/component/footer/footer.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".row-footer {\n    padding-top: 10px;\n    border-top: solid 1px #e2e2e2;\n    color: #999;\n    font-size: 0.9em;\n    padding-left: 20px;\n    margin-top: 50px;\n}\n\n.row-footer a {\n    color: #999;\n}\n\n.row-footer .block-header {\n    padding-bottom: 8px;\n}\n\n.row-footer .block-body {\n    padding-left: 20px;\n}\n\n.socialmedia-block-inner ul {\n    padding-left: 5px;\n    list-style-type: none;\n}\n\n.socialmedia-link {\n    height: 22px;\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/component/footer/footer.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"row row-footer\">\n    <div class=\"col-md-5\">\n        <div class=\"block-header\">Sponsored by:</div>\n        <ul>\n            <li><a href=\"https://www.niaid.nih.gov/\" target=\"_blank\">National Institute of Allergy and Infectious Diseases (NIAID)</a></li>\n            <li><a href=\"http://www.nih.gov/\" target=\"_blank\">National Institutes of Health (NIH)</a></li>\n            <li><a href=\"http://www.hhs.gov/\" target=\"_blank\">Health and Human Services (HHS)</a></li>\n        </ul>\n    </div>\n    <div class=\"col-md-5\">\n        <div class=\"block-header\">\n            <a href=\"mailto:BISC_Helpdesk@niaid.nih.gov\" >Contact Us</a> | \n            <a href=\"http://www.immport.org/immport-open/public/home/privacyPolicy\"\n                target=\"_blank\">Privacy Policy</a> | \n            <a href=\"http://www.immport.org/immport-open/public/home/disclaimer\"\n                target=\"_blank\">Disclaimer</a> | \n            <a href=\"http://www.immport.org/immport-open/public/home/accessibility\"\n                target=\"_blank\">Accessibility</a>\n        </div>\n        <div class=\"block-header\">\n            Recommended Browsers: Chrome, Firefox, Safari v7+, Internet Explorer v11+\n        </div>\n    </div>\n    <div class=\"col-md-2\">\n           <div id=\"socialmedia-block\" class=\"homePageText\">\n            <div class=\"title\" style=\"margin-bottom:7px;\">Connect with Us</div>\n            <div class=\"socialmedia-block-inner\">\n                <ul>\n                    <script>(function(d, s, id) {\n                      var js, fjs = d.getElementsByTagName(s)[0];\n                      if (d.getElementById(id)) return;\n                      js = d.createElement(s); js.id = id;\n                      js.src = \"https://connect.facebook.net/en_US/all.js#xfbml=1\";\n                      fjs.parentNode.insertBefore(js, fjs);\n                    }(document, 'script', 'facebook-jssdk'));\n                    </script>\n                    <li class=\"fb-like\" data-href=\"https://www.facebook.com/Immport\" \n                        style=\"padding-bottom:3px;\"\n                        data-send=\"false\" data-layout=\"button_count\" data-width=\"650\" \n                        data-show-faces=\"false\" data-font=\"lucida grande\"></li>\n                    <li class=\"socialmedia-link\">\n                        <img src=\"assets/images/footer/twitter-icon.png\" />\n                        <a class=\"twitter\" href=\"https://twitter.com/#!/ImmPortDB\" style=\"text-decoration: none\" target=\"_blank\">\n                            <span>Twitter</span>\n                        </a>\n                    </li>\n                    <li class=\"socialmedia-link\">\n                        <img src=\"assets/images/footer/rss-icon.png\" />\n                        <a href=\"http://www.immport.org/immport-open/resources/rss/newsfeed.xml\" \n                            style=\"text-decoration: none\" target=\"_blank\">\n                            <span>RSS Feed</span>\n                        </a>\n                    </li>\n                    <li class=\"socialmedia-link\">\n                        <img src=\"assets/images/footer/google-groups-icon.png\"/>\n                        <a href=\" https://groups.google.com/d/forum/immport\" style=\"text-decoration: none\" target=\"_blank\">\n                            <span>Google Group</span>\n                        </a>\n                    </li>\n                </ul>\n            </div>\n        </div>\n    </div>\n</div>"

/***/ }),

/***/ "../../../../../src/app/component/footer/footer.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return FooterComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var FooterComponent = (function () {
    function FooterComponent() {
    }
    FooterComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'nci-footer',
            template: __webpack_require__("../../../../../src/app/component/footer/footer.component.html"),
            styles: [__webpack_require__("../../../../../src/app/component/footer/footer.component.css")]
        })
    ], FooterComponent);
    return FooterComponent;
}());



/***/ }),

/***/ "../../../../../src/app/component/header/header.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "  .navbar {\n    min-height: 32px;\n  }\n  \n  .navbar-nav .main-site-logo {\n    width: 24px;\n    border-radius: 25%;\n  }\n  \n  .navbar-inverse .navbar-nav > li > a {\n    color: #ddd;\n  }\n  \n  .navbar-nav > li.menu-immport-org > a {\n    padding-top: 4px;\n    padding-bottom: 4px;\n  }\n  \n  .navbar-nav > li > a {\n    padding-top: 6px;\n    padding-bottom: 4px;\n  }\n  \n  .navbar-site {\n    margin-top: 24px;\n    padding-top: 5px;\n    padding-bottom: 5px;\n  }\n  \n  .navbar-site .nav.navbar-left > li > a {\n    padding-left: 0px;\n    padding-right: 0px;\n  }\n  \n  .navbar-site .indi-site-logo {\n    height: 48px;\n    padding-right: 20px;\n  }\n  \n  .navbar-site .indi-site-label {\n    display: inline-block;\n    font-size: 1.8em;\n  }\n  \n  .navbar-site .navbar-right {\n    margin-top: 20px;\n  }\n  \n  .menu-dropdown-logo {\n    width: 24px;\n    border: solid 2px #FFEB99;\n  }\n  \n  .navbar-inverse .navbar-nav > li > a {\n    color: #fff !important;\n    font-size: 14px;\n  }\n  \n  .navbar-inverse .navbar-nav > li > a:hover {\n    color: #fff ;\n    background-color: #000000;\n    border-radius: 10px;\n  }\n  \n  a.dropdown-toggle {\n    border-radius: 10px;\n  }\n  \n  #navbar-target-menu-collapse ul.navbar-right {\n    margin-right: 5px;\n  }\n  \n  #navbar-target-menu-collapse-applicationHeader ul.navbar-right {\n    margin-right: 5px;\n  }\n  \n  .dropdown-submenu {\n    position: relative;\n  }\n  \n  .dropdown-submenu>.dropdown-menu {\n    top: 0;\n    left: 100%;\n    margin-top: -6px;\n    margin-left: -1px;\n    border-radius: 0 6px 6px 6px;\n  }\n  \n  .dropdown-submenu:hover>.dropdown-menu {\n    display: block;\n  }\n  \n  .dropdown-submenu>a:after {\n    display: block;\n    content: \" \";\n    float: right;\n    width: 0;\n    height: 0;\n    border-color: transparent;\n    border-style: solid;\n    border-width: 5px 0 5px 5px;\n    border-left-color: #ccc;\n    margin-top: 5px;\n    margin-right: -10px;\n  }\n  \n  .dropdown-submenu:hover>a:after {\n    border-left-color: #fff;\n  }\n  \n  .dropdown-submenu.pull-lenavbarft {\n    float: none;\n  }\n  \n  .dropdown-submenu.pull-left>.dropdown-menu {\n    left: -100%;\n    margin-left: 10px;\n    border-radius: 6px 0 6px 6px;\n  }\n  \n  .title-menu {\n    display: inline-block; \n    padding-top: 2px;\n  }", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/component/header/header.component.html":
/***/ (function(module, exports) {

module.exports = "<nav class=\"navbar navbar-inverse navbar-fixed-top\">\n    <div class=\"container-fluid ecoSystemHeader\">\n  \n      <div class=\"navbar-header\">\n          <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-target-menu-collapse\">\n              <span class=\"sr-only\">Toggle navigation</span>\n              <span class=\"icon-bar\"></span>\n              <span class=\"icon-bar\"></span>\n              <span class=\"icon-bar\"></span>\n          </button>\n      </div>\n  \n      <div class=\"collapse navbar-collapse navbar-site-main\" id=\"navbar-target-menu-collapse\">\n          <ul class=\"nav navbar-nav navbar-left\">\n              <li routerLinkActive=\"active\" class=\"menu-immport-org\">\n                  <a href=\"#\" routerLink=\"/home\">\n                      <img class=\"main-site-logo\" src=\"assets/images/header/immport-private-data-icon.png\"/>\n                      <span class=\"title-menu\">NCI Report Writer</span>\n                      <span class=\"sr-only\">(current)</span>\n                  </a>\n              </li>\n          </ul>\n           <ul class=\"nav navbar-nav navbar-left\">\n              <li class=\"dropdown\">\n                  <a href=\"javascript:void(0);\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\">Template<span class=\"caret\"></span></a>\n                  <ul class=\"dropdown-menu\" role=\"menu\">\n                      <li><a href=\"#\" routerLink=\"/createTemplate/0\">Create Template</a></li>\n                      <li><a href=\"#\" routerLink=\"/reportTemplate\">View All Templates</a></li>\n                  </ul>\n              </li>\n          </ul>\n          <ul class=\"nav navbar-nav\">\n              <li><a routerLink=\"/reportTask\">All Report Tasks</a></li>\n          </ul>\n      </div>\n    </div>\n</nav>"

/***/ }),

/***/ "../../../../../src/app/component/header/header.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return HeaderComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var HeaderComponent = (function () {
    function HeaderComponent() {
    }
    HeaderComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'nci-header',
            template: __webpack_require__("../../../../../src/app/component/header/header.component.html"),
            styles: [__webpack_require__("../../../../../src/app/component/header/header.component.css")]
        })
    ], HeaderComponent);
    return HeaderComponent;
}());



/***/ }),

/***/ "../../../../../src/app/component/loader/loader.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".loader-hidden {\n    visibility: hidden;\n}\n.loader-overlay {\n    position: absolute;\n    width:100%;\n    top:40%;\n    left:45%;\n    opacity: 1;\n    z-index: 500000;\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/component/loader/loader.component.html":
/***/ (function(module, exports) {

module.exports = "<div [class.loader-hidden]=\"!show\">\n    <div class=\"loader-overlay\">\n        <div>\n            <!-- <p-progressBar *ngIf=\"show\" mode=\"indeterminate\" [style]=\"{'height': '6px'}\"></p-progressBar> -->\n            <p-progressSpinner *ngIf=\"show\" [style]=\"{width: '50px', height: '50px'}\" strokeWidth=\"4\" fill=\"#EEEEEE\" animationDuration=\".5s\"></p-progressSpinner>\n        </div>\n    </div>\n</div>    \n"

/***/ }),

/***/ "../../../../../src/app/component/loader/loader.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LoaderComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__service_loader_service__ = __webpack_require__("../../../../../src/app/service/loader.service.ts");
// import { Component, OnInit, OnDestroy } from '@angular/core';
// import { Subscription } from 'rxjs/Subscription';
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
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


var LoaderComponent = (function () {
    function LoaderComponent(loaderService) {
        this.loaderService = loaderService;
    }
    LoaderComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.loaderService.getLoaderSubject().subscribe(function (show) { return _this.show = show; });
    };
    LoaderComponent.prototype.ngOnDestroy = function () {
        this.subscription.unsubscribe();
    };
    LoaderComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'angular-loader',
            template: __webpack_require__("../../../../../src/app/component/loader/loader.component.html"),
            styles: [__webpack_require__("../../../../../src/app/component/loader/loader.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__service_loader_service__["a" /* LoaderService */]])
    ], LoaderComponent);
    return LoaderComponent;
}());



/***/ }),

/***/ "../../../../../src/app/component/report-task-output/report-task-output.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/component/report-task-output/report-task-output.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\n\n        <div class=\"page-header\">\n            <span> Report Writer Tasks</span>\n        </div>\n\n        <div class=\"ui-widget-header\" style=\"padding:4px 10px;border-bottom: 0 none\">\n                <i class=\"fa fa-search\" style=\"margin:4px 4px 0 0\"></i>\n                <input #gb type=\"text\" pInputText size=\"50\" placeholder=\"Global Filter\">\n            </div>\n\n            <p-dataTable  resizableColumns=\"true\" columnResizeMode=\"expand\" reorderableColumns=\"true\" [value]=\"reportData\" [responsive]=\"true\" [rows]=\"10\" [paginator]=\"true\" [alwaysShowPaginator]=\"true\" [pageLinks]=\"3\" [rowsPerPageOptions]=\"[5,10,20]\"\n                [globalFilter]=\"gb\" #dt>\n\n                <!-- <p-column *ngFor=\"let col of cols\" [field]=\"col.field\" [header]=\"col.header\" [sortable]=\"true\" [filter]=\"true\" filterPlaceholder=\"\" filterMatchMode=\"contains\"></p-column>-->\n\n                <p-column *ngFor=\"let col of cols\" [field]=\"col.field\" [header]=\"col.header\" [sortable]=\"true\" [filter]=\"true\" filterMatchMode=\"contains\">\n                    <ng-template let-rowData=\"rowData\" pTemplate=\"body\">\n                        <div title=\"{{rowData[col.field]}}\" style=\"overflow:hidden;width:100%\">{{rowData[col.field]}}</div>\n                    </ng-template>\n                </p-column>\n            </p-dataTable>\n</div>"

/***/ }),

/***/ "../../../../../src/app/component/report-task-output/report-task-output.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ReportTaskOutputComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__service_report_task_service__ = __webpack_require__("../../../../../src/app/service/report-task.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var ReportTaskOutputComponent = (function () {
    function ReportTaskOutputComponent(route, reportTaskService) {
        this.route = route;
        this.reportTaskService = reportTaskService;
        this.taskId = route.snapshot.params['id'];
    }
    ReportTaskOutputComponent.prototype.ngOnInit = function () {
        this.getReportTaskOutput();
    };
    ReportTaskOutputComponent.prototype.getReportTaskOutput = function () {
        var _this = this;
        this.reportTaskService.getReportTaskData(this.taskId).
            subscribe(function (reportTaskOutput) {
            _this.reportTaskOutput = reportTaskOutput;
            _this.cols = _this.reportTaskOutput.header;
            _this.reportData = _this.reportTaskOutput.data;
        });
    };
    ReportTaskOutputComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-report-task-output',
            template: __webpack_require__("../../../../../src/app/component/report-task-output/report-task-output.component.html"),
            styles: [__webpack_require__("../../../../../src/app/component/report-task-output/report-task-output.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_router__["ActivatedRoute"], __WEBPACK_IMPORTED_MODULE_2__service_report_task_service__["a" /* ReportTaskService */]])
    ], ReportTaskOutputComponent);
    return ReportTaskOutputComponent;
}());



/***/ }),

/***/ "../../../../../src/app/component/report-template-view/report-template-view.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/component/report-template-view/report-template-view.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\n        <div class=\"page-header\" *ngIf=\"templateFetched\">\n            <span> Report Template Details for Template - {{template.name}}</span>\n        </div>\n        <div *ngIf=\"templateFetched\">\n            <br>\n            <div class=\"ui-g\">\n                <div class=\"ui-g-2\">Name</div>\n                <div class=\"ui-g-4\">{{template.name}}</div>\n            </div>\n            <div class=\"ui-g\">\n                <div class=\"ui-g-2\">Type</div>\n                <div class=\"ui-g-4\">{{template.type}}</div>\n            </div>\n            <div class=\"ui-g\">\n                <div class=\"ui-g-2\">Association Name</div>\n                <div class=\"ui-g-4\">{{template.association}}</div>\n            </div>\n            <div class=\"ui-g\">\n                <div class=\"ui-g-2\">Root Concept Code</div>\n                <div class=\"ui-g-4\">{{template.rootConceptCode}}</div>\n            </div>\n            <div class=\"ui-g\">\n                <div class=\"ui-g-2\">Level</div>\n                <div class=\"ui-g-4\">{{template.level}}</div>\n            </div>\n            <div class=\"ui-g\">\n                <div class=\"ui-g-2\">Sort Column</div>\n                <div class=\"ui-g-4\">{{template.sortColumn}}</div>\n            </div>\n            <div class=\"ui-g\">\n                <div class=\"ui-g-2\">Status</div>\n                <div class=\"ui-g-4\">{{template.status}}</div>\n            </div>\n            <br>\n            <p-dataTable [value]=\"templateRows\" [responsive]=\"true\" [rows]=\"20\" [paginator]=\"true\" [pageLinks]=\"3\" [rowsPerPageOptions]=\"[5,10,20]\">\n                <p-column field=\"label\" header=\"Label\"></p-column>\n                <p-column field=\"display\" header=\"Display\"></p-column>\n                <p-column field=\"propertyType\" header=\"Property Type\"></p-column>\n                <p-column field=\"property\" header=\"Property Target\"></p-column>\n                <p-column field=\"source\" header=\"Source\"></p-column>\n                <p-column field=\"group\" header=\"Group\"></p-column>\n                <p-column field=\"subsource\" header=\"Subsource\"></p-column>\n            </p-dataTable>\n        </div>\n</div>"

/***/ }),

/***/ "../../../../../src/app/component/report-template-view/report-template-view.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ReportTemplateViewComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__service_report_template_service__ = __webpack_require__("../../../../../src/app/service/report-template.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var ReportTemplateViewComponent = (function () {
    function ReportTemplateViewComponent(route, reportTemplateService) {
        var _this = this;
        this.route = route;
        this.reportTemplateService = reportTemplateService;
        this.templateFetched = false;
        this.templateId = route.snapshot.params['id'];
        console.log("templateId -" + this.templateId);
        this.reportTemplateService.getReportTemplate(this.templateId).subscribe(function (template) {
            _this.template = template;
            _this.templateRows = template.columns;
            _this.templateFetched = true;
            console.log("after getReportTemplate ----- " + JSON.stringify(_this.template));
            console.log("after getReportTemplate ----- " + JSON.stringify(_this.templateRows));
        });
    }
    ReportTemplateViewComponent.prototype.ngOnInit = function () {
    };
    ReportTemplateViewComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-report-template-view',
            template: __webpack_require__("../../../../../src/app/component/report-template-view/report-template-view.component.html"),
            styles: [__webpack_require__("../../../../../src/app/component/report-template-view/report-template-view.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_router__["ActivatedRoute"], __WEBPACK_IMPORTED_MODULE_2__service_report_template_service__["a" /* ReportTemplateService */]])
    ], ReportTemplateViewComponent);
    return ReportTemplateViewComponent;
}());



/***/ }),

/***/ "../../../../../src/app/component/report-template/report-template.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/component/report-template/report-template.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\n        <div class=\"page-header\">\n            <span> Report Writer Templates</span>\n        </div>\n\n            <div *ngIf=\"taskRun\">\n                <p>\n                    <ngb-alert [type]=\"'success'\" *ngIf=\"!staticAlertClosed\" (close)=\"staticAlertClosed = true\">\n                        {{displayMessage}}.\n                        <div>\n                            <a href=\"/reportTask\" class=\"alert-link\">Click here to go the All Report Tasks Page</a>.\n                        </div>\n                    </ngb-alert>\n                </p>\n            </div>\n            <br>\n\n            <div class=\"ui-widget-header row\">\n                <div class=\"col-sm-6\">\n                    <div class=\"input-group \">\n                        <span class=\"input-group-addon\" id=\"basic-addon1\"><i class=\"fa fa-search\"></i></span>\n                        <input #gb type=\"text\" pInputText size=\"50\" placeholder=\"Global Filter\" class=\"form-control\" aria-describedby=\"basic-addon1\">\n                    </div>\n                </div>\n                <div class=\"col-sm-6 \">\n                    <div class=\"pull-right paginationCount\">{{pageinationcount}}</div>\n                </div>   \n            </div>\n            <p-dataTable [value]=\"templates\" (onPage)=\"onPageChange($event)\" [responsive]=\"true\" [rows]=\"5\" [paginator]=\"true\" [alwaysShowPaginator]=\"true\" [totalRecords]=\"2\" [pageLinks]=\"3\" [globalFilter]=\"gb\" [rowsPerPageOptions]=\"[5,10,20]\">\n                <p-column field=\"id\" styleClass=\"col-button\" header=\"Action\">\n                    <ng-template let-templateRow=\"rowData\" pTemplate=\"body\">\n                        <button type=\"button\" pButton [routerLink]=\"['/createTemplate', templateRow.id]\" icon=\"fa-pencil-square-o\"></button>\n                        <button type=\"button\" pButton [routerLink]=\"['/reportTemplateView', templateRow.id]\" icon=\"fa-eye\"></button>\n                        <button type=\"button\" pButton (click)=\"runTemplate(templateRow)\" label=\"Run\"></button>\n                    </ng-template>\n                </p-column>\n                <p-column field=\"name\" header=\"Name\" [filter]=\"true\" filterPlaceholder=\"Search\" filterMatchMode=\"contains\"></p-column>\n                <p-column field=\"status\" header=\"Status\" [filter]=\"true\" filterPlaceholder=\"Search\" filterMatchMode=\"contains\"></p-column>\n            </p-dataTable>\n</div>"

/***/ }),

/***/ "../../../../../src/app/component/report-template/report-template.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ReportTemplateComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__service_report_template_service__ = __webpack_require__("../../../../../src/app/service/report-template.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var ReportTemplateComponent = (function () {
    function ReportTemplateComponent(reportTemplateService) {
        this.reportTemplateService = reportTemplateService;
    }
    ReportTemplateComponent.prototype.ngOnInit = function () {
        this.getReportTemplates();
        this.staticAlertClosed = true;
        this.taskRun = false;
    };
    ReportTemplateComponent.prototype.getReportTemplates = function () {
        var _this = this;
        this.reportTemplateService.getReportTemplates().
            subscribe(function (templates) {
            _this.templates = templates;
            _this.totalRecordsCount = _this.templates.length;
            var currentpageCount = 0 + 1;
            var currentPageRows = 0 + 5;
            if (currentPageRows > _this.templates.length) {
                currentPageRows = _this.templates.length;
            }
            _this.pageinationcount = 'Showing ' + currentpageCount + ' to ' + currentPageRows + ' of ' + _this.templates.length;
        });
    };
    ReportTemplateComponent.prototype.editTemplate = function (template) {
        console.log("In editTemplate - " + JSON.stringify(template));
    };
    ReportTemplateComponent.prototype.onPageChange = function (e) {
        var currentpageCount = e.first + 1;
        var currentPageRows = e.first + e.rows;
        if (currentPageRows > this.templates.length) {
            currentPageRows = this.templates.length;
        }
        this.pageinationcount = 'Showing ' + currentpageCount + ' to ' + currentPageRows + ' of ' + this.templates.length;
    };
    ReportTemplateComponent.prototype.runTemplate = function (templateRow) {
        var _this = this;
        console.log("In runTemplate - " + JSON.stringify(templateRow));
        this.reportTemplateService.runReportTemplate(templateRow.id).
            subscribe(function (task) {
            _this.task = task;
            console.log(JSON.stringify(task));
            _this.displayMessage = " A task with id - " + task.id + " has been created for the template " + templateRow.name +
                ". Please check the status of the report in the All Report Tasks page.";
            _this.taskRun = true;
            _this.staticAlertClosed = false;
            setTimeout(function () { _this.staticAlertClosed = true; console.log("setting staticAlertClosed to true"); }, 50000);
        });
    };
    ReportTemplateComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-report-template',
            template: __webpack_require__("../../../../../src/app/component/report-template/report-template.component.html"),
            styles: [__webpack_require__("../../../../../src/app/component/report-template/report-template.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__service_report_template_service__["a" /* ReportTemplateService */]])
    ], ReportTemplateComponent);
    return ReportTemplateComponent;
}());



/***/ }),

/***/ "../../../../../src/app/component/reportwriter-home/reportwriter-home.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/component/reportwriter-home/reportwriter-home.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\n    <br>\n    <br>\n    <div class=\"page-header\">Report Writer</div>\n    Create templates, run templates , get reports in various formats\n</div>"

/***/ }),

/***/ "../../../../../src/app/component/reportwriter-home/reportwriter-home.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ReportwriterHomeComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var ReportwriterHomeComponent = (function () {
    function ReportwriterHomeComponent() {
    }
    ReportwriterHomeComponent.prototype.ngOnInit = function () {
    };
    ReportwriterHomeComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Component"])({
            selector: 'app-reportwriter-home',
            template: __webpack_require__("../../../../../src/app/component/reportwriter-home/reportwriter-home.component.html"),
            styles: [__webpack_require__("../../../../../src/app/component/reportwriter-home/reportwriter-home.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], ReportwriterHomeComponent);
    return ReportwriterHomeComponent;
}());



/***/ }),

/***/ "../../../../../src/app/model/lookup.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Lookup; });
var Lookup = (function () {
    function Lookup() {
    }
    Lookup.prototype.toString = function () {
        return this.label.toString();
    };
    return Lookup;
}());



/***/ }),

/***/ "../../../../../src/app/model/template-row.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return TemplateRow; });
/* unused harmony export TemplateRowUI */
var TemplateRow = (function () {
    function TemplateRow(id, columnNumber, label, display, propertyType, property, source, group, subsource) {
        this.id = id;
        this.columnNumber = columnNumber;
        this.label = label;
        this.display = display;
        this.propertyType = propertyType;
        this.property = property;
        this.source = source;
        this.group = group;
        this.subsource = subsource;
    }
    return TemplateRow;
}());

var TemplateRowUI = (function () {
    function TemplateRowUI(columnNumber, label, display, propertyType, propertyTarget, source, group, subsource) {
        this.columnNumber = columnNumber;
        this.label = label;
        this.display = display;
        this.propertyType = propertyType;
        this.propertyTarget = propertyTarget;
        this.source = source;
        this.group = group;
        this.subsource = subsource;
    }
    return TemplateRowUI;
}());



/***/ }),

/***/ "../../../../../src/app/model/template.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Template; });
var Template = (function () {
    function Template(id, name, type, association, rootConceptCode, level, status, sortColumn, columns) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.association = association;
        this.rootConceptCode = rootConceptCode;
        this.level = level;
        this.status = status;
        this.sortColumn = sortColumn;
        this.columns = columns;
    }
    return Template;
}());



/***/ }),

/***/ "../../../../../src/app/service/http.interceptor.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return HttpService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_Rx__ = __webpack_require__("../../../../rxjs/_esm5/Rx.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__loader_service__ = __webpack_require__("../../../../../src/app/service/loader.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var HttpService = (function () {
    function HttpService(loaderService) {
        this.loaderService = loaderService;
    }
    HttpService.prototype.intercept = function (req, next) {
        var _this = this;
        // start our loader here
        this.loaderService.showLoader();
        return next.handle(req).do(function (event) {
            // if the event is for http response
            if (event instanceof __WEBPACK_IMPORTED_MODULE_2__angular_common_http__["e" /* HttpResponse */]) {
                // stop our loader here
                _this.loaderService.hideLoader();
            }
        }, function (err) {
            // if any error (not for just HttpResponse) we stop our loader bar
            _this.loaderService.hideLoader();
        });
    };
    HttpService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_3__loader_service__["a" /* LoaderService */]])
    ], HttpService);
    return HttpService;
}());



/***/ }),

/***/ "../../../../../src/app/service/loader.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LoaderService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_BehaviorSubject__ = __webpack_require__("../../../../rxjs/_esm5/BehaviorSubject.js");
// import { Injectable } from '@angular/core';
// import { Observable } from 'rxjs';
// import { Subject } from 'rxjs/Subject';
// import { LoaderState } from './loader';
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
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


var LoaderService = (function () {
    function LoaderService() {
        this.loaderSubject = new __WEBPACK_IMPORTED_MODULE_1_rxjs_BehaviorSubject__["a" /* BehaviorSubject */](false);
    }
    LoaderService.prototype.showLoader = function () {
        this.loaderSubject.next(true);
        console.log("show loader");
    };
    LoaderService.prototype.hideLoader = function () {
        this.loaderSubject.next(false);
        console.log("hide loader");
    };
    LoaderService.prototype.getLoaderSubject = function () {
        return this.loaderSubject.asObservable();
    };
    LoaderService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
        __metadata("design:paramtypes", [])
    ], LoaderService);
    return LoaderService;
}());



/***/ }),

/***/ "../../../../../src/app/service/lookupvalues-template.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LookupvaluesTemplateService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_observable_of__ = __webpack_require__("../../../../rxjs/_esm5/observable/of.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_operators__ = __webpack_require__("../../../../rxjs/_esm5/operators.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var LookupvaluesTemplateService = (function () {
    function LookupvaluesTemplateService(http) {
        this.http = http;
        this.types = [{ label: 'Association', value: 'Association' }, { label: 'Concept List', value: 'Concept List' }];
        this.associations = [{ label: 'Concept_In_Subset', value: 'Concept_In_Subset' }, { label: 'Children', value: 'Children' }];
        this.statuses = [{ label: 'Pending', value: 'P' }, { label: 'Active', value: 'A' }];
    }
    LookupvaluesTemplateService.prototype.getTypes = function () {
        //return of(this.types);
        return this.http.get("/reportwriter/lkreporttemplatetype").pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError('lkreporttemplatetype', [])));
    };
    LookupvaluesTemplateService.prototype.getAssociations = function () {
        //return of(this.associations);
        return this.http.get("/reportwriter/lkassociation").pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError('lkpropertytype', [])));
    };
    LookupvaluesTemplateService.prototype.getStatuses = function () {
        //return of(this.statuses);
        return this.http.get("/reportwriter/lkreporttemplatestatus").pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError(' lkreporttemplatestatus', [])));
    };
    /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
    LookupvaluesTemplateService.prototype.handleError = function (operation, result) {
        if (operation === void 0) { operation = 'operation'; }
        return function (error) {
            // TODO: send the error to remote logging infrastructure
            console.error(error); // log to console instead
            // Let the app keep running by returning an empty result.
            return Object(__WEBPACK_IMPORTED_MODULE_1_rxjs_observable_of__["a" /* of */])(result);
        };
    };
    LookupvaluesTemplateService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__angular_common_http__["b" /* HttpClient */]])
    ], LookupvaluesTemplateService);
    return LookupvaluesTemplateService;
}());



/***/ }),

/***/ "../../../../../src/app/service/lookupvalues-templaterow.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LookupvaluesTemplaterowService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_observable_of__ = __webpack_require__("../../../../rxjs/_esm5/observable/of.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_operators__ = __webpack_require__("../../../../rxjs/_esm5/operators.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var LookupvaluesTemplaterowService = (function () {
    function LookupvaluesTemplaterowService(http) {
        this.http = http;
        this.displays = [{ label: 'code', value: 'code' }, { label: 'property', value: 'property' }, { label: 'source-code', value: 'source-code' }];
        this.propertyTypes = [{ label: 'DEFINITION', value: 'DEFINITION' }, { label: 'ALT_DEFINITION', value: 'ALT_DEFINITION' }, { label: 'FULL_SYN', value: 'FULL_SYN' }];
        this.propertyTargets = [{ label: 'Preferred_Name', value: 'Preferred_Name' }, { label: 'Contributing_Source', value: 'Contributing_Source' }];
        this.sources = [{ label: 'DTP', value: 'DTP' }, { label: 'FDA', value: 'FDA' }];
        this.groups = [{ label: 'AB', value: 'AB' }, { label: 'AD', value: 'AD' }];
        this.subsources = [{ label: 'CDRH', value: 'CDRH' }, { label: 'CTRP', value: 'CTRP' }];
    }
    LookupvaluesTemplaterowService.prototype.getDisplays = function () {
        //return of(this.displays);
        return this.http.get("/reportwriter/lkdisplay").pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError('lkdisplay', [])));
    };
    LookupvaluesTemplaterowService.prototype.getPropertyTypes = function () {
        //return of(this.propertyTypes);
        return this.http.get("/reportwriter/lkpropertytype").pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError('lkpropertytype', [])));
    };
    LookupvaluesTemplaterowService.prototype.getPropertyTargets = function () {
        //return of(this.propertyTargets);
        return this.http.get("/reportwriter/lkproperty").pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError('lkpropertytype', [])));
    };
    LookupvaluesTemplaterowService.prototype.getSources = function () {
        //return of(this.sources);
        return this.http.get("/reportwriter/lksource").pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError('lkpropertytype', [])));
    };
    LookupvaluesTemplaterowService.prototype.getGroups = function () {
        //return of(this.groups);
        return this.http.get("/reportwriter/lkgroup").pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError('lkpropertytype', [])));
    };
    LookupvaluesTemplaterowService.prototype.getSubsources = function () {
        //return of(this.subsources);
        return this.http.get("/reportwriter/lksubsource").pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError('lkpropertytype', [])));
    };
    /**
  * Handle Http operation that failed.
  * Let the app continue.
  * @param operation - name of the operation that failed
  * @param result - optional value to return as the observable result
  */
    LookupvaluesTemplaterowService.prototype.handleError = function (operation, result) {
        if (operation === void 0) { operation = 'operation'; }
        return function (error) {
            // TODO: send the error to remote logging infrastructure
            console.error(error); // log to console instead
            // Let the app keep running by returning an empty result.
            return Object(__WEBPACK_IMPORTED_MODULE_1_rxjs_observable_of__["a" /* of */])(result);
        };
    };
    LookupvaluesTemplaterowService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__angular_common_http__["b" /* HttpClient */]])
    ], LookupvaluesTemplaterowService);
    return LookupvaluesTemplaterowService;
}());



/***/ }),

/***/ "../../../../../src/app/service/report-task.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ReportTaskService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_observable_of__ = __webpack_require__("../../../../rxjs/_esm5/observable/of.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_operators__ = __webpack_require__("../../../../rxjs/_esm5/operators.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var ReportTaskService = (function () {
    function ReportTaskService(http) {
        this.http = http;
    }
    ReportTaskService.prototype.getReportTasks = function () {
        //return of(this.statuses);
        return this.http.get("/reportwriter/reporttasks").pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError(' reporttasks', [])));
    };
    ReportTaskService.prototype.deleteReportTask = function (taskId) {
        return this.http.get("/reportwriter/deleteReportTask/" + taskId).pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError(' reporttasks', null)));
    };
    ReportTaskService.prototype.getXLSReport = function (taskId) {
        return this.http.get("/reportwriter/getXLSReport/" + taskId).pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError(' getXLSReport', null)));
    };
    ReportTaskService.prototype.getTaskStatuses = function () {
        //return of(this.groups);
        return this.http.get("/reportwriter/lkreportstatus").pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError('lkreportstatus', [])));
    };
    ReportTaskService.prototype.getReportTaskData = function (taskId) {
        //return of(this.groups);
        return this.http.get("/reportwriter/getReportTaskData/" + taskId).pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError('getReportTaskData', null)));
    };
    /**
* Handle Http operation that failed.
* Let the app continue.
* @param operation - name of the operation that failed
* @param result - optional value to return as the observable result
*/
    ReportTaskService.prototype.handleError = function (operation, result) {
        if (operation === void 0) { operation = 'operation'; }
        return function (error) {
            // TODO: send the error to remote logging infrastructure
            console.error(error); // log to console instead
            // Let the app keep running by returning an empty result.
            return Object(__WEBPACK_IMPORTED_MODULE_1_rxjs_observable_of__["a" /* of */])(result);
        };
    };
    ReportTaskService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__angular_common_http__["b" /* HttpClient */]])
    ], ReportTaskService);
    return ReportTaskService;
}());



/***/ }),

/***/ "../../../../../src/app/service/report-template.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ReportTemplateService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_observable_of__ = __webpack_require__("../../../../rxjs/_esm5/observable/of.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_operators__ = __webpack_require__("../../../../rxjs/_esm5/operators.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var httpOptions = {
    headers: new __WEBPACK_IMPORTED_MODULE_2__angular_common_http__["d" /* HttpHeaders */]({ 'Content-Type': 'application/json' })
};
var ReportTemplateService = (function () {
    function ReportTemplateService(http) {
        this.http = http;
    }
    /** POST: add a new template to the server */
    ReportTemplateService.prototype.addReportTemplate = function (template) {
        console.log("addReportTemplate ----- " + JSON.stringify(template));
        return this.http.post("/reportwriter/createTemplate", template, httpOptions)
            .pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["tap"])(function (template) { return console.log("Template Created"); }), Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError('addReportTemplate')));
    };
    /** POST: save changes to the template to the server */
    ReportTemplateService.prototype.saveReportTemplate = function (template) {
        console.log("saveReportTemplate ----- " + JSON.stringify(template));
        return this.http.post("/reportwriter/saveTemplate", template, httpOptions).pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["tap"])(function (template) { return console.log("Template saved"); }), Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError('saveReportTemplate')));
    };
    ReportTemplateService.prototype.runReportTemplate = function (templateId) {
        return this.http.get("/reportwriter/runReport/" + templateId).pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError('runReportTemplate', null)));
    };
    ReportTemplateService.prototype.getReportTemplates = function () {
        //return of(this.statuses);
        return this.http.get("/reportwriter/reporttemplates").pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError(' reporttemplates', [])));
    };
    ReportTemplateService.prototype.getReportTemplate = function (reportTemplateId) {
        //return of(this.statuses);
        return this.http.get("/reportwriter/reporttemplate/" + reportTemplateId).pipe(Object(__WEBPACK_IMPORTED_MODULE_3_rxjs_operators__["catchError"])(this.handleError(' getReportTemplate', null)));
    };
    /**
* Handle Http operation that failed.
* Let the app continue.
* @param operation - name of the operation that failed
* @param result - optional value to return as the observable result
*/
    ReportTemplateService.prototype.handleError = function (operation, result) {
        if (operation === void 0) { operation = 'operation'; }
        return function (error) {
            // TODO: send the error to remote logging infrastructure
            console.error(error); // log to console instead
            // Let the app keep running by returning an empty result.
            return Object(__WEBPACK_IMPORTED_MODULE_1_rxjs_observable_of__["a" /* of */])(result);
        };
    };
    ReportTemplateService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["Injectable"])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__angular_common_http__["b" /* HttpClient */]])
    ], ReportTemplateService);
    return ReportTemplateService;
}());



/***/ }),

/***/ "../../../../../src/environments/environment.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return environment; });
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.
var environment = {
    production: false
};


/***/ }),

/***/ "../../../../../src/main.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__("../../../platform-browser-dynamic/esm5/platform-browser-dynamic.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_app_module__ = __webpack_require__("../../../../../src/app/app.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__("../../../../../src/environments/environment.ts");




if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["enableProdMode"])();
}
Object(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_2__app_app_module__["a" /* AppModule */])
    .catch(function (err) { return console.log(err); });


/***/ }),

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__("../../../../../src/main.ts");


/***/ })

},[0]);
//# sourceMappingURL=main.bundle.js.map