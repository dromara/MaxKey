import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppSaml20DetailsEditerComponent } from './app-saml20-details-editer.component';

describe('AppSaml20DetailsEditerComponent', () => {
  let component: AppSaml20DetailsEditerComponent;
  let fixture: ComponentFixture<AppSaml20DetailsEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppSaml20DetailsEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppSaml20DetailsEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
