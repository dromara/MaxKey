import { Platform } from '@angular/cdk/platform';
import { DOCUMENT } from '@angular/common';
import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Inject, OnInit, Renderer2 } from '@angular/core';
import type { Chart } from '@antv/g2';
import { OnboardingService } from '@delon/abc/onboarding';
import { environment } from '@env/environment';
import { format } from 'date-fns';
import { NzSafeAny } from 'ng-zorro-antd/core/types';

import { AppListService } from '../../../service/appList.service';

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

  constructor(
    private appListService: AppListService,
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
  ngOnInit(): void {
    if (environment.api.baseUrl.endsWith('/')) {
      this.baseUrl = environment.api.baseUrl.substring(0, environment.api.baseUrl.length - 1);
    } else {
      this.baseUrl = environment.api.baseUrl;
    }

    this.appListService.appList().subscribe(res => {
      console.log(res.data);
      this.appList = res.data;
      this.cdr.detectChanges();
    });
  }
}
