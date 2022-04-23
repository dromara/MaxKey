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
export class RolePrivilegesService extends BaseService<RoleMembers> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/permissions/privileges');
    this.server.urls.load = '/get';
  }
}
