import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllReportTaskStatusComponent } from './all-report-task-status.component';

describe('AllReportTaskStatusComponent', () => {
  let component: AllReportTaskStatusComponent;
  let fixture: ComponentFixture<AllReportTaskStatusComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllReportTaskStatusComponent ]
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
