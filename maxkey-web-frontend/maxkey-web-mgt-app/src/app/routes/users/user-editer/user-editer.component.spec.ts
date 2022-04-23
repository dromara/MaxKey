import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserEditerComponent } from './user-editer.component';

describe('UserEditerComponent', () => {
  let component: UserEditerComponent;
  let fixture: ComponentFixture<UserEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
