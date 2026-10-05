import { BreakpointObserver } from '@angular/cdk/layout';
import { Injectable, inject, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { filter, map } from 'rxjs';

import { SettingsService } from '@delon/theme';

import { LayoutDefaultOptions } from './types';

const DEFAULT: LayoutDefaultOptions = {
  logoExpanded: `./assets/logo-full.svg`,
  logoCollapsed: `./assets/logo.svg`,
  logoLink: `/`,
  showHeaderCollapse: true,
  showSiderCollapse: false,
  hideAside: false,
  hideHeader: false
};

@Injectable({ providedIn: 'root' })
export class LayoutDefaultService {
  private readonly settings = inject(SettingsService);
  private readonly bm = inject(BreakpointObserver);
  readonly options = signal(DEFAULT);
  readonly collapsedIcon = signal('');

  constructor() {
    const mobileMedia = 'only screen and (max-width: 767.99px)';
    this.bm
      .observe(mobileMedia)
      .pipe(takeUntilDestroyed())
      .subscribe(state => this.checkMedia(state.matches));
    this.checkMedia(this.bm.isMatched(mobileMedia));

    const settings = this.settings;
    settings.notify
      .pipe(
        filter(w => w.type === 'layout'),
        map(() => {
          const collapsed = settings.layout.collapsed;
          const ret =
            settings.layout.direction === 'rtl' ? (collapsed ? 'fold' : 'unfold') : collapsed ? 'unfold' : 'fold';
          return ret;
        }),
        takeUntilDestroyed()
      )
      .subscribe(type => this.collapsedIcon.set(`menu-${type}`));
  }

  private checkMedia(value: boolean): void {
    // 仅当匹配媒体（移动端）时自动折叠侧边栏
    // 不匹配时（桌面端）不应覆盖用户已持久化的 collapsed 偏好
    if (value) {
      this.settings.setLayout('collapsed', true);
    }
  }

  /**
   * Set layout configuration
   *
   * 设置布局配置
   */
  setOptions(options?: LayoutDefaultOptions | null): void {
    this.options.set({
      ...DEFAULT,
      ...options
    });
  }

  /**
   * Toggle the collapsed state of the sidebar menu bar
   *
   * 切换侧边栏菜单栏折叠状态
   */
  toggleCollapsed(status?: boolean): void {
    this.settings.setLayout('collapsed', status != null ? status : !this.settings.layout.collapsed);
  }
}
