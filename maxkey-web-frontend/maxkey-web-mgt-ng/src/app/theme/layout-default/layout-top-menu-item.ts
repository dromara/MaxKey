import { ChangeDetectionStrategy, Component, ViewEncapsulation, booleanAttribute, input } from '@angular/core';

@Component({
  selector: 'layout-default-top-menu-item',
  template: `<ng-content />`,
  host: {
    '[class.alain-default__nav-item]': `true`,
    '[class.alain-default__top-menu-item]': `true`,
    '[class.alain-default__top-menu-item-selected]': `selected()`,
    '[class.alain-default__top-menu-item-disabled]': `disabled()`
  },
  changeDetection: ChangeDetectionStrategy.OnPush,
  encapsulation: ViewEncapsulation.None
})
export class LayoutDefaultTopMenuItemComponent {
  readonly selected = input(false, { transform: booleanAttribute });
  readonly disabled = input(false, { transform: booleanAttribute });
}
