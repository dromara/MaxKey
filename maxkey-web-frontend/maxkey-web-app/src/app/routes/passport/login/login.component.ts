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

import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Inject, OnInit, OnDestroy, AfterViewInit, Optional } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { throwIfAlreadyLoaded } from '@core';
import { ReuseTabService } from '@delon/abc/reuse-tab';
import { SettingsService, _HttpClient } from '@delon/theme';
import { environment } from '@env/environment';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzTabChangeEvent } from 'ng-zorro-antd/tabs';
import { finalize } from 'rxjs/operators';

import { AuthnService } from '../../../service/authn.service';
import { ImageCaptchaService } from '../../../service/image-captcha.service';
import { QrCodeService } from '../../../service/qr-code.service';
import { SocialsProviderService } from '../../../service/socials-provider.service';
import { CONSTS } from '../../../shared/consts';

import { stringify } from 'querystring';

@Component({
  selector: 'passport-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserLoginComponent implements OnInit, OnDestroy {
  socials: {
    providers: NzSafeAny[];
    qrScan: string;
  } = {
    providers: [],
    qrScan: ''
  };

  form: FormGroup;
  error = '';
  switchTab = true;
  loginType = 'normal';
  loading = false;
  passwordVisible = false;
  qrexpire = false;
  imageCaptcha = '';
  captchaType = '';
  state = '';
  count = 0;
  interval$: any;
  //二维码内容
  ticket = '';

  constructor(
    fb: FormBuilder,
    private router: Router,
    private settingsService: SettingsService,
    private authnService: AuthnService,
    private socialsProviderService: SocialsProviderService,
    private imageCaptchaService: ImageCaptchaService,
    private qrCodeService: QrCodeService,
    @Optional()
    @Inject(ReuseTabService)
    private reuseTabService: ReuseTabService,
    private route: ActivatedRoute,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef
  ) {
    this.form = fb.group({
      userName: [null, [Validators.required]],
      password: [null, [Validators.required]],
      captcha: [null, [Validators.required]],
      mobile: [null, [Validators.required, Validators.pattern(/^1\d{10}$/)]],
      otpCaptcha: [null, [Validators.required]],
      remember: [false]
    });
  }

  ngOnInit(): void {
    //set redirect_uri , is BASE64URL
    if (this.route.snapshot.queryParams[CONSTS.REDIRECT_URI]) {
      this.authnService.setRedirectUri(this.route.snapshot.queryParams[CONSTS.REDIRECT_URI]);
    }

    //congress login
    if (this.route.snapshot.queryParams[CONSTS.CONGRESS]) {
      this.congressLogin(this.route.snapshot.queryParams[CONSTS.CONGRESS]);
    }

    //init socials,state
    this.authnService.clear();

    this.get();

    this.cdr.detectChanges();
  }

  get() {
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
          // 清空路由复用信息
          //console.log(res.data);
          //REMEMBER ME
          if (res.data.token) {
            // 清空路由复用信息
            this.reuseTabService.clear();
            // 设置用户Token信息
            this.authnService.auth(res.data);
            this.authnService.navigate({});
          } else {
            this.socials = res.data.socials;
            this.state = res.data.state;
            this.captchaType = res.data.captcha;
            if (this.captchaType === 'NONE') {
              //清除校验规则
              this.form.get('captcha')?.clearValidators();
            } else {
              //init image captcha
              this.getImageCaptcha();
            }
          }
        }
      });
  }

  congressLogin(congress: string) {
    this.authnService
      .congress({
        congress: congress
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
          // 清空路由复用信息
          this.reuseTabService.clear();
          // 设置用户Token信息
          this.authnService.auth(res.data);
          this.authnService.navigate({});
        }
      });
  }

  // #region fields

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

  // #endregion

  // #region get captcha
  getImageCaptcha(): void {
    this.imageCaptchaService.captcha({ state: this.state, captcha: this.captchaType }).subscribe(res => {
      if (res.code == 0) {
        this.imageCaptcha = res.data.image;
        this.cdr.detectChanges();
      } else {
        //令牌失效时，重新刷新页面
        window.location.reload();
      }
    });
  }

  //send sms
  sendOtpCode(): void {
    if (this.mobile.invalid) {
      this.mobile.markAsDirty({ onlySelf: true });
      this.mobile.updateValueAndValidity({ onlySelf: true });
      return;
    }
    this.authnService.produceOtp({ mobile: this.mobile.value }).subscribe(res => {
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

  // #endregion

  submit(): void {
    this.error = '';
    if (this.loginType === 'normal') {
      this.userName.markAsDirty();
      this.userName.updateValueAndValidity();
      this.password.markAsDirty();
      this.password.updateValueAndValidity();
      this.captcha.markAsDirty();
      this.captcha.updateValueAndValidity();
      if (this.userName.invalid || this.password.invalid || this.captcha.invalid) {
        return;
      }
    } else {
      this.mobile.markAsDirty();
      this.mobile.updateValueAndValidity();
      this.otpCaptcha.markAsDirty();
      this.otpCaptcha.updateValueAndValidity();
      if (this.mobile.invalid || this.otpCaptcha.invalid) {
        return;
      }
    }

    localStorage.setItem(CONSTS.REMEMBER, this.form.get(CONSTS.REMEMBER)?.value);

    this.loading = true;
    this.cdr.detectChanges();
    this.authnService
      .login({
        authType: this.loginType,
        state: this.state,
        username: this.userName.value,
        password: this.password.value,
        captcha: this.captcha.value,
        mobile: this.mobile.value,
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
          this.error = res.message;
          //this.msg.error(this.error);
          if (this.loginType === 'normal') {
            this.getImageCaptcha();
          }
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

  // #region social
  socialauth(provider: string): void {
    this.authnService.clearUser();
    this.socialsProviderService.authorize(provider).subscribe(res => {
      //console.log(res.data);
      window.location.href = res.data;
    });
  }

  /**
   * 获取二维码
   */
  getScanQrCode() {
    this.authnService.clearUser();
    console.log(`qrScan : ${this.socials.qrScan}`);
    if (this.socials.qrScan === 'workweixin' || this.socials.qrScan === 'dingtalk' || this.socials.qrScan === 'feishu') {
      this.socialsProviderService.scanqrcode(this.socials.qrScan).subscribe(res => {
        if (res.code === 0) {
          if (this.socials.qrScan === 'workweixin') {
            this.qrScanWorkweixin(res.data);
          } else if (this.socials.qrScan === 'dingtalk') {
            this.qrScanDingtalk(res.data);
          } else if (this.socials.qrScan === 'feishu') {
            this.qrScanFeishu(res.data);
          }
        }
      });
    } else {
      this.qrexpire = false;
      if (this.interval$) {
        clearInterval(this.interval$);
      }
      this.qrCodeService.getLoginQrCode().subscribe(res => {
        if (res.code === 0 && res.data.rqCode) {
          // 使用返回的 rqCode
          const qrImageElement = document.getElementById('div_qrcodelogin');
          this.ticket = res.data.ticket;
          if (qrImageElement) {
            qrImageElement.innerHTML = `<img src="${res.data.rqCode}" alt="QR Code" style="width: 200px; height: 200px;">`;
          }

          /*   // 设置5分钟后 qrexpire 为 false
        setTimeout(() => {
          this.qrexpire = true;
          this.cdr.detectChanges(); // 更新视图
        }, 5 * 60 * 1000); // 5 分钟*/
          this.scanQrCodeLogin();
        }
      });
    }
  }

  /**
   * 二维码轮询登录
   */
  scanQrCodeLogin() {
    const interval = setInterval(() => {
      this.qrCodeService
        .loginByQrCode({
          authType: 'scancode',
          code: this.ticket,
          state: this.state
        })
        .subscribe(res => {
          if (res.code === 0) {
            this.qrexpire = true;
            // 清空路由复用信息
            this.reuseTabService.clear();
            // 设置用户Token信息
            this.authnService.auth(res.data);
            this.authnService.navigate({});
          } else if (res.code === 20004) {
            this.qrexpire = true;
          } else if (res.code === 20005) {
            this.get();
          }

          // Handle response here

          // If you need to stop the interval after a certain condition is met,
          // you can clear the interval like this:
          if (this.qrexpire) {
            clearInterval(interval);
          }

          this.cdr.detectChanges(); // 更新视图
        });
    }, 5 * 1000); // 5 seconds
  }

  // #endregion

  ngOnDestroy(): void {
    if (this.interval$) {
      clearInterval(this.interval$);
    }
  }

  // #region QR Scan for workweixin, dingtalk ,feishu
  qrScanWorkweixin(data: any) {
    //see doc https://developer.work.weixin.qq.com/document/path/91025
    // @ts-ignore
    let wwLogin = new WwLogin({
      id: 'div_qrcodelogin',
      appid: data.clientId,
      agentid: data.agentId,
      redirect_uri: encodeURIComponent(data.redirectUri),
      state: data.state,
      href: 'data:text/css;base64,LmltcG93ZXJCb3ggLnFyY29kZSB7d2lkdGg6IDI1MHB4O30NCi5pbXBvd2VyQm94IC50aXRsZSB7ZGlzcGxheTogbm9uZTt9DQouaW1wb3dlckJveCAuaW5mbyB7d2lkdGg6IDI1MHB4O30NCi5zdGF0dXNfaWNvbiB7ZGlzcGxheTpub25lfQ0KLmltcG93ZXJCb3ggLnN0YXR1cyB7dGV4dC1hbGlnbjogY2VudGVyO30='
    });
  }

  qrScanFeishu(data: any) {
    //see doc https://open.feishu.cn/document/common-capabilities/sso/web-application-sso/qr-sdk-documentation
    //remove old div
    var qrcodeDiv = document.querySelector('#div_qrcodelogin');
    qrcodeDiv?.childNodes.forEach(function (value, index, array) {
      qrcodeDiv?.removeChild(value);
    });
    // @ts-ignore
    fsredirectUri = `https://passport.feishu.cn/suite/passport/oauth/authorize?client_id=${data.clientId}&redirect_uri=${encodeURIComponent(
      data.redirectUri
    )}&response_type=code&state=${data.state}`;
    // @ts-ignore
    var redirectUri = fsredirectUri;
    // @ts-ignore
    QRLoginObj = QRLogin({
      id: 'div_qrcodelogin',
      goto: redirectUri,
      width: '300',
      height: '300',
      style: 'border: 0;'
    });
  }

  qrScanDingtalk(data: any) {
    //see doc https://open.dingtalk.com/document/isvapp-server/scan-qr-code-to-log-on-to-third-party-websites
    var url = encodeURIComponent(data.redirectUri);
    var gotodingtalk = encodeURIComponent(
      `https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=${data.clientId}&response_type=code&scope=snsapi_login&state=${data.state}&redirect_uri=${url}`
    );
    // @ts-ignore
    ddredirect_uri = `https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=${data.clientId}&response_type=code&scope=snsapi_login&state=${data.state}&redirect_uri=${data.redirectUri}`;
    // @ts-ignore
    var obj = DDLogin({
      id: 'div_qrcodelogin', //这里需要你在自己的页面定义一个HTML标签并设置id，例如<div id="login_container"></div>或<span id="login_container"></span>
      goto: gotodingtalk, //请参考注释里的方式
      style: 'border:none;background-color:#FFFFFF;',
      width: '360',
      height: '400'
    });
  }

  // #region QR Scan end

  qrScanMaxkey(data: any) {
    // @ts-ignore
    document.getElementById('div_qrcodelogin').innerHTML = '';
    // @ts-ignore
    var qrcode = new QRCode('div_qrcodelogin', {
      width: 200,
      height: 200,
      colorDark: '#000000',
      colorLight: '#ffffff'
    }).makeCode(data.state);
    //3分钟监听二维码
    this.count = 90;
    this.interval$ = setInterval(() => {
      this.count -= 1;
      if (this.loginType != 'qrscan') {
        clearInterval(this.interval$);
      }
      if (this.count <= 0) {
        clearInterval(this.interval$);
      }
      //轮询发送监听请求
      this.socialsProviderService.qrcallback(this.socials.qrScan, data.state).subscribe(res => {
        if (res.code === 0) {
          // 清空路由复用信息
          this.reuseTabService.clear();
          // 设置用户Token信息
          this.authnService.auth(res.data);
          this.authnService.navigate({});
          clearInterval(this.interval$);
        } else if (res.code === 102) {
          // 二维码过期
          clearInterval(this.interval$);
          this.qrexpire = true;
          this.cdr.detectChanges();
        } else if (res.code === 103) {
          // 暂无用户扫码
        }
      });
      this.cdr.detectChanges();
    }, 2000);
  }
}
