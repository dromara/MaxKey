import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppFormBasedDetailsEditerComponent } from './app-form-based-details-editer.component';

describe('AppFormBasedDetailsEditerComponent', () => {
  let component: AppFormBasedDetailsEditerComponent;
  let fixture: ComponentFixture<AppFormBasedDetailsEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppFormBasedDetailsEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppFormBasedDetailsEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
