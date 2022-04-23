import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizationEditerComponent } from './organization-editer.component';

describe('OrganizationEditerComponent', () => {
  let component: OrganizationEditerComponent;
  let fixture: ComponentFixture<OrganizationEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrganizationEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrganizationEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
