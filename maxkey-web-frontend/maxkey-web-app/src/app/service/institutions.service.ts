import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { BaseEntity } from '../entity/BaseEntity';
import { Institutions } from '../entity/Institutions';
import { Message } from '../entity/Message';
import { PageResults } from '../entity/PageResults';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class InstitutionsService extends BaseService<Institutions> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/config/institutions');
  }
}
