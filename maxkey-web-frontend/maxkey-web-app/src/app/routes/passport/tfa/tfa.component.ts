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

import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  Inject,
  inject,
  OnInit,
  OnDestroy,
  AfterViewInit,
  Optional
} from '@angular/core';
import { AbstractControl, ReactiveFormsModule, FormBuilder, FormGroup, Validators, FormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { ReuseTabService } from '@delon/abc/reuse-tab';
import { SettingsService, _HttpClient, I18nPipe } from '@delon/theme';
import { environment } from '@env/environment';
import { NzAlertModule } from 'ng-zorro-antd/alert';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzRadioModule } from 'ng-zorro-antd/radio';
import { NzTabChangeEvent, NzTabsModule } from 'ng-zorro-antd/tabs';
import { NzToolTipModule } from 'ng-zorro-antd/tooltip';
import { finalize } from 'rxjs/operators';

import { AuthnService } from '../../../service/authn.service';
import { ImageCaptchaService } from '../../../service/image-captcha.service';
import { CONSTS } from '../../../shared/consts';

@Component({
  selector: 'app-tfa',
  templateUrl: './tfa.component.html',
  styleUrls: ['./tfa.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TfaComponent implements OnInit {
  form: FormGroup;
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
  constructor(
    fb: FormBuilder,
    private router: Router,
    private settingsService: SettingsService,
    private authnService: AuthnService,
    private imageCaptchaService: ImageCaptchaService,
    private route: ActivatedRoute,
    private msg: NzMessageService,
    @Optional()
    @Inject(ReuseTabService)
    private reuseTabService: ReuseTabService,
    private cdr: ChangeDetectorRef
  ) {
    this.form = fb.group({
      userName: [null, [Validators.required]],
      password: [null, [Validators.required]],
      captcha: [null, [Validators.required]],
      mobile: [null, [Validators.required, Validators.pattern(/^1\d{10}$/)]],
      twoFactorMobile: [null, [Validators.required, Validators.pattern(/^1\d{10}$/)]],
      twoFactorEmail: [null, [Validators.required, Validators.pattern(/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/)]],
      otpCaptcha: [null, [Validators.required]],
      remember: [false]
    });
  }

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
