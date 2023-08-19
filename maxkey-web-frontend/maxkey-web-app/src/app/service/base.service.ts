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

import { HttpClient, HttpParams } from '@angular/common/http';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { Message } from '../entity/Message';
import { PageResults } from '../entity/PageResults';

const nullValueHandling = 'include';
const dateValueHandling = 'timestamp';

export class BaseService<T> {
  http: HttpClient;
  server: {
    urls: {
      base: string;
      fetch: string;
      get: string;
      load: String;
      add: string;
      update: string;
      delete: string;
      tree: string;
      member: string;
      memberOut: string;
    };
  } = {
    urls: {
      base: '',
      fetch: '/fetch',
      add: '/add',
      get: '/get',
      load: '/load',
      update: '/update',
      delete: '/delete',
      tree: '/tree',
      member: '/member',
      memberOut: '/memberOut'
    }
  };

  constructor(httpClient: HttpClient, baseURL: string) {
    this.server.urls.base = baseURL;
    this.http = httpClient;
  }

  fetch(params: NzSafeAny, fetchURL?: string): Observable<Message<PageResults>> {
    let _fetchURL = '';
    if (fetchURL) {
      _fetchURL = fetchURL;
    } else {
      _fetchURL = this.server.urls.base + this.server.urls.fetch;
    }
    return this.http.get<Message<PageResults>>(_fetchURL, { params: this.parseParams(params) });
  }

  member(params: NzSafeAny): Observable<Message<PageResults>> {
    return this.http.get<Message<PageResults>>(this.server.urls.base + this.server.urls.member, {
      params: this.parseParams(params)
    });
  }

  memberOut(params: NzSafeAny): Observable<Message<PageResults>> {
    return this.http.get<Message<PageResults>>(this.server.urls.base + this.server.urls.memberOut, {
      params: this.parseParams(params)
    });
  }

  get(id: String): Observable<Message<T>> {
    if (id === null || id === '') {
      return this.http.get<Message<T>>(`${this.server.urls.base + this.server.urls.get}`);
    } else {
      return this.http.get<Message<T>>(`${this.server.urls.base + this.server.urls.get}/${id}`);
    }
  }

  getByParams(params: NzSafeAny, getURL?: string): Observable<Message<T>> {
    let _getURL = '';
    if (getURL) {
      _getURL = getURL;
    } else {
      _getURL = `${this.server.urls.base + this.server.urls.get}`;
    }
    return this.http.get<Message<T>>(_getURL, { params: this.parseParams(params) });
  }

  add(body: any): Observable<Message<T>> {
    return this.http.post<Message<T>>(`${this.server.urls.base + this.server.urls.add}`, body);
  }

  update(body: any): Observable<Message<T>> {
    return this.http.put<Message<T>>(`${this.server.urls.base + this.server.urls.update}`, body);
  }

  delete(ids: String): Observable<Message<T>> {
    return this.http.delete<Message<T>>(`${this.server.urls.base + this.server.urls.delete}?ids=${ids}`);
  }

  tree(params: NzSafeAny): Observable<Message<any>> {
    return this.http.get<Message<any>>(this.server.urls.base + this.server.urls.tree, {
      params: this.parseParams(params)
    });
  }

  parseParams(params: NzSafeAny): HttpParams {
    const newParams: NzSafeAny = {};
    if (params instanceof HttpParams) {
      return params;
    }

    Object.keys(params).forEach(key => {
      let _data = params[key];
      // 忽略空值
      if (_data == null) {
      } else {
        // 将时间转化为：时间戳 (秒)
        if (dateValueHandling === 'timestamp' && _data instanceof Date) {
          _data = _data.valueOf();
        }
        newParams[key] = _data;
      }
    });
    return new HttpParams({ fromObject: newParams });
  }
}
