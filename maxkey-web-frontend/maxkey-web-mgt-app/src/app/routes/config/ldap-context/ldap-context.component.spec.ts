import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LdapContextComponent } from './ldap-context.component';

describe('LdapContextComponent', () => {
  let component: LdapContextComponent;
  let fixture: ComponentFixture<LdapContextComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LdapContextComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LdapContextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
