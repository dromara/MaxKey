import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountEditerComponent } from './account-editer.component';

describe('AccountEditerComponent', () => {
  let component: AccountEditerComponent;
  let fixture: ComponentFixture<AccountEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
