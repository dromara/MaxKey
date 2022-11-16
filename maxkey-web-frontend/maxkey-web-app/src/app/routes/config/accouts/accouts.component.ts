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

import { Component, ChangeDetectorRef, OnInit, Input, Inject } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { I18NService } from '@core';
import { ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { environment } from '@env/environment';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

import { Accounts } from '../../../entity/Accounts';
import { AccountsService } from '../../../service/accounts.service';

@Component({
  selector: 'app-accouts',
  templateUrl: './accouts.component.html',
  styleUrls: ['./accouts.component.less']
})
export class AccoutsComponent implements OnInit {
  @Input() appId?: String;

  form: {
    submitting: boolean;
    model: Accounts;
  } = {
    submitting: false,
    model: new Accounts()
  };
  redirect_uri: string = '';
  formGroup: FormGroup = new FormGroup({});

  confirmPasswordVisible = false;

  passwordVisible = false;

  constructor(
    fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private modalRef: NzModalRef,
    private accountsService: AccountsService,
    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    if (this.appId) {
      this.accountsService.get(this.appId).subscribe(res => {
        console.log(res.data);
        this.form.model.init(res.data);
        this.cdr.detectChanges();
      });
    }
  }

  onClose(e: MouseEvent): void {
    e.preventDefault();
    this.modalRef.destroy({ refresh: false });
  }

  onSubmit(): void {
    this.form.submitting = true;
    this.form.model.trans();
    //if (this.formGroup.valid) {
    this.accountsService.update(this.form.model).subscribe(res => {
      if (res.code == 0) {
        this.form.model.init(res.data);
        this.msg.success(this.i18n.fanyi('mxk.alert.operate.success'));
        if (this.redirect_uri) {
          window.location.href = `${environment.api.baseUrl}${this.redirect_uri}`;
        }
      } else {
        this.msg.error(this.i18n.fanyi('mxk.alert.operate.error'));
      }
    });
    this.form.submitting = false;
    this.cdr.detectChanges();
  }
}
