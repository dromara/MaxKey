import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { AppsJwtDetails } from '../entity/AppsJwtDetails';
import { Message } from '../entity/Message';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class AppsJwtDetailsService extends BaseService<AppsJwtDetails> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/apps/jwt');
  }

  init(): Observable<Message<AppsJwtDetails>> {
    return this.getByParams({}, `${this.server.urls.base}/init`);
  }
}
