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

export class Organizations extends BaseEntity {
  orgCode!: string;
  orgName!: string;
  fullName!: string;
  parentId!: string;
  parentCode!: string;
  parentName!: string;
  type!: string;
  codePath!: string;
  namePath!: string;
  level!: number;
  division!: string;
  country!: string;
  region!: string;
  locality!: string;
  street!: string;
  address!: string;
  contact!: string;
  postalCode!: string;
  phone!: string;
  fax!: string;
  email!: string;
  switch_dynamic: boolean = false;

  constructor() {
    super();
    this.status = 1;
  }

  override init(data: any): void {
    Object.assign(this, data);
    if (this.status == 1) {
      this.switch_status = true;
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
