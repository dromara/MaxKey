import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { Accounts } from '../entity/Accounts';
import { Message } from '../entity/Message';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class AccountsService extends BaseService<Accounts> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/authz/credential');
  }

  generate(params: NzSafeAny): Observable<Message<Accounts>> {
    return this.getByParams(params, `${this.server.urls.base}/generate`);
  }
}
