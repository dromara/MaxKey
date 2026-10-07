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

import { Component, ChangeDetectorRef, Input, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { I18NService } from '@core';
import { _HttpClient, ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService, NZ_MODAL_DATA } from 'ng-zorro-antd/modal';
import { NzFormatEmitEvent, NzTreeNode, NzTreeNodeOptions } from 'ng-zorro-antd/tree';

import { IModalData } from '../../../../entity/IModalData';
import { Resources } from '../../../../entity/Resources';
import { ResourcesService } from '../../../../service/resources.service';
import { SHARED_IMPORTS } from '../../../../shared/shared-imports';

export interface IResourceModalData extends IModalData {
  parentNode: NzTreeNode;
  appId: string;
  appName: string;
}

@Component({
  selector: 'app-resource-editer',
  templateUrl: './resource-editer.component.html',
  styles: [
    `
      nz-form-item,
      nz-tabset {
        width: 90%;
      }
    `
  ],
  styleUrls: ['./resource-editer.component.less'],
  imports: [SHARED_IMPORTS]
})
export class ResourceEditerComponent implements OnInit {
  modalData = inject<IResourceModalData>(NZ_MODAL_DATA);

  form: {
    submitting: boolean;
    model: Resources;
  } = {
    submitting: false,
    model: new Resources()
  };

  formGroup: FormGroup = new FormGroup({});

  private readonly modalRef = inject(NzModalRef);
  private readonly resourcesService = inject(ResourcesService);
  private readonly fb = inject(FormBuilder);
  private readonly msg = inject(NzMessageService);
  private readonly i18n = inject<I18NService>(ALAIN_I18N_TOKEN);
  private readonly cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    if (this.modalData.isEdit) {
      this.resourcesService.get(`${this.modalData.id}`).subscribe(res => {
        this.form.model.init(res.data);
        this.cdr.detectChanges();
      });
    } else {
      if (this.modalData.parentNode) {
        this.form.model.appId = this.modalData.appId || '';
        this.form.model.appName = this.modalData.appName || '';
        this.form.model.parentId = this.modalData.parentNode?.key;
        this.form.model.parentName = this.modalData.parentNode?.title;
      }
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
    (this.modalData.isEdit ? this.resourcesService.update(this.form.model) : this.resourcesService.add(this.form.model)).subscribe(res => {
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
