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
 

import { Component } from '@angular/core';
import { ACLService } from '@delon/acl';
import { MenuService } from '@delon/theme';

@Component({
  selector: 'app-acl',
  templateUrl: './acl.component.html'
})
export class ACLComponent {
  full = true;
  roleA = '';
  roleB = '';

  get data(): {
    full: boolean;
    roles: string[];
    abilities: Array<string | number>;
  } {
    return this.aclSrv.data;
  }

  constructor(private aclSrv: ACLService, private menuSrv: MenuService) {}

  private reMenu(): void {
    this.menuSrv.resume();
  }

  toggleFull(): void {
    this.full = !this.full;
    this.aclSrv.setFull(this.full);
    this.reMenu();
  }

  toggleRoleA(): void {
    this.full = false;
    this.roleA = this.roleA === 'role-a' ? '' : 'role-a';
    this.aclSrv.setFull(this.full);
    this.aclSrv.setRole([this.roleA]);
    this.reMenu();
  }

  toggleRoleB(): void {
    this.full = false;
    this.roleB = this.roleB === 'role-b' ? '' : 'role-b';
    this.aclSrv.setFull(this.full);
    this.aclSrv.setRole([this.roleB]);
    this.reMenu();
  }
}
