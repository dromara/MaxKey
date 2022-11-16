/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import format from 'date-fns/format';

import { Apps } from './Apps';

export class AppsOauth20Details extends Apps {
  clientId!: String;

  clientSecret!: String;

  scope!: String;

  resourceIds!: String;

  authorizedGrantTypes!: String;

  registeredRedirectUris!: String;

  authorities!: String;

  accessTokenValiditySeconds!: String;

  refreshTokenValiditySeconds!: String;

  approvalPrompt!: String;

  // for OpenID Connect

  issuer!: String;

  audience!: String;

  algorithm!: String;

  algorithmKey!: String;

  encryptionMethod!: String;

  signature!: String;

  signatureKey!: String;

  subject!: String;

  userInfoResponse!: String;

  pkce!: String;

  select_authorizedGrantTypes!: string[];

  select_scope!: string[];

  constructor() {
    super();
    this.select_authorizedGrantTypes = ['authorization_code'];
    this.select_scope = ['read'];
    this.pkce = 'no';
    this.approvalPrompt = 'auto';
    this.accessTokenValiditySeconds = '300';
    this.refreshTokenValiditySeconds = '300';
    this.subject = 'username';
  }

  override init(data: any): void {
    Object.assign(this, data);
    super.init(data);
    if (this.status == 1) {
      this.switch_status = true;
    } else {
      this.switch_status = false;
    }
    if (this.approvalPrompt == '') {
    }
    this.select_scope = [''];
    let scopeArray: String[] = `${this.scope},`.split(',');
    for (let i = 0; i < scopeArray.length; i++) {
      this.select_scope.push(`${scopeArray[i]}`);
    }

    this.select_authorizedGrantTypes = [''];
    let authorizedGrantTypesArray = `${this.authorizedGrantTypes},`.split(',');
    for (let i = 0; i < authorizedGrantTypesArray.length; i++) {
      this.select_authorizedGrantTypes.push(authorizedGrantTypesArray[i]);
    }
    if (this.pkce == null || this.pkce == '') {
      this.pkce = 'no';
    }
    this.pkce = this.pkce.toLowerCase();
  }

  override trans(): void {
    if (this.switch_status) {
      this.status = 1;
    } else {
      this.status = 0;
    }
    this.scope = '';

    for (let i = 0; i < this.select_scope.length; i++) {
      if (this.select_scope[i] != '') {
        if (this.scope === '') {
          this.scope = this.select_scope[i];
        } else {
          this.scope = `${this.scope},${this.select_scope[i]}`;
        }
      }
    }
    this.authorizedGrantTypes = '';
    let n = 0;
    for (let i = 0; i < this.select_authorizedGrantTypes.length; i++) {
      if (this.select_authorizedGrantTypes[i] != '') {
        if (this.authorizedGrantTypes === '') {
          this.authorizedGrantTypes = this.select_authorizedGrantTypes[i];
        } else {
          this.authorizedGrantTypes = `${this.authorizedGrantTypes},${this.select_authorizedGrantTypes[i]}`;
        }
      }
    }
  }
}
