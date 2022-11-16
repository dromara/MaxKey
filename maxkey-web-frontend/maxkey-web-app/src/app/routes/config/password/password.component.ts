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

  formGroup: FormGroup = new FormGroup({});

  oldPasswordVisible = false;

  passwordVisible = false;

  constructor(
    private fb: FormBuilder,
    private settingsService: SettingsService,
    private passwordService: PasswordService,
    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.formGroup = this.fb.group({
      oldPassword: [null, [Validators.required]],
      confirmPassword: [null, [Validators.required]],
      password: [1, [Validators.min(6), Validators.max(20)]]
    });

    let user: any = this.settingsService.user;
    this.form.model.userId = user.userId;
    this.form.model.displayName = user.displayName;
    this.form.model.username = user.username;
    this.form.model.id = user.userId;

    this.cdr.detectChanges();
  }

  onSubmit(): void {
    this.form.submitting = true;
    this.form.model.trans();
    //if (this.formGroup.valid) {
    this.passwordService.changePassword(this.form.model).subscribe(res => {
      if (res.code == 0) {
        this.form.model.init(res.data);
        this.msg.success(this.i18n.fanyi('mxk.alert.operate.success'));
      } else {
        this.msg.error(this.i18n.fanyi('mxk.alert.operate.error'));
      }
    });
    // } else {
    //  this.formGroup.updateValueAndValidity({ onlySelf: true });
    // this.msg.success(`提交失败`);
    //}
    this.form.submitting = false;
    this.cdr.detectChanges();
  }
}
