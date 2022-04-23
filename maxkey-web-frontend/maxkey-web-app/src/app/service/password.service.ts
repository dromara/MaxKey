import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { ChangePassword } from '../entity/ChangePassword';
import { Message } from '../entity/Message';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class PasswordService extends BaseService<ChangePassword> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/config');
  }

  public changePassword(body: NzSafeAny): Observable<Message<ChangePassword>> {
    return this.http.put<Message<ChangePassword>>('/config/changePassword', body);
  }
}
