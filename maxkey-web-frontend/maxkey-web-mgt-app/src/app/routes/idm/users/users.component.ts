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
import { Router } from '@angular/router';
import { I18NService } from '@core';
import { _HttpClient, ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { format, addDays } from 'date-fns';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { NzContextMenuService, NzDropdownMenuComponent } from 'ng-zorro-antd/dropdown';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { NzFormatEmitEvent, NzTreeNode, NzTreeNodeOptions } from 'ng-zorro-antd/tree';
import { Users } from 'src/app/entity/Users';

import { PageResults } from '../../../entity/PageResults';
import { TreeNodes } from '../../../entity/TreeNodes';
import { OrganizationsService } from '../../../service/organizations.service';
import { UsersService } from '../../../service/users.service';
import { set2String } from '../../../shared/index';
import { PasswordComponent } from './password/password.component';
import { UserEditerComponent } from './user-editer/user-editer.component';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.less']
})
export class UsersComponent implements OnInit {
  query: {
    params: {
      name: String;
      username: String;
      displayName: String;
      departmentId: String;
      startDate: String;
      endDate: String;
      startDatePicker: Date;
      endDatePicker: Date;
      pageSize: number;
      pageNumber: number;
      pageSizeOptions: number[];
    };
    results: PageResults;
    expandForm: Boolean;
    submitLoading: boolean;
    tableLoading: boolean;
    tableCheckedId: Set<String>;
    indeterminate: boolean;
    checked: boolean;
  } = {
    params: {
      name: '',
      displayName: '',
      departmentId: '',
      username: '',
      startDate: '',
      endDate: '',
      startDatePicker: addDays(new Date(), -30),
      endDatePicker: new Date(),
      pageSize: 10,
      pageNumber: 1,
      pageSizeOptions: [10, 20, 50]
    },
    results: new PageResults(),
    expandForm: false,
    submitLoading: false,
    tableLoading: false,
    tableCheckedId: new Set<String>(),
    indeterminate: false,
    checked: false
  };

  // TreeNodes
  treeNodes = new TreeNodes(false);

  constructor(
    private modal: NzModalService,
    private viewContainerRef: ViewContainerRef,
    private usersService: UsersService,
    private orgsService: OrganizationsService,
    private fb: FormBuilder,
    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.fetch();
    this.tree();
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
    this.usersService.delete(set2String(this.query.tableCheckedId)).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi('mxk.alert.delete.success'));
        this.fetch();
      } else {
        this.msg.error(this.i18n.fanyi('mxk.alert.delete.error'));
      }
      this.cdr.detectChanges();
    });
  }

  changePassword(e: MouseEvent): void {
    e.preventDefault();
    let lastCheckedId: String = '';
    this.query.tableCheckedId.forEach(value => {
      lastCheckedId = value;
    });
    for (var i = 0; i < this.query.results.rows.length; i++) {
      let user = this.query.results.rows[i];
      if (lastCheckedId == user.id) {
        const modal = this.modal.create({
          nzContent: PasswordComponent,
          nzViewContainerRef: this.viewContainerRef,
          nzComponentParams: {
            id: user.id,
            username: user.username,
            displayName: user.displayName
          },
          nzWidth: 450,
          nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
        });
      }
    }
  }

  changePasswordById(e: MouseEvent, userId: String): void {
    e.preventDefault();
    for (var i = 0; i < this.query.results.rows.length; i++) {
      let user = this.query.results.rows[i];
      if (userId == user.id) {
        const modal = this.modal.create({
          nzContent: PasswordComponent,
          nzViewContainerRef: this.viewContainerRef,
          nzComponentParams: {
            id: user.id,
            username: user.username,
            displayName: user.displayName
          },
          nzWidth: 450,
          nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
        });
      }
    }
  }

  onAdd(e: MouseEvent): void {
    e.preventDefault();
    const modal = this.modal.create({
      nzContent: UserEditerComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzComponentParams: {
        isEdit: false,
        parentNode: this.treeNodes.activated,
        orgNodes: this.treeNodes.nodes,
        id: ''
      },
      nzWidth: 750,
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.fetch();
      }
    });
  }

  onEdit(e: MouseEvent, editId: String): void {
    e.preventDefault();

    const modal = this.modal.create({
      nzContent: UserEditerComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzComponentParams: {
        isEdit: true,
        orgNodes: this.treeNodes.nodes,
        id: editId
      },
      nzWidth: 750,
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.fetch();
      }
    });
  }

  onNavToUrl(e: MouseEvent, userId: String, username: String, navType: String) {
    e.preventDefault();
    if (navType === 'groups') {
      this.router.navigateByUrl(`/idm/groupmembers?username=${username}&userId=${userId}`);
    }
  }

  onUpdateStatus(e: MouseEvent, userId: String, status: number): void {
    e.preventDefault();
    this.usersService.updateStatus({ id: userId, status: status }).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi('mxk.alert.operate.success'));
        this.fetch();
      } else {
        this.msg.error(this.i18n.fanyi('mxk.alert.operate.error'));
      }
      this.cdr.detectChanges();
    });
  }

  onDelete(deleteId: String): void {
    this.usersService.delete(deleteId).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi('mxk.alert.operate.success'));
        this.fetch();
      } else {
        this.msg.error(this.i18n.fanyi('mxk.alert.operate.error'));
      }
      this.cdr.detectChanges();
    });
  }

  tree(): void {
    this.orgsService.tree({}).subscribe(res => {
      this.treeNodes.init(res.data);
      this.treeNodes.nodes = this.treeNodes.build();
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
    this.usersService.fetch(this.query.params).subscribe(res => {
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

  openFolder(data: NzTreeNode | NzFormatEmitEvent): void {
    // do something if u want
    if (data instanceof NzTreeNode) {
      data.isExpanded = !data.isExpanded;
    } else {
      const node = data.node;
      if (node) {
        node.isExpanded = !node.isExpanded;
      }
    }
  }

  activeNode(data: NzFormatEmitEvent): void {
    this.treeNodes.activated = data.node!;
    this.query.params.departmentId = data.node!.key;
    this.fetch();
  }

  contextMenu($event: MouseEvent, menu: NzDropdownMenuComponent): void {
    //this.nzContextMenuService.create($event, menu);
  }

  selectDropdown(): void {
    // do something
  }
}
