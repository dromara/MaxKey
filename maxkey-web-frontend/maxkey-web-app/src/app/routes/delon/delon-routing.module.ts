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
 

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ACLGuard } from '@delon/acl';

import { ACLComponent } from './acl/acl.component';
import { CacheComponent } from './cache/cache.component';
import { DownFileComponent } from './downfile/downfile.component';
import { DelonFormComponent } from './form/form.component';
import { GuardAdminComponent } from './guard/admin.component';
import { GuardAuthComponent } from './guard/auth.component';
import { CanLeaveProvide } from './guard/can-leave.provide';
import { GuardComponent } from './guard/guard.component';
import { GuardLeaveComponent } from './guard/leave.component';
import { PrintComponent } from './print/print.component';
import { QRComponent } from './qr/qr.component';
import { STDemoComponent } from './st/st.component';
import { UtilComponent } from './util/util.component';
import { XlsxComponent } from './xlsx/xlsx.component';
import { ZipComponent } from './zip/zip.component';

const routes: Routes = [
  { path: 'st', component: STDemoComponent },
  { path: 'util', component: UtilComponent },
  { path: 'print', component: PrintComponent },
  { path: 'acl', component: ACLComponent },
  {
    path: 'guard',
    component: GuardComponent,
    children: [
      {
        path: 'leave',
        component: GuardLeaveComponent,
        canDeactivate: [CanLeaveProvide]
      },
      {
        path: 'auth',
        component: GuardAuthComponent,
        canActivate: [ACLGuard],
        data: { guard: 'user1' }
      },
      {
        path: 'admin',
        component: GuardAdminComponent,
        canActivate: [ACLGuard],
        data: { guard: 'admin' }
      }
    ]
  },
  { path: 'cache', component: CacheComponent },
  { path: 'qr', component: QRComponent },
  { path: 'downfile', component: DownFileComponent },
  { path: 'xlsx', component: XlsxComponent },
  { path: 'zip', component: ZipComponent },
  { path: 'form', component: DelonFormComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DelonRoutingModule {}
