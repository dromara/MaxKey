import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NzAvatarModule } from 'ng-zorro-antd/avatar';
import { NzBadgeModule } from 'ng-zorro-antd/badge';
import { NzDropDownModule } from 'ng-zorro-antd/dropdown';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzMessageModule } from 'ng-zorro-antd/message';
import { NzToolTipModule } from 'ng-zorro-antd/tooltip';

import { LayoutDefaultHeaderItemTriggerDirective } from './layout-header-item-trigger.directive';
import { LayoutDefaultHeaderItemComponent } from './layout-header-item.component';
import { LayoutDefaultHeaderComponent } from './layout-header.component';
import { LayoutDefaultNavComponent } from './layout-nav.component';
import { LayoutDefaultComponent } from './layout.component';

const COMPONENTS = [
  LayoutDefaultComponent,
  LayoutDefaultNavComponent,
  LayoutDefaultHeaderComponent,
  LayoutDefaultHeaderItemComponent,
  LayoutDefaultHeaderItemTriggerDirective
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    NzToolTipModule,
    NzIconModule,
    NzAvatarModule,
    NzDropDownModule,
    NzMessageModule,
    NzBadgeModule
  ],
  declarations: COMPONENTS,
  exports: COMPONENTS
})
export class LayoutDefaultModule {}
