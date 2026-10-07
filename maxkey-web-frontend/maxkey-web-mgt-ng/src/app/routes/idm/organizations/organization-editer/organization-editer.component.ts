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
import { I18nPluralPipe } from '@angular/common';
import { LocalizedString } from '@angular/compiler';
import { Component, ChangeDetectorRef, Input, OnInit, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { I18NService } from '@core';
import { _HttpClient, ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { NzI18nService } from 'ng-zorro-antd/i18n';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService, NZ_MODAL_DATA } from 'ng-zorro-antd/modal';
import { NzFormatEmitEvent, NzTreeNode, NzTreeNodeOptions } from 'ng-zorro-antd/tree';
import { NzTreeSelectComponent } from 'ng-zorro-antd/tree-select';

import { IModalData } from '../../../../entity/IModalData';
import { Organizations } from '../../../../entity/Organizations';
import { OrganizationsService } from '../../../../service/organizations.service';
import { SHARED_IMPORTS } from '../../../../shared/shared-imports';

export interface IOrgModalData extends IModalData {
  parentNode: NzTreeNode;
  orgNodes: any[];
}

@Component({
  selector: 'app-organization-editer',
  templateUrl: './organization-editer.component.html',
  styles: [
    `
      nz-form-item,
      nz-tabset {
        width: 90%;
      }
    `
  ],
  styleUrls: ['./organization-editer.component.less'],
  imports: [SHARED_IMPORTS]
})
export class OrganizationEditerComponent implements OnInit {
  @ViewChild('orgTree') orgTree!: NzTreeSelectComponent;

  modalData = inject<IOrgModalData>(NZ_MODAL_DATA);
  form: {
    submitting: boolean;
    model: Organizations;
  } = {
    submitting: false,
    model: new Organizations()
  };

  formGroup: FormGroup = new FormGroup({});

  private readonly modalRef: NzModalRef = inject(NzModalRef);
  private readonly orgsService: OrganizationsService = inject(OrganizationsService);
  private readonly fb: FormBuilder = inject(FormBuilder);
  private readonly msg: NzMessageService = inject(NzMessageService);
  private readonly i18n: I18NService = inject<I18NService>(ALAIN_I18N_TOKEN);
  private readonly cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  ngOnInit(): void {
    if (this.modalData.isEdit) {
      this.orgsService.get(`${this.modalData.id}`).subscribe(res => {
        this.form.model.init(res.data);
        this.cdr.detectChanges();
      });
    } else {
      this.form.model.type = 'department';
      this.form.model.sortIndex = 11;
      if (this.modalData.parentNode) {
        this.form.model.parentId = this.modalData.parentNode?.key;
        this.form.model.parentName = this.modalData.parentNode?.title;
        this.cdr.detectChanges();
      }
    }
  }
  onDeptChange(key: string): void {
    let node = this.orgTree.getTreeNodeByKey(key);
    if (node) {
      this.form.model.parentName = node.title;
    }
  }
  onClose(e: MouseEvent): void {
    e.preventDefault();
    this.modalRef.destroy({ refresh: false });
  }

  onSubmit(e: MouseEvent): void {
    e.preventDefault();
    this.form.submitting = true;
    this.form.model.trans();

    (this.modalData.isEdit ? this.orgsService.update(this.form.model) : this.orgsService.add(this.form.model)).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi(this.modalData.isEdit ? 'mxk.alert.update.success' : 'mxk.alert.add.success'));
      } else {
        this.msg.error(this.i18n.fanyi(this.modalData.isEdit ? 'mxk.alert.update.error' : 'mxk.alert.add.error'));
      }
      this.form.submitting = false;
      this.modalRef.destroy({ refresh: true });
      this.cdr.detectChanges();
    });
  }
}
