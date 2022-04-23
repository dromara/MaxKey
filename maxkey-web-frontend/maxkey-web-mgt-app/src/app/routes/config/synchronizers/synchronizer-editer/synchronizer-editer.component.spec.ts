import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SynchronizerEditerComponent } from './synchronizer-editer.component';

describe('SynchronizerEditerComponent', () => {
  let component: SynchronizerEditerComponent;
  let fixture: ComponentFixture<SynchronizerEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SynchronizerEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SynchronizerEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
