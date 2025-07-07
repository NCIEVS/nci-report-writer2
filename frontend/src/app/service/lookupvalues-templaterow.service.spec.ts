import { TestBed, inject } from '@angular/core/testing';

import { LookupvaluesTemplaterowService } from './../service/lookupvalues-templaterow.service';

describe('LookupvaluesTemplaterowService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LookupvaluesTemplaterowService]
    });
  });

  it('should be created', inject([LookupvaluesTemplaterowService], (service: LookupvaluesTemplaterowService) => {
    expect(service).toBeTruthy();
  }));
});
