import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { BaseEntity } from '../entity/BaseEntity';
import { Message } from '../entity/Message';
import { PageResults } from '../entity/PageResults';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class AnalysisService extends BaseService<BaseEntity> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/analysis');
  }

  dashBoard(params: NzSafeAny): Observable<Message<BaseEntity>> {
    return this.getByParams(params, '/dashboard');
  }
}
