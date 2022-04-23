import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { Message } from '../entity/Message';
import { Resources } from '../entity/Resources';
import { BaseService } from './base.service';
@Injectable({
  providedIn: 'root'
})
export class ResourcesService extends BaseService<Resources> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/permissions/resources');
  }
}
