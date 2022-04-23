import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Groups } from '../entity/Groups';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class GroupsService extends BaseService<Groups> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/access/groups');
  }
}
