import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { Message } from '../entity/Message';
import { Synchronizers } from '../entity/Synchronizers';
import { BaseService } from './base.service';

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
}
