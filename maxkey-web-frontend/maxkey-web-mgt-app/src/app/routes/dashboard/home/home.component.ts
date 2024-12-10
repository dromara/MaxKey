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
// Features like Universal Transition and Label Layout
import * as echarts from 'echarts';
import { NzSafeAny } from 'ng-zorro-antd/core/types';

import { AnalysisService } from '../../../service/analysis.service';
import chinaMap from '../../../shared/map/json/china.json';

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

  //地图数据
  provinceMapData: any[] = [];

  provinceTableData: any[] = [];

  top10ProvinceTableData: any[] = [];

  mapSplitList: any[] = [
    { start: 400, end: 500 },
    { start: 300, end: 400 },
    { start: 200, end: 300 },
    { start: 100, end: 200 },
    { start: 0, end: 100 }
  ];

  mapColor: any[] = ['#5475f5', '#9feaa5', '#85daef', '#e6ac53', '#9fb5ea'];

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

      this.provinceTableData = res.data.reportProvince;
      this.reportApp = res.data.reportApp;
      this.reportBrowser = res.data.reportBrowser;
      this.top10ProvinceTableData = [this.provinceTableData.length];
      for (let i = 0; i < this.provinceTableData.length && i < 10; i += 1) {
        this.top10ProvinceTableData[i] = this.provinceTableData[i];
      }
      //延迟加载地图，否则dom无法加载echarts地图
      setTimeout(() => {
        this.initCharts();
      });
      this.cdr.detectChanges();
    });
  }

  initCharts() {
    let maxMapCount = 0;
    for (let i = 0; i < this.provinceTableData.length; i++) {
      this.provinceMapData.push({
        value: this.provinceTableData[i].reportcount,
        provinceName: this.provinceTableData[i].reportstring,
        name: this.provinceTableData[i].name,
        itemStyle: { color: this.mapColor.length - 1 }
      });
      if (maxMapCount < this.provinceMapData[i].value) {
        maxMapCount = this.provinceMapData[i].value;
      }
    }
    console.log(`maxMapCount ${maxMapCount}`);
    if (maxMapCount <= 100) {
      //100以内
      this.mapSplitList = [
        { start: 80, end: 100 },
        { start: 60, end: 80 },
        { start: 40, end: 60 },
        { start: 20, end: 40 },
        { start: 0, end: 20 }
      ];
    } else if (maxMapCount <= 500) {
      //500以内
      this.mapSplitList = [
        { start: 400, end: 500 },
        { start: 300, end: 400 },
        { start: 200, end: 300 },
        { start: 100, end: 200 },
        { start: 0, end: 100 }
      ];
    } else if (maxMapCount <= 1000) {
      //1000以内
      this.mapSplitList = [
        { start: 800, end: 1000 },
        { start: 600, end: 800 },
        { start: 400, end: 600 },
        { start: 200, end: 400 },
        { start: 0, end: 200 }
      ];
    } else if (maxMapCount <= 5000) {
      //5000以内
      this.mapSplitList = [
        { start: 4000, end: 5000 },
        { start: 3000, end: 4000 },
        { start: 2000, end: 3000 },
        { start: 1000, end: 2000 },
        { start: 0, end: 1000 }
      ];
    } else if (maxMapCount <= 10000) {
      //10000以内
      this.mapSplitList = [
        { start: 8000, end: 10000 },
        { start: 6000, end: 8000 },
        { start: 4000, end: 6000 },
        { start: 2000, end: 4000 },
        { start: 0, end: 2000 }
      ];
    } else if (maxMapCount <= 50000) {
      //50000以内
      this.mapSplitList = [
        { start: 40000, end: 50000 },
        { start: 30000, end: 40000 },
        { start: 20000, end: 30000 },
        { start: 10000, end: 20000 },
        { start: 0, end: 10000 }
      ];
    } else if (maxMapCount <= 100000) {
      //100000以内
      this.mapSplitList = [
        { start: 80000, end: 100000 },
        { start: 60000, end: 80000 },
        { start: 40000, end: 60000 },
        { start: 20000, end: 40000 },
        { start: 0, end: 20000 }
      ];
    }
    for (let i = 0; i < this.provinceMapData.length; i++) {
      for (let si = 0; si < this.mapSplitList.length; si++) {
        if (this.mapSplitList[si].start < this.provinceMapData[i].value && this.provinceMapData[i].value <= this.mapSplitList[si].end) {
          this.provinceMapData[i].itemStyle.color = this.mapColor[si];
          break;
        }
      }
    }
    console.log(this.provinceMapData);
    const ec = echarts as any;

    let mapChart = ec.init(document.getElementById('mapChart'));
    //注册地图到echarts中  这里的 "china" 要与地图数据的option中的geo中的map对应
    ec.registerMap('china', { geoJSON: chinaMap });
    let optionMap = {
      backgroundColor: '#FFFFFF',
      tooltip: {
        trigger: 'item'
      },
      //左侧小导航图标
      visualMap: {
        show: true,
        x: 'left',
        y: 'center',
        splitList: this.mapSplitList,
        color: this.mapColor
      },
      //配置属性
      series: [
        {
          name: '数据',
          type: 'map',
          mapType: 'china',
          roam: true,
          label: {
            normal: {
              show: true //省份名称
            },
            emphasis: {
              show: false
            }
          },
          data: this.provinceMapData //数据
        }
      ]
    };
    mapChart.setOption(optionMap);
    this.cdr.detectChanges();
  }
}
