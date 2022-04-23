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

import { AccountsStrategyEditerComponent } from './accounts-strategy/accounts-strategy-editer/accounts-strategy-editer.component';
import { AccountsStrategyComponent } from './accounts-strategy/accounts-strategy.component';
import { SelectAccountsStrategyComponent } from './accounts-strategy/select-accounts-strategy/select-accounts-strategy.component';
import { AdapterEditerComponent } from './adapters/adapter-editer/adapter-editer.component';
import { AdaptersComponent } from './adapters/adapters.component';
import { SelectAdaptersComponent } from './adapters/select-adapters/select-adapters.component';
import { EmailSendersComponent } from './email-senders/email-senders.component';
import { InstitutionsComponent } from './institutions/institutions.component';
import { LdapContextComponent } from './ldap-context/ldap-context.component';
import { NoticesComponent } from './notices/notices.component';
import { PasswordPolicyComponent } from './password-policy/password-policy.component';
import { SmsProviderComponent } from './sms-provider/sms-provider.component';
import { SocialsProviderEditerComponent } from './socials-provider/socials-provider-editer/socials-provider-editer.component';
import { SocialsProviderComponent } from './socials-provider/socials-provider.component';
import { SynchronizerEditerComponent } from './synchronizers/synchronizer-editer/synchronizer-editer.component';
import { SynchronizersComponent } from './synchronizers/synchronizers.component';

const routes: Routes = [
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

const COMPONENTS = [PasswordPolicyComponent, EmailSendersComponent, LdapContextComponent, SmsProviderComponent];

@NgModule({
  declarations: [
    ...COMPONENTS,
    AdaptersComponent,
    AdapterEditerComponent,
    SocialsProviderComponent,
    SocialsProviderEditerComponent,
    SynchronizersComponent,
    SynchronizerEditerComponent,
    AccountsStrategyComponent,
    AccountsStrategyEditerComponent,
    InstitutionsComponent,
    NoticesComponent,
    SelectAccountsStrategyComponent,
    SelectAdaptersComponent
  ],
  imports: [SharedModule, CommonModule, RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConfigModule { }
