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

import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { I18NService } from '@core';
import { ALAIN_I18N_TOKEN } from '@delon/theme';

import { Groups } from '../entity/Groups';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class AppCategoryService extends BaseService<Groups> {
  constructor(private _httpClient: HttpClient, @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService) {
    super(_httpClient, '/app/appcategory');
  }

  list(): any {
    return [
      {
        id: 'none',
        name: this.i18n.fanyi('mxk.apps.category.none')
      },
      {
        id: '1011',
        name: this.i18n.fanyi('mxk.apps.category.1011')
      },
      {
        id: '1012',
        name: this.i18n.fanyi('mxk.apps.category.1012')
      },
      {
        id: '1013',
        name: this.i18n.fanyi('mxk.apps.category.1013')
      },
      {
        id: '1014',
        name: this.i18n.fanyi('mxk.apps.category.1014')
      },
      {
        id: '1015',
        name: this.i18n.fanyi('mxk.apps.category.1015')
      },
      {
        id: '1016',
        name: this.i18n.fanyi('mxk.apps.category.1016')
      },
      {
        id: '1017',
        name: this.i18n.fanyi('mxk.apps.category.1017')
      },
      {
        id: '1111',
        name: this.i18n.fanyi('mxk.apps.category.1111')
      },
      {
        id: '1112',
        name: this.i18n.fanyi('mxk.apps.category.1112')
      },
      {
        id: '1113',
        name: this.i18n.fanyi('mxk.apps.category.1113')
      },
      {
        id: '1114',
        name: this.i18n.fanyi('mxk.apps.category.1114')
      },
      {
        id: '1211',
        name: this.i18n.fanyi('mxk.apps.category.1211')
      },
      {
        id: '1212',
        name: this.i18n.fanyi('mxk.apps.category.1212')
      },
      {
        id: '1213',
        name: this.i18n.fanyi('mxk.apps.category.1213')
      },
      {
        id: '1214',
        name: this.i18n.fanyi('mxk.apps.category.1214')
      },
      {
        id: '1215',
        name: this.i18n.fanyi('mxk.apps.category.1215')
      },
      {
        id: '1215',
        name: this.i18n.fanyi('mxk.apps.category.1215')
      },
      {
        id: '1311',
        name: this.i18n.fanyi('mxk.apps.category.1311')
      },
      {
        id: '1411',
        name: this.i18n.fanyi('mxk.apps.category.1411')
      },
      {
        id: '1511',
        name: this.i18n.fanyi('mxk.apps.category.1511')
      },
      {
        id: '1512',
        name: this.i18n.fanyi('mxk.apps.category.1512')
      },
      {
        id: '1611',
        name: this.i18n.fanyi('mxk.apps.category.1611')
      },
      {
        id: '1711',
        name: this.i18n.fanyi('mxk.apps.category.1711')
      },
      {
        id: '1712',
        name: this.i18n.fanyi('mxk.apps.category.1712')
      },
      {
        id: '1811',
        name: this.i18n.fanyi('mxk.apps.category.1811')
      },
      {
        id: '1812',
        name: this.i18n.fanyi('mxk.apps.category.1812')
      },
      {
        id: '1911',
        name: this.i18n.fanyi('mxk.apps.category.1911')
      },
      {
        id: '1912',
        name: this.i18n.fanyi('mxk.apps.category.1912')
      }
    ];
  }
}
