import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { EmailSenders } from '../entity/EmailSenders';
import { BaseService } from './base.service';

@Injectable({
  providedIn: 'root'
})
export class EmailSendersService extends BaseService<EmailSenders> {
  constructor(private _httpClient: HttpClient) {
    super(_httpClient, '/config/emailsenders');
  }
}
//
