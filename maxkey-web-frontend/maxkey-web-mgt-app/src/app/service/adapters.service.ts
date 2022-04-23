import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { Adapters } from '../entity/Adapters';
import { Message } from '../entity/Message';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class AdaptersService extends BaseService<Adapters> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/config/adapters');
  }
}
