/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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
import { NzContextMenuService, NzDropdownMenuComponent } from 'ng-zorro-antd/dropdown';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { NzFormatEmitEvent, NzTreeNode, NzTreeNodeOptions } from 'ng-zorro-antd/tree';

import { RoleMembersService } from '../../../service/role-members.service';
import { RolesService } from '../../../service/roles.service';
import { set2String } from '../../../shared/index';
import { SelectRolesComponent } from '../roles/select-roles/select-roles.component';
import { MemberRolesEditerComponent } from './member-roles-editer/member-roles-editer.component';
import { RoleMembersEditerComponent } from './role-members-editer/role-members-editer.component';

@Component({
  selector: 'app-role-members',
  templateUrl: './role-members.component.html',
  styleUrls: ['./role-members.component.less']
})
export class RoleMembersComponent implements OnInit {
  query: {
    params: {
      roleName: String;
      displayName: String;
      memberName: String;
      roleId: String;
      appName: String;
      appId: String;
      selectRoleName: String;
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
    tableInitialize: boolean;
    tableCheckedId: Set<String>;
    indeterminate: boolean;
    checked: boolean;
  } = {
    params: {
      roleName: '',
      displayName: '',
      memberName: '',
      roleId: '',
      appName: '',
      appId: '',
      selectRoleName: '',
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
    tableInitialize: true,
    tableCheckedId: new Set<String>(),
    indeterminate: false,
    checked: false
  };

  constructor(
    private modalService: NzModalService,
    private rolesService: RolesService,
    private roleMembersService: RoleMembersService,
    private viewContainerRef: ViewContainerRef,
    private fb: FormBuilder,
    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    if (this.route.snapshot.queryParams['username']) {
      this.query.params.memberName = this.route.snapshot.queryParams['username'];
    }
    if (this.route.snapshot.queryParams['roleId']) {
      this.query.params.roleId = this.route.snapshot.queryParams['roleId'];
    }

    if (this.route.snapshot.queryParams['roleName']) {
      //显示用
      this.query.params.selectRoleName = this.route.snapshot.queryParams['roleName'];
    }

    if (this.route.snapshot.queryParams['appId']) {
      this.query.params.appId = this.route.snapshot.queryParams['appId'];
      this.leftQuery.params.appId = this.query.params.appId;
    }

    this.query.tableInitialize = false;
  }

  onQueryParamsChange(tableQueryParams: NzTableQueryParams): void {
    this.query.params.pageNumber = tableQueryParams.pageIndex;
    this.query.params.pageSize = tableQueryParams.pageSize;
    if (!this.query.tableInitialize) {
      this.fetch();
    }
  }

  onSearch(): void {
    this.fetch();
  }

  onReset(): void {
    this.query.params.memberName = '';
    this.query.params.roleId = '';
    this.query.params.roleName = '';
    this.fetch();
  }

  onBatchDelete(): void {
    this.roleMembersService.delete(set2String(this.query.tableCheckedId)).subscribe(res => {
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
    if (this.query.params.memberName != '') {
      const modal = this.modalService.create({
        nzContent: MemberRolesEditerComponent,
        nzViewContainerRef: this.viewContainerRef,
        nzComponentParams: {
          username: this.query.params.memberName
        },
        nzWidth: 700,
        nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
      });
      // Return a result when closed
      modal.afterClose.subscribe(result => {
        if (result.refresh) {
          this.fetch();
        }
      });
    } else if (this.query.params.roleId != '') {
      const modal = this.modalService.create({
        nzContent: RoleMembersEditerComponent,
        nzViewContainerRef: this.viewContainerRef,
        nzComponentParams: {
          isEdit: false,
          roleId: this.query.params.roleId
        },
        nzWidth: 700,
        nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
      });
      // Return a result when closed
      modal.afterClose.subscribe(result => {
        if (result.refresh) {
          this.fetch();
        }
      });
    }
  }

  onSelect(e: MouseEvent): void {
    e.preventDefault();

    const modal = this.modalService.create({
      nzContent: SelectRolesComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzComponentParams: {},
      nzWidth: 700,
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.query.params.roleName = result.data.roleName;
        this.query.params.roleId = result.data.id;
        console.log(result);
        this.fetch();
      }
    });
  }

  onDelete(deleteId: String): void {
    this.roleMembersService.delete(deleteId).subscribe(res => {
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
    this.roleMembersService.member(this.query.params).subscribe(res => {
      this.query.results = res.data;
      this.query.submitLoading = false;
      this.query.tableLoading = false;
      console.log(res.data);
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

  leftQuery: {
    params: {
      id: String;
      roleName: String;
      appId: String;
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
      id: '',
      roleName: '',
      appId: '',
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

  //LEFT TABLE
  onLeftSearch(): void {
    this.leftFetch();
  }

  leftFetch(): void {
    this.leftQuery.submitLoading = true;
    this.leftQuery.tableLoading = true;
    this.leftQuery.indeterminate = false;
    this.leftQuery.checked = false;
    this.leftQuery.tableCheckedId.clear();
    if (this.leftQuery.expandForm) {
      this.leftQuery.params.endDate = format(this.leftQuery.params.endDatePicker, 'yyyy-MM-dd HH:mm:ss');
      this.leftQuery.params.startDate = format(this.leftQuery.params.startDatePicker, 'yyyy-MM-dd HH:mm:ss');
    } else {
      this.leftQuery.params.endDate = '';
      this.leftQuery.params.startDate = '';
    }
    this.rolesService.fetch(this.leftQuery.params).subscribe(res => {
      this.leftQuery.results = res.data;
      this.leftQuery.submitLoading = false;
      this.leftQuery.tableLoading = false;
      this.cdr.detectChanges();
    });
  }

  onLeftQueryParamsChange(tableQueryParams: NzTableQueryParams): void {
    this.leftQuery.params.pageNumber = tableQueryParams.pageIndex;
    this.leftQuery.params.pageSize = tableQueryParams.pageSize;
    this.leftFetch();
  }

  updateLeftTableCheckedSet(id: String, checked: boolean): void {
    if (checked) {
      this.leftQuery.tableCheckedId.add(id);
    } else {
      this.leftQuery.tableCheckedId.delete(id);
    }
  }

  refreshLeftTableCheckedStatus(): void {
    const listOfEnabledData = this.leftQuery.results.rows.filter(({ disabled }) => !disabled);
    this.leftQuery.checked = listOfEnabledData.every(({ id }) => this.leftQuery.tableCheckedId.has(id));
    this.leftQuery.indeterminate = listOfEnabledData.some(({ id }) => this.leftQuery.tableCheckedId.has(id)) && !this.leftQuery.checked;
  }

  onLeftTableItemChecked(id: String, roleName: String, checked: boolean): void {
    this.onLeftTableAllChecked(false);
    this.updateLeftTableCheckedSet(id, checked);
    this.refreshLeftTableCheckedStatus();
    this.query.params.roleId = id;
    this.query.params.selectRoleName = roleName;
    this.fetch();
  }

  onLeftTableAllChecked(checked: boolean): void {
    this.leftQuery.results.rows.filter(({ disabled }) => !disabled).forEach(({ id }) => this.updateLeftTableCheckedSet(id, checked));
    this.refreshTableCheckedStatus();
  }
}
