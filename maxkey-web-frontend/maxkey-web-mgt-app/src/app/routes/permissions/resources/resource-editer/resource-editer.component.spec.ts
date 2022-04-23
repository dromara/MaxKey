import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResourceEditerComponent } from './resource-editer.component';

describe('ResourceEditerComponent', () => {
  let component: ResourceEditerComponent;
  let fixture: ComponentFixture<ResourceEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResourceEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourceEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
