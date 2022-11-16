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
import { SocialsProvider } from './SocialsProvider';

export class SocialsAssociate extends SocialsProvider {
  redirectUri!: String;
  accountId!: String;
  bindTime!: String;
  unBindTime!: String;
  lastLoginTime!: String;
  state!: String;
  userBind!: String;

  constructor() {
    super();
  }

  override init(data: any): void {
    Object.assign(this, data);
  }

  override trans(): void {}
}
