import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AvatarListModule } from '@delon/abc/avatar-list';
import { EllipsisModule } from '@delon/abc/ellipsis';
import { FooterToolbarModule } from '@delon/abc/footer-toolbar';
import { TagSelectModule } from '@delon/abc/tag-select';
import { CurrencyPipeModule } from '@delon/util/pipes/currency';
import { SharedModule } from '@shared';
import { NzPageHeaderModule } from 'ng-zorro-antd/page-header';
import { NzPaginationModule } from 'ng-zorro-antd/pagination';
import { NzStepsModule } from 'ng-zorro-antd/steps';

import { PasswordComponent } from './password/password.component';
import { ProfileComponent } from './profile/profile.component';
import { SocialsAssociateComponent } from './socials-associate/socials-associate.component';
import { SocialsProviderComponent } from './socials-provider/socials-provider.component';
import { TimebasedComponent } from './timebased/timebased.component';

const routes: Routes = [
  {
    path: 'profile',
    component: ProfileComponent
  },
  {
    path: 'password',
    component: PasswordComponent
  },
  {
    path: 'socialsassociate',
    component: SocialsAssociateComponent
  },
  {
    path: 'timebased',
    component: TimebasedComponent
  }
];

const COMPONENTS = [ProfileComponent];

@NgModule({
  declarations: [
    ...COMPONENTS,
    SocialsProviderComponent,
    TimebasedComponent,
    SocialsAssociateComponent,
    PasswordComponent,
    ProfileComponent
  ],
  imports: [SharedModule, CommonModule, RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConfigModule { }
