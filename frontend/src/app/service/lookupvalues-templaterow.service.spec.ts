import { TestBed, inject } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { LookupvaluesTemplaterowService } from './../service/lookupvalues-templaterow.service';

describe('LookupvaluesTemplaterowService', () => {
  let service: LookupvaluesTemplaterowService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        LookupvaluesTemplaterowService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });

    service = TestBed.inject(LookupvaluesTemplaterowService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();  // Verify no outstanding requests
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
