import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SocialsAssociateComponent } from './socials-associate.component';

describe('SocialsAssociateComponent', () => {
  let component: SocialsAssociateComponent;
  let fixture: ComponentFixture<SocialsAssociateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SocialsAssociateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SocialsAssociateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
