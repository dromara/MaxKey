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
 

import { Component, Inject } from '@angular/core';
import { DA_SERVICE_TOKEN, ITokenService } from '@delon/auth';
import { _HttpClient } from '@delon/theme';

@Component({
  selector: 'exception-trigger',
  template: `
    <div class="pt-lg">
      <nz-card>
        <button *ngFor="let t of types" (click)="go(t)" nz-button nzDanger>触发{{ t }}</button>
        <button nz-button nzType="link" (click)="refresh()">触发刷新Token</button>
      </nz-card>
    </div>
  `
})
export class ExceptionTriggerComponent {
  types = [401, 403, 404, 500];

  constructor(private http: _HttpClient, @Inject(DA_SERVICE_TOKEN) private tokenService: ITokenService) {}

  go(type: number): void {
    this.http.get(`/api/${type}`).subscribe();
  }

  refresh(): void {
    this.tokenService.set({ token: 'invalid-token' });
    // 必须提供一个后端地址，无法通过 Mock 来模拟
    this.http.post(`https://localhost:5001/auth`).subscribe(
      res => console.warn('成功', res),
      err => {
        console.log('最后结果失败', err);
      }
    );
  }
}
