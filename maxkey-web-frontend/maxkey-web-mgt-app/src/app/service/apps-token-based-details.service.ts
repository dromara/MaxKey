import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { AppsTokenBasedDetails } from '../entity/AppsTokenBasedDetails';
import { Message } from '../entity/Message';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class AppsTokenBasedDetailsService extends BaseService<AppsTokenBasedDetails> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/apps/tokenbased');
  }

  init(): Observable<Message<AppsTokenBasedDetails>> {
    return this.getByParams({}, `${this.server.urls.base}/init`);
  }
}
