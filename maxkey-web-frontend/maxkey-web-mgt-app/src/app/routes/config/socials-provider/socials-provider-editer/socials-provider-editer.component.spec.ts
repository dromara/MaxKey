import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SocialsProviderEditerComponent } from './socials-provider-editer.component';

describe('SocialsProviderEditerComponent', () => {
  let component: SocialsProviderEditerComponent;
  let fixture: ComponentFixture<SocialsProviderEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SocialsProviderEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SocialsProviderEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
