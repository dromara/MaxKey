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

import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';

import { Oauth2ApproveService } from '../../../service/oauth2-approve.service';
import { SHARED_IMPORTS } from '../../../shared/shared-imports';
@Component({
  selector: 'app-oauth2-approve',
  templateUrl: './oauth2-approve.component.html',
  imports: [...SHARED_IMPORTS],
  styleUrls: ['./oauth2-approve.component.less']
})
export class Oauth2ApproveComponent implements OnInit {
  private readonly oauth2ApproveService = inject(Oauth2ApproveService);
  private readonly route = inject(ActivatedRoute);
  private readonly msg = inject(NzMessageService);
  private readonly cdr = inject(ChangeDetectorRef);

  form: {
    submitting: boolean;
    model: {
      clientId?: string;
      appName?: string;
      iconBase64?: string;
      oauth_version?: string;
      user_oauth_approval?: string;
      approval_prompt?: string;
    };
  } = {
    submitting: false,
    model: {
      approval_prompt: 'force'
    }
  };
  redirect_uri: string = '';
  formGroup: FormGroup = new FormGroup({});

  ngOnInit(): void {
    this.oauth2ApproveService.get(this.route.snapshot.queryParams['oauth_approval']).subscribe(res => {
      console.log(res.data);
      this.form.model.clientId = res.data.clientId;
      this.form.model.appName = res.data.appName;
      this.form.model.iconBase64 = res.data.iconBase64;
      this.form.model.oauth_version = res.data.oauth_version;
      this.form.model.user_oauth_approval = 'true';
      this.form.model.approval_prompt = res.data.approval_prompt;
      //this.form.model.init(res.data);
      this.cdr.detectChanges();

      if (this.form.model.approval_prompt == 'auto') {
        this.onSubmit();
      }
    });
  }

  onSubmit(): void {
    this.form.submitting = true;
    //if (this.formGroup.valid) {
    this.oauth2ApproveService.approval(this.form.model).subscribe(res => {
      if (res.code == 0) {
        if (this.form.model.approval_prompt == 'force') {
          this.msg.success(`提交成功`);
        }
        window.location.href = res.data;
      } else {
        this.msg.success(`提交失败`);
      }
    });
    // } else {
    //  this.formGroup.updateValueAndValidity({ onlySelf: true });
    // this.msg.success(`提交失败`);
    //}
    this.form.submitting = false;
    this.cdr.detectChanges();
  }
  onDeny(): void {
    window.close();
  }
}
