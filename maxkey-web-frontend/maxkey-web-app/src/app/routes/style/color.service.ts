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

import { Injectable } from '@angular/core';

@Injectable()
export class ColorService {
  APP_COLORS = {
    primary: '#1890ff',
    success: '#52c41a',
    error: '#f5222d',
    warning: '#fadb14',
    red: '#f5222d',
    volcano: '#fa541c',
    orange: '#fa8c16',
    gold: '#faad14',
    yellow: '#fadb14',
    lime: '#a0d911',
    green: '#52c41a',
    cyan: '#13c2c2',
    blue: '#1890ff',
    geekblue: '#2f54eb',
    purple: '#722ed1',
    magenta: '#eb2f96'
  };

  get names(): string[] {
    return Object.keys(this.APP_COLORS).filter((_, index) => index > 3);
  }

  get brands(): string[] {
    return ['primary', 'success', 'error', 'warning'];
  }
}
