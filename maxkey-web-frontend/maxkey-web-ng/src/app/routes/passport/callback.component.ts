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

import { Component, OnInit, ViewContainerRef, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ReuseTabService } from '@delon/abc/reuse-tab';
import { SettingsService } from '@delon/theme';
import { NzModalService } from 'ng-zorro-antd/modal';

import { SocialsProviderBindUserComponent } from './socials-provider-bind-user/socials-provider-bind-user.component';
import { AuthnService } from '../../service/authn.service';
import { SocialsProviderService } from '../../service/socials-provider.service';

@Component({
  selector: 'app-callback',
  template: ``
})
export class CallbackComponent implements OnInit {
  provider = '';

  private readonly viewContainerRef = inject(ViewContainerRef);
  private readonly modalService = inject(NzModalService);
  private readonly router = inject(Router);
  private readonly socialsProviderService = inject(SocialsProviderService);
  private readonly settingsService = inject(SettingsService);
  private readonly authnService = inject(AuthnService);
  private readonly reuseTabService = inject(ReuseTabService, { optional: true });
  private readonly route = inject(ActivatedRoute);

  ngOnInit(): void {
    this.provider = this.route.snapshot.params['provider'];
    if (!this.settingsService.user.name) {
      this.socialsProviderService.callback(this.provider, this.route.snapshot.queryParams).subscribe(res => {
        if (res.code === 0) {
          // 清空路由复用信息
          this.reuseTabService?.clear();
          // 设置用户Token信息
          this.authnService.auth(res.data);
        } else if (res.code === 102) {
          //绑定用户
          this.openBindUser(res.message);
          return;
        }
        this.authnService.navigate({});
      });
    } else {
      this.socialsProviderService.bind(this.provider, this.route.snapshot.queryParams).subscribe(res => {
        if (res.code === 0) {
        }
        this.router.navigateByUrl('/config/socialsassociate');
      });
    }
  }

  openBindUser(socialUserId: string) {
    console.log('bind user : ', this.provider, socialUserId);
    const modal = this.modalService.create({
      nzContent: SocialsProviderBindUserComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzData: {
        socialUserId: socialUserId,
        provider: this.provider
      },
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
      }
    });
  }
}
