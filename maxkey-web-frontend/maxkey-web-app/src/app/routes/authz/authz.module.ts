import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '@shared';

import { LayoutBlankComponent } from '../../layout/blank/blank.component';
import { CredentialComponent } from './credential/credential.component';
import { Oauth2ApproveComponent } from './oauth2-approve/oauth2-approve.component';
const routes: Routes = [
  /*
  {
    path: 'audit-logins',
    component: AuditLoginsComponent
  }
  */
  {
    path: 'authz',
    component: LayoutBlankComponent,
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
      }
    ]
  }
];

const COMPONENTS = [CredentialComponent];

@NgModule({
  declarations: [...COMPONENTS, Oauth2ApproveComponent],
  imports: [SharedModule, CommonModule, RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthzModule { }
