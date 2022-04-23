import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SocialsProviderComponent } from './socials-provider.component';

describe('SocialsProviderComponent', () => {
  let component: SocialsProviderComponent;
  let fixture: ComponentFixture<SocialsProviderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SocialsProviderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SocialsProviderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
