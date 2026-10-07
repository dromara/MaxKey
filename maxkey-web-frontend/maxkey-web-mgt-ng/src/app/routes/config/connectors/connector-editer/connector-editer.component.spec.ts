import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConnectorEditerComponent } from './connector-editer.component';

describe('ConnectorEditerComponent', () => {
  let component: ConnectorEditerComponent;
  let fixture: ComponentFixture<ConnectorEditerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConnectorEditerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConnectorEditerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
