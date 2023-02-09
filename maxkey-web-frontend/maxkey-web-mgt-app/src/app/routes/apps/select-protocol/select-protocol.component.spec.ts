import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectProtocolComponent } from './select-protocol.component';

describe('SelectProtocolComponent', () => {
  let component: SelectProtocolComponent;
  let fixture: ComponentFixture<SelectProtocolComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelectProtocolComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectProtocolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
