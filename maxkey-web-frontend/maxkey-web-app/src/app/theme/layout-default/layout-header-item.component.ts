/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
