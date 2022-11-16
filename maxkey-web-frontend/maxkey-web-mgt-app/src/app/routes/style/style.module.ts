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

import { NgModule } from '@angular/core';
import { SharedModule } from '@shared';

import { ColorService } from './color.service';
import { ColorsComponent } from './colors/colors.component';
import { GridMasonryComponent } from './gridmasonry/gridmasonry.component';
import { StyleRoutingModule } from './style-routing.module';
import { TypographyComponent } from './typography/typography.component';

const COMPONENTS = [GridMasonryComponent, TypographyComponent, ColorsComponent];

@NgModule({
  imports: [SharedModule, StyleRoutingModule],
  providers: [ColorService],
  declarations: [...COMPONENTS]
})
export class StyleModule {}
