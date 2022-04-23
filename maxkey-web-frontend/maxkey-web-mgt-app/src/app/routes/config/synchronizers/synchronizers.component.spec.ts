import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SynchronizersComponent } from './synchronizers.component';

describe('SynchronizersComponent', () => {
  let component: SynchronizersComponent;
  let fixture: ComponentFixture<SynchronizersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SynchronizersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SynchronizersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
