import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { ReportTemplateViewComponent } from './report-template-view.component';

import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';

import { ReportTemplateService } from '../../service/report-template.service';

describe('ReportTemplateViewComponent', () => {
  let component: ReportTemplateViewComponent;
  let fixture: ComponentFixture<ReportTemplateViewComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportTemplateViewComponent ],
      providers: [
        ReportTemplateService,
        provideRouter([]),
        provideHttpClient(),
        provideHttpClientTesting()
      ]
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
