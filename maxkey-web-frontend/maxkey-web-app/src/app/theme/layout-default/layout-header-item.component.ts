import { Component, Input, TemplateRef, ViewChild } from '@angular/core';

import { LayoutDefaultHeaderItemDirection, LayoutDefaultHeaderItemHidden } from './types';

@Component({
  selector: 'layout-default-header-item',
  template: `
    <ng-template #host>
      <ng-content></ng-content>
    </ng-template>
  `
})
export class LayoutDefaultHeaderItemComponent {
  @ViewChild('host', { static: true }) host!: TemplateRef<void>;

  @Input() hidden: LayoutDefaultHeaderItemHidden = 'none';
  @Input() direction: LayoutDefaultHeaderItemDirection = 'right';
}
