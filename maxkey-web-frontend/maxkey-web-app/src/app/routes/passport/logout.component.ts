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

import { Inject, Optional, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ReuseTabService } from '@delon/abc/reuse-tab';
import { DA_SERVICE_TOKEN, ITokenService } from '@delon/auth';
import { SettingsService } from '@delon/theme';
import { finalize } from 'rxjs/operators';

import { AuthnService } from '../../service/authn.service';
import { SocialsProviderService } from '../../service/socials-provider.service';
import { CONSTS } from '../../shared/consts';

@Component({
  selector: 'app-logout',
  template: ``
})
export class LogoutComponent implements OnInit {
  redirect_uri = '';

  constructor(
    private router: Router,
    private settingsService: SettingsService,
    private authnService: AuthnService,
    @Inject(DA_SERVICE_TOKEN) private tokenService: ITokenService,
    @Optional()
    @Inject(ReuseTabService)
    private reuseTabService: ReuseTabService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.redirect_uri = this.route.snapshot.params[CONSTS.REDIRECT_URI];
    if (this.redirect_uri == null || this.redirect_uri == '') {
      this.redirect_uri = this.route.snapshot.queryParams[CONSTS.REDIRECT_URI];
    }
    this.authnService
      .logout()
      .pipe(
        finalize(() => {
          this.tokenService.clear();
          if (this.redirect_uri == null || this.redirect_uri == '') {
            this.router.navigateByUrl(this.tokenService.login_url!);
          } else {
            if (this.redirect_uri.startsWith('http')) {
              location.href = this.redirect_uri;
            } else {
              this.router.navigateByUrl(this.redirect_uri);
            }
          }
        })
      )
      .subscribe(res => {
        console.log(`Logout Response ${res.data}`);
      });
  }
}
