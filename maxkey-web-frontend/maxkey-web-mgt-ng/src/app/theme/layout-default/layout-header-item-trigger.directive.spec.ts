import { Component } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';

import { LayoutDefaultHeaderItemTriggerDirective } from './layout-header-item-trigger.directive';

@Component({
  template: `<div layout-default-header-item-trigger></div>`,
  imports: [LayoutDefaultHeaderItemTriggerDirective]
})
class TestComponent {}

describe('theme: layout-default-header-item-trigger', () => {
  let fixture: ComponentFixture<TestComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({ imports: [TestComponent] });
    fixture = TestBed.createComponent(TestComponent);
    fixture.detectChanges();
  });

  it('should be add the nav item class to host', () => {
    const el = fixture.debugElement.query(By.css('[layout-default-header-item-trigger]')).nativeElement as HTMLElement;
    expect(el.classList).toContain('alain-default__nav-item');
  });
});
