import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrivilegesEditerComponent } from './privileges-editer.component';

describe('PrivilegesEditerComponent', () => {
  let component: PrivilegesEditerComponent;
  let fixture: ComponentFixture<PrivilegesEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrivilegesEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PrivilegesEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
