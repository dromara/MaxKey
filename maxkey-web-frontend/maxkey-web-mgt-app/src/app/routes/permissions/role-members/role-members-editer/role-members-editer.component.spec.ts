import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleMembersEditerComponent } from './role-members-editer.component';

describe('RoleMembersEditerComponent', () => {
  let component: RoleMembersEditerComponent;
  let fixture: ComponentFixture<RoleMembersEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoleMembersEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RoleMembersEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
