import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppBasicDetailsEditerComponent } from './app-basic-details-editer.component';

describe('AppBasicDetailsEditerComponent', () => {
  let component: AppBasicDetailsEditerComponent;
  let fixture: ComponentFixture<AppBasicDetailsEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppBasicDetailsEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppBasicDetailsEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
