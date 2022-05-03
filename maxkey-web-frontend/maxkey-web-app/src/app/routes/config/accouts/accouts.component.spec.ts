import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccoutsComponent } from './accouts.component';

describe('AccoutsComponent', () => {
  let component: AccoutsComponent;
  let fixture: ComponentFixture<AccoutsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccoutsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccoutsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
