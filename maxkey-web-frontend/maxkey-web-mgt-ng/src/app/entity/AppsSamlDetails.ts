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

import { format } from 'date-fns';

import { Apps } from './Apps';

export class AppsSamlDetails extends Apps {
  certIssuer!: string;

  certSubject!: string;

  certExpiration!: string;

  signature!: string;

  digestMethod!: string;

  entityId!: string;

  spAcsUrl!: string;

  issuer!: string;

  audience!: string;

  nameidFormat!: string;

  validityInterval!: string;
  /**
   * Redirect-Post Post-Post IdpInit-Post Redirect-PostSimpleSign
   * Post-PostSimpleSign IdpInit-PostSimpleSign
   */

  binding!: string;

  /**
   * yes or no
   */

  encrypted!: string;
  /**
   * metadata_file metadata_url or certificate
   */
  fileType!: string;

  metaUrl!: string;

  metaFileId!: string;

  /**
   * original , uppercase  or lowercase
   */

  nameIdConvert!: string;

  nameIdSuffix!: string;

  constructor() {
    super();
    this.fileType = 'certificate';
    this.validityInterval = '300';
    this.nameidFormat = 'persistent';
    this.nameIdConvert = 'original';
    this.signature = 'RSAwithSHA1';
    this.digestMethod = 'SHA1';
    this.encrypted = 'no';
    this.binding = 'Redirect-Post';
  }

  override init(data: any): void {
    Object.assign(this, data);
    super.init(data);
    this.fileType = 'certificate';
    this.metaUrl = '';
    if (this.category == null || this.category == '') {
      this.category = 'NONE';
    }
    if (this.status == 1) {
      this.switch_status = true;
    } else {
      this.switch_status = false;
    }
  }

  override trans(): void {
    if (this.switch_status) {
      this.status = 1;
    } else {
      this.status = 0;
    }
  }
}
