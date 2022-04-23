import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { AppsExtendApiDetails } from '../entity/AppsExtendApiDetails';
import { Message } from '../entity/Message';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class AppsExtendApiDetailsService extends BaseService<AppsExtendApiDetails> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/apps/extendapi');
  }

  init(): Observable<Message<AppsExtendApiDetails>> {
    return this.getByParams({}, `${this.server.urls.base}/init`);
  }
}
