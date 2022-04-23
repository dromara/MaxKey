import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditLoginAppsComponent } from './audit-login-apps.component';

describe('AuditLoginAppsComponent', () => {
  let component: AuditLoginAppsComponent;
  let fixture: ComponentFixture<AuditLoginAppsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuditLoginAppsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuditLoginAppsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
