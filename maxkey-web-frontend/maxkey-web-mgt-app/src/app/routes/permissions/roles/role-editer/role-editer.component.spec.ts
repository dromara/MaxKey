import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleEditerComponent } from './role-editer.component';

describe('RoleEditerComponent', () => {
  let component: RoleEditerComponent;
  let fixture: ComponentFixture<RoleEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoleEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RoleEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
