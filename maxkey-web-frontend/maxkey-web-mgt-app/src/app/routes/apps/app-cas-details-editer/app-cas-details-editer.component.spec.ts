import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppCasDetailsEditerComponent } from './app-cas-details-editer.component';

describe('AppCasDetailsEditerComponent', () => {
  let component: AppCasDetailsEditerComponent;
  let fixture: ComponentFixture<AppCasDetailsEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppCasDetailsEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppCasDetailsEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
