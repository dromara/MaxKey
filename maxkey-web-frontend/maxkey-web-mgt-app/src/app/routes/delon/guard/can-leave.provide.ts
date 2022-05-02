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
 

import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanDeactivate, RouterStateSnapshot } from '@angular/router';
import { NzModalService } from 'ng-zorro-antd/modal';
import { Observable } from 'rxjs';

import { GuardComponent } from './guard.component';

@Injectable()
export class CanLeaveProvide implements CanDeactivate<GuardComponent> {
  constructor(private confirmSrv: NzModalService) {}

  canDeactivate(
    component: GuardComponent,
    currentRoute: ActivatedRouteSnapshot,
    currentState: RouterStateSnapshot,
    nextState?: RouterStateSnapshot
  ): boolean | Observable<boolean> | Promise<boolean> {
    return new Observable(observer => {
      this.confirmSrv.confirm({
        nzTitle: '确认要离开吗？',
        nzContent: '你已经填写了部分表单离开会放弃已经填写的内容。',
        nzOkText: '离开',
        nzCancelText: '取消',
        nzOnOk: () => {
          observer.next(true);
          observer.complete();
        },
        nzOnCancel: () => {
          observer.next(false);
          observer.complete();
        }
      });
    });
  }
}
