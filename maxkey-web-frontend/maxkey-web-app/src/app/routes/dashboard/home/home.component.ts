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

import { Platform } from '@angular/cdk/platform';
import { DOCUMENT } from '@angular/common';
import { ChangeDetectionStrategy, ViewContainerRef, ChangeDetectorRef, Component, Inject, OnInit, Renderer2 } from '@angular/core';
import type { Chart } from '@antv/g2';
import { I18NService } from '@core';
import { OnboardingService } from '@delon/abc/onboarding';
import { ACLService } from '@delon/acl';
import { ALAIN_I18N_TOKEN } from '@delon/theme';
import { environment } from '@env/environment';
import { format } from 'date-fns';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

import { AppCategoryService } from '../../../service/app-category.service';
import { AppListService } from '../../../service/appList.service';
import { AuthnService } from '../../../service/authn.service';
import { AccoutsComponent } from '../../config/accouts/accouts.component';

import { Console } from 'console';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',

  styles: [
    `
      .appListimage {
        width: 65px;
        height: 65px;
        border: 0;
      }
    `
  ],
  styleUrls: ['./home.component.less']
})
export class HomeComponent implements OnInit {
  loading: boolean = true;
  appList: any[] = [];
  baseUrl: String = '';
  staticAppList: any[] = [];
  appCategoryList: any[] = [];
  appsCategory: String = '';
  constructor(
    private modal: NzModalService,
    private viewContainerRef: ViewContainerRef,
    private appListService: AppListService,
    private appCategoryService: AppCategoryService,
    private cdr: ChangeDetectorRef,
    private obSrv: OnboardingService,
    private platform: Platform,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    @Inject(DOCUMENT) private doc: NzSafeAny
  ) {
    // TODO: Wait for the page to load
    //setTimeout(() => this.genOnboarding(), 1000);
  }

  fixDark(chart: Chart): void {
    if (!this.platform.isBrowser || (this.doc.body as HTMLBodyElement).getAttribute('data-theme') !== 'dark') return;

    chart.theme({
      styleSheet: {
        backgroundColor: 'transparent'
      }
    });
  }

  onAuthz(e: MouseEvent, appId: string): void {
    e.preventDefault();
    for (let i = 0; i < this.appList.length; i++) {
      if (this.appList[i].id == appId && (this.appList[i].protocol == 'Basic' || this.appList[i].inducer == 'SP')) {
        window.open(this.appList[i].loginUrl);
        return;
      }
    }
    window.open(`${this.baseUrl}authz/${appId}`);
  }
  setAccount(appId: string): void {
    const modal = this.modal.create({
      nzContent: AccoutsComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzComponentParams: {
        appId: appId
      },
      nzWidth: 550
      //nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
  }

  ngOnInit(): void {
    this.appCategoryList = this.appCategoryService.list();
    this.baseUrl = environment.api.baseUrl;
    if (!this.baseUrl.endsWith('/')) {
      this.baseUrl = `${this.baseUrl}/`;
    }
    console.log(`baseUrl : ${this.baseUrl}`);
    this.appListService.appList().subscribe(res => {
      //console.log(res.data);
      this.appList = res.data;
      this.staticAppList = this.appList;
      this.loading = false;
      this.cdr.detectChanges();
    });
  }

  changeCategory(category: String): void {
    //console.log(category);
    if (this.appCategoryService.list() === null || this.appCategoryService.list() === '' || category === 'All') {
      this.appList = this.staticAppList;
    } else {
      this.appList = [];
      for (let i = 0; i < this.staticAppList.length; i++) {
        //console.log(this.staticAppList[i]);
        if (this.staticAppList[i].category === this.appsCategory) {
          this.appList.push(this.staticAppList[i]);
        }
      }
    }
    this.cdr.detectChanges();
  }
}
