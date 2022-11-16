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

import { Component } from '@angular/core';
import { copy } from '@delon/util/browser';
import { NzMessageService } from 'ng-zorro-antd/message';

import { ColorService } from '../color.service';

@Component({
  selector: 'app-colors',
  templateUrl: './colors.component.html',
  styleUrls: ['./colors.component.less']
})
export class ColorsComponent {
  nums = Array(10)
    .fill(1)
    .map((v, i) => v + i);

  get names(): string[] {
    return this.colorSrv.names;
  }

  get brands(): string[] {
    return this.colorSrv.brands;
  }

  constructor(private colorSrv: ColorService, private msg: NzMessageService) {}

  onCopy(str: string): void {
    copy(str).then(() => this.msg.success(`Copied Success!`));
  }
}
