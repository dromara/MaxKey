import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdapterEditerComponent } from './adapter-editer.component';

describe('AdapterEditerComponent', () => {
  let component: AdapterEditerComponent;
  let fixture: ComponentFixture<AdapterEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdapterEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdapterEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
