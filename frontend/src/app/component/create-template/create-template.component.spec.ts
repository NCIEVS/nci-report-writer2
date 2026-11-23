import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { CreateTemplateComponent } from './create-template.component';

import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';

import { LookupvaluesTemplateService } from '../../service/lookupvalues-template.service';
import { LookupvaluesTemplaterowService } from '../../service/lookupvalues-templaterow.service';
import { ReportTemplateService } from '../../service/report-template.service';
import { FormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { SelectModule } from 'primeng/select';
import { ButtonModule } from 'primeng/button';

describe('CreateTemplateComponent', () => {
  let component: CreateTemplateComponent;
  let fixture: ComponentFixture<CreateTemplateComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateTemplateComponent ],
      imports: [
        ButtonModule,
        DialogModule,
        FormsModule,
        TableModule,
        SelectModule
      ],
      providers: [
        LookupvaluesTemplateService,
        LookupvaluesTemplaterowService,
        ReportTemplateService,
        provideRouter([]),
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
