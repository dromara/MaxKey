import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { Message } from '../entity/Message';
import { PageResults } from '../entity/PageResults';
import { RoleMembers } from '../entity/RoleMembers';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class RoleMembersService extends BaseService<RoleMembers> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/permissions/rolemembers');
    this.server.urls.member = '/memberInRole';
    this.server.urls.memberOut = '/memberNotInRole';
  }

  rolesNoMember(params: NzSafeAny): Observable<Message<PageResults>> {
    return this.http.get<Message<PageResults>>(`${this.server.urls.base}/rolesNoMember`, {
      params: this.parseParams(params)
    });
  }

  memberPostNotInRole(params: NzSafeAny): Observable<Message<PageResults>> {
    return this.http.get<Message<PageResults>>(`${this.server.urls.base}/memberPostNotInRole`, {
      params: this.parseParams(params)
    });
  }

  addMember2Roles(body: any): Observable<Message<PageResults>> {
    return this.http.post<Message<PageResults>>(`${`${this.server.urls.base}/addMember2Roles`}`, body);
  }
}
