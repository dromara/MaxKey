import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountsStrategyComponent } from './accounts-strategy.component';

describe('AccountsStrategyComponent', () => {
  let component: AccountsStrategyComponent;
  let fixture: ComponentFixture<AccountsStrategyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountsStrategyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountsStrategyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
