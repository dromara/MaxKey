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
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { Message } from '../entity/Message';
import { Synchronizers } from '../entity/Synchronizers';
import { BaseService } from './base.service';
import {JobConfigFeild} from "../entity/JobConfigFeild";

@Injectable({
  providedIn: 'root'
})
export class SynchronizersService extends BaseService<Synchronizers> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/config/synchronizers');
  }

  synchr(synchrId: String): Observable<Message<Synchronizers>> {
    return this.http.get<Message<Synchronizers>>(`${`${this.server.urls.base}/synchr`}?id=${synchrId}`);
  }
  getMapping(synchrId: String): Observable<Message<Synchronizers>> {
    return this.http.get<Message<JobConfigFeild>>(`${`${this.server.urls.base}/mapping-list`}/${synchrId}`);
  }

  getField(id: String): Observable<Message<Synchronizers>> {
    return this.http.get<Message<JobConfigFeild>>(`${`${this.server.urls.base}/mapping-get`}/${id}`);
  }

  mappingAdd(body: any): Observable<Message<JobConfigFeild>> {
    return this.http.post<Message<JobConfigFeild>>(`${this.server.urls.base}/mapping-add`, body);
  }

  mappingUpdate(body: any): Observable<Message<JobConfigFeild>> {
    return this.http.put<Message<JobConfigFeild>>(`${this.server.urls.base}/mapping-update`, body);
  }

  deleteMapping(id: String): Observable<Message<JobConfigFeild>> {
    return this.http.get<Message<JobConfigFeild>>(`${`${this.server.urls.base}/mapping-delete`}/${id}`);
  }

}
