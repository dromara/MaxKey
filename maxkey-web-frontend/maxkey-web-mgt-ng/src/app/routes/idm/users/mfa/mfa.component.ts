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

import { Component, ChangeDetectorRef, OnInit, Input, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { I18NService } from '@core';
import { ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService, NZ_MODAL_DATA } from 'ng-zorro-antd/modal';

import { IModalData } from '../../../../entity/IModalData';
import { Users } from '../../../../entity/Users';
import { UsersService } from '../../../../service/users.service';
import { SHARED_IMPORTS } from '../../../../shared/shared-imports';

export interface IUserModalData extends IModalData {
  username: string;
  displayName: string;
}
@Component({
  selector: 'app-mfa',
  templateUrl: './mfa.component.html',
  styleUrls: ['./mfa.component.less'],
  imports: [SHARED_IMPORTS]
})
export class MfaComponent implements OnInit {
  modalData = inject<IUserModalData>(NZ_MODAL_DATA);

  form: {
    submitting: boolean;
    model: Users;
  } = {
    submitting: false,
    model: new Users()
  };

  private readonly usersService: UsersService = inject(UsersService);
  private readonly msg: NzMessageService = inject(NzMessageService);
  private readonly i18n: I18NService = inject<I18NService>(ALAIN_I18N_TOKEN);
  private readonly router: Router = inject(Router);
  private readonly modalRef: NzModalRef = inject(NzModalRef);
  private readonly cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  ngOnInit(): void {
    if (this.modalData.id) {
      this.usersService.get(this.modalData.id).subscribe(res => {
        this.form.model = res.data;
        this.cdr.detectChanges();
      });
    }
  }

  onClose(e: MouseEvent): void {
    e.preventDefault();
    this.modalRef.destroy({ refresh: false });
  }
  onSubmit(e: MouseEvent): void {
    e.preventDefault();
    this.form.submitting = true;

    this.usersService.updateAuthnType(this.form.model).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi('mxk.alert.operate.success'));
        this.modalRef.destroy({ refresh: true });
        this.cdr.detectChanges();
      } else {
        this.msg.error(res.message);
      }
    });
  }
}
