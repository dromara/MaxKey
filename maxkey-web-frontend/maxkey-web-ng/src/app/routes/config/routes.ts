/*
 * Copyright [2026] [MaxKey of copyright http://www.maxkey.top]
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

import { MfaComponent } from './mfa/mfa.component';
import { PasskeyComponent } from './passkey/passkey.component';
import { PasswordComponent } from './password/password.component';
import { ProfileComponent } from './profile/profile.component';
import { SocialsAssociateComponent } from './socials-associate/socials-associate.component';
import { TimebasedComponent } from './timebased/timebased.component';

export const routes: Routes = [
  {
    path: 'profile',
    component: ProfileComponent
  },
  {
    path: 'password',
    component: PasswordComponent
  },
  {
    path: 'passkey',
    component: PasskeyComponent
  },
  {
    path: 'socialsassociate',
    component: SocialsAssociateComponent
  },
  {
    path: 'timebased',
    component: TimebasedComponent
  },
  {
    path: 'mfa',
    component: MfaComponent
  }
];
