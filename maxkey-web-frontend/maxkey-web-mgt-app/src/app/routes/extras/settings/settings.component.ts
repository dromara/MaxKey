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
 

import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-extras-settings',
  templateUrl: './settings.component.html'
})
export class ExtrasSettingsComponent implements OnInit {
  active = 1;
  profileForm: FormGroup;
  pwd = {
    old_password: '',
    new_password: '',
    confirm_new_password: ''
  };
  // Email
  primary_email = 'cipchk@qq.com';

  constructor(fb: FormBuilder, public msg: NzMessageService) {
    this.profileForm = fb.group({
      name: [null, Validators.compose([Validators.required, Validators.pattern(`^[-_a-zA-Z0-9]{4,20}$`)])],
      email: '',
      bio: [null, Validators.maxLength(160)],
      url: '',
      company: '',
      location: ''
    });
  }

  get name(): AbstractControl {
    return this.profileForm.get('name')!;
  }

  profileSave(value: any): void {
    console.log('profile value', value);
  }

  pwdSave(): void {
    if (!this.pwd.old_password) {
      this.msg.error('invalid old password');
      return;
    }
    if (!this.pwd.new_password) {
      this.msg.error('invalid new password');
      return;
    }
    if (!this.pwd.confirm_new_password) {
      this.msg.error('invalid confirm new password');
      return;
    }
    console.log('pwd value', this.pwd);
  }

  ngOnInit(): void {
    this.profileForm.patchValue({
      name: 'cipchk',
      email: 'cipchk@qq.com'
    });
  }
}
