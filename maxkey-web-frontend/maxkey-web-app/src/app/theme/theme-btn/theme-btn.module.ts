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
