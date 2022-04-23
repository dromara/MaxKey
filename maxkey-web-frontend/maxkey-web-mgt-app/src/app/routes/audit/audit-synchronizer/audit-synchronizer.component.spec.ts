import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditSynchronizerComponent } from './audit-synchronizer.component';

describe('AuditSynchronizerComponent', () => {
  let component: AuditSynchronizerComponent;
  let fixture: ComponentFixture<AuditSynchronizerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuditSynchronizerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditSynchronizerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
