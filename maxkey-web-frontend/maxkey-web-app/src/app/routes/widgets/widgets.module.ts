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

import { NgModule, Type } from '@angular/core';
import { G2MiniAreaModule } from '@delon/chart/mini-area';
import { G2MiniBarModule } from '@delon/chart/mini-bar';
import { SharedModule } from '@shared';
import { NzCarouselModule } from 'ng-zorro-antd/carousel';

import { WidgetsRoutingModule } from './widgets-routing.module';
import { WidgetsComponent } from './widgets/widgets.component';

const COMPONENTS: Array<Type<void>> = [WidgetsComponent];

@NgModule({
  imports: [SharedModule, WidgetsRoutingModule, NzCarouselModule, G2MiniBarModule, G2MiniAreaModule],
  declarations: COMPONENTS
})
export class WidgetsModule {}
