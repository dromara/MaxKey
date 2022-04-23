import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppOauth20DetailsEditerComponent } from './app-oauth20-details-editer.component';

describe('AppOauth20DetailsEditerComponent', () => {
  let component: AppOauth20DetailsEditerComponent;
  let fixture: ComponentFixture<AppOauth20DetailsEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppOauth20DetailsEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppOauth20DetailsEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
