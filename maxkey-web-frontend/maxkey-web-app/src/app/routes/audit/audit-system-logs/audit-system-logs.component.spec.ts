import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditSystemLogsComponent } from './audit-system-logs.component';

describe('AuditSystemLogsComponent', () => {
  let component: AuditSystemLogsComponent;
  let fixture: ComponentFixture<AuditSystemLogsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuditSystemLogsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditSystemLogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
