import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { SmsProvider } from '../entity/SmsProvider';
import { BaseService } from './base.service';
@Injectable({
  providedIn: 'root'
})
export class SmsProviderService extends BaseService<SmsProvider> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/config/smsprovider');
  }
}
