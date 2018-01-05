import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportTaskOutputComponent } from './report-task-output.component';

describe('ReportTaskOutputComponent', () => {
  let component: ReportTaskOutputComponent;
  let fixture: ComponentFixture<ReportTaskOutputComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportTaskOutputComponent ]
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
