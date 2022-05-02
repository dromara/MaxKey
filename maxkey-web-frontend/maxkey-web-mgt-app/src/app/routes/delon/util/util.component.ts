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
import { format } from '@delon/util/format';
import { yuan } from '@shared';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-util',
  templateUrl: './util.component.html'
})
export class UtilComponent {
  constructor(public messageSrv: NzMessageService) {}

  // region: string

  format_str = 'this is ${name}';
  format_res = '';
  format_obj = JSON.stringify({ name: 'asdf' });

  // yuan
  yuan_str: any;
  yuan_res!: string;

  // endregion

  // region: other

  content = `time ${+new Date()}

    中文！@#￥%……&*`;
  onFormat(): void {
    let obj = null;
    try {
      obj = JSON.parse(this.format_obj);
    } catch {
      this.messageSrv.error(`无法使用 JSON.parse 转换`);
      return;
    }
    this.format_res = format(this.format_str, obj, true);
  }
  onYuan(value: string): void {
    this.yuan_res = yuan(value);
  }
  onCopy(): void {
    copy(`time ${+new Date()}`).then(() => this.messageSrv.success(`success`));
  }

  // endregion
}
