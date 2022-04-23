import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '@shared';
import { NzIconModule } from 'ng-zorro-antd/icon';

import { SessionsComponent } from './sessions/sessions.component';

const routes: Routes = [
  {
    path: 'sessions',
    component: SessionsComponent
  }
];

const COMPONENTS = [SessionsComponent];

@NgModule({
  declarations: [...COMPONENTS],
  imports: [NzIconModule, SharedModule, CommonModule, RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccessModule { }
