import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppTokenBasedDetailsEditerComponent } from './app-token-based-details-editer.component';

describe('AppTokenBasedDetailsEditerComponent', () => {
  let component: AppTokenBasedDetailsEditerComponent;
  let fixture: ComponentFixture<AppTokenBasedDetailsEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppTokenBasedDetailsEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppTokenBasedDetailsEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
