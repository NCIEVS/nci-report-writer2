import { TestBed, inject } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { ReportTaskService } from './report-task.service';

describe('ReportTaskService', () => {
  let service: ReportTaskService;
  let httpMock: HttpTestingController;
  
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        ReportTaskService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    
    service = TestBed.inject(ReportTaskService);
    httpMock = TestBed.inject(HttpTestingController);
  });
  
  afterEach(() => {
    httpMock.verify();  // Verify no outstanding requests
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
