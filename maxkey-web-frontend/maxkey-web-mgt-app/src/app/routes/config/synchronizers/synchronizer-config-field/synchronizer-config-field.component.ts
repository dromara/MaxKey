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

import {Component, ChangeDetectorRef, Input, OnInit, Inject, ViewContainerRef} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { I18NService } from '@core';
import { _HttpClient, ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import format from 'date-fns/format';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

import { JobConfigFeild } from '../../../../entity/JobConfigFeild';
import { SynchronizersService } from '../../../../service/synchronizers.service';
import {NzSafeAny} from "ng-zorro-antd/core/types";
import { SynchronizerConfigFieldEditComponent } from './editer/synchronizer-config-field-edit.component';

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
  styleUrls: ['./synchronizer-config-field.component.less']
})
export class SynchronizerConfigFieldComponent implements OnInit {
  @Input() jobId?: String;
  @Input() isEdit?: boolean;

  form: {
    submitting: boolean;
    rows: NzSafeAny[]
  } = {
    submitting: false,
    rows: []
  };

  formGroup: FormGroup = new FormGroup({});

  constructor(
    private modalRef: NzModalRef,
    private modalService: NzModalService,
    private synchronizersService: SynchronizersService,
    private viewContainerRef: ViewContainerRef,
    private fb: FormBuilder,
    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
   this.fetch()
  }
  fetch(){
    this.synchronizersService.getMapping(`${this.jobId}`).subscribe(res => {
      this.form.rows = res.data
      this.cdr.detectChanges();
    });
  }
  onClose(e: MouseEvent): void {
    e.preventDefault();
    this.modalRef.destroy({ refresh: false });
  }

  onDelete(e: MouseEvent, deleteId: String): void {
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
  onEdit(e: MouseEvent, id: String): void {
    e.preventDefault();
    const modal = this.modalService.create({
      nzContent: SynchronizerConfigFieldEditComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzComponentParams: {
        isEdit: true,
        id: id,
        jobId: this.jobId,
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
      nzComponentParams: {
        isEdit: false,
        jobId: this.jobId,
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
