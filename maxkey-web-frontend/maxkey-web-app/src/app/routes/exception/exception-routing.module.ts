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

import { ExceptionComponent } from './exception.component';
import { ExceptionTriggerComponent } from './trigger.component';

const routes: Routes = [
  { path: '403', component: ExceptionComponent, data: { type: 403 } },
  { path: '404', component: ExceptionComponent, data: { type: 404 } },
  { path: '500', component: ExceptionComponent, data: { type: 500 } },
  { path: 'trigger', component: ExceptionTriggerComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ExceptionRoutingModule {}
