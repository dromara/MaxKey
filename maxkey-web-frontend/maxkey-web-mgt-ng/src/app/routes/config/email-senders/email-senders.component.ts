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

import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { I18NService } from '@core';
import { _HttpClient, ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';

import { EmailSenders } from '../../../entity/CnfEmailSenders';
import { EmailSendersService } from '../../../service/email-senders.service';
import { SHARED_IMPORTS } from '../../../shared/shared-imports';

@Component({
  selector: 'app-email-senders',
  templateUrl: './email-senders.component.html',
  styles: [
    `
      [nz-form] {
        max-width: 90%;
      }

      nz-form-item {
        width: 50%;
      }
    `
  ],
  styleUrls: ['./email-senders.component.less'],
  imports: [SHARED_IMPORTS]
})
export class EmailSendersComponent implements OnInit {
  form: {
    submitting: boolean;
    model: EmailSenders;
  } = {
    submitting: false,
    model: new EmailSenders()
  };

  formGroup: FormGroup = new FormGroup({});

  private readonly fb: FormBuilder = inject(FormBuilder);
  private readonly emailSendersService: EmailSendersService = inject(EmailSendersService);
  private readonly msg: NzMessageService = inject(NzMessageService);
  private readonly i18n: I18NService = inject<I18NService>(ALAIN_I18N_TOKEN);
  private readonly cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.emailSendersService.get('').subscribe(res => {
      this.form.model.init(res.data);
      this.cdr.detectChanges();
    });
  }

  onSubmit(): void {
    this.form.submitting = true;
    this.form.model.trans();
    this.emailSendersService.update(this.form.model).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi('mxk.alert.operate.success'));
      } else {
        this.msg.error(this.i18n.fanyi('mxk.alert.operate.error'));
      }
      this.form.submitting = false;
      this.cdr.detectChanges();
    });
  }
}
