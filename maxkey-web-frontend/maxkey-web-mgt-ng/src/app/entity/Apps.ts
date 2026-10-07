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

import { BaseEntity } from './BaseEntity';

export class Apps extends BaseEntity {
  appName!: string;
  loginUrl!: string;
  category!: string;
  protocol!: string;
  secret!: string;
  iconBase64!: string;
  visible!: string;
  inducer!: string;
  vendor!: string;
  vendorUrl!: string;
  credential!: string;
  sharedUsername!: string;
  sharedPassword!: string;
  systemUserAttr!: string;
  principal!: string;
  credentials!: string;

  logoutUrl!: string;
  logoutType!: string;
  isExtendAttr!: string;
  extendAttr!: string;
  resourceMgt!: string;
  openapiRight!: string;
  userPropertys!: string;
  isSignature!: string;
  isAdapter!: string;
  adapterId!: string;
  adapterName!: string;
  adapter!: string;
  iconId!: string;
  frequently!: string;

  select_userPropertys!: string[];

  constructor() {
    super();
    this.category = 'none';
    this.frequently = 'no';
    this.resourceMgt = 'false';
    this.visible = '0';
    this.isAdapter = '0';
    this.logoutType = '0';
    this.isExtendAttr = '0';
  }

  override init(data: any): void {
    Object.assign(this, data);
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
