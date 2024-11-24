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

import { Inject, Optional, Component, OnInit, ViewContainerRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ReuseTabService } from '@delon/abc/reuse-tab';
import { SettingsService } from '@delon/theme';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

import { AuthnService } from '../../service/authn.service';
import { SocialsProviderService } from '../../service/socials-provider.service';
import { SocialsProviderBindUserComponent } from './socials-provider-bind-user/socials-provider-bind-user.component';

@Component({
  selector: 'app-callback',
  template: ``
})
export class CallbackComponent implements OnInit {
  provider = '';

  constructor(
    private viewContainerRef: ViewContainerRef,
    private modalService: NzModalService,
    private router: Router,
    private socialsProviderService: SocialsProviderService,
    private settingsService: SettingsService,
    private authnService: AuthnService,
    @Optional()
    @Inject(ReuseTabService)
    private reuseTabService: ReuseTabService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.provider = this.route.snapshot.params['provider'];
    if (!this.settingsService.user.name) {
      this.socialsProviderService.callback(this.provider, this.route.snapshot.queryParams).subscribe(res => {
        if (res.code === 0) {
          // 清空路由复用信息
          this.reuseTabService.clear();
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

  openBindUser(socialUserId: String) {
    console.log('bind user : ', this.provider, socialUserId);
    const modal = this.modalService.create({
      nzContent: SocialsProviderBindUserComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzComponentParams: {
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
