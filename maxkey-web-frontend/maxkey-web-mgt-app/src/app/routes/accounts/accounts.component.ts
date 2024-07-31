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

import { ChangeDetectionStrategy, ViewContainerRef, ChangeDetectorRef, Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { I18NService } from '@core';
import { _HttpClient, ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { format, addDays } from 'date-fns';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { NzTableQueryParams } from 'ng-zorro-antd/table';

import { AccountsService } from '../../service/accounts.service';
import { set2String } from '../../shared/index';
import { SelectAppsComponent } from '../apps/select-apps/select-apps.component';
import { AccountEditerComponent } from './account-editer/account-editer.component';

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.less']
})
export class AccountsComponent implements OnInit {
  userId: String = '';
  query: {
    params: {
      username: String;
      displayName: String;
      employeeNumber: String;
      appId: String;
      appName: String;
      startDate: String;
      endDate: String;
      startDatePicker: Date;
      endDatePicker: Date;
      pageSize: number;
      pageNumber: number;
      pageSizeOptions: number[];
    };
    results: {
      records: number;
      rows: NzSafeAny[];
    };
    expandForm: Boolean;
    submitLoading: boolean;
    tableLoading: boolean;
    tableCheckedId: Set<String>;
    indeterminate: boolean;
    checked: boolean;
  } = {
    params: {
      username: '',
      displayName: '',
      employeeNumber: '',
      appId: '',
      appName: '',
      startDate: '',
      endDate: '',
      startDatePicker: addDays(new Date(), -30),
      endDatePicker: new Date(),
      pageSize: 10,
      pageNumber: 1,
      pageSizeOptions: [10, 20, 50]
    },
    results: {
      records: 0,
      rows: []
    },
    expandForm: false,
    submitLoading: false,
    tableLoading: false,
    tableCheckedId: new Set<String>(),
    indeterminate: false,
    checked: false
  };

  constructor(
    private modalService: NzModalService,
    private viewContainerRef: ViewContainerRef,
    private accountsService: AccountsService,
    private fb: FormBuilder,
    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    if (this.route.snapshot.queryParams['username']) {
      this.query.params.username = this.route.snapshot.queryParams['username'];
    }
    if (this.route.snapshot.queryParams['userId']) {
      this.userId = this.route.snapshot.queryParams['userId'];
    }

    this.fetch();
  }

  onQueryParamsChange(tableQueryParams: NzTableQueryParams): void {
    this.query.params.pageNumber = tableQueryParams.pageIndex;
    this.query.params.pageSize = tableQueryParams.pageSize;
    this.fetch();
  }

  onSearch(): void {
    this.fetch();
  }

  onReset(): void {
    this.query.params.appId = '';
    this.query.params.appName = '';
    this.query.params.username = '';
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
        this.query.params.appName = result.data.appName;
        this.query.params.appId = result.data.id;
        console.log(result);
        this.fetch();
      }
    });
  }
  onBatchDelete(): void {
    this.accountsService.delete(set2String(this.query.tableCheckedId)).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi('mxk.alert.delete.success'));
        this.fetch();
      } else {
        this.msg.error(this.i18n.fanyi('mxk.alert.delete.error'));
      }
      this.cdr.detectChanges();
    });
  }

  onAdd(e: MouseEvent): void {
    e.preventDefault();
    const modal = this.modalService.create({
      nzContent: AccountEditerComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzComponentParams: {
        isEdit: false,
        username: this.query.params.username,
        id: ''
      },
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.fetch();
      }
    });
  }

  onDelete(deleteId: String): void {
    this.accountsService.delete(deleteId).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi('mxk.alert.delete.success'));
        this.fetch();
      } else {
        this.msg.error(this.i18n.fanyi('mxk.alert.delete.error'));
      }
      this.cdr.detectChanges();
    });
  }

  onUpdateStatus(e: MouseEvent, accountId: String, status: number): void {
    e.preventDefault();
    this.accountsService.updateStatus({ id: accountId, status: status }).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi('mxk.alert.operate.success'));
        this.fetch();
      } else {
        this.msg.error(this.i18n.fanyi('mxk.alert.operate.error'));
      }
      this.cdr.detectChanges();
    });
  }

  fetch(): void {
    this.query.submitLoading = true;
    this.query.tableLoading = true;
    this.query.indeterminate = false;
    this.query.checked = false;
    this.query.tableCheckedId.clear();
    if (this.query.expandForm) {
      this.query.params.endDate = format(this.query.params.endDatePicker, 'yyyy-MM-dd HH:mm:ss');
      this.query.params.startDate = format(this.query.params.startDatePicker, 'yyyy-MM-dd HH:mm:ss');
    } else {
      this.query.params.endDate = '';
      this.query.params.startDate = '';
    }
    this.accountsService.fetch(this.query.params).subscribe(res => {
      this.query.results = res.data;
      this.query.submitLoading = false;
      this.query.tableLoading = false;
      this.cdr.detectChanges();
    });
  }

  updateTableCheckedSet(id: String, checked: boolean): void {
    if (checked) {
      this.query.tableCheckedId.add(id);
    } else {
      this.query.tableCheckedId.delete(id);
    }
  }

  refreshTableCheckedStatus(): void {
    const listOfEnabledData = this.query.results.rows.filter(({ disabled }) => !disabled);
    this.query.checked = listOfEnabledData.every(({ id }) => this.query.tableCheckedId.has(id));
    this.query.indeterminate = listOfEnabledData.some(({ id }) => this.query.tableCheckedId.has(id)) && !this.query.checked;
  }

  onTableItemChecked(id: String, checked: boolean): void {
    this.updateTableCheckedSet(id, checked);
    this.refreshTableCheckedStatus();
  }

  onTableAllChecked(checked: boolean): void {
    this.query.results.rows.filter(({ disabled }) => !disabled).forEach(({ id }) => this.updateTableCheckedSet(id, checked));
    this.refreshTableCheckedStatus();
  }
}
