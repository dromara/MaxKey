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

import { DOCUMENT } from '@angular/common';
import { Component, ContentChildren, ElementRef, Inject, Input, OnDestroy, OnInit, QueryList, Renderer2, TemplateRef } from '@angular/core';
import { NavigationCancel, NavigationEnd, NavigationError, RouteConfigLoadEnd, RouteConfigLoadStart, Router, Event } from '@angular/router';
import { SettingsService } from '@delon/theme';
import { updateHostClass } from '@delon/util/browser';
import type { NzSafeAny } from 'ng-zorro-antd/core/types';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { LayoutDefaultHeaderItemComponent } from './layout-header-item.component';
import { LayoutDefaultOptions } from './types';

@Component({
  selector: 'layout-default',
  exportAs: 'layoutDefault',
  template: `
    <div class="alain-default__progress-bar" *ngIf="isFetching"></div>
    <layout-default-header [options]="options" [items]="headerItems"></layout-default-header>
    <!--menu-->
    <div id="mainMenu" nzTheme="dark" style="background: #001529;" class="alain-default__fixed">
      <!--<ng-container *ngTemplateOutlet="asideUser"></ng-container>-->
      <ng-container *ngTemplateOutlet="nav"></ng-container>
      <layout-default-nav *ngIf="!nav" class="d-block" style="margin-top: 64px; "></layout-default-nav>
    </div>

    <section class="alain-default__content">
      <ng-container *ngTemplateOutlet="content"></ng-container>
      <ng-content></ng-content>
    </section>
  `
})
export class LayoutDefaultComponent implements OnInit, OnDestroy {
  @ContentChildren(LayoutDefaultHeaderItemComponent, { descendants: false })
  headerItems!: QueryList<LayoutDefaultHeaderItemComponent>;

  @Input() options!: LayoutDefaultOptions;
  @Input() asideUser: TemplateRef<void> | null = null;
  @Input() nav: TemplateRef<void> | null = null;
  @Input() content: TemplateRef<void> | null = null;
  @Input() customError?: string | null;

  private destroy$ = new Subject<void>();
  isFetching = false;

  constructor(
    router: Router,
    private msgSrv: NzMessageService,
    private settings: SettingsService,
    private el: ElementRef,
    private renderer: Renderer2,
    @Inject(DOCUMENT) private doc: NzSafeAny
  ) {
    router.events.pipe(takeUntil(this.destroy$)).subscribe(ev => this.processEv(ev));
  }

  processEv(ev: Event): void {
    if (!this.isFetching && ev instanceof RouteConfigLoadStart) {
      this.isFetching = true;
    }
    if (ev instanceof NavigationError || ev instanceof NavigationCancel) {
      this.isFetching = false;
      const err = this.customError === null ? null : this.customError ?? `Could not load ${ev.url} route`;
      if (err && ev instanceof NavigationError) {
        this.msgSrv.error(err, { nzDuration: 1000 * 3 });
      }
      return;
    }
    if (!(ev instanceof NavigationEnd || ev instanceof RouteConfigLoadEnd)) {
      return;
    }
    if (this.isFetching) {
      setTimeout(() => {
        this.isFetching = false;
      }, 100);
    }
  }

  private setClass(): void {
    const { el, doc, renderer, settings } = this;
    const layout = settings.layout;
    updateHostClass(el.nativeElement, renderer, {
      ['alain-default']: true,
      [`alain-default__fixed`]: layout['fixed'],
      [`alain-default__collapsed`]: layout.collapsed,
      [`alain-default__hide-aside`]: this.options!.hideAside
    });

    doc.body.classList[layout.colorWeak ? 'add' : 'remove']('color-weak');
  }

  ngOnInit(): void {
    this.options = {
      logoExpanded: `./assets/logo-full.svg`,
      logoCollapsed: `./assets/logo.svg`,
      logoLink: `/`,
      hideAside: false,
      ...this.options
    };
    const { settings, destroy$ } = this;
    settings.notify.pipe(takeUntil(destroy$)).subscribe(() => this.setClass());
    this.setClass();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
