import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectRolesComponent } from './select-roles.component';

describe('SelectRolesComponent', () => {
  let component: SelectRolesComponent;
  let fixture: ComponentFixture<SelectRolesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelectRolesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectRolesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
