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
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Inject, OnInit, Renderer2 } from '@angular/core';
import type { Chart } from '@antv/g2';
import { OnboardingService } from '@delon/abc/onboarding';
import { format } from 'date-fns';
import { NzSafeAny } from 'ng-zorro-antd/core/types';

import { AnalysisService } from '../../../service/analysis.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.less']
})
export class HomeComponent implements OnInit {
  simulateData: any[] = [];
  //当月新增
  newUsers!: any[];
  //当月活动
  activeUsers!: any[];
  //当天统计
  dayCount!: any[];
  //在线用户
  onlineUsers!: any[];
  //当日
  dayData: any[] = [];
  //当月
  mouthData: any[] = [];

  reportApp: any[] = [];

  reportBrowser: any[] = [];

  constructor(
    private analysisService: AnalysisService,
    private cdr: ChangeDetectorRef,
    private obSrv: OnboardingService,
    private platform: Platform,
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

  ngOnInit(): void {
    this.analysisService.dashBoard({}).subscribe(res => {
      this.onlineUsers = res.data.onlineUsers;
      this.dayCount = res.data.dayCount;
      this.newUsers = res.data.newUsers;
      this.activeUsers = res.data.activeUsers;

      const beginDay = new Date().getTime();
      const fakeY = [7, 5, 4, 2, 4, 7, 5, 6, 5, 9, 6, 3, 1, 5, 3, 6, 5];
      for (let i = 0; i < fakeY.length; i += 1) {
        this.simulateData.push({
          x: format(new Date(beginDay + 1000 * 60 * 60 * 24 * i), 'yyyy-MM-dd'),
          y: fakeY[i]
        });
      }
      this.dayData = [];
      for (let i = 0; i < res.data.reportDayHour.length; i += 1) {
        this.dayData.push({
          x: res.data.reportDayHour[i].reportstring,
          y: res.data.reportDayHour[i].reportcount
        });
      }

      this.mouthData = [];
      for (let i = 0; i < res.data.reportMonth.length; i += 1) {
        this.mouthData.push({
          x: res.data.reportMonth[i].reportstring,
          y: res.data.reportMonth[i].reportcount
        });
      }

      this.reportApp = res.data.reportApp;
      this.reportBrowser = res.data.reportBrowser;
      this.cdr.detectChanges();
    });
  }
}
