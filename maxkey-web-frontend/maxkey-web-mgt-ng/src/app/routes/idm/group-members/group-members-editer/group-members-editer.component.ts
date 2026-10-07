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

import { ChangeDetectionStrategy, ViewContainerRef, Input, ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { I18NService } from '@core';
import { _HttpClient, ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { format, addDays } from 'date-fns';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService, NZ_MODAL_DATA } from 'ng-zorro-antd/modal';
import { NzTableQueryParams } from 'ng-zorro-antd/table';

import { Groups } from '../../../../entity/Groups';
import { IModalData } from '../../../../entity/IModalData';
import { GroupMembersService } from '../../../../service/group-members.service';
import { SHARED_IMPORTS } from '../../../../shared/shared-imports';

export interface IGroupModalData extends IModalData {
  groupId: string;
}

@Component({
  selector: 'app-group-members-editer',
  templateUrl: './group-members-editer.component.html',
  styleUrls: ['./group-members-editer.component.less'],
  imports: [SHARED_IMPORTS]
})
export class GroupMembersEditerComponent implements OnInit {
  modalData = inject<IGroupModalData>(NZ_MODAL_DATA);

  query: {
    params: {
      name: string;
      displayName: string;
      username: string;
      groupId: string;
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
      name: '',
      displayName: '',
      username: '',
      groupId: '',
      startDate: '',
      endDate: '',
      startDatePicker: addDays(new Date(), -30),
      endDatePicker: new Date(),
      pageSize: 5,
      pageNumber: 1,
      pageSizeOptions: [5, 15, 50]
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

  private readonly modalRef: NzModalRef = inject(NzModalRef);
  private readonly groupMembersService: GroupMembersService = inject(GroupMembersService);
  private readonly viewContainerRef: ViewContainerRef = inject(ViewContainerRef);
  private readonly fb: FormBuilder = inject(FormBuilder);
  private readonly msg: NzMessageService = inject(NzMessageService);
  private readonly i18n: I18NService = inject<I18NService>(ALAIN_I18N_TOKEN);
  private readonly cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  ngOnInit(): void {
    if (this.modalData.groupId) {
      this.query.params.groupId = this.modalData.groupId;
      this.fetch();
    }
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
    this.groupMembersService.memberOut(this.query.params).subscribe(res => {
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
    //this.onTableAllChecked(false);
    this.updateTableCheckedSet(id, checked);
    this.refreshTableCheckedStatus();
  }

  onTableAllChecked(checked: boolean): void {
    this.query.results.rows.filter(({ disabled }) => !disabled).forEach(({ id }) => this.updateTableCheckedSet(id, checked));
    this.refreshTableCheckedStatus();
  }

  onSubmit(e: MouseEvent): void {
    e.preventDefault();
    const listOfEnabledData = this.query.results.rows.filter(({ disabled }) => !disabled);
    let selectedData = listOfEnabledData.filter(({ id, name }) => {
      return this.query.tableCheckedId.has(id);
    });
    let memberIds = '';
    let memberNames = '';
    for (let i = 0; i < selectedData.length; i++) {
      memberIds = `${memberIds},${selectedData[i].id}`;
      memberNames = `${memberNames},${selectedData[i].username}`;
    }
    this.groupMembersService
      .add({ type: 'USER', groupId: this.modalData.groupId, memberId: memberIds, memberName: memberNames })
      .subscribe(res => {
        this.query.results = res.data;
        this.query.submitLoading = false;
        this.query.tableLoading = false;
        if (res.code == 0) {
          this.msg.success(this.i18n.fanyi('mxk.alert.operate.success'));
          this.fetch();
        } else {
          this.msg.error(this.i18n.fanyi('mxk.alert.operate.error'));
        }
        this.cdr.detectChanges();
      });
  }

  onClose(e: MouseEvent): void {
    e.preventDefault();
  }
}
