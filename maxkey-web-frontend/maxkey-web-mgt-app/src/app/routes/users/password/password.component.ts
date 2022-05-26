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
 

import { Component, ChangeDetectorRef, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

import { ChangePassword } from '../../../entity/ChangePassword';
import { PasswordService } from '../../../service/password.service';
import { UsersService } from '../../../service/users.service';

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.less']
})
export class PasswordComponent implements OnInit {
  @Input() id?: String;
  @Input() username?: String;
  @Input() displayName?: String;

  form: {
    submitting: boolean;
    model: ChangePassword;
  } = {
      submitting: false,
      model: new ChangePassword()
    };

  passwordVisible: boolean = false;

  formGroup: FormGroup = new FormGroup({});

  constructor(
    private usersService: UsersService,
    private passwordService: PasswordService,
    private modalRef: NzModalRef,
    private fb: FormBuilder,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.form.model.id = this.id || '';
    this.form.model.userId = this.id || '';
    this.form.model.username = this.username || '';
    this.form.model.displayName = this.displayName || '';
  }

  onPassword(e: MouseEvent): void {
    e.preventDefault();
    this.usersService.generatePassword({}).subscribe(res => {
      this.form.model.password = res.data;
      this.form.model.confirmPassword = res.data;
      this.cdr.detectChanges();
    });
  }

  onClose(e: MouseEvent): void {
    e.preventDefault();
    this.modalRef.destroy({ refresh: false });
  }

  onSubmit(e: MouseEvent): void {
    e.preventDefault();
    this.form.submitting = true;
    this.passwordService.changePassword(this.form.model).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(`提交成功`);
      } else {
        this.msg.success(`提交失败`);
      }
      this.modalRef.destroy({ refresh: true });
      this.cdr.detectChanges();
    });
  }
}
