import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { CloneTemplateComponent } from './clone-template.component';

import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { ReportTemplateService } from '../../service/report-template.service';
import { ButtonModule } from 'primeng/button';
import { SelectModule } from 'primeng/select';
import { FormsModule } from '@angular/forms';

describe('CloneTemplateComponent', () => {
  let component: CloneTemplateComponent;
  let fixture: ComponentFixture<CloneTemplateComponent>;
  // let httpMock: HttpTestingController;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CloneTemplateComponent ],
      imports: [
        ButtonModule,
        FormsModule,
        SelectModule
      ],
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
    fixture = TestBed.createComponent(CloneTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    // httpMock = TestBed.inject(HttpTestingController);
  });

  // afterEach(() => {
  //   httpMock.verify();  // Verify no outstanding requests
  // });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
