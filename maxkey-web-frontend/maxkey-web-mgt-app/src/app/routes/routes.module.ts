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
import { SharedModule } from '@shared';

import { AccountEditerComponent } from './accounts/account-editer/account-editer.component';
import { AccountsComponent } from './accounts/accounts.component';
import { AppBasicDetailsEditerComponent } from './apps/app-basic-details-editer/app-basic-details-editer.component';
import { AppCasDetailsEditerComponent } from './apps/app-cas-details-editer/app-cas-details-editer.component';
import { AppExtendApiDetailsEditerComponent } from './apps/app-extend-api-details-editer/app-extend-api-details-editer.component';
import { AppFormBasedDetailsEditerComponent } from './apps/app-form-based-details-editer/app-form-based-details-editer.component';
import { AppJwtDetailsEditerComponent } from './apps/app-jwt-details-editer/app-jwt-details-editer.component';
import { AppOauth20DetailsEditerComponent } from './apps/app-oauth20-details-editer/app-oauth20-details-editer.component';
import { AppSaml20DetailsEditerComponent } from './apps/app-saml20-details-editer/app-saml20-details-editer.component';
import { AppTokenBasedDetailsEditerComponent } from './apps/app-token-based-details-editer/app-token-based-details-editer.component';
import { AppsComponent } from './apps/apps.component';
import { SelectAppsComponent } from './apps/select-apps/select-apps.component';
import { OrganizationEditerComponent } from './organizations/organization-editer/organization-editer.component';
import { OrganizationsComponent } from './organizations/organizations.component';
import { RouteRoutingModule } from './routes-routing.module';
import { PasswordComponent } from './users/password/password.component';
import { SelectUserComponent } from './users/select-user/select-user.component';
import { UserEditerComponent } from './users/user-editer/user-editer.component';
import { UsersComponent } from './users/users.component';

const COMPONENTS: Array<Type<null>> = [];

@NgModule({
  imports: [SharedModule, RouteRoutingModule],
  declarations: [
    ...COMPONENTS,
    UsersComponent,
    AppsComponent,
    AccountsComponent,
    OrganizationsComponent,
    SelectAppsComponent,
    OrganizationEditerComponent,
    UserEditerComponent,
    AccountEditerComponent,
    SelectUserComponent,
    AppCasDetailsEditerComponent,
    AppFormBasedDetailsEditerComponent,
    AppJwtDetailsEditerComponent,
    AppOauth20DetailsEditerComponent,
    AppSaml20DetailsEditerComponent,
    AppTokenBasedDetailsEditerComponent,
    AppExtendApiDetailsEditerComponent,
    AppBasicDetailsEditerComponent,
    PasswordComponent
  ]
})
export class RoutesModule { }
