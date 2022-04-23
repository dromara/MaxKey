import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { Message } from '../entity/Message';
import { SocialsProvider } from '../entity/SocialsProvider';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class SocialsProviderService extends BaseService<SocialsProvider> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/config/socialsprovider');
  }
}
