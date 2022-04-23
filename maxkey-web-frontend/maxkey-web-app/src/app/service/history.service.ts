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
export class HistoryService extends BaseService<BaseEntity> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/historys');
  }

  public fetchConnector(params: NzSafeAny): Observable<Message<PageResults>> {
    return this.fetch(params, '/historys/connectorHistory/fetch');
  }

  public fetchLoginApps(params: NzSafeAny): Observable<Message<PageResults>> {
    return this.fetch(params, '/historys/loginAppsHistory/fetch');
  }

  public fetchLogin(params: NzSafeAny): Observable<Message<PageResults>> {
    return this.fetch(params, '/historys/loginHistory/fetch');
  }

  public fetchSynchronizers(params: NzSafeAny): Observable<Message<PageResults>> {
    return this.fetch(params, '/historys/synchronizerHistory/fetch');
  }

  public fetchSystemLogs(params: NzSafeAny): Observable<Message<PageResults>> {
    return this.fetch(params, '/historys/systemLogs/fetch');
  }
}
