import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '@shared';

import { AuditLoginAppsComponent } from './audit-login-apps/audit-login-apps.component';
import { AuditLoginsComponent } from './audit-logins/audit-logins.component';
import { AuditSystemLogsComponent } from './audit-system-logs/audit-system-logs.component';

const routes: Routes = [
  {
    path: 'audit-logins',
    component: AuditLoginsComponent
  },
  {
    path: 'audit-login-apps',
    component: AuditLoginAppsComponent
  },
  {
    path: 'audit-system-logs',
    component: AuditSystemLogsComponent
  },
  {
    path: 'audit',
    children: [{ path: 'audit-logins', component: AuditLoginsComponent }]
  }
];

const COMPONENTS = [AuditLoginsComponent, AuditLoginAppsComponent, AuditSystemLogsComponent];

@NgModule({
  declarations: [...COMPONENTS],
  imports: [SharedModule, CommonModule, RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuditModule { }
