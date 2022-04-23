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
export class Oauth2ApproveService extends BaseService<Apps> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/authz/oauth/v20/approval_confirm');
  }

  approval(body: any): Observable<Message<Apps>> {
    return this.http.post<Message<Apps>>(`/authz/oauth/v20/authorize/approval?user_oauth_approval=${body.user_oauth_approval}`, body);
  }
}
