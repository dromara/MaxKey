import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { GroupMembers } from '../entity/GroupMembers';
import { Message } from '../entity/Message';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class GroupPrivilegesService extends BaseService<GroupMembers> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/access/privileges');
    this.server.urls.member = '/appsInGroup';
    this.server.urls.memberOut = '/appsNotInGroup';
  }
}
