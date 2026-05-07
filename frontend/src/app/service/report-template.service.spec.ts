import { TestBed, inject } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { ReportTemplateService } from './report-template.service';

describe('ReportTemplateService', () => {
  let service: ReportTemplateService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        ReportTemplateService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });

    service = TestBed.inject(ReportTemplateService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();  // Verify no outstanding requests
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
