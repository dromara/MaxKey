import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemberRolesEditerComponent } from './member-roles-editer.component';

describe('MemberRolesEditerComponent', () => {
  let component: MemberRolesEditerComponent;
  let fixture: ComponentFixture<MemberRolesEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MemberRolesEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MemberRolesEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
