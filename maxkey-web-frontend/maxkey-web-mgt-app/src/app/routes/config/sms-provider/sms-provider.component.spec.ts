import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SmsProviderComponent } from './sms-provider.component';

describe('SmsProviderComponent', () => {
  let component: SmsProviderComponent;
  let fixture: ComponentFixture<SmsProviderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SmsProviderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SmsProviderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
