import { Component, input, TemplateRef, viewChild } from '@angular/core';

import { LayoutDefaultHeaderItemDirection, LayoutDefaultHeaderItemHidden } from './types';

@Component({
  selector: 'layout-default-header-item',
  template: `
    <ng-template #host>
      <ng-content />
    </ng-template>
  `
})
export class LayoutDefaultHeaderItemComponent {
  readonly host = viewChild<TemplateRef<void>>('host');

  readonly hidden = input<LayoutDefaultHeaderItemHidden>('none');
  readonly direction = input<LayoutDefaultHeaderItemDirection>('right');
}
