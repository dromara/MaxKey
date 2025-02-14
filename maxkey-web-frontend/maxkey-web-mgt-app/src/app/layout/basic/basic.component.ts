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

import { Component, OnInit } from '@angular/core';
import { SettingsService, User } from '@delon/theme';
import { environment } from '@env/environment';
import { AuthnService } from 'src/app/service/authn.service';
import { CONSTS } from 'src/app/shared/consts';

import { knowHost } from '../../shared/utils/knowhost';
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
      <layout-default-header-item direction="left" *ngIf="!inst.custom">
        <a href="#">
          <img src="./assets/logo.png" alt="logo" style="height: 50px;height: 50px;float: left;" />
          <div
            class="alain-default__nav-item_title"
            style="letter-spacing: 2px;
              font-size: 20px;
              font-weight: bolder;
              width: 450px;
              margin-top: 12px;"
          >
            <span style="color: #000099;">Max</span>
            <span style="color: #FFD700;">Key</span>
            {{ 'mxk.title' | i18n }}
          </div>
        </a>
      </layout-default-header-item>
      <layout-default-header-item direction="left" *ngIf="inst.custom">
        <a href="#">
          <img src="{{ inst.logo }}" alt="logo" style="height: 50px;height: 50px;float: left;" />
          <div
            class="alain-default__nav-item_title"
            style="letter-spacing: 2px;
              font-size: 20px;
              font-weight: bolder;
              width: 450px;
              margin-top: 12px;"
          >
            {{ inst.title }}
          </div>
        </a>
      </layout-default-header-item>
      <layout-default-header-item direction="right" hidden="mobile">
        <div layout-default-header-item-trigger>
          <a href="https://www.maxkey.top/zh/about/download.html" target="_blank" style="color: black;">
            <i nz-icon nzType="github" nzTheme="outline"></i>
          </a>
        </div>
      </layout-default-header-item>
      <layout-default-header-item direction="right" hidden="mobile">
        <div layout-default-header-item-trigger>
          <header-i18n></header-i18n>
        </div>
      </layout-default-header-item>
      <layout-default-header-item direction="right" hidden="mobile">
        <div layout-default-header-item-trigger nz-dropdown [nzDropdownMenu]="settingsMenu" nzTrigger="click" nzPlacement="bottomRight">
          <i nz-icon nzType="setting"></i>
        </div>
        <nz-dropdown-menu #settingsMenu="nzDropdownMenu">
          <div nz-menu style="width: 200px;">
            <div nz-menu-item>
              <header-rtl></header-rtl>
            </div>
            <div nz-menu-item>
              <header-fullscreen></header-fullscreen>
            </div>
            <div nz-menu-item>
              <header-clear-storage></header-clear-storage>
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
            <strong>{{ user.name }}</strong>
            <!--<p class="mb0">{{ user.email }}</p>-->
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
    <global-footer style="border-top: 1px solid #e5e5e5; min-height: 120px; text-shadow: 0 1px 0 #fff;margin:0;">
      <div style="margin-top: 30px">
        MaxKey {{ version }}<br />
        Copyright
        <i nz-icon nzType="copyright"></i> {{ copyrightYear }} <a href="//www.maxkey.top" target="_blank">http://www.maxkey.top</a><br />
        Licensed under the Apache License, Version 2.0
      </div>
    </global-footer>
    <setting-drawer *ngIf="showSettingDrawer"></setting-drawer>
    <theme-btn></theme-btn>
  `
})
export class LayoutBasicComponent implements OnInit {
  version = CONSTS.VERSION;
  copyrightYear = new Date().getFullYear();
  inst: any;
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

  ngOnInit(): void {
    this.inst = this.authnService.getInst();
    if (this.inst == null) {
      this.inst = { custom: false };
      this.authnService.initInst().subscribe(res => {
        this.authnService.setInst(res.data, !knowHost());
        this.inst = this.authnService.getInst();
      });
    }
  }

  constructor(private settingsService: SettingsService, private authnService: AuthnService) {}
}
