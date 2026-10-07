import { Routes } from '@angular/router';
import { startPageGuard } from '@core';
import { authSimpleCanActivate, authSimpleCanActivateChild } from '@delon/auth';

import { LayoutBasic, LayoutBlank } from '../layout';
import { AccountsComponent } from './accounts/accounts.component';
import { AppsComponent } from './apps/apps.component';

export const routes: Routes = [
  {
    path: '',
    component: LayoutBasic,
    canActivate: [startPageGuard, authSimpleCanActivate],
    canActivateChild: [authSimpleCanActivateChild],
    data: {},
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      {
        path: 'dashboard',
        loadChildren: () => import('./dashboard/routes').then(m => m.routes)
      },
      {
        path: 'widgets',
        loadChildren: () => import('./widgets/routes').then(m => m.routes)
      },
      { path: 'idm', loadChildren: () => import('./idm/routes').then(m => m.routes) },
      { path: 'accounts', component: AccountsComponent },
      { path: 'apps', component: AppsComponent },
      { path: 'access', loadChildren: () => import('./access/routes').then(m => m.routes) },
      { path: 'permissions', loadChildren: () => import('./permissions/routes').then(m => m.routes) },
      { path: 'audit', loadChildren: () => import('./audit/routes').then(m => m.routes) },
      { path: 'config', loadChildren: () => import('./config/routes').then(m => m.routes) },
      { path: 'widgets', loadChildren: () => import('./widgets/routes').then(m => m.routes) },
      { path: 'style', loadChildren: () => import('./style/routes').then(m => m.routes) }
      //{ path: 'delon', loadChildren: () => import('./delon/routes').then(m => m.routes) },
      //{ path: 'extras', loadChildren: () => import('./extras/routes').then(m => m.routes) },
      //{ path: 'pro', loadChildren: () => import('./pro/routes').then(m => m.routes) }
    ]
  },
  // Blank Layout 空白布局
  {
    path: 'data-v',
    component: LayoutBlank
    //children: [{ path: '', loadChildren: () => import('./data-v/routes').then(m => m.routes) }]
  },
  // passport
  { path: '', loadChildren: () => import('./passport/routes').then(m => m.routes) },
  { path: 'exception', loadChildren: () => import('./exception/routes').then(m => m.routes) },
  { path: '**', redirectTo: 'exception/404' }
];
