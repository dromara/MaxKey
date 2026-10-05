/*
 * Copyright (c) 2026, MaxKey and/or its affiliates. All rights reserved.
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * License Restrictions
 * This software and related documentation are provided under a license
 * agreement containing restrictions on use and disclosure and are
 * protected by intellectual property laws. Except as expressly permitted
 * in your license agreement or allowed by law, you may not use, copy,
 * reproduce, translate, broadcast, modify, license, transmit, distribute,
 * exhibit, perform, publish, or display any part, in any form, or by any means.
 * Reverse engineering, disassembly, or decompilation of this software,
 * unless required by law for interoperability, is prohibited.
 *
 * Please contact MaxKey, visit www.maxkey.top if you need additional information
 * or have any questions,support email support@maxsso.net .
 *
 */

import { Routes } from '@angular/router';

import { AuditLoginAppsComponent } from './audit-login-apps/audit-login-apps.component';
import { AuditLoginsComponent } from './audit-logins/audit-logins.component';
import { AuditSystemLogsComponent } from './audit-system-logs/audit-system-logs.component';

export const routes: Routes = [
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
