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

import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ReuseTabService } from '@delon/abc/reuse-tab';
import { DA_SERVICE_TOKEN, ITokenService } from '@delon/auth';
import { SettingsService } from '@delon/theme';

import { AuthnService } from '../../service/authn.service';

@Component({
  selector: 'app-trust-auth',
  template: ``
})
export class TrustAuthComponent implements OnInit {
  ticket = '';

  constructor(
    private authnService: AuthnService,
    @Inject(ReuseTabService)
    private reuseTabService: ReuseTabService,
    private router: Router,
    private settingsSrv: SettingsService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.ticket = this.route.snapshot.queryParams['ticket'];

    this.authnService.trustAuth({ ticket: this.ticket }).subscribe(res => {
      if (res.code !== 0) {
        this.router.navigateByUrl('/passport/login');
      } else {
        // 清空路由复用信息
        this.reuseTabService.clear();
        // 设置用户Token信息
        this.authnService.auth(res.data);
        this.authnService.navigate({});
      }
    });
  }
}
