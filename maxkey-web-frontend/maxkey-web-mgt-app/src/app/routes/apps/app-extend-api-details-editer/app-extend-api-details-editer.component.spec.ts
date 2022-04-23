import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppExtendApiDetailsEditerComponent } from './app-extend-api-details-editer.component';

describe('AppExtendApiDetailsEditerComponent', () => {
  let component: AppExtendApiDetailsEditerComponent;
  let fixture: ComponentFixture<AppExtendApiDetailsEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AppExtendApiDetailsEditerComponent]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppExtendApiDetailsEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
