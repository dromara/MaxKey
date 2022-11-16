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
import { RouterModule, Routes } from '@angular/router';
import { AvatarListModule } from '@delon/abc/avatar-list';
import { EllipsisModule } from '@delon/abc/ellipsis';
import { FooterToolbarModule } from '@delon/abc/footer-toolbar';
import { TagSelectModule } from '@delon/abc/tag-select';
import { CurrencyPipeModule } from '@delon/util/pipes/currency';
import { SharedModule } from '@shared';
import { NzPageHeaderModule } from 'ng-zorro-antd/page-header';
import { NzPaginationModule } from 'ng-zorro-antd/pagination';
import { NzStepsModule } from 'ng-zorro-antd/steps';

import { AccoutsComponent } from './accouts/accouts.component';
import { PasswordComponent } from './password/password.component';
import { ProfileComponent } from './profile/profile.component';
import { SocialsAssociateComponent } from './socials-associate/socials-associate.component';
import { SocialsProviderComponent } from './socials-provider/socials-provider.component';
import { TimebasedComponent } from './timebased/timebased.component';

const routes: Routes = [
  {
    path: 'profile',
    component: ProfileComponent
  },
  {
    path: 'password',
    component: PasswordComponent
  },
  {
    path: 'socialsassociate',
    component: SocialsAssociateComponent
  },
  {
    path: 'timebased',
    component: TimebasedComponent
  }
];

const COMPONENTS = [ProfileComponent];

@NgModule({
  declarations: [
    ...COMPONENTS,
    SocialsProviderComponent,
    TimebasedComponent,
    SocialsAssociateComponent,
    PasswordComponent,
    ProfileComponent,
    AccoutsComponent
  ],
  imports: [SharedModule, CommonModule, RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConfigModule {}
