import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountsStrategyEditerComponent } from './accounts-strategy-editer.component';

describe('AccountsStrategyEditerComponent', () => {
  let component: AccountsStrategyEditerComponent;
  let fixture: ComponentFixture<AccountsStrategyEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountsStrategyEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountsStrategyEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
