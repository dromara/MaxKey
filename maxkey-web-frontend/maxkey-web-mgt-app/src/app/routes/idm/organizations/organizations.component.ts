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
import { I18NService } from '@core';
import { ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { format, addDays } from 'date-fns';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { NzContextMenuService, NzDropdownMenuComponent } from 'ng-zorro-antd/dropdown';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { NzFormatEmitEvent, NzTreeNode, NzTreeNodeOptions } from 'ng-zorro-antd/tree';

import { PageResults } from '../../../entity/PageResults';
import { TreeNodes } from '../../../entity/TreeNodes';
import { OrganizationsService } from '../../../service/organizations.service';
import { set2String } from '../../../shared/index';
import { OrganizationEditerComponent } from './organization-editer/organization-editer.component';

@Component({
  selector: 'app-organizations',
  templateUrl: './organizations.component.html',
  styles: [
    `
      nz-tree {
        overflow: hidden;
        margin: 0 -24px;
        padding: 0 24px;
      }

      .custom-node {
        cursor: pointer;
        line-height: 24px;
        margin-left: 4px;
        display: inline-block;
      }

      .file-name,
      .folder-name {
        margin-left: 4px;
      }

      .file-desc,
      .folder-desc {
        padding: 0 8px;
        display: inline-block;
        background: #87ceff;
        color: #ffffff;
        position: relative;
        left: 12px;
      }
    `
  ],
  styleUrls: ['./organizations.component.less']
})
export class OrganizationsComponent implements OnInit {
  query: {
    params: {
      orgName: String;
      displayName: String;
      parentId: String;
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
      orgName: '',
      displayName: '',
      parentId: '',
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
    private orgsService: OrganizationsService,
    private fb: FormBuilder,
    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
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
    this.orgsService.delete(set2String(this.query.tableCheckedId)).subscribe(res => {
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
    const modal = this.modal.create({
      nzContent: OrganizationEditerComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzComponentParams: {
        isEdit: false,
        parentNode: this.treeNodes.activated,
        orgNodes: this.treeNodes.nodes,
        id: ''
      },
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.fetch();
        this.tree();
      }
    });
  }

  onEdit(e: MouseEvent, editId: String): void {
    e.preventDefault();

    const modal = this.modal.create({
      nzContent: OrganizationEditerComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzComponentParams: {
        isEdit: true,
        orgNodes: this.treeNodes.nodes,
        id: editId
      },
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.fetch();
        this.tree();
      }
    });
  }

  onDelete(deleteId: String): void {
    this.orgsService.delete(deleteId).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi('mxk.alert.delete.success'));
        this.fetch();
        this.tree();
      } else {
        this.msg.error(this.i18n.fanyi('mxk.alert.delete.error'));
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
    this.orgsService.fetch(this.query.params).subscribe(res => {
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
    this.query.params.parentId = data.node!.key;
    this.fetch();
  }

  contextMenu($event: MouseEvent, menu: NzDropdownMenuComponent): void {
    //this.nzContextMenuService.create($event, menu);
  }

  selectDropdown(): void {
    // do something
  }
}
