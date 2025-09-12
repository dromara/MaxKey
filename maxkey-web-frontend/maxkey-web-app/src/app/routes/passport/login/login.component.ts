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
    private cdr: ChangeDetectorRef,
    private http: _HttpClient
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
          if (res.data.twoFactor === '0') {
            // 设置用户Token信息
            this.authnService.auth(res.data);
            this.authnService.navigate({});
          } else {
            localStorage.setItem(CONSTS.TWO_FACTOR_DATA, JSON.stringify(res.data));
            this.router.navigateByUrl('/passport/tfa');
          }
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

  /**
   * Passkey 无用户名登录
   */
  async passkeyLogin(): Promise<void> {
    console.log('=== PASSKEY LOGIN DEBUG START ===');
    console.log('Passkey usernameless login clicked at:', new Date().toISOString());
    
    try {
      // 检查浏览器是否支持 WebAuthn
      if (!window.PublicKeyCredential) {
        console.error('WebAuthn not supported');
        this.msg.error('您的浏览器不支持 WebAuthn/Passkey 功能');
        return;
      }
      console.log('WebAuthn support confirmed');

      this.loading = true;
      this.cdr.detectChanges();
      
      // 1. 调用后端 API 获取认证选项（不传递任何用户信息）
      console.log('Step 1: Requesting authentication options from backend...');
      let authOptionsResponse;
      try {
        authOptionsResponse = await this.http.post<any>('/passkey/authentication/begin?_allow_anonymous=true', {}).toPromise();
      } catch (httpError: any) {
        console.error('HTTP error occurred:', httpError);
        // 处理HTTP错误，提取错误信息
        let errorMessage = '获取认证选项失败';
        if (httpError.error && httpError.error.message) {
          errorMessage = httpError.error.message;
        } else if (httpError.message) {
          errorMessage = httpError.message;
        }
        
        // 检查是否是没有注册 Passkey 的错误
        if (errorMessage.includes('没有注册任何 Passkey') || 
            errorMessage.includes('No Passkeys registered') ||
            errorMessage.includes('还没有注册任何 Passkey') ||
            errorMessage.includes('系统中还没有注册任何 Passkey')) {
          // 直接显示友好提示并返回，不抛出错误避免被全局拦截器捕获
          this.msg.warning('还未注册 Passkey，请注册 Passkey');
          console.log('=== PASSKEY LOGIN DEBUG END ===');
          return;
        }
        throw new Error(errorMessage);
      }
      
      console.log('Backend auth options response:', authOptionsResponse);
      
      if (!authOptionsResponse || authOptionsResponse.code !== 0) {
        console.error('Failed to get auth options:', authOptionsResponse);
        // 检查是否是没有注册 Passkey 的错误
        const errorMessage = authOptionsResponse?.message || '获取认证选项失败';
        if (errorMessage.includes('没有注册任何 Passkey') || 
            errorMessage.includes('No Passkeys registered') ||
            errorMessage.includes('还没有注册任何 Passkey') ||
            errorMessage.includes('系统中还没有注册任何 Passkey')) {
          // 直接显示友好提示并返回，不抛出错误避免被全局拦截器捕获
          this.msg.warning('还未注册 Passkey，请注册 Passkey');
          console.log('=== PASSKEY LOGIN DEBUG END ===');
          return;
        }
        throw new Error(errorMessage);
      }
      
      const authOptions = authOptionsResponse.data;
      console.log('Auth options received:', authOptions);
      
      // 检查返回的数据是否有效
      if (!authOptions || !authOptions.challenge) {
        console.error('Invalid auth options:', authOptions);
        throw new Error('服务器返回的认证选项无效');
      }
      
      // 2. 转换认证选项格式
      console.log('Step 2: Converting authentication options...');
      const convertedOptions: PublicKeyCredentialRequestOptions = {
        challenge: this.base64ToArrayBuffer(authOptions.challenge),
        timeout: authOptions.timeout || 60000,
        rpId: authOptions.rpId,
        userVerification: authOptions.userVerification || 'preferred'
        // 注意：不设置 allowCredentials，让认证器自动选择可用的凭据
      };
      console.log('Converted options:', {
        challengeLength: convertedOptions.challenge.byteLength,
        timeout: convertedOptions.timeout,
        rpId: convertedOptions.rpId,
        userVerification: convertedOptions.userVerification,
        allowCredentials: convertedOptions.allowCredentials || 'undefined (auto-select)'
      });

      // 3. 调用 WebAuthn API 进行认证
      console.log('Step 3: Calling WebAuthn API navigator.credentials.get()...');
      console.log('Available authenticators will be queried automatically');
      
      const credential = await navigator.credentials.get({
        publicKey: convertedOptions
      }) as PublicKeyCredential;

      if (!credential) {
        console.error('No credential returned from WebAuthn API');
        throw new Error('认证失败');
      }
      
      console.log('=== CREDENTIAL DEBUG INFO ===');
      console.log('Credential ID:', credential.id);
      console.log('Credential ID length:', credential.id.length);
      console.log('Credential type:', credential.type);
      console.log('Credential rawId length:', credential.rawId.byteLength);
      console.log('Credential rawId as base64:', this.arrayBufferToBase64(credential.rawId));
      
      // 验证 credential.id 和 rawId 的一致性
      const rawIdBase64 = this.arrayBufferToBase64(credential.rawId);
      console.log('ID consistency check:');
      console.log('  credential.id:', credential.id);
      console.log('  rawId as base64:', rawIdBase64);
      console.log('  IDs match:', credential.id === rawIdBase64);
      
      const credentialResponse = credential.response as AuthenticatorAssertionResponse;
      console.log('Authenticator response type:', credentialResponse.constructor.name);
      console.log('User handle:', credentialResponse.userHandle ? this.arrayBufferToBase64(credentialResponse.userHandle) : 'null');
      console.log('=== END CREDENTIAL DEBUG INFO ===');

      // 4. 将认证结果发送到后端验证
      console.log('Step 4: Sending credential to backend for verification...');
      const requestPayload = {
        challengeId: authOptions.challengeId,
        credentialId: credential.id,
        authenticatorData: this.arrayBufferToBase64(credentialResponse.authenticatorData),
        clientDataJSON: this.arrayBufferToBase64(credentialResponse.clientDataJSON),
        signature: this.arrayBufferToBase64(credentialResponse.signature),
        userHandle: credentialResponse.userHandle ? this.arrayBufferToBase64(credentialResponse.userHandle) : null
      };
      console.log('Request payload to backend:', {
        challengeId: requestPayload.challengeId,
        credentialId: requestPayload.credentialId,
        credentialIdLength: requestPayload.credentialId.length,
        authenticatorDataLength: requestPayload.authenticatorData.length,
        clientDataJSONLength: requestPayload.clientDataJSON.length,
        signatureLength: requestPayload.signature.length,
        userHandle: requestPayload.userHandle
      });
      
      const finishResponse = await this.http.post<any>('/passkey/authentication/finish?_allow_anonymous=true', requestPayload).toPromise();
      console.log('Backend finish response:', finishResponse);
      
      if (!finishResponse || finishResponse.code !== 0) {
        console.error('Backend verification failed:', finishResponse);
        throw new Error(finishResponse?.message || 'Passkey认证失败');
      }

      // 5. 认证成功，设置用户信息并跳转
      console.log('Step 5: Authentication successful, setting user info...');
      const authResult = finishResponse.data;
      console.log('Auth result received:', authResult);
      
      this.msg.success(`Passkey 登录成功！欢迎 ${authResult.username || '用户'}`);
      
      // 清空路由复用信息
      console.log('Clearing reuse tab service...');
      this.reuseTabService.clear();
      
      // 设置用户Token信息
      if (authResult && authResult.userId) {
        console.log('Valid auth result with userId:', authResult.userId);
        // 构建完整的认证信息对象，包含 SimpleGuard 所需的 token 和 ticket
        const userInfo = {
          id: authResult.userId,
          userId: authResult.userId,
          username: authResult.username,
          displayName: authResult.displayName || authResult.username,
          email: authResult.email || '',
          authTime: authResult.authTime,
          authType: 'passkey',
          // 关键：包含认证所需的 token 和 ticket
          token: authResult.token || authResult.congress || '',
          ticket: authResult.ticket || authResult.onlineTicket || '',
          // 其他可能需要的字段
          remeberMe: false,
          passwordSetType: authResult.passwordSetType || 'normal',
          authorities: authResult.authorities || []
        };
        
        console.log('Setting auth info:', userInfo);
        
        // 设置认证信息
        this.authnService.auth(userInfo);
        
        // 使用 navigate 方法进行跳转，它会处理 StartupService 的重新加载
        console.log('Navigating with auth result...');
        this.authnService.navigate(authResult);
        console.log('=== PASSKEY LOGIN SUCCESS ===');
      } else {
        console.error('Invalid auth result - missing userId:', authResult);
        throw new Error('认证成功但用户数据无效');
      }
      
    } catch (error: any) {
      console.error('=== PASSKEY LOGIN ERROR ===');
      console.error('Error type:', error.constructor.name);
      console.error('Error message:', error.message);
      console.error('Error stack:', error.stack);
      console.error('Full error object:', error);
      
      // 检查是否是没有注册 Passkey 的错误
      if (error.message && (error.message.includes('PASSKEY_NOT_REGISTERED') ||
                           error.message.includes('没有找到可用的凭据') || 
                           error.message.includes('No credentials available') ||
                           error.message.includes('用户未注册') ||
                           error.message.includes('credential not found') ||
                           error.message.includes('没有注册任何 Passkey') ||
                           error.message.includes('No Passkeys registered') ||
                           error.message.includes('还没有注册任何 Passkey'))) {
        this.msg.warning('还未注册 Passkey，请注册 Passkey');
        console.log('=== PASSKEY LOGIN DEBUG END ===');
        return;
      }
      
      // 如果是 WebAuthn 相关错误，提供更详细的信息
      if (error.name) {
        console.error('WebAuthn error name:', error.name);
        switch (error.name) {
          case 'NotAllowedError':
            console.error('User cancelled the operation or timeout occurred');
            // 检查是否是因为没有可用凭据导致的取消
            this.msg.warning('Passkey 登录已取消。如果您还没有注册 Passkey，请先注册后再使用');
            break;
          case 'SecurityError':
            console.error('Security error - invalid domain or HTTPS required');
            this.msg.error('安全错误：请确保在 HTTPS 环境下使用 Passkey 功能');
            break;
          case 'NotSupportedError':
            console.error('Operation not supported by authenticator');
            this.msg.error('您的设备不支持 Passkey 功能');
            break;
          case 'InvalidStateError':
            console.error('Authenticator is in invalid state');
            this.msg.error('认证器状态异常，请重试');
            break;
          case 'ConstraintError':
            console.error('Constraint error in authenticator');
            this.msg.error('认证器约束错误，请重试');
            break;
          default:
            console.error('Unknown WebAuthn error');
            this.msg.error('Passkey 登录失败：' + (error.message || '请重试或使用其他登录方式'));
        }
      } else {
        this.msg.error('Passkey 登录失败：' + (error.message || '请重试或使用其他登录方式'));
      }
      console.log('=== PASSKEY LOGIN DEBUG END ===');
    } finally {
      this.loading = false;
      this.cdr.detectChanges();
      console.log('Login loading state reset');
    }
  }

  // 添加辅助方法
  private base64ToArrayBuffer(base64: string): ArrayBuffer {
    const binaryString = atob(base64.replace(/-/g, '+').replace(/_/g, '/'));
    const bytes = new Uint8Array(binaryString.length);
    for (let i = 0; i < binaryString.length; i++) {
      bytes[i] = binaryString.charCodeAt(i);
    }
    return bytes.buffer;
  }

  private arrayBufferToBase64(buffer: ArrayBuffer): string {
    const bytes = new Uint8Array(buffer);
    let binary = '';
    for (let i = 0; i < bytes.byteLength; i++) {
      binary += String.fromCharCode(bytes[i]);
    }
    return btoa(binary).replace(/\+/g, '-').replace(/\//g, '_').replace(/=/g, '');
  }
}
