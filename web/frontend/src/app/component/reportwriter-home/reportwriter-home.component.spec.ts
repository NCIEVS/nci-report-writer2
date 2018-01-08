import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportwriterHomeComponent } from './reportwriter-home.component';

describe('ReportwriterHomeComponent', () => {
  let component: ReportwriterHomeComponent;
  let fixture: ComponentFixture<ReportwriterHomeComponent>;

  beforeEach(async(() => {
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
