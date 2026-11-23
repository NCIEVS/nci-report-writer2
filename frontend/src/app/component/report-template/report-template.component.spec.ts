import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportTemplateComponent } from './report-template.component';

import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { ReportTemplateService } from '../../service/report-template.service';
import { LookupvaluesTemplateService } from '../../service/lookupvalues-template.service';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { FormsModule } from '@angular/forms';
import { SelectModule } from 'primeng/select';

describe('ReportTemplateComponent', () => {
  let component: ReportTemplateComponent;
  let fixture: ComponentFixture<ReportTemplateComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportTemplateComponent ],
      imports: [
        TableModule,
        DialogModule,
        SelectModule,
        FormsModule
      ],
      providers: [
        ReportTemplateService,
        LookupvaluesTemplateService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
