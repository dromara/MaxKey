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

import { Component, ChangeDetectorRef, Input, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { I18NService } from '@core';
import { _HttpClient, ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef } from 'ng-zorro-antd/modal';

import { JobConfigFeild } from '../../../../../entity/JobConfigFeild';
import { SynchronizersService } from '../../../../../service/synchronizers.service';


@Component({
  selector: 'app-synchronizer-config-field-edit',
  templateUrl: './synchronizer-config-field-edit.component.html',
  styles: [
    `
      nz-form-item {
        width: 100%;
      }
    `
  ],
})
export class SynchronizerConfigFieldEditComponent implements OnInit {
  @Input() id?: String;
  @Input() jobId?: String;
  @Input() isEdit?: boolean;

  form: {
    submitting: boolean;
    model:  JobConfigFeild
  } = {
    submitting: false,
    model: new JobConfigFeild(),
  };
  formGroup: FormGroup = new FormGroup({});
  constructor(
    private modalRef: NzModalRef,
    private synchronizersService: SynchronizersService,
    private fb: FormBuilder,
    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
   this.get()
  }
  get(){
    if (this.isEdit) {
      this.synchronizersService.getField(`${this.id}`).subscribe(res => {
        this.form.model.init(res.data);
        this.cdr.detectChanges();
      });
    }
  }
  onClose(e: MouseEvent): void {
    e.preventDefault();
    this.modalRef.destroy({ refresh: false });
  }

  onSubmit(e: MouseEvent): void {
    e.preventDefault();
    this.form.submitting = true;
    this.form.model.jobId = this.jobId +"";
   (this.isEdit ? this.synchronizersService.mappingUpdate(this.form.model) : this.synchronizersService.mappingAdd(this.form.model)).subscribe(res => {
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
