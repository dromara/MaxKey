import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Organizations } from '../entity/Organizations';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class OrganizationsService extends BaseService<Organizations> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/orgs');
  }
}
