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
import { RouterModule, Routes } from '@angular/router';

import { LayoutPassportComponent } from '../../layout/passport/passport.component';
import { CallbackComponent } from './callback.component';
import { ForgotComponent } from './forgot/forgot.component';
import { JwtAuthComponent } from './jwt-auth.component';
import { UserLockComponent } from './lock/lock.component';
import { UserLoginComponent } from './login/login.component';
import { LogoutComponent } from './logout.component';
import { UserRegisterResultComponent } from './register-result/register-result.component';
import { UserRegisterComponent } from './register/register.component';
import { TfaComponent } from './tfa/tfa.component';

const routes: Routes = [
  // passport
  {
    path: 'passport',
    component: LayoutPassportComponent,
    children: [
      {
        path: 'login',
        component: UserLoginComponent,
        data: { title: '登录', titleI18n: 'app.login.login' }
      },
      {
        path: 'tfa',
        component: TfaComponent,
        data: { title: '登录二次认证', titleI18n: 'app.login.login' }
      },
      {
        path: 'register',
        component: UserRegisterComponent,
        data: { title: '注册', titleI18n: 'app.register.register' }
      },
      {
        path: 'register-result',
        component: UserRegisterResultComponent,
        data: { title: '注册结果', titleI18n: 'app.register.register' }
      },
      {
        path: 'forgot',
        component: ForgotComponent,
        data: { title: '忘记密码', titleI18n: 'app.forgot.forgot' }
      },
      {
        path: 'lock',
        component: UserLockComponent,
        data: { title: '锁屏', titleI18n: 'app.lock' }
      }
    ]
  },
  // 单页不包裹Layout
  { path: 'passport/callback/:provider', component: CallbackComponent },
  { path: 'passport/jwt/auth', component: JwtAuthComponent },
  { path: 'passport/logout', component: LogoutComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PassportRoutingModule {}
