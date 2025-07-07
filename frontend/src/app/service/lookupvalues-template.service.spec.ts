import { TestBed, inject } from '@angular/core/testing';

import { LookupvaluesTemplateService } from './../service/lookupvalues-template.service';

describe('LookupvaluesTemplateService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LookupvaluesTemplateService]
    });
  });

  it('should be created', inject([LookupvaluesTemplateService], (service: LookupvaluesTemplateService) => {
    expect(service).toBeTruthy();
  }));
});
