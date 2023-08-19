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
import { Component, ChangeDetectorRef, Input, OnInit, Inject, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { I18NService } from '@core';
import { _HttpClient, ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import format from 'date-fns/format';
import { NzI18nService } from 'ng-zorro-antd/i18n';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { NzFormatEmitEvent, NzTreeNode, NzTreeNodeOptions } from 'ng-zorro-antd/tree';
import { NzTreeSelectComponent } from 'ng-zorro-antd/tree-select';

import { Organizations } from '../../../../entity/Organizations';
import { OrganizationsService } from '../../../../service/organizations.service';

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
  styleUrls: ['./organization-editer.component.less']
})
export class OrganizationEditerComponent implements OnInit {
  @Input() id?: String;
  @Input() parentNode?: NzTreeNode;
  @Input() isEdit?: boolean;
  @Input() orgNodes!: any[];
  @ViewChild('orgTree') orgTree!: NzTreeSelectComponent;
  form: {
    submitting: boolean;
    model: Organizations;
  } = {
    submitting: false,
    model: new Organizations()
  };

  formGroup: FormGroup = new FormGroup({});

  constructor(
    private modalRef: NzModalRef,
    private orgsService: OrganizationsService,
    private fb: FormBuilder,
    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    if (this.isEdit) {
      this.orgsService.get(`${this.id}`).subscribe(res => {
        this.form.model.init(res.data);
        this.cdr.detectChanges();
      });
    } else {
      this.form.model.type = 'department';
      this.form.model.sortIndex = 11;
      if (this.parentNode) {
        this.form.model.parentId = this.parentNode?.key;
        this.form.model.parentName = this.parentNode?.title;
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

    (this.isEdit ? this.orgsService.update(this.form.model) : this.orgsService.add(this.form.model)).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi(this.isEdit ? 'mxk.alert.update.success' : 'mxk.alert.add.success'));
      } else {
        this.msg.error(this.i18n.fanyi(this.isEdit ? 'mxk.alert.update.error' : 'mxk.alert.add.error'));
      }
      this.form.submitting = false;
      this.modalRef.destroy({ refresh: true });
      this.cdr.detectChanges();
    });
  }
}
