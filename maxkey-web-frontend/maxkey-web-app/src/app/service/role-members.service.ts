import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { Message } from '../entity/Message';
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
}
