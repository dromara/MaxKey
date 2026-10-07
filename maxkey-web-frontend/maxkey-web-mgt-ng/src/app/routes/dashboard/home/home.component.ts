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
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, Renderer2, inject, ViewChild } from '@angular/core';
import type { Chart } from '@antv/g2';
import { I18NService } from '@core';
import { OnboardingService } from '@delon/abc/onboarding';
import { QuickMenuModule } from '@delon/abc/quick-menu';
import { G2BarModule } from '@delon/chart/bar';
import { G2CardModule } from '@delon/chart/card';
import { G2GaugeModule } from '@delon/chart/gauge';
import { G2MiniAreaModule } from '@delon/chart/mini-area';
import { G2MiniBarModule } from '@delon/chart/mini-bar';
import { G2MiniProgressModule } from '@delon/chart/mini-progress';
import { NumberInfoModule } from '@delon/chart/number-info';
import { G2PieModule, G2PieClickItem, G2PieComponent, G2PieData } from '@delon/chart/pie';
import { G2RadarModule } from '@delon/chart/radar';
import { G2SingleBarModule } from '@delon/chart/single-bar';
import { G2TagCloudModule } from '@delon/chart/tag-cloud';
import { G2TimelineModule } from '@delon/chart/timeline';
import { TrendModule } from '@delon/chart/trend';
import { G2WaterWaveModule } from '@delon/chart/water-wave';
import { ALAIN_I18N_TOKEN } from '@delon/theme';
import { format } from 'date-fns';
// Features like Universal Transition and Label Layout
import {
  TitleComponent,
  TooltipComponent,
  GridComponent,
  DatasetComponent,
  GeoComponent,
  TransformComponent,
  VisualMapComponent
} from 'echarts/components';
// Features like Universal Transition and Label Layout
import * as echarts from 'echarts/core';
import { LabelLayout, UniversalTransition } from 'echarts/features';
// Import the Canvas renderer
// Note that including the CanvasRenderer or SVGRenderer is a required step
import { CanvasRenderer } from 'echarts/renderers';
import { GeoJSONSourceInput } from 'echarts/types/src/coord/geo/geoTypes.js';
import { NzSafeAny } from 'ng-zorro-antd/core/types';

import { AnalysisService } from '../../../service/analysis.service';
import chinaJson from '../../../shared/map/china.json';
import worldJson from '../../../shared/map/world.zh.json';
import { SHARED_IMPORTS } from '../../../shared/shared-imports';

// Register the required components
echarts.use([
  TitleComponent,
  TooltipComponent,
  GridComponent,
  GeoComponent,
  DatasetComponent,
  TransformComponent,
  VisualMapComponent,
  LabelLayout,
  UniversalTransition,
  CanvasRenderer
]);
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.less'],
  imports: [
    SHARED_IMPORTS,
    QuickMenuModule,
    G2BarModule,
    G2CardModule,
    G2GaugeModule,
    G2MiniAreaModule,
    G2MiniBarModule,
    NumberInfoModule,
    G2PieModule,
    G2RadarModule,
    G2SingleBarModule,
    G2TagCloudModule,
    G2TimelineModule,
    TrendModule,
    G2WaterWaveModule,
    G2MiniProgressModule
  ]
})
export class HomeComponent implements OnInit {
  simulateData: any[] = [];
  //褰撴湀鏂板
  newUsers: number = 0;
  //褰撴湀娲诲姩
  activeUsers: number = 0;
  //褰撳ぉ缁熻
  dayCount: number = 0;

  monthCount: number = 0;

  totalUsers: number = 0;

  totalDepts: number = 0;

  totalApps: number = 0;

  totalGroups: number = 0;
  //鍦ㄧ嚎鐢ㄦ埛
  onlineUsers: number = 0;
  //褰撴棩
  dayData: any[] = [{ time: 0, y1: 0 }];

  dayTitleMap!: any;
  //褰撴湀
  mouthData: any[] = [];

  reportApp: any[] = [];

  reportBrowser: any[] = [];

