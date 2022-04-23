import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleMembersComponent } from './role-members.component';

describe('RoleMembersComponent', () => {
  let component: RoleMembersComponent;
  let fixture: ComponentFixture<RoleMembersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoleMembersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RoleMembersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
