import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { AccountsStrategy } from '../entity/AccountsStrategy';
import { Message } from '../entity/Message';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class AccountsStrategyService extends BaseService<AccountsStrategy> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/config/accountsstrategy');
  }
}
