import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { AppsFormBasedDetails } from '../entity/AppsFormBasedDetails';
import { Message } from '../entity/Message';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class AppsFormBasedDetailsService extends BaseService<AppsFormBasedDetails> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/apps/formbased');
  }

  init(): Observable<Message<AppsFormBasedDetails>> {
    return this.getByParams({}, `${this.server.urls.base}/init`);
  }
}
