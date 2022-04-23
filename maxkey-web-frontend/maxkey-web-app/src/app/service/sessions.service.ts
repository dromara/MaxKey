import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { BaseEntity } from '../entity/BaseEntity';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class SessionsService extends BaseService<BaseEntity> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/access/session');
    this.server.urls.delete = '/terminate';
  }
}
