import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TfaComponent } from './tfa.component';

describe('TfaComponent', () => {
  let component: TfaComponent;
  let fixture: ComponentFixture<TfaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TfaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TfaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
