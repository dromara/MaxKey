import { DOCUMENT, NgTemplateOutlet } from '@angular/common';
import {
  Component,
  TemplateRef,
  booleanAttribute,
  computed,
  contentChildren,
  effect,
  inject,
  input,
  signal
} from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import {
  NavigationCancel,
  NavigationEnd,
  NavigationError,
  RouteConfigLoadEnd,
  RouteConfigLoadStart,
  Router,
  Event
} from '@angular/router';
import { filter } from 'rxjs';

import { SettingsService } from '@delon/theme';
import type { NzSafeAny } from 'ng-zorro-antd/core/types';
import { NzIconDirective } from 'ng-zorro-antd/icon';
import { NzMessageService } from 'ng-zorro-antd/message';

import { LayoutDefaultHeaderItemComponent } from './layout-header-item.component';
import { LayoutDefaultHeaderComponent } from './layout-header.component';
import { LayoutDefaultNavComponent } from './layout-nav.component';
import { LayoutDefaultService } from './layout.service';
import { LayoutDefaultOptions } from './types';

@Component({
  selector: 'layout-default',
  exportAs: 'layoutDefault',
  template: `
    @let _opt = opt();
    @if (showFetching()) {
      <div class="alain-default__progress-bar"></div>
    }
    @if (!_opt.hideHeader) {
      <layout-default-header [items]="headerItems()" />
    }
    @if (!_opt.hideAside) {
      <div class="alain-default__aside">
        <div class="alain-default__aside-wrap">
          <div class="alain-default__aside-inner">
            <ng-container *ngTemplateOutlet="asideUser()" />
            <ng-container *ngTemplateOutlet="nav()" />
            @if (!nav()) {
              <layout-default-nav />
            }
          </div>
          @if (_opt.showSiderCollapse) {
            <div class="alain-default__aside-link">
              @if (asideBottom()) {
                <ng-container *ngTemplateOutlet="asideBottom()" />
              } @else {
                <div class="alain-default__aside-link-collapsed" (click)="srv.toggleCollapsed()">
                  <nz-icon [nzType]="srv.collapsedIcon()" />
                </div>
              }
            </div>
          }
        </div>
      </div>
    }
    <section class="alain-default__content">
      <ng-container *ngTemplateOutlet="content()" />
      <ng-content />
    </section>
  `,
  host: {
    class: 'alain-default',
    '[class.alain-default__fixed]': 'layout().fixed',
    '[class.alain-default__collapsed]': 'layout().collapsed',
    '[class.alain-default__hide-aside]': 'opt().hideAside',
    '[class.alain-default__hide-header]': 'opt().hideHeader'
  },
  imports: [NgTemplateOutlet, LayoutDefaultHeaderComponent, LayoutDefaultNavComponent, NzIconDirective]
})
export class LayoutDefaultComponent {
  private readonly router = inject(Router);
  private readonly msgSrv = inject(NzMessageService);
  private readonly settings = inject(SettingsService);
  private readonly doc = inject(DOCUMENT);
  protected readonly srv = inject(LayoutDefaultService);

  protected readonly headerItems = contentChildren(LayoutDefaultHeaderItemComponent, { descendants: false });

  protected opt = this.srv.options;
  protected layout = this.settings.layoutSignal;

  readonly options = input<LayoutDefaultOptions>();
  readonly asideUser = input<TemplateRef<void> | null>();
  readonly asideBottom = input<TemplateRef<NzSafeAny> | null>();
  readonly nav = input<TemplateRef<void> | null>();
  readonly content = input<TemplateRef<void> | null>();
  readonly customError = input<string | null>();
  readonly fetchingStrictly = input(false, { transform: booleanAttribute });
  readonly fetching = input(false, { transform: booleanAttribute });

  private isFetching = signal(false);
  readonly showFetching = computed(() => {
    if (this.fetchingStrictly()) return this.fetching();
    return this.isFetching();
  });

  constructor() {
    this.router.events
      .pipe(
        takeUntilDestroyed(),
        filter(() => !this.fetchingStrictly())
      )
      .subscribe(ev => this.processEv(ev));

    effect(() => {
      const opt = this.options();
      this.srv.setOptions(opt);
    });

    effect(() => {
      this.doc.body.classList[this.layout().colorWeak ? 'add' : 'remove']('color-weak');
    });
  }

  processEv(ev: Event): void {
    if (!this.isFetching() && ev instanceof RouteConfigLoadStart) {
      this.isFetching.set(true);
    }
    if (ev instanceof NavigationError || ev instanceof NavigationCancel) {
      this.isFetching.set(false);
      const customError = this.customError();
      const err = customError === null ? null : (customError ?? `Could not load ${ev.url} route`);
      if (err && ev instanceof NavigationError) {
        this.msgSrv.error(err, { nzDuration: 1000 * 3 });
      }
      return;
    }
    if (!(ev instanceof NavigationEnd || ev instanceof RouteConfigLoadEnd)) {
      return;
    }
    if (this.isFetching()) {
      setTimeout(() => {
        this.isFetching.set(false);
      }, 100);
    }
  }
}
