import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectAccountsStrategyComponent } from './select-accounts-strategy.component';

describe('SelectAccountsStrategyComponent', () => {
  let component: SelectAccountsStrategyComponent;
  let fixture: ComponentFixture<SelectAccountsStrategyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelectAccountsStrategyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectAccountsStrategyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
