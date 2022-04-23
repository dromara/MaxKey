import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Roles } from '../entity/Roles';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class RolesService extends BaseService<Roles> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/permissions/roles');
  }
}
