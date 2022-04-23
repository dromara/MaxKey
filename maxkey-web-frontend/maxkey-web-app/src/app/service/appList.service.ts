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
export class AppListService extends BaseService<Apps> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/appList');
  }

  appList(): Observable<Message<Apps>> {
    return this.getByParams({}, '/appList');
  }
}
