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

import { Component, ChangeDetectorRef, OnInit, Input, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { I18NService } from '@core';
import { ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService, NZ_MODAL_DATA } from 'ng-zorro-antd/modal';

import { ChangePassword } from '../../../../entity/ChangePassword';
import { IModalData } from '../../../../entity/IModalData';
import { PasswordService } from '../../../../service/password.service';
import { UsersService } from '../../../../service/users.service';
import { SHARED_IMPORTS } from '../../../../shared/shared-imports';

export interface IUserModalData extends IModalData {
  username: string;
  displayName: string;
}
@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.less'],
  imports: [SHARED_IMPORTS]
})
export class PasswordComponent implements OnInit {
  modalData = inject<IUserModalData>(NZ_MODAL_DATA);

  form: {
    submitting: boolean;
    model: ChangePassword;
  } = {
    submitting: false,
    model: new ChangePassword()
  };

  passwordVisible: boolean = false;

  formGroup: FormGroup = new FormGroup({});

  private readonly usersService: UsersService = inject(UsersService);
  private readonly passwordService: PasswordService = inject(PasswordService);
  private readonly modalRef: NzModalRef = inject(NzModalRef);
  private readonly fb: FormBuilder = inject(FormBuilder);
  private readonly msg: NzMessageService = inject(NzMessageService);
  private readonly i18n: I18NService = inject<I18NService>(ALAIN_I18N_TOKEN);
  private readonly cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.form.model.id = this.modalData.id || '';
    this.form.model.userId = this.modalData.id || '';
    this.form.model.username = this.modalData.username || '';
    this.form.model.displayName = this.modalData.displayName || '';
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
        this.msg.success(this.i18n.fanyi('mxk.alert.operate.success'));
      } else {
        this.msg.error(this.i18n.fanyi('mxk.alert.operate.error'));
      }
      this.modalRef.destroy({ refresh: true });
      this.cdr.detectChanges();
    });
  }
}
