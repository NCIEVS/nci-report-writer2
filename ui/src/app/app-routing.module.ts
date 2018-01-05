import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
 
import { CreateTemplateComponent }   from './component/create-template/create-template.component';
import { ReportTemplateComponent }      from './component/report-template/report-template.component';
import {ReportTemplateViewComponent}       from './component/report-template-view/report-template-view.component';
import {AllReportTaskStatusComponent}       from './component/all-report-task-status/all-report-task-status.component';
import {ReportTaskOutputComponent}       from './component/report-task-output/report-task-output.component';
import {ReportwriterHomeComponent}       from './component/reportwriter-home/reportwriter-home.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'createTemplate/:id',  component: CreateTemplateComponent }, 
  { path: 'reportTemplate',     component: ReportTemplateComponent },
  { path: 'reportTemplateView/:id',     component: ReportTemplateViewComponent },
  { path: 'reportTask',     component: AllReportTaskStatusComponent },
  { path: 'reportTaskOutput/:id',     component: ReportTaskOutputComponent },
  { path: 'home',     component: ReportwriterHomeComponent }
  
];
 
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}