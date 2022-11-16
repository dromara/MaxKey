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

import { Direction, Directionality } from '@angular/cdk/bidi';
import { Platform } from '@angular/cdk/platform';
import { DOCUMENT } from '@angular/common';
import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Inject,
  InjectionToken,
  Input,
  isDevMode,
  OnDestroy,
  OnInit,
  Optional,
  Output,
  Renderer2
} from '@angular/core';
import { AlainConfigService } from '@delon/util/config';
import type { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

export interface ThemeBtnType {
  key: string;
  text: string;
}

export const ALAIN_THEME_BTN_KEYS = new InjectionToken<string>('ALAIN_THEME_BTN_KEYS');

@Component({
  selector: 'theme-btn',
  templateUrl: './theme-btn.component.html',
  host: {
    '[class.theme-btn]': `true`,
    '[class.theme-btn-rtl]': `dir === 'rtl'`
  },
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ThemeBtnComponent implements OnInit, OnDestroy {
  private theme = 'default';
  isDev = isDevMode();
  @Input() types: ThemeBtnType[] = [
    { key: 'default', text: 'Default Theme' },
    { key: 'classic', text: 'Classic Theme' },
    { key: 'dark', text: 'Dark Theme' },
    { key: 'compact', text: 'Compact Theme' }
  ];
  @Input() devTips = `When the dark.css file can't be found, you need to run it once: npm run theme`;
  @Input() deployUrl = '';
  @Output() readonly themeChange = new EventEmitter<string>();
  private destroy$ = new Subject<void>();
  dir: Direction = 'ltr';

  constructor(
    private renderer: Renderer2,
    private configSrv: AlainConfigService,
    private platform: Platform,
    @Inject(DOCUMENT) private doc: NzSafeAny,
    @Optional() private directionality: Directionality,
    @Inject(ALAIN_THEME_BTN_KEYS) private KEYS: string
  ) {}

  ngOnInit(): void {
    this.dir = this.directionality.value;
    this.directionality.change?.pipe(takeUntil(this.destroy$)).subscribe((direction: Direction) => {
      this.dir = direction;
    });
    this.initTheme();
  }

  private initTheme(): void {
    if (!this.platform.isBrowser) {
      return;
    }
    this.theme = localStorage.getItem(this.KEYS) || 'default';
    this.updateChartTheme();
    this.onThemeChange(this.theme);
  }

  private updateChartTheme(): void {
    this.configSrv.set('chart', { theme: this.theme === 'dark' ? 'dark' : '' });
  }

  onThemeChange(theme: string): void {
    if (!this.platform.isBrowser) {
      return;
    }
    this.theme = theme;
    this.themeChange.emit(theme);
    this.renderer.setAttribute(this.doc.body, 'data-theme', theme);
    const dom = this.doc.getElementById(this.KEYS);
    if (dom) {
      dom.remove();
    }
    localStorage.removeItem(this.KEYS);
    if (theme !== 'default') {
      const el = this.doc.createElement('link');
      el.type = 'text/css';
      el.rel = 'stylesheet';
      el.id = this.KEYS;
      el.href = `${this.deployUrl}assets/style.${theme}.css`;

      localStorage.setItem(this.KEYS, theme);
      this.doc.body.append(el);
    }
    this.updateChartTheme();
  }

  ngOnDestroy(): void {
    const el = this.doc.getElementById(this.KEYS);
    if (el != null) {
      this.doc.body.removeChild(el);
    }
    this.destroy$.next();
    this.destroy$.complete();
  }
}
