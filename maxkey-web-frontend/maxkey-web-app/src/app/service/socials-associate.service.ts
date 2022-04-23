import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Observable } from 'rxjs';

import { Message } from '../entity/Message';
import { SocialsAssociate } from '../entity/SocialsAssociate';
import { BaseService } from './base.service';
@Injectable({
  providedIn: 'root'
})
export class SocialsAssociateService extends BaseService<SocialsAssociate> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/config/socialsignon');
  }
}
