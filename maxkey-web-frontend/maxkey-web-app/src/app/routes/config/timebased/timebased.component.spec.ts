import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TimebasedComponent } from './timebased.component';

describe('TimebasedComponent', () => {
  let component: TimebasedComponent;
  let fixture: ComponentFixture<TimebasedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TimebasedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TimebasedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
