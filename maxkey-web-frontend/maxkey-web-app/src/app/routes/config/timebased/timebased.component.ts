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

import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { _HttpClient } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';

import { TimeBased } from '../../../entity/TimeBased';
import { TimeBasedService } from '../../../service/time-based.service';
import { splitString, concatArrayString } from '../../../shared/utils/set2stringstil';

import { Console } from 'console';

@Component({
  selector: 'app-timebased',
  templateUrl: './timebased.component.html',
  styleUrls: ['./timebased.component.less']
})
export class TimebasedComponent implements OnInit {
  form: {
    submitting: boolean;
    model: TimeBased;
  } = {
      submitting: false,
      model: new TimeBased()
    };

  isDisabled = true;

  formGroup: FormGroup = new FormGroup({});

  constructor(
    private fb: FormBuilder,
    private timeBasedService: TimeBasedService,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    /*this.form = this.fb.group({
  title: [null, [Validators.required]],
  date: [null, [Validators.required]],
  goal: [null, [Validators.required]],
  standard: [null, [Validators.required]],
  client: [null, []],
  invites: [null, []],
  weight: [null, []],
  public: [1, [Validators.min(1), Validators.max(3)]],
  publicUsers: [null, []]
});*/
    this.timeBasedService.view('').subscribe(res => {
      this.form.model.init(res.data);
      this.formatSecret();
      this.cdr.detectChanges();
    });
  }

  formatSecret(): void {
    this.form.model.formatSharedSecret = concatArrayString(splitString(this.form.model.sharedSecret, 4), ' ');
    //this.form.model.hexSharedSecret = concatArrayString(splitString(this.form.model.hexSharedSecret, 4), ' ');
  }

  generate(): void {
    this.form.submitting = true;
    this.form.model.trans();
    this.timeBasedService.generate('').subscribe(res => {
      if (res.code == 0) {
        this.form.model.init(res.data);
        this.formatSecret();
        //this.msg.success(`提交成功`);
      } else {
        //this.msg.success(`提交失败`);
      }
      this.form.submitting = false;
      this.cdr.detectChanges();
    });
  }

  onSubmit(): void {
    this.form.submitting = true;
    //this.form.model.trans();
    this.timeBasedService.update(this.form.model).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(`提交成功`);
      } else {
        this.msg.success(`提交失败`);
      }
      this.form.submitting = false;
      this.cdr.detectChanges();
    });
  }

  verify(e: MouseEvent, otp: string): void {
    e.preventDefault();
    this.timeBasedService.verify(otp).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(`验证成功`);
      } else {
        this.msg.error('验证失败');
      }
    });
    // this.timeBasedService.verify(otp)
  }

  protected readonly String = String;
}
