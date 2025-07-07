import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportTemplateViewComponent } from './report-template-view.component';

describe('ReportTemplateViewComponent', () => {
  let component: ReportTemplateViewComponent;
  let fixture: ComponentFixture<ReportTemplateViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportTemplateViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportTemplateViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
