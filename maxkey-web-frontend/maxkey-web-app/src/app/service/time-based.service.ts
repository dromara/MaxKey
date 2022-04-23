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
    super(_httpClient, '/config');
  }

  override get(id: String): Observable<Message<TimeBased>> {
    return this.http.get<Message<TimeBased>>(`${this.server.urls.base}/timebased?generate=NO`);
  }

  override update(body: any): Observable<Message<TimeBased>> {
    return this.http.get<Message<TimeBased>>(`${this.server.urls.base}/timebased?generate=YES`);
  }
}
