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
import { Observable } from 'rxjs';

import { Message } from '../entity/Message';
import { TimeBased } from '../entity/TimeBased';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class TimeBasedService extends BaseService<TimeBased> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/config/timebased');
  }

  view(id: String): Observable<Message<TimeBased>> {
    return this.http.get<Message<TimeBased>>(`${this.server.urls.base}/view`);
  }

  generate(id: String): Observable<Message<TimeBased>> {
    return this.http.get<Message<TimeBased>>(`${this.server.urls.base}/generate`);
  }

  override update(body: any): Observable<Message<TimeBased>> {
    return this.http.put<Message<TimeBased>>(`${this.server.urls.base}/update`, body);
  }

  verify(otp: string): Observable<Message<TimeBased>> {
    return this.http.get<Message<TimeBased>>(`${this.server.urls.base}/verify?otpCode=${otp}`);
  }
}
