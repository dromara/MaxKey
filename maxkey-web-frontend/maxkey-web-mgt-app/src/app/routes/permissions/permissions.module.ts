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

import { PrivilegesComponent } from './privileges/privileges.component';
import { ResourcesComponent } from './resources/resources.component';
import { RoleMembersComponent } from './role-members/role-members.component';
import { RoleEditerComponent } from './roles/role-editer/role-editer.component';
import { RolesComponent } from './roles/roles.component';
import { ResourceEditerComponent } from './resources/resource-editer/resource-editer.component';
import { SelectRolesComponent } from './roles/select-roles/select-roles.component';
import { RoleMembersEditerComponent } from './role-members/role-members-editer/role-members-editer.component';
import { MemberRolesEditerComponent } from './role-members/member-roles-editer/member-roles-editer.component';
const routes: Routes = [
  {
    path: 'roles',
    component: RolesComponent
  },
  {
    path: 'resources',
    component: ResourcesComponent
  },
  {
    path: 'rolemembers',
    component: RoleMembersComponent
  },
  {
    path: 'privileges',
    component: PrivilegesComponent
  }
];

const COMPONENTS = [RolesComponent];

@NgModule({
  declarations: [...COMPONENTS, RoleEditerComponent, RoleMembersComponent, ResourcesComponent, PrivilegesComponent, ResourceEditerComponent, SelectRolesComponent, RoleMembersEditerComponent, MemberRolesEditerComponent],
  imports: [NzIconModule, SharedModule, CommonModule, RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PermissionsModule { }
