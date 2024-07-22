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

import { BaseEntity } from './BaseEntity';

export class LdapContext extends BaseEntity {
  product!: String;
  providerUrl!: String;
  principal!: String;
  credentials!: String;
  sslSwitch!: Number;
  filters!: String;
  basedn!: String;
  msadDomain!: String;
  accountMapping!: String;
  trustStore!: String;
  trustStorePassword!: String;
  switch_sslSwitch: boolean = false;
  switch_accountMapping: boolean = false;

  override init(data: any): void {
    Object.assign(this, data);
    if (this.sslSwitch == 1) {
      this.switch_sslSwitch = true;
    }
    if (this.status == 1) {
      this.switch_status = true;
    }
    if (this.accountMapping == 'YES') {
      this.switch_accountMapping = true;
    }
  }

  override trans(): void {
    if (this.switch_sslSwitch) {
      this.sslSwitch = 1;
    } else {
      this.sslSwitch = 0;
    }
    if (this.switch_status) {
      this.status = 1;
    } else {
      this.status = 0;
    }
    if (this.switch_accountMapping) {
      this.accountMapping = 'YES';
    } else {
      this.accountMapping = 'NO';
    }
  }
}
