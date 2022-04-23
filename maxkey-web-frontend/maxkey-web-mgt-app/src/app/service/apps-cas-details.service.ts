import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { AppsCasDetails } from '../entity/AppsCasDetails';
import { Message } from '../entity/Message';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class AppsCasDetailsService extends BaseService<AppsCasDetails> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/apps/cas');
  }

  init(): Observable<Message<AppsCasDetails>> {
    return this.getByParams({}, `${this.server.urls.base}/init`);
  }
}
