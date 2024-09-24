import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PermissionRoleComponent } from './permission-role.component';

describe('PermissionRoleComponent', () => {
  let component: PermissionRoleComponent;
  let fixture: ComponentFixture<PermissionRoleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PermissionRoleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PermissionRoleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
