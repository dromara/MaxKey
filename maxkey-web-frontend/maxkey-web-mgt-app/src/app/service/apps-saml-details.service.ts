import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { AppsSamlDetails } from '../entity/AppsSamlDetails';
import { Message } from '../entity/Message';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class AppsSamlDetailsService extends BaseService<AppsSamlDetails> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/apps/saml20');
  }

  init(): Observable<Message<AppsSamlDetails>> {
    return this.getByParams({}, `${this.server.urls.base}/init`);
  }
}
