import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupEditerComponent } from './group-editer.component';

describe('GroupEditerComponent', () => {
  let component: GroupEditerComponent;
  let fixture: ComponentFixture<GroupEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
