import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectAdaptersComponent } from './select-adapters.component';

describe('SelectAdaptersComponent', () => {
  let component: SelectAdaptersComponent;
  let fixture: ComponentFixture<SelectAdaptersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelectAdaptersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectAdaptersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
