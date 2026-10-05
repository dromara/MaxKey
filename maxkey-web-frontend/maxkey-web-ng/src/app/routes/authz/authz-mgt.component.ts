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
import { SettingsService } from '@delon/theme';
import { environment } from '@env/environment';

import { AuthnService } from '../../service/authn.service';
import { SocialsProviderService } from '../../service/socials-provider.service';

@Component({
  selector: 'app-callback',
  template: ``
})
export class AuthzMgtComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {
    let baseUrl = '';
    if (environment.api.baseUrl.endsWith('/')) {
      baseUrl = environment.api.baseUrl.substring(0, environment.api.baseUrl.length - 1);
    } else {
      baseUrl = environment.api.baseUrl;
    }
    window.location.href = `${baseUrl}/authz/maxkey_mgt`;
  }
}
