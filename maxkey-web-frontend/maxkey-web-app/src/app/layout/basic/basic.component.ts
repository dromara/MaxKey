import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SettingsService, User } from '@delon/theme';
import { environment } from '@env/environment';

import { LayoutDefaultOptions } from '../../theme/layout-default';

@Component({
  selector: 'layout-basic',
  styles: [
    `
      .alain-default__collapsed .alain-default__aside-user {
        width: 64px;
        margin-left: 16px;
      }
    `
  ],
  template: `
    <layout-default [options]="options" [asideUser]="asideUserTpl" [content]="contentTpl" [customError]="null">
      <layout-default-header-item direction="left">
        <a href="#">
          <img src="../assets/logo.jpg" alt="logo" style="height: 50px;height: 50px;float: left;" />
          <div class="alain-default__header-title"> Max<span style="color: #FFD700;">Key</span>{{ 'mxk.title' | i18n }} </div>
        </a>
      </layout-default-header-item>

      <layout-default-header-item direction="right" hidden="mobile">
        <div layout-default-header-item-trigger (click)="profile()"> {{ user.name }}</div>
      </layout-default-header-item>
      <layout-default-header-item direction="right" hidden="mobile">
        <div layout-default-header-item-trigger nz-dropdown [nzDropdownMenu]="settingsMenu" nzTrigger="click" nzPlacement="bottomRight">
          <i nz-icon nzType="setting"></i>
        </div>
        <nz-dropdown-menu #settingsMenu="nzDropdownMenu">
          <div nz-menu style="width: 200px;">
            <div nz-menu-item (click)="changePassword()">
              <i nz-icon nzType="key" nzTheme="outline"></i>
              {{ 'mxk.menu.config.password' | i18n }}
            </div>
            <div nz-menu-item>
              <header-rtl></header-rtl>
            </div>
            <div nz-menu-item>
              <header-fullscreen></header-fullscreen>
            </div>
            <div nz-menu-item>
              <header-clear-storage></header-clear-storage>
            </div>
            <div nz-menu-item>
              <header-i18n></header-i18n>
            </div>
          </div>
        </nz-dropdown-menu>
      </layout-default-header-item>
      <layout-default-header-item direction="right">
        <header-user></header-user>
      </layout-default-header-item>
      <ng-template #asideUserTpl>
        <div nz-dropdown nzTrigger="click" [nzDropdownMenu]="userMenu" class="alain-default__aside-user">
          <div class="alain-default__aside-user-info">
            <!--<strong>{{ user.name }}</strong>
            <p class="mb0">{{ user.email }}</p>-->
          </div>
          <!--
          <nz-avatar class="alain-default__aside-user-avatar" [nzSrc]="user.avatar"></nz-avatar>
        -->
        </div>
        <nz-dropdown-menu #userMenu="nzDropdownMenu">
          <ul nz-menu>
            <!--
            <li nz-menu-item routerLink="/pro/account/center">{{ 'menu.account.center' | i18n }}</li>
            <li nz-menu-item routerLink="/pro/account/settings">{{ 'menu.account.settings' | i18n }}</li>
          -->
          </ul>
        </nz-dropdown-menu>
      </ng-template>
      <ng-template #contentTpl>
        <router-outlet></router-outlet>
      </ng-template>
    </layout-default>

    <setting-drawer *ngIf="showSettingDrawer"></setting-drawer>
    <theme-btn></theme-btn>
  `
})
export class LayoutBasicComponent {
  options: LayoutDefaultOptions = {
    logoExpanded: `./assets/logo-full.svg`,
    logoCollapsed: `./assets/logo.svg`,
    hideAside: false
  };
  searchToggleStatus = false;
  showSettingDrawer = !environment.production;
  get user(): User {
    return this.settingsService.user;
  }

  profile(): void {
    this.router.navigateByUrl('/config/profile');
  }

  changePassword(): void {
    this.router.navigateByUrl('/config/password');
  }
  constructor(private settingsService: SettingsService, private router: Router) { }
}
