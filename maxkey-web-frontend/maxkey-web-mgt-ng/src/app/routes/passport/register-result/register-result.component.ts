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

import { Component, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { I18nPipe } from '@delon/theme';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzResultModule } from 'ng-zorro-antd/result';

import { SHARED_IMPORTS } from '../../../shared/shared-imports';

@Component({
  selector: 'passport-register-result',
  templateUrl: './register-result.component.html',
  imports: [SHARED_IMPORTS, RouterLink, I18nPipe, NzButtonModule, NzResultModule]
})
export class UserRegisterResultComponent {
  private readonly route = inject(ActivatedRoute);
  readonly msg = inject(NzMessageService);
  readonly email = this.route.snapshot.queryParams['email'] || 'ng-alain@example.com';
  params = { email: this.email };
}
