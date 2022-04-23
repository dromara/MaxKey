import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditConnectorComponent } from './audit-connector.component';

describe('AuditConnectorComponent', () => {
  let component: AuditConnectorComponent;
  let fixture: ComponentFixture<AuditConnectorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuditConnectorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditConnectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
