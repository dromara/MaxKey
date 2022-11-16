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

import { AccountsStrategy } from '../../../../entity/AccountsStrategy';
import { TreeNodes } from '../../../../entity/TreeNodes';
import { AccountsStrategyService } from '../../../../service/accounts-strategy.service';
import { OrganizationsService } from '../../../../service/organizations.service';
import { SelectAppsComponent } from '../../../apps/select-apps/select-apps.component';

@Component({
  selector: 'app-accounts-strategy-editer',
  templateUrl: './accounts-strategy-editer.component.html',
  styles: [
    `
      nz-form-item {
        width: 100%;
      }
    `
  ],
  styleUrls: ['./accounts-strategy-editer.component.less']
})
export class AccountsStrategyEditerComponent implements OnInit {
  @Input() id?: String;
  @Input() isEdit?: boolean;

  form: {
    submitting: boolean;
    model: AccountsStrategy;
  } = {
    submitting: false,
    model: new AccountsStrategy()
  };

  formGroup: FormGroup = new FormGroup({});
  // TreeNodes
  treeNodes = new TreeNodes(false);

  selectValues: string[] = [];

  constructor(
    private modal: NzModalRef,
    private accountsStrategyService: AccountsStrategyService,
    private orgsService: OrganizationsService,
    private fb: FormBuilder,
    private modalService: NzModalService,
    private viewContainerRef: ViewContainerRef,
    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.tree();
    if (this.isEdit) {
      this.accountsStrategyService.get(`${this.id}`).subscribe(res => {
        this.form.model.init(res.data);
        this.selectValues = this.form.model.orgIdsList.split(',');
        this.cdr.detectChanges();
      });
    }
  }

  tree(): void {
    this.orgsService.tree({}).subscribe(res => {
      this.treeNodes.init(res.data);
      this.treeNodes.nodes = this.treeNodes.build();
      this.cdr.detectChanges();
    });
  }

  onSelect(e: MouseEvent): void {
    e.preventDefault();
    const modal = this.modalService.create({
      nzContent: SelectAppsComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzComponentParams: {},
      nzWidth: 700,
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.form.model.appId = result.data.id;
        this.form.model.appName = result.data.name;
        this.cdr.detectChanges();
      }
    });
  }

  onClose(e: MouseEvent): void {
    e.preventDefault();
    this.modal.destroy({ refresh: false });
  }

  onSubmit(e: MouseEvent): void {
    e.preventDefault();
    this.form.submitting = true;
    this.form.model.trans();
    this.form.model.orgIdsList = '';
    this.selectValues.forEach(value => {
      this.form.model.orgIdsList = `${this.form.model.orgIdsList + value},`;
    });

    (this.isEdit ? this.accountsStrategyService.update(this.form.model) : this.accountsStrategyService.add(this.form.model)).subscribe(
      res => {
        if (res.code == 0) {
          this.msg.success(this.i18n.fanyi(this.isEdit ? 'mxk.alert.update.success' : 'mxk.alert.add.success'));
        } else {
          this.msg.error(this.i18n.fanyi(this.isEdit ? 'mxk.alert.update.error' : 'mxk.alert.add.error'));
        }
        this.form.submitting = false;
        this.modal.destroy({ refresh: true });
        this.cdr.detectChanges();
      }
    );
  }
}
