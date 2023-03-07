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
import { OnboardingService } from '@delon/abc/onboarding';
import { ACLService } from '@delon/acl';
import { environment } from '@env/environment';
import { format } from 'date-fns';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { CONSTS } from 'src/app/shared/consts';

import { AppListService } from '../../../service/appList.service';
import { AuthnService } from '../../../service/authn.service';
import { AccoutsComponent } from '../../config/accouts/accouts.component';

import { Console } from 'console';
import {ALAIN_I18N_TOKEN} from "@delon/theme";
import {I18NService} from "@core";

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
  loading: boolean = false;
  appList: any[] = [];
  baseUrl: String = '';
  staticAppList: any[] = [];
  appCategoryList: any[] = [];
  appsCategory: String = '';
  constructor(
    private modal: NzModalService,
    private viewContainerRef: ViewContainerRef,
    private appListService: AppListService,
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
    window.open(`${this.baseUrl}/authz/${appId}`);
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
    if (environment.api.baseUrl.endsWith('/')) {
      this.baseUrl = environment.api.baseUrl.substring(0, environment.api.baseUrl.length - 1);
    } else {
      this.baseUrl = environment.api.baseUrl;
    }
    this.appListService.appList().subscribe(res => {
      //console.log(res.data);
      this.appList = res.data;
      this.staticAppList = this.appList;
      this.cdr.detectChanges();
    });
    this.appCategoryList = [{
        id:'none',
        name:this.i18n.fanyi('mxk.apps.category.none')
      },{
        id:'1011',
        name:this.i18n.fanyi('mxk.apps.category.1011')
      },
      {
        id:'1012',
        name:this.i18n.fanyi('mxk.apps.category.1012')
      },
      {
        id:'1013',
        name:this.i18n.fanyi('mxk.apps.category.1013')
      },
      {
        id:'1014',
        name:this.i18n.fanyi('mxk.apps.category.1014')
      },
      {
        id:'1015',
        name:this.i18n.fanyi('mxk.apps.category.1015')
      },
      {
        id:'1016',
        name:this.i18n.fanyi('mxk.apps.category.1016')
      },
      {
        id:'1017',
        name:this.i18n.fanyi('mxk.apps.category.1017')
      },
      {
        id:'1111',
        name:this.i18n.fanyi('mxk.apps.category.1111')
      },
      {
        id:'1112',
        name:this.i18n.fanyi('mxk.apps.category.1112')
      },
      {
        id:'1113',
        name:this.i18n.fanyi('mxk.apps.category.1113')
      },
      {
        id:'1114',
        name:this.i18n.fanyi('mxk.apps.category.1114')
      },
      {
        id:'1211',
        name:this.i18n.fanyi('mxk.apps.category.1211')
      },
      {
        id:'1212',
        name:this.i18n.fanyi('mxk.apps.category.1212')
      },
      {
        id:'1213',
        name:this.i18n.fanyi('mxk.apps.category.1213')
      },
      {
        id:'1214',
        name:this.i18n.fanyi('mxk.apps.category.1214')
      },
      {
        id:'1215',
        name:this.i18n.fanyi('mxk.apps.category.1215')
      },
      {
        id:'1215',
        name:this.i18n.fanyi('mxk.apps.category.1215')
      },
      {
        id:'1311',
        name:this.i18n.fanyi('mxk.apps.category.1311')
      },
      {
        id:'1411',
        name:this.i18n.fanyi('mxk.apps.category.1411')
      },
      {
        id:'1511',
        name:this.i18n.fanyi('mxk.apps.category.1511')
      },
      {
        id:'1512',
        name:this.i18n.fanyi('mxk.apps.category.1512')
      },
      {
        id:'1611',
        name:this.i18n.fanyi('mxk.apps.category.1611')
      },
      {
        id:'1711',
        name:this.i18n.fanyi('mxk.apps.category.1711')
      },
      {
        id:'1712',
        name:this.i18n.fanyi('mxk.apps.category.1712')
      },
      {
        id:'1811',
        name:this.i18n.fanyi('mxk.apps.category.1811')
      },
      {
        id:'1812',
        name:this.i18n.fanyi('mxk.apps.category.1812')
      },{
        id:'1911',
        name:this.i18n.fanyi('mxk.apps.category.1911')
      },
      {
        id:'1912',
        name:this.i18n.fanyi('mxk.apps.category.1912')
      }
    ]
  }
  changeCategory (): void {
    this.appList = [];
    if (this.appsCategory === null || this.appsCategory === '') {
      this.appList = this.staticAppList;
    } else {
      for(let i = 0;i<this.staticAppList.length;i++){
        if(this.staticAppList[i].category === this.appsCategory) {
          this.appList.push(this.staticAppList[i]);
        }
      }
    }
    this.cdr.detectChanges();
  }
}
