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

import { Component, OnInit, ChangeDetectorRef, Inject } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { I18NService } from '@core';
import { ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzStepsModule } from 'ng-zorro-antd/steps';

import { ChangePassword } from '../../../entity/ChangePassword';
import { ForgotPasswordService } from '../../../service/forgot-password.service';
import { ImageCaptchaService } from '../../../service/image-captcha.service';
import {PasswordService} from "../../../service/password.service";

@Component({
  selector: 'app-forgot',
  templateUrl: './forgot.component.html',
  styleUrls: ['./forgot.component.less']
})
export class ForgotComponent implements OnInit {
  form: {
    submitting: boolean;
    model: ChangePassword;
  } = {
    submitting: false,
    model: new ChangePassword()
  };

  imageCaptcha = '';
  formGroup: FormGroup;
  state = '';
  step = 0;
  forgotType = 'mobile';
  passwordVisible = false;
  loading = false;
  count = 0;
  interval$: any;
  userId = '';
  username = '';
  policyMessage:any[] =[];
  policy:any = {};
  dynamicallyCheckPasswordErrorMsg:string = '';
  constructor(
    fb: FormBuilder,
    private router: Router,
    private forgotPasswordService: ForgotPasswordService,
    private imageCaptchaService: ImageCaptchaService,
    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    private cdr: ChangeDetectorRef
  ) {
    this.formGroup = fb.group({
      mobile: [null, [Validators.required, Validators.pattern(/^1\d{10}$/)]],
      email: [null, [Validators.required]],
      password: [null, [Validators.required]],
      captcha: [null, [Validators.required]],
      otpCaptcha: [null, [Validators.required]],
      confirmPassword: [null, [Validators.required]]
    });
  }

  get email(): AbstractControl {
    return this.formGroup.get('email')!;
  }
  get password(): AbstractControl {
    return this.formGroup.get('password')!;
  }
  get confirmPassword(): AbstractControl {
    return this.formGroup.get('confirmPassword')!;
  }
  get mobile(): AbstractControl {
    return this.formGroup.get('mobile')!;
  }
  get captcha(): AbstractControl {
    return this.formGroup.get('captcha')!;
  }

  get otpCaptcha(): AbstractControl {
    return this.formGroup.get('otpCaptcha')!;
  }

  ngOnInit(): void {
    this.getImageCaptcha();
    this.policyMessage=[];
    this.forgotPasswordService.passwordpolicy().subscribe(res => {
      if(res.code == 0) {
        let data = res.data;
        this.policy = data;
        this.policyMessage=res.data.policMessageList;
      }
    });
  }

  getImageCaptcha(): void {
    this.imageCaptchaService.captcha({}).subscribe(res => {
      this.imageCaptcha = res.data.image;
      this.state = res.data.state;
      this.cdr.detectChanges();
    });
  }

  //send sms
  sendOtpCode(): void {
    if (this.forgotType == 'mobile' && this.mobile.invalid) {
      this.mobile.markAsDirty({ onlySelf: true });
      this.mobile.updateValueAndValidity({ onlySelf: true });
      return;
    }

    if (this.forgotType == 'email' && this.email.invalid) {
      this.email.markAsDirty({ onlySelf: true });
      this.email.updateValueAndValidity({ onlySelf: true });
      return;
    }
    if (this.forgotType == 'mobile') {
      this.forgotPasswordService
        .produceOtp({ mobile: this.mobile.value, state: this.state, captcha: this.captcha.value })
        .subscribe(res => {
          if (res.code == 103) {
            this.msg.error(this.i18n.fanyi('validation.forgot.captcha.error'));
            this.getImageCaptcha();
            this.cdr.detectChanges();
            return;
          }
          if (res.code != 0) {
            this.msg.error(this.i18n.fanyi('mxk.alert.operate.error'));
            this.getImageCaptcha();
            this.cdr.detectChanges();
            return;
          }
          this.userId = res.data.userId;
          this.username = res.data.username;
          this.count = 59;
          this.interval$ = setInterval(() => {
            this.count -= 1;
            if (this.count <= 0) {
              clearInterval(this.interval$);
            }
            this.cdr.detectChanges();
          }, 1000);
        });
    } else if (this.forgotType == 'email') {
      this.forgotPasswordService
        .produceEmailOtp({ email: this.email.value, state: this.state, captcha: this.captcha.value })
        .subscribe(res => {
          if (res.code == 103) {
            this.msg.error(this.i18n.fanyi('validation.forgot.captcha.error'));
            this.getImageCaptcha();
            this.cdr.detectChanges();
            return;
          }
          if (res.code != 0) {
            this.msg.error(this.i18n.fanyi('mxk.alert.operate.error'));
            this.getImageCaptcha();
            this.cdr.detectChanges();
            return;
          }
          this.userId = res.data.userId;
          this.username = res.data.username;
          this.count = 59;
          this.interval$ = setInterval(() => {
            this.count -= 1;
            if (this.count <= 0) {
              clearInterval(this.interval$);
            }
            this.cdr.detectChanges();
          }, 1000);
          //console.log(res.data);
        });
    }

  }

  onNextReset(e: MouseEvent) {
    if (this.forgotType == 'mobile' && this.mobile.invalid) {
      this.mobile.markAsDirty({ onlySelf: true });
      this.mobile.updateValueAndValidity({ onlySelf: true });
      return;
    }

    if (this.forgotType == 'email' && this.email.invalid) {
      this.email.markAsDirty({ onlySelf: true });
      this.email.updateValueAndValidity({ onlySelf: true });
      return;
    }

    if (this.captcha.invalid) {
      this.captcha.markAsDirty({ onlySelf: true });
      this.captcha.updateValueAndValidity({ onlySelf: true });
      return;
    }

    if (this.otpCaptcha.invalid) {
      this.otpCaptcha.markAsDirty({ onlySelf: true });
      this.otpCaptcha.updateValueAndValidity({ onlySelf: true });
      return;
    }

    //this.step = 1;
    //判断验证码
    this.forgotPasswordService.validateCaptcha({ userId:this.userId, state: this.state, captcha: this.captcha.value, otpCaptcha: this.otpCaptcha.value })
      .subscribe(res => {
        if (res.code !== 0) {
          this.msg.error(this.i18n.fanyi('app.login.message-invalid-verification-code'));
          this.cdr.detectChanges();
          return;
        }
        this.step = 1;
      });
  }

