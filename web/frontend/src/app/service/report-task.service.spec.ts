import { TestBed, inject } from '@angular/core/testing';

import { ReportTaskService } from './report-task.service';

describe('ReportTaskService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ReportTaskService]
    });
  });

  it('should be created', inject([ReportTaskService], (service: ReportTaskService) => {
    expect(service).toBeTruthy();
  }));
});
