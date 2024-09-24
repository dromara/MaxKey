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
import { NzContextMenuService, NzDropdownMenuComponent } from 'ng-zorro-antd/dropdown';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { NzFormatEmitEvent, NzTreeNode, NzTreeNodeOptions } from 'ng-zorro-antd/tree';

import { GroupMembersService } from '../../../service/group-members.service';
import { GroupsService } from '../../../service/groups.service';
import { set2String } from '../../../shared/index';
import { SelectGroupsComponent } from '../groups/select-groups/select-groups.component';
import { GroupMembersEditerComponent } from './group-members-editer/group-members-editer.component';
import { MemberGroupsEditerComponent } from './member-groups-editer/member-groups-editer.component';

@Component({
  selector: 'app-group-members',
  templateUrl: './group-members.component.html',
  styleUrls: ['./group-members.component.less']
})
export class GroupMembersComponent implements OnInit {
  query: {
    params: {
      groupName: String;
      displayName: String;
      username: String;
      groupId: String;
      appName: String;
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
    tableInitialize: boolean;
    tableCheckedId: Set<String>;
    indeterminate: boolean;
    checked: boolean;
  } = {
    params: {
      groupName: '',
      displayName: '',
      username: '',
      groupId: '',
      appName: '',
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
    tableInitialize: true,
    tableCheckedId: new Set<String>(),
    indeterminate: false,
    checked: false
  };

  constructor(
    private modalService: NzModalService,
    private groupMembersService: GroupMembersService,
    private groupsService: GroupsService,
    private viewContainerRef: ViewContainerRef,
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
    if (this.route.snapshot.queryParams['groupId']) {
      this.query.params.groupId = this.route.snapshot.queryParams['groupId'];
      this.query.params.groupName = this.route.snapshot.queryParams['groupName'];
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
    this.query.params.username = '';
    this.query.params.groupId = '';
    this.query.params.groupName = '';
    this.fetch();
  }

  onBatchDelete(): void {
    this.groupMembersService.delete(set2String(this.query.tableCheckedId)).subscribe(res => {
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
    if (this.query.params.username != '') {
      const modal = this.modalService.create({
        nzContent: MemberGroupsEditerComponent,
        nzViewContainerRef: this.viewContainerRef,
        nzComponentParams: {
          username: this.query.params.username
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
    } else if (this.query.params.groupId != '') {
      const modal = this.modalService.create({
        nzContent: GroupMembersEditerComponent,
        nzViewContainerRef: this.viewContainerRef,
        nzComponentParams: {
          isEdit: false,
          groupId: this.query.params.groupId
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
      nzContent: SelectGroupsComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzComponentParams: {},
      nzWidth: 700,
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.query.params.groupName = result.data.groupName;
        this.query.params.groupId = result.data.id;
        console.log(result);
        this.fetch();
      }
    });
  }

  onDelete(deleteId: String): void {
    this.groupMembersService.delete(deleteId).subscribe(res => {
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
    this.groupMembersService.member(this.query.params).subscribe(res => {
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

  //group list
  groupQuery: {
    params: {
      groupName: String;
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
      groupName: '',
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

  onGroupSearch(): void {
    this.fetchGroup();
  }

  onGroupQueryParamsChange(tableQueryParams: NzTableQueryParams): void {
    this.groupQuery.params.pageNumber = tableQueryParams.pageIndex;
    this.groupQuery.params.pageSize = tableQueryParams.pageSize;
    this.fetchGroup();
  }

  fetchGroup(): void {
    this.groupQuery.submitLoading = true;
    this.groupQuery.tableLoading = true;
    this.groupQuery.indeterminate = false;
    this.groupQuery.checked = false;
    this.groupQuery.tableCheckedId.clear();
    this.groupsService.fetch(this.groupQuery.params).subscribe(res => {
      this.groupQuery.results = res.data;
      this.groupQuery.submitLoading = false;
      this.groupQuery.tableLoading = false;
      this.cdr.detectChanges();
    });
  }

  updateGroupTableCheckedSet(id: String, checked: boolean): void {
    if (checked) {
      this.groupQuery.tableCheckedId.add(id);
    } else {
      this.groupQuery.tableCheckedId.delete(id);
    }
  }

  refreshGroupTableCheckedStatus(): void {
    const listOfEnabledData = this.groupQuery.results.rows.filter(({ disabled }) => !disabled);
    this.groupQuery.checked = listOfEnabledData.every(({ id }) => this.groupQuery.tableCheckedId.has(id));
    this.groupQuery.indeterminate = listOfEnabledData.some(({ id }) => this.groupQuery.tableCheckedId.has(id)) && !this.groupQuery.checked;
  }

  onGroupTableItemChecked(groupId: String, groupName: String, checked: boolean): void {
    console.log(`checked ${checked} , groupId ${groupId}  , groupName ${groupName}`);
    this.onGroupTableAllChecked(false);
    this.updateGroupTableCheckedSet(groupId, checked);
    this.refreshGroupTableCheckedStatus();
    this.query.params.groupId = groupId;
    this.query.params.groupName = groupName;
    this.fetch();
  }

  onGroupTableAllChecked(checked: boolean): void {
    this.groupQuery.results.rows.filter(({ disabled }) => !disabled).forEach(({ id }) => this.updateGroupTableCheckedSet(id, checked));
    this.refreshGroupTableCheckedStatus();
  }
}
