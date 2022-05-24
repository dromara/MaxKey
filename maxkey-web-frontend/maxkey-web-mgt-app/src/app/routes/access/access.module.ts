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
 

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '@shared';
import { NzIconModule } from 'ng-zorro-antd/icon';

import { GroupMembersComponent } from './group-members/group-members.component';
import { GroupEditerComponent } from './groups/group-editer/group-editer.component';
import { GroupsComponent } from './groups/groups.component';
import { PrivilegesComponent } from './privileges/privileges.component';
import { SessionsComponent } from './sessions/sessions.component';
import { SelectGroupsComponent } from './groups/select-groups/select-groups.component';
import { GroupMembersEditerComponent } from './group-members/group-members-editer/group-members-editer.component';
import { PrivilegesEditerComponent } from './privileges/privileges-editer/privileges-editer.component';
import { MemberGroupsEditerComponent } from './group-members/member-groups-editer/member-groups-editer.component';

const routes: Routes = [
  {
    path: 'sessions',
    component: SessionsComponent
  },
  {
    path: 'groups',
    component: GroupsComponent
  },
  {
    path: 'groupmembers',
    component: GroupMembersComponent
  },
  {
    path: 'privileges',
    component: PrivilegesComponent
  }
];

const COMPONENTS = [SessionsComponent, GroupsComponent, GroupMembersComponent, PrivilegesComponent, GroupEditerComponent];

@NgModule({
  declarations: [...COMPONENTS, PrivilegesComponent, SelectGroupsComponent, GroupMembersEditerComponent, PrivilegesEditerComponent, MemberGroupsEditerComponent],
  imports: [NzIconModule, SharedModule, CommonModule, RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccessModule { }
