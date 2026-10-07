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

import { Component, ChangeDetectorRef, ViewContainerRef, Input, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { I18NService } from '@core';
import { _HttpClient, ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService, NZ_MODAL_DATA } from 'ng-zorro-antd/modal';

import { Accounts } from '../../../entity/Accounts';
import { IModalData } from '../../../entity/IModalData';
import { AccountsService } from '../../../service/accounts.service';
import { UsersService } from '../../../service/users.service';
import { SHARED_IMPORTS } from '../../../shared/shared-imports';
import { SelectAppsComponent } from '../../apps/select-apps/select-apps.component';
import { SelectUserComponent } from '../../idm/users/select-user/select-user.component';

export interface IUserAppModalData extends IModalData {
  userId: string;
  username: string;
  appId: string;
  appName: string;
}

@Component({
  selector: 'app-account-editer',
  templateUrl: './account-editer.component.html',
  styles: [
    `
      nz-form-item {
        width: 100%;
      }
    `
  ],
  styleUrls: ['./account-editer.component.less'],
  imports: [SHARED_IMPORTS]
})
export class AccountEditerComponent implements OnInit {
  modalData = inject<IUserAppModalData>(NZ_MODAL_DATA);

  passwordVisible = false;

  form: {
    submitting: boolean;
    model: Accounts;
  } = {
    submitting: false,
    model: new Accounts()
  };

  formGroup: FormGroup = new FormGroup({});

  private readonly modalRef: NzModalRef = inject(NzModalRef);
  private readonly modalService: NzModalService = inject(NzModalService);
  private readonly accountsService: AccountsService = inject(AccountsService);
  private readonly usersService: UsersService = inject(UsersService);
  private readonly viewContainerRef: ViewContainerRef = inject(ViewContainerRef);
  private readonly fb: FormBuilder = inject(FormBuilder);
  private readonly msg: NzMessageService = inject(NzMessageService);
  private readonly i18n: I18NService = inject<I18NService>(ALAIN_I18N_TOKEN);
  private readonly cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  ngOnInit(): void {
    if (this.modalData.isEdit) {
      this.accountsService.get(`${this.modalData.id}`).subscribe(res => {
        this.form.model.init(res.data);
        this.cdr.detectChanges();
      });
    }

    if (this.modalData.username) {
      this.usersService.getByUsername(`${this.modalData.username}`).subscribe(res => {
        this.form.model.userId = res.data.id;
        this.form.model.username = res.data.username;
        this.form.model.displayName = res.data.displayName;
        this.cdr.detectChanges();
      });
    }
  }

  onSelectUser(e: MouseEvent): void {
    e.preventDefault();
    const modal = this.modalService.create({
      nzContent: SelectUserComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzData: {},
      nzWidth: 900,
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.form.model.userId = result.data.id;
        this.form.model.username = result.data.username;
        this.form.model.displayName = result.data.displayName;
        this.cdr.detectChanges();
      }
    });
  }

  onSelectApp(e: MouseEvent): void {
    e.preventDefault();
    const modal = this.modalService.create({
      nzContent: SelectAppsComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzData: {},
      nzWidth: 600,
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.form.model.appId = result.data.id;
        this.form.model.appName = result.data.appName;
        this.cdr.detectChanges();
      }
    });
  }

  onGenerate(e: MouseEvent): void {
    e.preventDefault();
    this.accountsService.generate({ strategyId: this.form.model.strategyId, userId: this.form.model.userId }).subscribe(res => {
      this.form.model.relatedUsername = res.data;
      this.cdr.detectChanges();
    });
  }

  onPassword(e: MouseEvent): void {
    e.preventDefault();
    this.usersService.generatePassword({}).subscribe(res => {
      this.form.model.relatedPassword = res.data;
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
    this.form.model.trans();
    (this.modalData.isEdit ? this.accountsService.update(this.form.model) : this.accountsService.add(this.form.model)).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi(this.modalData.isEdit ? 'mxk.alert.update.success' : 'mxk.alert.add.success'));
      } else {
        this.msg.error(this.i18n.fanyi(this.modalData.isEdit ? 'mxk.alert.update.error' : 'mxk.alert.add.error'));
      }
      this.form.submitting = false;
      this.modalRef.destroy({ refresh: true });
      this.cdr.detectChanges();
    });
  }
}
