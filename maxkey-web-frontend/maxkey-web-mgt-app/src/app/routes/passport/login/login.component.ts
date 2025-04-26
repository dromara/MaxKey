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

import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, Inject, OnDestroy, Optional } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { StartupService } from '@core';
import { ReuseTabService } from '@delon/abc/reuse-tab';
import { DA_SERVICE_TOKEN, ITokenService } from '@delon/auth';
import { SettingsService, _HttpClient, User } from '@delon/theme';
import { environment } from '@env/environment';
import { NzTabChangeEvent } from 'ng-zorro-antd/tabs';
import { finalize } from 'rxjs/operators';

import { AuthnService } from '../../../service/authn.service';
import { ImageCaptchaService } from '../../../service/image-captcha.service';

@Component({
  selector: 'passport-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserLoginComponent implements OnInit, OnDestroy {
  constructor(
    fb: FormBuilder,
    @Optional()
    @Inject(ReuseTabService)
    private reuseTabService: ReuseTabService,
    private authnService: AuthnService,
    private imageCaptchaService: ImageCaptchaService,
    private cdr: ChangeDetectorRef
  ) {
    this.form = fb.group({
      userName: [null, [Validators.required]],
      password: [null, [Validators.required]],
      captcha: [null, [Validators.required]]
    });
  }

  // #region fields

  get userName(): AbstractControl {
    return this.form.get('userName')!;
  }
  get password(): AbstractControl {
    return this.form.get('password')!;
  }
  get captcha(): AbstractControl {
    return this.form.get('captcha')!;
  }
  form: FormGroup;
  error = '';
  type = 0;
  loading = false;
  state = '';
  captchaType = '';
  imageCaptcha = '';
  passwordVisible = false;

  // #region get captcha

  count = 0;
  interval$: any;

  // #endregion

  ngOnInit(): void {
    //init socials,state
    this.authnService.clear();
    this.authnService
      .get({})
      .pipe(
        finalize(() => {
          this.loading = false;
          this.cdr.detectChanges();
        })
      )
      .subscribe(res => {
        this.loading = true;
        if (res.code !== 0) {
          this.error = res.msg;
        } else {
          // 清空路由复用信息
          //console.log(res.data);
          this.state = res.data.state;
          this.captchaType = res.data.captcha;
          //init image captcha
          this.getImageCaptcha();
        }
      });
  }

  switch({ index }: NzTabChangeEvent): void {
    this.type = index!;
  }

  getImageCaptcha(): void {
    this.imageCaptchaService.captcha({ state: this.state, captcha: this.captchaType }).subscribe(res => {
      if (res.code === 0) {
        this.imageCaptcha = res.data.image;
        this.cdr.detectChanges();
      } else {
        //令牌失效时，重新刷新页面
        window.location.reload();
      }
    });
  }

  getCaptcha(): void {
    this.count = 59;
    this.interval$ = setInterval(() => {
      this.count -= 1;
      if (this.count <= 0) {
        clearInterval(this.interval$);
      }
    }, 1000);
  }

  // #endregion

  submit(): void {
    this.error = '';
    this.userName.markAsDirty();
    this.userName.updateValueAndValidity();
    this.password.markAsDirty();
    this.password.updateValueAndValidity();
    this.captcha.markAsDirty();
    this.captcha.updateValueAndValidity();
    if (this.userName.invalid || this.password.invalid || this.captcha.invalid) {
      return;
    }

    // 默认配置中对所有HTTP请求都会强制 [校验](https://ng-alain.com/auth/getting-started) 用户 Token
    // 然一般来说登录请求不需要校验，因此可以在请求URL加上：`/login?_allow_anonymous=true` 表示不触发用户 Token 校验
    this.loading = true;
    this.cdr.detectChanges();
    this.authnService
      .login({
        authType: 'normal',
        state: this.state,
        username: this.userName.value,
        password: this.password.value,
        captcha: this.captcha.value
      })
      .pipe(
        finalize(() => {
          this.loading = false;
          this.cdr.detectChanges();
        })
      )
      .subscribe(res => {
        this.loading = true;
        if (res.code !== 0) {
          this.error = res.message;
          //this.msg.success(`登录失败，请重新登录！`);
          this.getImageCaptcha();
        } else {
          // 清空路由复用信息
          this.reuseTabService.clear();
          // 设置用户Token信息
          this.authnService.auth(res.data);
          this.authnService.navigate({});
        }
        this.cdr.detectChanges();
      });
  }

  ngOnDestroy(): void {
    if (this.interval$) {
      clearInterval(this.interval$);
    }
  }
}
