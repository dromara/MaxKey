import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectAppsComponent } from './select-apps.component';

describe('SelectAppsComponent', () => {
  let component: SelectAppsComponent;
  let fixture: ComponentFixture<SelectAppsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelectAppsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectAppsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
