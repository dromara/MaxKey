import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemberGroupsEditerComponent } from './member-groups-editer.component';

describe('MemberGroupsEditerComponent', () => {
  let component: MemberGroupsEditerComponent;
  let fixture: ComponentFixture<MemberGroupsEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MemberGroupsEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MemberGroupsEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
