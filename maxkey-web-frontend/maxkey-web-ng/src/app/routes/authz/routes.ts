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

import { Routes } from '@angular/router';

import { AuthzMgtComponent } from './authz-mgt.component';
import { CredentialComponent } from './credential/credential.component';
import { Oauth2ApproveComponent } from './oauth2-approve/oauth2-approve.component';
import { LayoutBlank } from '../../layout/blank/blank';

export const routes: Routes = [
  /*
  {
    path: 'audit-logins',
    component: AuditLoginsComponent
  }
  */
  {
    path: 'authz',
    component: LayoutBlank,
    children: [
      {
        path: 'credential',
        component: CredentialComponent,
        data: { title: 'credential', titleI18n: 'app.login.login' }
      },
      {
        path: 'oauth2approve',
        component: Oauth2ApproveComponent,
        data: { title: 'credential', titleI18n: 'app.login.login' }
      },
      {
        path: 'mgt',
        component: AuthzMgtComponent,
        data: { title: 'mgt', titleI18n: 'app.login.login' }
      }
    ]
  }
];
