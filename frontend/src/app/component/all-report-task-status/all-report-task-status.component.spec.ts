import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { AllReportTaskStatusComponent } from './all-report-task-status.component';

import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';

import { ReportTaskService } from "./../../service/report-task.service";
import { TableModule } from 'primeng/table';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { FormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';

describe('AllReportTaskStatusComponent', () => {
  let component: AllReportTaskStatusComponent;
  let fixture: ComponentFixture<AllReportTaskStatusComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AllReportTaskStatusComponent ],
      imports: [
        TableModule,
        ConfirmDialogModule,
        DialogModule,
        FormsModule,
        DropdownModule
      ],
      providers: [
        ReportTaskService,
        provideRouter([]),
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllReportTaskStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
