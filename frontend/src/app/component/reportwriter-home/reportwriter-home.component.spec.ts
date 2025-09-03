import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportwriterHomeComponent } from './reportwriter-home.component';

describe('ReportwriterHomeComponent', () => {
  let component: ReportwriterHomeComponent;
  let fixture: ComponentFixture<ReportwriterHomeComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportwriterHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportwriterHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
