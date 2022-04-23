import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditLoginsComponent } from './audit-logins.component';

describe('AuditLoginsComponent', () => {
  let component: AuditLoginsComponent;
  let fixture: ComponentFixture<AuditLoginsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuditLoginsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditLoginsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
