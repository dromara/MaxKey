import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { NzDropDownModule } from 'ng-zorro-antd/dropdown';
import { NzToolTipModule } from 'ng-zorro-antd/tooltip';

import { ALAIN_THEME_BTN_KEYS, ThemeBtnComponent } from './theme-btn.component';

const COMPONENTS = [ThemeBtnComponent];

@NgModule({
  imports: [CommonModule, NzDropDownModule, NzToolTipModule],
  providers: [
    {
      provide: ALAIN_THEME_BTN_KEYS,
      useValue: 'site-theme'
    }
  ],
  declarations: COMPONENTS,
  exports: COMPONENTS
})
export class ThemeBtnModule {}