  mapType = 'china';
  //鍦板浘鏁版嵁
  provinceMapData: any[] = [];

  provinceTableData: any[] = [];

  top10ProvinceTableData: any[] = [];

  worldMapData: any[] = [];

  worldTableData: any[] = [];

  top10WorldTableData: any[] = [];

  mapSplitList: any[] = [
    { start: 90, end: 100 },
    { start: 80, end: 90 },
    { start: 70, end: 80 },
    { start: 60, end: 70 },
    { start: 50, end: 60 },
    { start: 40, end: 50 },
    { start: 30, end: 40 },
    { start: 20, end: 30 },
    { start: 10, end: 20 },
    { start: 0, end: 10 }
  ];

  mapColor: any[] = ['#DC143C', '#33A1C9', '#EE82EE', '#4B0082', '#5475f5', '#9feaa5', '#85daef', '#e6ac53', '#FAEBD7', '#F0F8FF'];

  private readonly analysisService: AnalysisService = inject(AnalysisService);
  private readonly cdr: ChangeDetectorRef = inject(ChangeDetectorRef);
  private readonly obSrv: OnboardingService = inject(OnboardingService);
  private readonly platform: Platform = inject(Platform);
  private readonly doc: NzSafeAny = inject(DOCUMENT);
  private i18n = inject<I18NService>(ALAIN_I18N_TOKEN);

