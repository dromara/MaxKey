import { Directive } from '@angular/core';

@Directive({
  selector: '[layout-default-header-item-trigger]',
  host: {
    '[class.alain-default__nav-item]': `true`
  }
})
export class LayoutDefaultHeaderItemTriggerDirective {}
