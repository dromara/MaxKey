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
 

import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { I18NService } from '@core';
import { SettingsService, User, ALAIN_I18N_TOKEN } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';

import { Users } from '../../../entity/Users';
import { UsersService } from '../../../service/users.service';

@Component({
  selector: 'app-mfa',
  templateUrl: './mfa.component.html',
  styleUrls: ['./mfa.component.less']
})
export class MfaComponent implements OnInit {
  form: {
    submitting: boolean;
    model: Users;
  } = {
    submitting: false,
    model: new Users()
  };
  loading = false;
  constructor(
    private router: Router,
    private fb: FormBuilder,
    private settingsService: SettingsService,
    private usersService: UsersService,

    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    let user: any = this.settingsService.user;
    this.form.model.id = user.userId;
    this.form.model.displayName = user.displayName;
    this.form.model.username = user.username;
    this.form.model.mobile = user.mobile;
    this.form.model.email = user.email;
    this.form.model.authnType = '0';
    this.usersService.getProfile().subscribe(res => {
      this.form.model.init(res.data);
      this.cdr.detectChanges();
    });
  }

  onSubmit(): void {
    this.form.submitting = true;
    this.form.model.trans();
    this.usersService.updateAuthnType(this.form.model).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi('mxk.alert.operate.success'));
      } else {
        this.msg.error(this.i18n.fanyi('mxk.alert.operate.error'));
      }
      this.form.submitting = false;
      this.cdr.detectChanges();
    });
  }
}
