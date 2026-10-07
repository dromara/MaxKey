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

import { Component, ChangeDetectorRef, Input, OnInit, ViewContainerRef, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { I18NService } from '@core';
import { _HttpClient, ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService, NZ_MODAL_DATA } from 'ng-zorro-antd/modal';

import { SynchronizerConfigFieldEditComponent } from './editer/synchronizer-config-field-edit.component';
import { IModalData } from '../../../../entity/IModalData';
import { JobConfigFeild } from '../../../../entity/JobConfigFeild';
import { SynchronizersService } from '../../../../service/synchronizers.service';
import { SHARED_IMPORTS } from '../../../../shared/shared-imports';

export interface IJobConfigModalData extends IModalData {
  jobId: string;
}
@Component({
  selector: 'app-synchronizer-config-field',
  templateUrl: './synchronizer-config-field.component.html',
  styles: [
    `
      nz-form-item {
        width: 100%;
      }
    `
  ],
  styleUrls: ['./synchronizer-config-field.component.less'],
  imports: [SHARED_IMPORTS]
})
export class SynchronizerConfigFieldComponent implements OnInit {
  modalData = inject<IJobConfigModalData>(NZ_MODAL_DATA);

  form: {
    submitting: boolean;
    rows: NzSafeAny[];
  } = {
    submitting: false,
    rows: []
  };

  formGroup: FormGroup = new FormGroup({});

  private readonly modalRef: NzModalRef = inject(NzModalRef);
  private readonly modalService: NzModalService = inject(NzModalService);
  private readonly synchronizersService: SynchronizersService = inject(SynchronizersService);
  private readonly viewContainerRef: ViewContainerRef = inject(ViewContainerRef);
  private readonly fb: FormBuilder = inject(FormBuilder);
  private readonly msg: NzMessageService = inject(NzMessageService);
  private readonly i18n: I18NService = inject<I18NService>(ALAIN_I18N_TOKEN);
  private readonly cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.fetch();
  }
  fetch() {
    this.synchronizersService.getMapping(`${this.modalData.jobId}`).subscribe(res => {
      this.form.rows = res.data;
      this.cdr.detectChanges();
    });
  }
  onClose(e: MouseEvent): void {
    e.preventDefault();
    this.modalRef.destroy({ refresh: false });
  }

  onDelete(e: MouseEvent, deleteId: string): void {
    e.preventDefault();
    this.synchronizersService.deleteMapping(deleteId).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi('mxk.alert.delete.success'));
        this.fetch();
      } else {
        this.msg.error(this.i18n.fanyi('mxk.alert.delete.error'));
      }
      this.cdr.detectChanges();
    });
  }
  onEdit(e: MouseEvent, id: string): void {
    e.preventDefault();
    const modal = this.modalService.create({
      nzContent: SynchronizerConfigFieldEditComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzData: {
        isEdit: true,
        id: id,
        jobId: this.modalData.jobId
      },
      nzWidth: 1200,
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.fetch();
      }
    });
  }
  onAdd(e: MouseEvent): void {
    e.preventDefault();
    const modal = this.modalService.create({
      nzContent: SynchronizerConfigFieldEditComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzData: {
        isEdit: false,
        jobId: this.modalData.jobId
      },
      nzWidth: 1200,
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.fetch();
      }
    });
  }

  onSubmit(e: MouseEvent): void {
    e.preventDefault();
    this.form.submitting = true;

    /* (this.isEdit ? this.synchronizersService.update(this.form.model) : this.synchronizersService.add(this.form.model)).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi(this.isEdit ? 'mxk.alert.update.success' : 'mxk.alert.add.success'));
      } else {
        this.msg.error(this.i18n.fanyi(this.isEdit ? 'mxk.alert.update.error' : 'mxk.alert.add.error'));
      }
      this.form.submitting = false;
      this.modalRef.destroy({ refresh: true });
      this.cdr.detectChanges();
    });*/
  }
}