  onSubmit(e: MouseEvent) {
    if (this.password.invalid) {
      this.password.markAsDirty({ onlySelf: true });
      this.password.updateValueAndValidity({ onlySelf: true });
      return;
    }

    if (this.confirmPassword.invalid) {
      this.confirmPassword.markAsDirty({ onlySelf: true });
      this.confirmPassword.updateValueAndValidity({ onlySelf: true });
      return;
    }
    if (this.dynamicallyCheckPasswordErrorMsg == '') {
      this.forgotPasswordService
        .setPassWord({
          forgotType: this.forgotType,
          userId: this.userId,
          username: this.username,
          password: this.password.value,
          confirmPassword: this.confirmPassword.value,
          otpCaptcha: this.otpCaptcha.value,
          state: this.state
        })
        .subscribe(res => {
          if (res.code !== 0) {
            this.msg.error(this.i18n.fanyi('mxk.alert.operate.error'));
            this.getImageCaptcha();
            this.step = 1;
            this.cdr.detectChanges();
            return;
          }
          this.msg.success(this.i18n.fanyi('mxk.alert.operate.success'));
          setTimeout(() => {
            this.router.navigateByUrl('/');
          }, 3000);
        });
    }
  }

  ngModelChange() {
    if (this.forgotType == 'email') {
      this.mobile.reset();
    }
    if (this.forgotType == 'mobile') {
      this.email.reset();
    }
  }

  dynamicallyCheckConfirm(value:any):void {
    if (value != this.form.model.password) {
      this.dynamicallyCheckPasswordErrorMsg = this.i18n.fanyi('validation.password.twice')
    } else {
      this.dynamicallyCheckPasswordErrorMsg = '';
    }
  }


  dynamicallyCheckPassword(value:any):void {
    if (value == '') {
      this.dynamicallyCheckPasswordErrorMsg = '';
      return;
    }
    let data = this.policy;
    if (data.minLength != 0 && data.maxLength != 0) {
      let inputLength = value.length;
      if (inputLength < data.minLength || inputLength > data.maxLength) {
        //this.dynamicallyCheckPasswordErrorMsg = "限定新密码长度为"+data.minLength+"-"+data.maxLength+"位"
        this.dynamicallyCheckPasswordErrorMsg = this.i18n.fanyi('validation.password.non-conformance-strength')
        return;
      }
    }

    if (data.lowerCase > 0) {
      let strArr = Array.from(value)
      let abc:any = [];
      strArr.forEach(function (value:any, index, array) {
        let code = value.charCodeAt()
        if (code >= 'a'.charCodeAt(0) && code <= 'z'.charCodeAt(0)) {
          abc.push(value)
        }
      })
      if(abc.length < data.lowerCase) {
        //this.dynamicallyCheckPasswordErrorMsg = "限定新密码至少需要包含"+data.lowerCase+"位【a-z】小写字母"
        this.dynamicallyCheckPasswordErrorMsg = this.i18n.fanyi('validation.password.non-conformance-strength')
        return;
      }
    }


    if(data.upperCase > 0) {
      let strArr = Array.from(value)
      let ABC:any = [];
      strArr.forEach(function (value:any, index, array) {
        let code = value.charCodeAt()
        if (code >= 'A'.charCodeAt(0) && code <= 'Z'.charCodeAt(0)) {
          ABC.push(value)
        }
      })
      if(ABC.length < data.upperCase) {
        this.dynamicallyCheckPasswordErrorMsg = this.i18n.fanyi('validation.password.non-conformance-strength')
        //this.dynamicallyCheckPasswordErrorMsg = "限定新密码至少需要包含"+data.lowerCase+"位【A-Z】大写字母"
        return;
      }
    }

    if (data.digits > 0) {
      let strArr = Array.from(value)
      let number:any = [];
      strArr.forEach(function (value:any, index, array) {
        var regPos = /^[0-9]+.?[0-9]*/; //判断是否是数字。
        if(regPos.test(value) ){
          number.push(value)
        }
      })
      if(number.length < data.digits) {
        //this.dynamicallyCheckPasswordErrorMsg = "限定新密码至少需要包含"+data.digits+"位【0-9】阿拉伯数字";
        this.dynamicallyCheckPasswordErrorMsg = this.i18n.fanyi('validation.password.non-conformance-strength')
        return;
      }
    }


    if(data.specialChar > 0) {
      var AllNumIsSame = new Array("’","”","!","@","#","$","%","^","&","*",".");
      let strArr = Array.from(value)
      let number:any = [];
      strArr.forEach(function (value:any, index, array) {
        if(AllNumIsSame.indexOf(value) != -1){//$.type 是jquery的函数，用来判断对象类型
          number.push(value)
        }
      })
      if(number.length < data.specialChar) {
        //this.dynamicallyCheckPasswordErrorMsg = "限定新密码至少需要包含"+data.specialChar+"位特殊字符";
        this.dynamicallyCheckPasswordErrorMsg = this.i18n.fanyi('validation.password.non-conformance-strength')
        return;
      }
    }
    this.dynamicallyCheckPasswordErrorMsg = '';

  }

}
