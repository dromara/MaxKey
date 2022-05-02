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
import { Router } from '@angular/router';
import { ACLService } from '@delon/acl';
import { MenuService } from '@delon/theme';

@Component({
  selector: 'app-guard',
  templateUrl: './guard.component.html'
})
export class GuardComponent {
  get data(): any {
    return this.aclSrv.data;
  }

  constructor(private aclSrv: ACLService, private menuSrv: MenuService, private router: Router) {}

  setRole(value: string | boolean): void {
    this.aclSrv.setFull(typeof value === 'boolean' ? value : false);
    this.aclSrv.set({ role: [value as string] });
    this.menuSrv.resume();
    this.router.navigate(['/delon/guard']);
  }
}
