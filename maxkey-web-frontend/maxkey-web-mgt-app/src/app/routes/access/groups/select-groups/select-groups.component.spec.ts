import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectGroupsComponent } from './select-groups.component';

describe('SelectGroupsComponent', () => {
  let component: SelectGroupsComponent;
  let fixture: ComponentFixture<SelectGroupsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelectGroupsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectGroupsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
