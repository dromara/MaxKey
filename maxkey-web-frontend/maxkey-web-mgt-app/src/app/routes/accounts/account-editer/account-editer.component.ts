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

import { Component, ChangeDetectorRef, ViewContainerRef, Input, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { I18NService } from '@core';
import { _HttpClient, ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import format from 'date-fns/format';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

import { Accounts } from '../../../entity/Accounts';
import { AccountsService } from '../../../service/accounts.service';
import { UsersService } from '../../../service/users.service';
import { SelectAppsComponent } from '../../apps/select-apps/select-apps.component';
import { SelectUserComponent } from '../../idm/users/select-user/select-user.component';

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
  styleUrls: ['./account-editer.component.less']
})
export class AccountEditerComponent implements OnInit {
  @Input() id?: String;
  @Input() username?: String;
  @Input() isEdit?: boolean;

  passwordVisible = false;

  form: {
    submitting: boolean;
    model: Accounts;
  } = {
    submitting: false,
    model: new Accounts()
  };

  formGroup: FormGroup = new FormGroup({});

  constructor(
    private modalRef: NzModalRef,
    private modalService: NzModalService,
    private accountsService: AccountsService,
    private usersService: UsersService,
    private viewContainerRef: ViewContainerRef,
    private fb: FormBuilder,
    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    if (this.isEdit) {
      this.accountsService.get(`${this.id}`).subscribe(res => {
        this.form.model.init(res.data);
        this.cdr.detectChanges();
      });
    }

    if (this.username) {
      this.usersService.getByUsername(`${this.username}`).subscribe(res => {
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
      nzComponentParams: {},
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
      nzComponentParams: {},
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
    (this.isEdit ? this.accountsService.update(this.form.model) : this.accountsService.add(this.form.model)).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi(this.isEdit ? 'mxk.alert.update.success' : 'mxk.alert.add.success'));
      } else {
        this.msg.error(this.i18n.fanyi(this.isEdit ? 'mxk.alert.update.error' : 'mxk.alert.add.error'));
      }
      this.form.submitting = false;
      this.modalRef.destroy({ refresh: true });
      this.cdr.detectChanges();
    });
  }
}
