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

import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { I18NService } from '@core';
import { SettingsService, User, ALAIN_I18N_TOKEN } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';

import { ChangePassword } from '../../../entity/ChangePassword';
import { PasswordService } from '../../../service/password.service';

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.less']
})
export class PasswordComponent implements OnInit {
  form: {
    submitting: boolean;
    model: ChangePassword;
  } = {
    submitting: false,
    model: new ChangePassword()
  };
  validateForm!: FormGroup;
  oldPasswordVisible = false;
  policy: any = {};
  passwordVisible = false;
  policyMessage: any[] = [];
  dynamicallyCheckPasswordErrorMsg = '';
  constructor(
    private router: Router,
    private fb: FormBuilder,
    private settingsService: SettingsService,
    private passwordService: PasswordService,
    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadPolicy();
    this.validateForm = this.fb.group({
      oldPassword: [null, [Validators.required]],
      confirmPassword: [null, [Validators.required]],
      password: [null, [Validators.required]]
    });

    let user: any = this.settingsService.user;
    this.form.model.userId = user.userId;
    this.form.model.displayName = user.displayName;
    this.form.model.username = user.username;
    this.form.model.id = user.userId;
    this.cdr.detectChanges();
  }

  loadPolicy(): void {
    this.policyMessage = [];
    this.passwordService.passwordpolicy().subscribe(res => {
      if (res.code == 0) {
        let data = res.data;
        this.policy = data;
        this.policyMessage = res.data.policMessageList;
      }
    });
  }

  dynamicallyCheckConfirm(value: any): void {
    if (value != this.form.model.password) {
      this.dynamicallyCheckPasswordErrorMsg = this.i18n.fanyi('validation.password.twice');
    } else {
      this.dynamicallyCheckPasswordErrorMsg = '';
    }
  }

  dynamicallyCheckPassword(value: any): void {
    if (value == '') {
      this.dynamicallyCheckPasswordErrorMsg = '';
      return;
    }
    let data = this.policy;
    if (data.minLength != 0 && data.maxLength != 0) {
      let inputLength = value.length;
      if (inputLength < data.minLength || inputLength > data.maxLength) {
        //this.dynamicallyCheckPasswordErrorMsg = "限定新密码长度为"+data.minLength+"-"+data.maxLength+"位"
        this.dynamicallyCheckPasswordErrorMsg = this.i18n.fanyi('validation.password.non-conformance-strength');
        return;
      }
    }

    if (data.lowerCase > 0) {
      let strArr = Array.from(value);
      let abc: any = [];
      strArr.forEach(function (value: any, index, array) {
        let code = value.charCodeAt();
        if (code >= 'a'.charCodeAt(0) && code <= 'z'.charCodeAt(0)) {
          abc.push(value);
        }
      });
      if (abc.length < data.lowerCase) {
        //this.dynamicallyCheckPasswordErrorMsg = "限定新密码至少需要包含"+data.lowerCase+"位【a-z】小写字母"
        this.dynamicallyCheckPasswordErrorMsg = this.i18n.fanyi('validation.password.non-conformance-strength');
        return;
      }
    }

    if (data.upperCase > 0) {
      let strArr = Array.from(value);
      let ABC: any = [];
      strArr.forEach(function (value: any, index, array) {
        let code = value.charCodeAt();
        if (code >= 'A'.charCodeAt(0) && code <= 'Z'.charCodeAt(0)) {
          ABC.push(value);
        }
      });
      if (ABC.length < data.upperCase) {
        this.dynamicallyCheckPasswordErrorMsg = this.i18n.fanyi('validation.password.non-conformance-strength');
        //this.dynamicallyCheckPasswordErrorMsg = "限定新密码至少需要包含"+data.lowerCase+"位【A-Z】大写字母"
        return;
      }
    }

    if (data.digits > 0) {
      let strArr = Array.from(value);
      let number: any = [];
      strArr.forEach(function (value: any, index, array) {
        var regPos = /^[0-9]+.?[0-9]*/; //判断是否是数字。
        if (regPos.test(value)) {
          number.push(value);
        }
      });
      if (number.length < data.digits) {
        //this.dynamicallyCheckPasswordErrorMsg = "限定新密码至少需要包含"+data.digits+"位【0-9】阿拉伯数字";
        this.dynamicallyCheckPasswordErrorMsg = this.i18n.fanyi('validation.password.non-conformance-strength');
        return;
      }
    }

    if (data.specialChar > 0) {
      var AllNumIsSame = new Array('’', '”', '!', '@', '#', '$', '%', '^', '&', '*', '.');
      let strArr = Array.from(value);
      let number: any = [];
      strArr.forEach(function (value: any, index, array) {
        if (AllNumIsSame.indexOf(value) != -1) {
          //$.type 是jquery的函数，用来判断对象类型
          number.push(value);
        }
      });
      if (number.length < data.specialChar) {
        //this.dynamicallyCheckPasswordErrorMsg = "限定新密码至少需要包含"+data.specialChar+"位特殊字符";
        this.dynamicallyCheckPasswordErrorMsg = this.i18n.fanyi('validation.password.non-conformance-strength');
        return;
      }
    }
    this.dynamicallyCheckPasswordErrorMsg = '';
  }

  onSubmit(): void {
    if (this.validateForm.valid) {
      if (this.dynamicallyCheckPasswordErrorMsg == '') {
        this.form.submitting = true;
        this.form.model.trans();
        this.passwordService.changePassword(this.form.model).subscribe(res => {
          if (res.code == 0) {
            this.form.model.init(res.data);
            this.msg.success(this.i18n.fanyi('mxk.alert.operate.success'));
            //设置密码正常，路由不进行拦截
            let user = this.settingsService.user;
            user['passwordSetType'] = 0;
            this.settingsService.setUser(user);
            //this.router.navigateByUrl('/');
          } else {
            if (res.message) {
              this.msg.error(res.message);
              return;
            }
            this.msg.error(this.i18n.fanyi('mxk.alert.operate.error'));
          }
        });
        this.form.submitting = false;
        this.cdr.detectChanges();
      }
    } else {
      Object.values(this.validateForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
  }
}
