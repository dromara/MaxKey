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
import { SimpleGuard } from '@delon/auth';
import { PreloadOptionalModules } from '@delon/theme';
import { environment } from '@env/environment';

// layout
import { LayoutBasicComponent } from '../layout/basic/basic.component';
import { LayoutBlankComponent } from '../layout/blank/blank.component';
import { AccountsComponent } from './accounts/accounts.component';
import { AppsComponent } from './apps/apps.component';

const routes: Routes = [
  {
    path: '',
    component: LayoutBasicComponent,
    canActivate: [SimpleGuard],
    canActivateChild: [SimpleGuard],
    data: {},
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

      {
        path: 'dashboard',
        loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule),
        data: { preload: true }
      },
      { path: 'idm', loadChildren: () => import('./idm/idm.module').then(m => m.IdmModule) },
      { path: 'accounts', component: AccountsComponent },
      { path: 'apps', component: AppsComponent },
      { path: 'access', loadChildren: () => import('./access/access.module').then(m => m.AccessModule) },
      { path: 'permissions', loadChildren: () => import('./permissions/permissions.module').then(m => m.PermissionsModule) },
      { path: 'audit', loadChildren: () => import('./audit/audit.module').then(m => m.AuditModule) },
      { path: 'config', loadChildren: () => import('./config/config.module').then(m => m.ConfigModule) },
      { path: 'widgets', loadChildren: () => import('./widgets/widgets.module').then(m => m.WidgetsModule) },
      { path: 'style', loadChildren: () => import('./style/style.module').then(m => m.StyleModule) }
    ]
  },
  // Blak Layout 空白布局
  // passport
  { path: '', loadChildren: () => import('./passport/passport.module').then(m => m.PassportModule), data: { preload: true } },
  { path: 'exception', loadChildren: () => import('./exception/exception.module').then(m => m.ExceptionModule) },
  { path: '**', redirectTo: 'exception/404' }
];

@NgModule({
  providers: [PreloadOptionalModules],
  imports: [
    RouterModule.forRoot(routes, {
      useHash: environment.useHash,
      // NOTICE: If you use `reuse-tab` component and turn on keepingScroll you can set to `disabled`
      // Pls refer to https://ng-alain.com/components/reuse-tab
      scrollPositionRestoration: 'top',
      preloadingStrategy: PreloadOptionalModules
    })
  ],
  exports: [RouterModule]
})
export class RouteRoutingModule {}
