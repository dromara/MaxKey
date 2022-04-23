import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailSendersComponent } from './email-senders.component';

describe('EmailSendersComponent', () => {
  let component: EmailSendersComponent;
  let fixture: ComponentFixture<EmailSendersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmailSendersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailSendersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