  constructor() {
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
      this.monthCount = res.data.monthCount;
      this.newUsers = res.data.newUsers;
      this.activeUsers = res.data.activeUsers;
      this.totalDepts = res.data.totalDepts;
      this.totalUsers = res.data.totalUsers;
      this.totalApps = res.data.totalApps;
      this.totalGroups = res.data.totalGroups;

      const beginDay = new Date().getTime();
      const fakeY = [7, 5, 4, 2, 4, 7, 5, 6, 5, 9, 6, 3, 1, 5, 3, 6, 5];
      this.simulateData = [fakeY.length];
      for (let i = 0; i < fakeY.length; i += 1) {
        this.simulateData[i] = {
          x: format(new Date(beginDay + 1000 * 60 * 60 * 24 * i), 'yyyy-MM-dd'),
          y: fakeY[i]
        };
      }
      this.dayData = [];
      let currDate = new Date();
      for (let dayHour of res.data.reportDayHour) {
        let dateTimeString = `${currDate.getFullYear()}/${currDate.getMonth() + 1}/${currDate.getDate()} ${dayHour.reportstring}:00:00`;
        let reportTime = new Date(dateTimeString);
        this.dayData.push({
          time: reportTime,
          y1: dayHour.reportcount
        });
      }
      this.dayTitleMap = {
        y1: this.i18n.fanyi('mxk.text.hour')
      };

      this.mouthData = [];
      for (let reportMonth of res.data.reportMonth) {
        this.mouthData.push({
          x: reportMonth.reportstring,
          y: reportMonth.reportcount
        });
      }

      this.provinceTableData = res.data.reportProvince;
      this.reportApp = res.data.reportApp;
      this.reportBrowser = res.data.reportBrowser;
      this.top10ProvinceTableData = [this.provinceTableData.length];
      for (let i = 0; i < this.provinceTableData.length && i < 10; i += 1) {
        this.top10ProvinceTableData[i] = this.provinceTableData[i];
      }

      this.worldTableData = res.data.reportCountry;
      this.top10WorldTableData = [this.worldTableData.length];
      for (let i = 0; i < this.worldTableData.length && i < 10; i += 1) {
        this.top10WorldTableData[i] = this.worldTableData[i];
      }
      this.cdr.detectChanges();
      //寤惰繜鍔犺浇鍦板浘锛屽惁鍒檇om鏃犳硶鍔犺浇echarts鍦板浘
      setTimeout(() => {
        this.initCharts();
        this.initWorldChart();
        this.cdr.detectChanges();
      }, 1000);
    });
  }

  /*
   * 涓栫晫鍦板浘
   */
  initWorldChart() {
    let maxMapCount = 0;

    for (let i = 0; i < this.worldTableData.length; i++) {
      this.worldMapData.push({
        value: this.worldTableData[i].reportcount,
        name: this.worldTableData[i].reportstring,
        itemStyle: { color: this.mapColor.length - 1 }
      });
      //console.log(`country ${this.worldMapData[i].name} ${this.worldMapData[i].value}`);
      if (maxMapCount < this.worldMapData[i].value) {
        maxMapCount = this.worldMapData[i].value;
      }
    }

    //console.log(`maxMapCount ${maxMapCount}`);
    if (maxMapCount <= 100) {
      //100以内，10
      this.mapSplitList = [
        { start: 90, end: 100 },
        { start: 80, end: 90 },
        { start: 70, end: 80 },
        { start: 60, end: 70 },
        { start: 50, end: 60 },
        { start: 40, end: 50 },
        { start: 30, end: 40 },
        { start: 20, end: 30 },
        { start: 10, end: 20 },
        { start: 0, end: 10 }
      ];
    } else if (maxMapCount <= 500) {
      //500以内 50
      this.mapSplitList = [
        { start: 450, end: 500 },
        { start: 400, end: 450 },
        { start: 350, end: 400 },
        { start: 300, end: 350 },
        { start: 250, end: 300 },
        { start: 200, end: 250 },
        { start: 150, end: 200 },
        { start: 100, end: 150 },
        { start: 50, end: 100 },
        { start: 0, end: 50 }
      ];
    } else if (maxMapCount <= 1000) {
      //1000以内 100
      this.mapSplitList = [
        { start: 900, end: 100 },
        { start: 800, end: 900 },
        { start: 700, end: 800 },
        { start: 600, end: 700 },
        { start: 500, end: 600 },
        { start: 400, end: 500 },
        { start: 300, end: 400 },
        { start: 200, end: 300 },
        { start: 100, end: 200 },
        { start: 0, end: 100 }
      ];
    } else if (maxMapCount <= 5000) {
      //5000以内 500
      this.mapSplitList = [
        { start: 4500, end: 5000 },
        { start: 4000, end: 4500 },
        { start: 3500, end: 4000 },
        { start: 3000, end: 3500 },
        { start: 2500, end: 3000 },
        { start: 2000, end: 2500 },
        { start: 1500, end: 2000 },
        { start: 1000, end: 1500 },
        { start: 500, end: 1000 },
        { start: 0, end: 500 }
      ];
    } else if (maxMapCount <= 10000) {
      //10000以内 1000
      this.mapSplitList = [
        { start: 9000, end: 1000 },
        { start: 8000, end: 9000 },
        { start: 7000, end: 8000 },
        { start: 6000, end: 7000 },
        { start: 5000, end: 6000 },
        { start: 4000, end: 5000 },
        { start: 3000, end: 4000 },
        { start: 2000, end: 3000 },
        { start: 1000, end: 2000 },
        { start: 0, end: 1000 }
      ];
    } else if (maxMapCount <= 50000) {
      //50000以内 5000
      this.mapSplitList = [
        { start: 45000, end: 50000 },
        { start: 40000, end: 45000 },
        { start: 35000, end: 40000 },
        { start: 30000, end: 35000 },
        { start: 25000, end: 30000 },
        { start: 20000, end: 25000 },
        { start: 15000, end: 20000 },
        { start: 10000, end: 15000 },
        { start: 5000, end: 10000 },
        { start: 0, end: 5000 }
      ];
    } else if (maxMapCount <= 100000) {
      //100000以内 10000
      this.mapSplitList = [
        { start: 90000, end: 10000 },
        { start: 80000, end: 90000 },
        { start: 70000, end: 80000 },
        { start: 60000, end: 70000 },
        { start: 50000, end: 60000 },
        { start: 40000, end: 50000 },
        { start: 30000, end: 40000 },
        { start: 20000, end: 30000 },
        { start: 10000, end: 20000 },
        { start: 0, end: 10000 }
      ];
    }
    for (let mapData of this.worldMapData) {
      for (let si = 0; si < this.mapSplitList.length; si++) {
        if (this.mapSplitList[si].start < mapData.value && mapData.value <= this.mapSplitList[si].end) {
          mapData.itemStyle.color = this.mapColor[si];
          break;
        }
      }
    }
    /* test data
    this.worldMapData = [
      { name: '美国', value: 2963.496, itemStyle: { color: this.mapColor[0] } },
      { name: '中国', value: 4822023, itemStyle: { color: this.mapColor[0] } }
    ];
    */
    //console.log(`worldMapData `);
    //console.log(this.worldMapData);

    echarts.registerMap('worldMap', worldJson as GeoJSONSourceInput); //注册地图数据
    // The chart is initialized and configured in the same manner as before
    let mapWorldChartInstance = echarts.init(document.getElementById('mapWorldChart'));
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
      geo: [
        {
          name: '数据',
          type: 'map',
          map: 'worldMap',
          layoutCenter: ['50%', '70%'],
          layoutSize: '140%',
          label: {
            show: false
          },
          itemStyle: {
            color: '#fff'
          },
          roam: true,
          emphasis: {
            show: false,
            label: {
              normal: {
                show: true
              }
            }
          },
          regions: this.worldMapData //数据
        }
      ]
    };
    mapWorldChartInstance.setOption(optionMap);
    this.cdr.detectChanges();
  }

  initCharts() {
    let maxMapCount = 0;
    for (let i = 0; i < this.provinceTableData.length; i++) {
      this.provinceMapData.push({
        value: this.provinceTableData[i].reportcount,
        provinceName: this.provinceTableData[i].reportstring,
        name: this.provinceTableData[i].reportstring,
        itemStyle: { color: this.mapColor[this.mapColor.length - 1] }
      });
      //console.log(`provinceName ${this.provinceMapData[i].value}`);
      if (maxMapCount < this.provinceMapData[i].value) {
        maxMapCount = this.provinceMapData[i].value;
      }
      let provinceName = `${this.provinceMapData[i].name}`;
      //console.log(`provinceName ${provinceName}`);
      if (provinceName.indexOf('新疆') > -1) {
        this.provinceMapData[i].name = '新疆维吾尔自治区';
      } else if (provinceName.indexOf('广西') > -1) {
        this.provinceMapData[i].name = '广西壮族自治区';
      } else if (provinceName.indexOf('内蒙古') > -1) {
        this.provinceMapData[i].name = '内蒙古自治区';
      } else if (provinceName.indexOf('宁夏') > -1) {
        this.provinceMapData[i].name = '宁夏回族自治区';
      } else if (provinceName.indexOf('西藏') > -1) {
        this.provinceMapData[i].name = '西藏自治区';
      } else if (provinceName.indexOf('香港') > -1) {
        this.provinceMapData[i].name = '香港特别行政区';
      } else if (provinceName.indexOf('澳门') > -1) {
        this.provinceMapData[i].name = '澳门特别行政区';
      } else if (provinceName.indexOf('上海') > -1) {
        this.provinceMapData[i].name = '上海市';
      } else if (provinceName.indexOf('北京') > -1) {
        this.provinceMapData[i].name = '北京市';
      } else if (provinceName.indexOf('重庆') > -1) {
        this.provinceMapData[i].name = '重庆市';
      } else if (provinceName.indexOf('天津') > -1) {
        this.provinceMapData[i].name = '天津市';
      }
    }

    //console.log(`maxMapCount ${maxMapCount}`);
    if (maxMapCount <= 100) {
      //100以内，10
      this.mapSplitList = [
        { start: 90, end: 100 },
        { start: 80, end: 90 },
        { start: 70, end: 80 },
        { start: 60, end: 70 },
        { start: 50, end: 60 },
        { start: 40, end: 50 },
        { start: 30, end: 40 },
        { start: 20, end: 30 },
        { start: 10, end: 20 },
        { start: 0, end: 10 }
      ];
    } else if (maxMapCount <= 500) {
      //500以内 50
      this.mapSplitList = [
        { start: 450, end: 500 },
        { start: 400, end: 450 },
        { start: 350, end: 400 },
        { start: 300, end: 350 },
        { start: 250, end: 300 },
        { start: 200, end: 250 },
        { start: 150, end: 200 },
        { start: 100, end: 150 },
        { start: 50, end: 100 },
        { start: 0, end: 50 }
      ];
    } else if (maxMapCount <= 1000) {
      //1000以内 100
      this.mapSplitList = [
        { start: 900, end: 100 },
        { start: 800, end: 900 },
        { start: 700, end: 800 },
        { start: 600, end: 700 },
        { start: 500, end: 600 },
        { start: 400, end: 500 },
        { start: 300, end: 400 },
        { start: 200, end: 300 },
        { start: 100, end: 200 },
        { start: 0, end: 100 }
      ];
    } else if (maxMapCount <= 5000) {
      //5000以内 500
      this.mapSplitList = [
        { start: 4500, end: 5000 },
        { start: 4000, end: 4500 },
        { start: 3500, end: 4000 },
        { start: 3000, end: 3500 },
        { start: 2500, end: 3000 },
        { start: 2000, end: 2500 },
        { start: 1500, end: 2000 },
        { start: 1000, end: 1500 },
        { start: 500, end: 1000 },
        { start: 0, end: 500 }
      ];
    } else if (maxMapCount <= 10000) {
      //10000以内 1000
      this.mapSplitList = [
        { start: 9000, end: 1000 },
        { start: 8000, end: 9000 },
        { start: 7000, end: 8000 },
        { start: 6000, end: 7000 },
        { start: 5000, end: 6000 },
        { start: 4000, end: 5000 },
        { start: 3000, end: 4000 },
        { start: 2000, end: 3000 },
        { start: 1000, end: 2000 },
        { start: 0, end: 1000 }
      ];
    } else if (maxMapCount <= 50000) {
      //50000以内 5000
      this.mapSplitList = [
        { start: 45000, end: 50000 },
        { start: 40000, end: 45000 },
        { start: 35000, end: 40000 },
        { start: 30000, end: 35000 },
        { start: 25000, end: 30000 },
        { start: 20000, end: 25000 },
        { start: 15000, end: 20000 },
        { start: 10000, end: 15000 },
        { start: 5000, end: 10000 },
        { start: 0, end: 5000 }
      ];
    } else if (maxMapCount <= 100000) {
      //100000以内 10000
      this.mapSplitList = [
        { start: 90000, end: 10000 },
        { start: 80000, end: 90000 },
        { start: 70000, end: 80000 },
        { start: 60000, end: 70000 },
        { start: 50000, end: 60000 },
        { start: 40000, end: 50000 },
        { start: 30000, end: 40000 },
        { start: 20000, end: 30000 },
        { start: 10000, end: 20000 },
        { start: 0, end: 10000 }
      ];
    }
    for (let mapData of this.provinceMapData) {
      for (let si = 0; si < this.mapSplitList.length; si++) {
        if (this.mapSplitList[si].start < mapData.value && mapData.value <= this.mapSplitList[si].end) {
          mapData.itemStyle.color = this.mapColor[si];
          break;
        }
      }
    }
    //console.log('provinceMapData');
    //console.log(this.provinceMapData);
    echarts.registerMap('china', chinaJson as GeoJSONSourceInput); //注册地图数据
    // The chart is initialized and configured in the same manner as before
    let mapChartInstance = echarts.init(document.getElementById('mapChart'));
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
      geo: [
        {
          name: '数据',
          type: 'map',
          map: 'china',
          layoutCenter: ['50%', '70%'],
          layoutSize: '140%',
          label: {
            show: true
          },
          itemStyle: {
            color: '#fff'
          },
          roam: true,
          emphasis: {
            show: false,
            label: {
              normal: {
                show: true //省份名称
              }
            }
          },
          regions: this.provinceMapData //数据
          //,regions: [{ name: '湖南省', itemStyle: { color: '#5475f5' } }]
        }
      ]
    };
    mapChartInstance.setOption(optionMap);
    this.cdr.detectChanges();
  }
}
