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
import { _HttpClient } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService, NZ_MODAL_DATA } from 'ng-zorro-antd/modal';

import { Adapters } from '../../../../entity/Adapters';
import { IModalData } from '../../../../entity/IModalData';
import { AdaptersService } from '../../../../service/adapters.service';
import { SHARED_IMPORTS } from '../../../../shared/shared-imports';

@Component({
  selector: 'app-adapter-editer',
  templateUrl: './adapter-editer.component.html',
  styles: [
    `
      nz-form-item {
        width: 100%;
      }
    `
  ],
  styleUrls: ['./adapter-editer.component.less'],
  imports: [SHARED_IMPORTS]
})
export class AdapterEditerComponent implements OnInit {
  modalData = inject<IModalData>(NZ_MODAL_DATA);

  form: {
    submitting: boolean;
    model: Adapters;
  } = {
    submitting: false,
    model: new Adapters()
  };

  formGroup: FormGroup = new FormGroup({});

  private readonly modalRef: NzModalRef = inject(NzModalRef);
  private readonly adaptersService: AdaptersService = inject(AdaptersService);
  private readonly fb: FormBuilder = inject(FormBuilder);
  private readonly msg: NzMessageService = inject(NzMessageService);
  private readonly cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  ngOnInit(): void {
    if (this.modalData.isEdit) {
      this.adaptersService.get(`${this.modalData.id}`).subscribe(res => {
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
    this.form.model.trans();
    (this.modalData.isEdit ? this.adaptersService.update(this.form.model) : this.adaptersService.add(this.form.model)).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(`鎻愪氦鎴愬姛`);
      } else {
        this.msg.success(`鎻愪氦澶辫触`);
      }
      this.form.submitting = false;
      this.modalRef.destroy({ refresh: true });
      this.cdr.detectChanges();
    });
  }
}
