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

import { ChangeDetectionStrategy, ViewContainerRef, ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { I18NService } from '@core';
import { _HttpClient, ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { format, addDays } from 'date-fns';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { NzTableQueryParams } from 'ng-zorro-antd/table';

import { AppBasicDetailsEditerComponent } from './app-basic-details-editer/app-basic-details-editer.component';
import { AppCasDetailsEditerComponent } from './app-cas-details-editer/app-cas-details-editer.component';
import { AppExtendApiDetailsEditerComponent } from './app-extend-api-details-editer/app-extend-api-details-editer.component';
import { AppFormBasedDetailsEditerComponent } from './app-form-based-details-editer/app-form-based-details-editer.component';
import { AppJwtDetailsEditerComponent } from './app-jwt-details-editer/app-jwt-details-editer.component';
import { AppOauth20DetailsEditerComponent } from './app-oauth20-details-editer/app-oauth20-details-editer.component';
import { AppSaml20DetailsEditerComponent } from './app-saml20-details-editer/app-saml20-details-editer.component';
import { AppTokenBasedDetailsEditerComponent } from './app-token-based-details-editer/app-token-based-details-editer.component';
import { SelectProtocolComponent } from './select-protocol/select-protocol.component';
import { AppsService } from '../../service/apps.service';
import { set2String } from '../../shared/index';
import { SHARED_IMPORTS } from '../../shared/shared-imports';
@Component({
  selector: 'app-apps',
  templateUrl: './apps.component.html',
  styleUrls: ['./apps.component.less'],
  imports: [SHARED_IMPORTS]
})
export class AppsComponent implements OnInit {
  query: {
    params: {
      appName: string;
      displayName: string;
      protocol: string;
      startDate: string;
      endDate: string;
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
    expandForm: boolean;
    submitLoading: boolean;
    tableLoading: boolean;
    tableCheckedId: Set<string>;
    indeterminate: boolean;
    checked: boolean;
  } = {
    params: {
      appName: '',
      displayName: '',
      protocol: '',
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
    tableCheckedId: new Set<string>(),
    indeterminate: false,
    checked: false
  };

  private readonly modalService: NzModalService = inject(NzModalService);
  private readonly viewContainerRef: ViewContainerRef = inject(ViewContainerRef);
  private readonly appsService: AppsService = inject(AppsService);
  private readonly fb: FormBuilder = inject(FormBuilder);
  private readonly msg: NzMessageService = inject(NzMessageService);
  private readonly i18n: I18NService = inject<I18NService>(ALAIN_I18N_TOKEN);
  private readonly router: Router = inject(Router);
  private readonly cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  ngOnInit(): void {
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

  onReset(): void {}

  onBatchDelete(): void {
    this.appsService.delete(set2String(this.query.tableCheckedId)).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi('mxk.alert.delete.success'));
        this.fetch();
      } else {
        this.msg.error(this.i18n.fanyi('mxk.alert.delete.error'));
      }
      this.cdr.detectChanges();
    });
  }

  chooseComponent(protocol: string): any {
    let ProtocolComponent: any;
    switch (protocol) {
      case 'OAuth_v2.0': {
        ProtocolComponent = AppOauth20DetailsEditerComponent;
        break;
      }
      case 'OAuth_v2.1': {
        ProtocolComponent = AppOauth20DetailsEditerComponent;
        break;
      }
      case 'OpenID_Connect_v1.0': {
        ProtocolComponent = AppOauth20DetailsEditerComponent;
        break;
      }
      case 'SAML_v2.0': {
        ProtocolComponent = AppSaml20DetailsEditerComponent;
        break;
      }
      case 'CAS': {
        ProtocolComponent = AppCasDetailsEditerComponent;
        break;
      }
      case 'JWT': {
        ProtocolComponent = AppJwtDetailsEditerComponent;
        break;
      }
      case 'Token_Based': {
        ProtocolComponent = AppTokenBasedDetailsEditerComponent;
        break;
      }
      case 'Form_Based': {
        ProtocolComponent = AppFormBasedDetailsEditerComponent;
        break;
      }
      case 'Extend_API': {
        ProtocolComponent = AppExtendApiDetailsEditerComponent;
        break;
      }
      case 'Basic': {
        ProtocolComponent = AppBasicDetailsEditerComponent;
        break;
      }
      default: {
        ProtocolComponent = AppBasicDetailsEditerComponent;
      }
    }
    return ProtocolComponent;
  }

  onResourcesMgmt(e: MouseEvent, appId: string, appName: string): void {
    this.router.navigateByUrl(`/permissions/resources?appId=${appId}&appName=${appName}`);
  }

  onAddSelectProtocol(e: MouseEvent): void {
    e.preventDefault();

    const modal = this.modalService.create({
      nzContent: SelectProtocolComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzData: {},
      nzWidth: 960,
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.data) {
        this.onAdd(`${result.data}`);
      }
    });
  }

  onAdd(protocol: string): void {
    const modal = this.modalService.create({
      nzContent: this.chooseComponent(protocol),
      nzViewContainerRef: this.viewContainerRef,
      nzData: {
        isEdit: false,
        protocol: protocol,
        id: ''
      },
      nzWidth: 960,
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.fetch();
      }
    });
  }

  onEdit(e: MouseEvent, editId: string, protocol: string): void {
    e.preventDefault();

    const modal = this.modalService.create({
      nzContent: this.chooseComponent(protocol),
      nzViewContainerRef: this.viewContainerRef,
      nzData: {
        isEdit: true,
        id: editId
      },
      nzWidth: 800,
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.fetch();
      }
    });
  }

  onDelete(e: MouseEvent, deleteId: string): void {
    e.preventDefault();
    this.appsService.delete(deleteId).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi('mxk.alert.delete.success'));
        this.fetch();
      } else {
        this.msg.error(this.i18n.fanyi('mxk.alert.delete.error'));
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
    this.appsService.fetch(this.query.params).subscribe(res => {
      this.query.results = res.data;
      this.query.submitLoading = false;
      this.query.tableLoading = false;
      this.cdr.detectChanges();
    });
  }

  updateTableCheckedSet(id: string, checked: boolean): void {
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

  onTableItemChecked(id: string, checked: boolean): void {
    this.updateTableCheckedSet(id, checked);
    this.refreshTableCheckedStatus();
  }

  onTableAllChecked(checked: boolean): void {
    this.query.results.rows.filter(({ disabled }) => !disabled).forEach(({ id }) => this.updateTableCheckedSet(id, checked));
    this.refreshTableCheckedStatus();
  }

  tableHasCheckedItem(): boolean {
    return this.query.tableCheckedId.size <= 0;
  }
}
