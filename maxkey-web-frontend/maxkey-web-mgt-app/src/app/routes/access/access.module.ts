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
  declarations: [...COMPONENTS, PrivilegesComponent, SelectGroupsComponent, GroupMembersEditerComponent, PrivilegesEditerComponent],
  imports: [NzIconModule, SharedModule, CommonModule, RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccessModule { }
