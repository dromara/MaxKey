import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NzAvatarModule } from 'ng-zorro-antd/avatar';
import { NzBadgeModule } from 'ng-zorro-antd/badge';
import { NzDropdownModule } from 'ng-zorro-antd/dropdown';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzTooltipModule } from 'ng-zorro-antd/tooltip';

import { LayoutDefaultHeaderItemTriggerDirective } from './layout-header-item-trigger.directive';
import { LayoutDefaultHeaderItemComponent } from './layout-header-item.component';
import { LayoutDefaultHeaderComponent } from './layout-header.component';
import { LayoutDefaultNavComponent } from './layout-nav.component';
import { LayoutDefaultTopMenuItemComponent } from './layout-top-menu-item';
import { LayoutDefaultComponent } from './layout.component';

const COMPONENTS = [
  LayoutDefaultComponent,
  LayoutDefaultNavComponent,
  LayoutDefaultHeaderComponent,
  LayoutDefaultHeaderItemComponent,
  LayoutDefaultHeaderItemTriggerDirective,
  LayoutDefaultTopMenuItemComponent
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    NzTooltipModule,
    NzIconModule,
    NzAvatarModule,
    NzDropdownModule,
    NzBadgeModule,
    ...COMPONENTS
  ],
  exports: COMPONENTS
})
export class LayoutDefaultModule {}
