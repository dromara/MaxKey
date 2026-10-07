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

import { AccountsStrategyComponent } from './accounts-strategy/accounts-strategy.component';
import { AdaptersComponent } from './adapters/adapters.component';
import { ConnectorsComponent } from './connectors/connectors.component';
import { EmailSendersComponent } from './email-senders/email-senders.component';
import { InstitutionsComponent } from './institutions/institutions.component';
import { LdapContextComponent } from './ldap-context/ldap-context.component';
import { NoticesComponent } from './notices/notices.component';
import { PasswordPolicyComponent } from './password-policy/password-policy.component';
import { SmsProviderComponent } from './sms-provider/sms-provider.component';
import { SocialsProviderComponent } from './socials-provider/socials-provider.component';
import { SynchronizersComponent } from './synchronizers/synchronizers.component';

export const routes: Routes = [
  {
    path: 'passwordpolicy',
    component: PasswordPolicyComponent
  },
  {
    path: 'emailsender',
    component: EmailSendersComponent
  },
  {
    path: 'ldapcontext',
    component: LdapContextComponent
  },
  {
    path: 'smsprovider',
    component: SmsProviderComponent
  },
  {
    path: 'adapters',
    component: AdaptersComponent
  },
  {
    path: 'socialsproviders',
    component: SocialsProviderComponent
  },
  {
    path: 'synchronizers',
    component: SynchronizersComponent
  },
  {
    path: 'connectors',
    component: ConnectorsComponent
  },
  {
    path: 'accountsstrategys',
    component: AccountsStrategyComponent
  },
  {
    path: 'institutions',
    component: InstitutionsComponent
  },
  {
    path: 'notices',
    component: NoticesComponent
  },
  {
    path: 'config',
    children: [{ path: 'passwordpolicy', component: PasswordPolicyComponent }]
  }
];
