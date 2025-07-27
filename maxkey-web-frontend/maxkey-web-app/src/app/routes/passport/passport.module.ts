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
import { NzStepsModule } from 'ng-zorro-antd/steps';

import { CallbackComponent } from './callback.component';
import { ForgotComponent } from './forgot/forgot.component';
import { UserLockComponent } from './lock/lock.component';
import { UserLoginComponent } from './login/login.component';
import { PassportRoutingModule } from './passport-routing.module';
import { UserRegisterResultComponent } from './register-result/register-result.component';
import { UserRegisterComponent } from './register/register.component';
import { SocialsProviderBindUserComponent } from './socials-provider-bind-user/socials-provider-bind-user.component';
import { TfaComponent } from './tfa/tfa.component';

const COMPONENTS = [
  TfaComponent,
  SocialsProviderBindUserComponent,
  UserLoginComponent,
  UserRegisterResultComponent,
  UserRegisterComponent,
  UserLockComponent,
  CallbackComponent
];

@NgModule({
  imports: [SharedModule, PassportRoutingModule, NzStepsModule],
  declarations: [...COMPONENTS, ForgotComponent]
})
export class PassportModule {}
