import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupMembersEditerComponent } from './group-members-editer.component';

describe('GroupMembersEditerComponent', () => {
  let component: GroupMembersEditerComponent;
  let fixture: ComponentFixture<GroupMembersEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupMembersEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupMembersEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
