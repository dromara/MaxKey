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
import format from 'date-fns/format';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { AuthnService } from "../../../service/authn.service";
import { SocialsProviderService } from "../../../service/socials-provider.service";
import { ReuseTabService } from "@delon/abc/reuse-tab";

@Component({
  selector: 'app-socials-provider-binduser',
  templateUrl: './socials-provider-bind-user.component.html',
  styles: [
    `
      nz-form-item {
        width: 100%;
      }
    `
  ],
  styleUrls: ['./socials-provider-bind-user.component.less']
})
export class SocialsProviderBindUserComponent implements OnInit {
  @Input() socialUserId?: String;
  @Input() provider?: String;
  loading = false;
  count = 0;
  formGroup: FormGroup = new FormGroup({});
  interval$: any;
  constructor(
    private modalRef: NzModalRef,
    private fb: FormBuilder,
    private authnService: AuthnService,
    private msg: NzMessageService,
    private socialsProviderService: SocialsProviderService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    @Inject(ReuseTabService)
    private reuseTabService: ReuseTabService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.formGroup = this.fb.group({
      telephone: [null, [Validators.required]],
      verificationCode: [null, [Validators.required]]
    });
    console.log("bind open form : ",this.provider,this.socialUserId)
  }

  onClose(e: MouseEvent): void {
    e.preventDefault();
    this.modalRef.destroy({ refresh: false });
  }

  onSubmit(e: MouseEvent): void {
    console.log("this.formGroup.valid",this.formGroup.valid)
    //表单验证
    if (this.formGroup.valid) {
      let request = {
        username: this.socialUserId,
        mobile: this.formGroup.get('telephone')?.value,
        code: this.formGroup.get('verificationCode')?.value,
        authType: this.provider
      }
      this.authnService.bindSocialsUser(request).subscribe(res => {
        if (res.code === 0) {
          // 清空路由复用信息
          this.reuseTabService.clear();
          // 设置用户Token信息
          this.authnService.auth(res.data);
          this.authnService.navigate({});
        } else {
          this.msg.error(`绑定失败`);
        }
      });

    } else {
      Object.values(this.formGroup.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }

    e.preventDefault();
  }

  sendOtpCode(): void {

    this.authnService.produceOtp({ mobile:this.formGroup.get('telephone')?.value}).subscribe(res => {
      if (res.code !== 0) {
        this.msg.error(`发送失败`);
      }else {
        this.msg.success(`发送成功`);
      }
    });
    this.count = 59;
    this.interval$ = setInterval(() => {
      this.count -= 1;
      if (this.count <= 0) {
        clearInterval(this.interval$);
      }
      this.cdr.detectChanges();
    }, 1000);
  }
}
