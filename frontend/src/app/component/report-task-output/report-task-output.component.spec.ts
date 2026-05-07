import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { ReportTaskOutputComponent } from './report-task-output.component';

import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';

import { ReportTaskService } from './../../service/report-task.service';
import { TableModule } from 'primeng/table';

describe('ReportTaskOutputComponent', () => {
  let component: ReportTaskOutputComponent;
  let fixture: ComponentFixture<ReportTaskOutputComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportTaskOutputComponent ],
      imports: [
        TableModule,
        
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
    fixture = TestBed.createComponent(ReportTaskOutputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
