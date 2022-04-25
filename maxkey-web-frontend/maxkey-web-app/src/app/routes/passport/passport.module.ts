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

const COMPONENTS = [UserLoginComponent, UserRegisterResultComponent, UserRegisterComponent, UserLockComponent, CallbackComponent];

@NgModule({
  imports: [SharedModule, PassportRoutingModule, NzStepsModule],
  declarations: [...COMPONENTS, ForgotComponent]
})
export class PassportModule { }
