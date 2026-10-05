import { Directionality } from '@angular/cdk/bidi';
import { Platform } from '@angular/cdk/platform';
import { DOCUMENT } from '@angular/common';
import {
  afterNextRender,
  ChangeDetectionStrategy,
  Component,
  inject,
  InjectionToken,
  input,
  isDevMode,
  OnDestroy,
  output,
  Renderer2
} from '@angular/core';

import { AlainConfigService } from '@delon/util/config';
import { NzDropdownDirective, NzDropdownMenuComponent } from 'ng-zorro-antd/dropdown';
import { NzMenuDirective, NzMenuItemComponent } from 'ng-zorro-antd/menu';
import { NzTooltipDirective } from 'ng-zorro-antd/tooltip';

export interface ThemeBtnType {
  key: string;
  text: string;
}

export const ALAIN_THEME_BTN_KEYS = new InjectionToken<string>('ALAIN_THEME_BTN_KEYS');

@Component({
  selector: 'theme-btn',
  template: `
    <div
      class="ant-avatar ant-avatar-circle ant-avatar-icon"
      nz-dropdown
      nzPlacement="topCenter"
      [nzDropdownMenu]="types().length > 0 ? menu : null"
    >
      <svg
        nz-tooltip
        [nzTooltipTitle]="isDev ? devTips() : null"
        class="anticon"
        role="img"
        width="21"
        height="21"
        viewBox="0 0 21 21"
        fill="currentColor"
      >
        <g fill-rule="evenodd">
          <g fill-rule="nonzero">
            <path
              d="M7.02 3.635l12.518 12.518a1.863 1.863 0 010 2.635l-1.317 1.318a1.863 1.863 0 01-2.635 0L3.068 7.588A2.795 2.795 0 117.02 3.635zm2.09 14.428a.932.932 0 110 1.864.932.932 0 010-1.864zm-.043-9.747L7.75 9.635l9.154 9.153 1.318-1.317-9.154-9.155zM3.52 12.473c.514 0 .931.417.931.931v.932h.932a.932.932 0 110 1.864h-.932v.931a.932.932 0 01-1.863 0l-.001-.931h-.93a.932.932 0 010-1.864h.93v-.932c0-.514.418-.931.933-.931zm15.374-3.727a1.398 1.398 0 110 2.795 1.398 1.398 0 010-2.795zM4.385 4.953a.932.932 0 000 1.317l2.046 2.047L7.75 7 5.703 4.953a.932.932 0 00-1.318 0zM14.701.36a.932.932 0 01.931.932v.931h.932a.932.932 0 010 1.864h-.933l.001.932a.932.932 0 11-1.863 0l-.001-.932h-.93a.932.932 0 110-1.864h.93v-.931a.932.932 0 01.933-.932z"
            />
          </g>
        </g>
      </svg>
      <nz-dropdown-menu #menu="nzDropdownMenu">
        <ul nz-menu nzSelectable>
          @for (i of types(); track $index) {
            <li nz-menu-item (click)="onThemeChange(i.key)">{{ i.text }}</li>
          }
        </ul>
      </nz-dropdown-menu>
    </div>
  `,
  host: {
    class: 'theme-btn',
    '[class.theme-btn-rtl]': `dir() === 'rtl'`
  },
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [NzDropdownDirective, NzDropdownMenuComponent, NzMenuDirective, NzMenuItemComponent, NzTooltipDirective]
})
export class ThemeBtnComponent implements OnDestroy {
  private readonly doc = inject(DOCUMENT);
  private readonly platform = inject(Platform);
  private readonly renderer = inject(Renderer2);
  private readonly configSrv = inject(AlainConfigService);
  protected dir = inject(Directionality).valueSignal;
  private key = inject(ALAIN_THEME_BTN_KEYS, { optional: true }) ?? 'site-theme';

  private theme = 'default';
  protected isDev = isDevMode();
  readonly types = input([
    { key: 'default', text: 'Default Theme' },
    { key: 'dark', text: 'Dark Theme' },
    { key: 'compact', text: 'Compact Theme' }
  ] as ThemeBtnType[]);
  readonly devTips = input(`When the dark.css file can't be found, you need to run it once: npm run theme`);
  readonly deployUrl = input('');
  readonly themeChange = output<string>();

  constructor() {
    afterNextRender(() => this.initTheme());
  }

  private initTheme(): void {
    if (!this.platform.isBrowser) {
      return;
    }
    this.theme = localStorage.getItem(this.key) ?? 'default';
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
    const dom = this.doc.getElementById(this.key);
    if (dom) {
      dom.remove();
    }
    localStorage.removeItem(this.key);
    if (theme !== 'default') {
      const el = this.doc.createElement('link');
      el.type = 'text/css';
      el.rel = 'stylesheet';
      el.id = this.key;
      el.href = `${this.deployUrl()}assets/style.${theme}.css`;

      localStorage.setItem(this.key, theme);
      this.doc.body.append(el);
    }
    this.updateChartTheme();
  }

  ngOnDestroy(): void {
    const el = this.doc.getElementById(this.key);
    if (el != null) {
      this.doc.body.removeChild(el);
    }
  }
}
