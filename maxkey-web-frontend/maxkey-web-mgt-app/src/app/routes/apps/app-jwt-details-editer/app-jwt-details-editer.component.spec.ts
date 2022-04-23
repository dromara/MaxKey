import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppJwtDetailsEditerComponent } from './app-jwt-details-editer.component';

describe('AppJwtDetailsEditerComponent', () => {
  let component: AppJwtDetailsEditerComponent;
  let fixture: ComponentFixture<AppJwtDetailsEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppJwtDetailsEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppJwtDetailsEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
