/*
 * Copyright [2025] [MaxKey of copyright http://www.maxkey.top]
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

import { ChangeDetectionStrategy, ChangeDetectorRef, Component, inject, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators, FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ReuseTabService } from '@delon/abc/reuse-tab';
import { SettingsService, _HttpClient } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';
import { finalize } from 'rxjs/operators';

import { AuthnService } from '../../../service/authn.service';
import { ImageCaptchaService } from '../../../service/image-captcha.service';
import { CONSTS } from '../../../shared/consts';
import { SHARED_IMPORTS } from '../../../shared/shared-imports';

@Component({
  selector: 'app-tfa',
  templateUrl: './tfa.component.html',
  imports: [...SHARED_IMPORTS],
  styleUrls: ['./tfa.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TfaComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly router = inject(Router);
  private readonly settingsService = inject(SettingsService);
  private readonly authnService = inject(AuthnService);
  private readonly imageCaptchaService = inject(ImageCaptchaService);
  private readonly route = inject(ActivatedRoute);
  private readonly msg = inject(NzMessageService);
  private readonly reuseTabService = inject(ReuseTabService, { optional: true });
  private readonly cdr = inject(ChangeDetectorRef);

  form: FormGroup = this.fb.group({
    userName: [null, [Validators.required]],
    password: [null, [Validators.required]],
    captcha: [null, [Validators.required]],
    mobile: [null, [Validators.required, Validators.pattern(/^1\d{10}$/)]],
    twoFactorMobile: [null, [Validators.required, Validators.pattern(/^1\d{10}$/)]],
    twoFactorEmail: [null, [Validators.required, Validators.pattern(/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/)]],
    otpCaptcha: [null, [Validators.required]],
    remember: [false]
  });
  error = '';
  secretKey = '';
  secretPublicKey = '';
  captchaType = '';
  twoFactorType = '0';
  twoFactorJwt = '';
  isFirstPasswordModify = 'N';
  state = '';
  defualtRedirectUri = '';
  count = 0;
  interval$: any;
  loading = false;

  ngOnInit(): void {
    this.authnService
      .get({ remember_me: localStorage.getItem(CONSTS.REMEMBER) })
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
          this.state = res.data.state;
          this.defualtRedirectUri = res.data.redirectUri;
          this.captchaType = res.data.captcha;
          this.secretKey = res.data.secretKey;
          this.secretPublicKey = res.data.secretPublicKey;
          this.isFirstPasswordModify = res.data.isFirstPasswordModify;
        }
      });
    let twoFactorData = JSON.parse(localStorage.getItem(CONSTS.TWO_FACTOR_DATA) || '');
    this.twoFactorType = twoFactorData.twoFactor;
    this.twoFactorJwt = twoFactorData.token;
    this.twoFactorMobile.setValue(twoFactorData.mobile);
    this.twoFactorEmail.setValue(twoFactorData.email);
  }

  get userName(): AbstractControl {
    return this.form.get('userName')!;
  }
  get password(): AbstractControl {
    return this.form.get('password')!;
  }
  get mobile(): AbstractControl {
    return this.form.get('mobile')!;
  }
  get captcha(): AbstractControl {
    return this.form.get('captcha')!;
  }

  get otpCaptcha(): AbstractControl {
    return this.form.get('otpCaptcha')!;
  }

  get remember(): AbstractControl {
    return this.form.get('remember')!;
  }

  get twoFactorMobile(): AbstractControl {
    return this.form.get('twoFactorMobile')!;
  }

  get twoFactorEmail(): AbstractControl {
    return this.form.get('twoFactorEmail')!;
  }

  sendTwoFactorOtpCode(): void {
    this.authnService.sendTwoFactorCode({ jwtToken: this.twoFactorJwt }).subscribe(res => {
      if (res.code !== 0) {
        this.msg.success(`发送失败`);
      }
    });
    this.count = 59;
    this.interval$ = setInterval(() => {
      this.count -= 1;
      if (this.count <= 0) {
        clearInterval(this.interval$);
      }
      this.cdr.detectChanges();
    }, 1000);
  }

  submit(): void {
    this.error = '';

    this.otpCaptcha.markAsDirty();
    this.otpCaptcha.updateValueAndValidity();

    localStorage.setItem(CONSTS.REMEMBER, this.form.get(CONSTS.REMEMBER)?.value);

    this.loading = true;
    this.cdr.detectChanges();
    this.authnService
      .login({
        authType: 'twoFactor',
        state: this.state,
        jwtToken: this.twoFactorJwt,
        otpCaptcha: this.otpCaptcha.value,
        remeberMe: this.remember.value
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
          this.error = res.msg;
        } else {
          localStorage.removeItem(CONSTS.TWO_FACTOR_DATA);
          // 清空路由复用信息
          this.reuseTabService?.clear();
          // 设置用户Token信息
          this.authnService.auth(res.data);
          this.authnService.navigate({
            defualtRedirectUri: this.defualtRedirectUri,
            isFirstPasswordModify: this.isFirstPasswordModify,
            passwordSetType: res.data.passwordSetType
          });
        }
        this.cdr.detectChanges();
      });
  }
}
