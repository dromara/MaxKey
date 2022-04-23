import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { Message } from '../entity/Message';
import { Users } from '../entity/Users';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class UsersService extends BaseService<Users> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/users');
  }

  generatePassword(params: NzSafeAny): Observable<Message<Users>> {
    return this.http.get<Message<Users>>(`${this.server.urls.base}/randomPassword`, {
      params: this.parseParams(params)
    });
  }

  getProfile(): Observable<Message<Users>> {
    return this.http.get<Message<Users>>('/config/profile/get', {});
  }

  updateProfile(body: any): Observable<Message<Users>> {
    return this.http.put<Message<Users>>('/config/profile/update', body);
  }
}
