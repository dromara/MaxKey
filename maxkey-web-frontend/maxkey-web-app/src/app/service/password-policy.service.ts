import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { PasswordPolicy } from '../entity/PasswordPolicy';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class PasswordPolicyService extends BaseService<PasswordPolicy> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/config/passwordpolicy');
  }
}
