import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Oauth2ApproveComponent } from './oauth2-approve.component';

describe('Oauth2ApproveComponent', () => {
  let component: Oauth2ApproveComponent;
  let fixture: ComponentFixture<Oauth2ApproveComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Oauth2ApproveComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Oauth2ApproveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
