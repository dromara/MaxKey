import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { Apps } from '../entity/Apps';
import { Message } from '../entity/Message';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class AppsService extends BaseService<Apps> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/apps');
  }

  init(): Observable<Message<Apps>> {
    return this.getByParams({}, `${this.server.urls.base}/init`);
  }

  generateSecret(type: String): Observable<Message<Apps>> {
    return this.getByParams({}, `${this.server.urls.base}/generate/secret/${type}`);
  }

  generateKeys(id: String, type: String): Observable<Message<Apps>> {
    return this.getByParams({}, `/apps/generate/secret/${type}?id=${id}`);
  }
}
