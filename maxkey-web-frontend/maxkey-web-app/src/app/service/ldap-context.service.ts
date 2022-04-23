import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { LdapContext } from '../entity/LdapContext';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class LdapContextService extends BaseService<LdapContext> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/config/ldapcontext');
  }
}
