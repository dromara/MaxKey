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
import { NzContextMenuService, NzDropdownMenuComponent } from 'ng-zorro-antd/dropdown';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { NzFormatEmitEvent, NzTreeNode, NzTreeNodeOptions } from 'ng-zorro-antd/tree';

import { MfaComponent } from './mfa/mfa.component';
import { PasswordComponent } from './password/password.component';
import { UserEditerComponent } from './user-editer/user-editer.component';
import { PageResults } from '../../../entity/PageResults';
import { TreeNodes } from '../../../entity/TreeNodes';
import { Users } from '../../../entity/Users';
import { OrganizationsService } from '../../../service/organizations.service';
import { UsersService } from '../../../service/users.service';
import { set2String } from '../../../shared/index';
import { SHARED_IMPORTS } from '../../../shared/shared-imports';
@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.less'],
  imports: [SHARED_IMPORTS]
})
export class UsersComponent implements OnInit {
  private readonly modal = inject(NzModalService);
  private readonly viewContainerRef = inject(ViewContainerRef);
  private readonly usersService = inject(UsersService);
  private readonly orgsService = inject(OrganizationsService);
  private readonly fb = inject(FormBuilder);
  private readonly msg = inject(NzMessageService);
  private readonly i18n = inject<I18NService>(ALAIN_I18N_TOKEN);
  private readonly router = inject(Router);
  private readonly cdr = inject(ChangeDetectorRef);

  query: {
    params: {
      name: string;
      username: string;
      displayName: string;
      departmentId: string;
      startDate: string;
      endDate: string;
      startDatePicker: Date;
      endDatePicker: Date;
      pageSize: number;
      pageNumber: number;
      pageSizeOptions: number[];
    };
    results: PageResults;
    expandForm: boolean;
    submitLoading: boolean;
    tableLoading: boolean;
    tableCheckedId: Set<string>;
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
    tableCheckedId: new Set<string>(),
    indeterminate: false,
    checked: false
  };

  // TreeNodes
  treeNodes = new TreeNodes(false);

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
    let lastCheckedId = '';
    this.query.tableCheckedId.forEach(value => {
      lastCheckedId = value;
    });
    for (let user of this.query.results.rows) {
      if (lastCheckedId == user.id) {
        const modal = this.modal.create({
          nzContent: PasswordComponent,
          nzViewContainerRef: this.viewContainerRef,
          nzData: {
            id: user.id,
            username: user.username,
            displayName: user.displayName
          },
          nzWidth: 450,
          nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
        });
        break;
      }
    }
  }

  changePasswordById(e: MouseEvent, userId: string): void {
    e.preventDefault();
    for (let user of this.query.results.rows) {
      if (userId == user.id) {
        const modal = this.modal.create({
          nzContent: PasswordComponent,
          nzViewContainerRef: this.viewContainerRef,
          nzData: {
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
      nzData: {
        isEdit: false,
        parentNode: this.treeNodes.activated,
        treeNodes: this.treeNodes.nodes,
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

  onEdit(e: MouseEvent, editId: string): void {
    e.preventDefault();

    const modal = this.modal.create({
      nzContent: UserEditerComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzData: {
        isEdit: true,
        treeNodes: this.treeNodes.nodes,
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

  changeMfaById(userId: string): void {
    console.log(`Changing MFA for userId: ${userId}`);
    for (let user of this.query.results.rows) {
      if (userId == user.id) {
        console.log(`Found user for MFA change: ${user.username}`);
        const modal = this.modal.create({
          nzContent: MfaComponent,
          nzViewContainerRef: this.viewContainerRef,
          nzData: {
            id: user.id,
            username: user.username,
            displayName: user.displayName
          },
          nzWidth: 450,
          nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
        });
        break;
      }
    }
  }

  onNavToUrl(e: MouseEvent, userId: string, username: string, navType: string) {
    e.preventDefault();
    if (navType === 'groups') {
      this.router.navigateByUrl(`/idm/groupmembers?username=${username}&userId=${userId}`);
    }
  }

  onUpdateStatus(e: MouseEvent, userId: string, status: number): void {
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

  onDelete(deleteId: string): void {
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
    this.query.params.pageNumber = 1;
    this.fetch();
  }

  contextMenu($event: MouseEvent, menu: NzDropdownMenuComponent): void {
    //this.nzContextMenuService.create($event, menu);
  }

  selectDropdown(): void {
    // do something
  }
}
