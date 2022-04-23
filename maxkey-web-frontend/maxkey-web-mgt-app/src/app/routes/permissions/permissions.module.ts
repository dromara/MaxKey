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
  declarations: [...COMPONENTS, RoleEditerComponent, RoleMembersComponent, ResourcesComponent, PrivilegesComponent, ResourceEditerComponent, SelectRolesComponent, RoleMembersEditerComponent],
  imports: [NzIconModule, SharedModule, CommonModule, RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PermissionsModule { }
