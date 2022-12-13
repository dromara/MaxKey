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
import { DOCUMENT } from '@angular/common';
import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Inject,
  Input,
  NgZone,
  OnDestroy,
  OnInit,
  Optional,
  Output,
  Renderer2,
  ViewEncapsulation
} from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { NavigationEnd, Router } from '@angular/router';
import { Menu, MenuIcon, MenuInner, MenuService, SettingsService } from '@delon/theme';
import { BooleanInput, InputBoolean, InputNumber, NumberInput, ZoneOutside } from '@delon/util/decorator';
import { WINDOW } from '@delon/util/token';
import type { NzSafeAny } from 'ng-zorro-antd/core/types';
import { Subject } from 'rxjs';
import { filter, takeUntil } from 'rxjs/operators';

import { LayoutDefaultOptions } from './types';

export interface Nav extends MenuInner {
  _needIcon?: boolean;
  _text?: SafeHtml;
}

const SHOWCLS = 'sidebar-nav__floating-show';
const FLOATINGCLS = 'sidebar-nav__floating';

@Component({
  selector: 'layout-default-nav',
  templateUrl: './layout-nav.component.html',
  host: {
    '(click)': '_click()',
    '(document:click)': 'closeSubMenu()'
  },
  preserveWhitespaces: false,
  changeDetection: ChangeDetectionStrategy.OnPush,
  encapsulation: ViewEncapsulation.None
})
export class LayoutDefaultNavComponent implements OnInit, OnDestroy {
  static ngAcceptInputType_disabledAcl: BooleanInput;
  static ngAcceptInputType_autoCloseUnderPad: BooleanInput;
  static ngAcceptInputType_recursivePath: BooleanInput;
  static ngAcceptInputType_openStrictly: BooleanInput;
  static ngAcceptInputType_maxLevelIcon: NumberInput;

  private bodyEl!: HTMLBodyElement;
  private destroy$ = new Subject<void>();
  private floatingEl!: HTMLDivElement;
  dir: Direction = 'ltr';
  list: Nav[] = [];

  @Input() @InputBoolean() disabledAcl = false;
  @Input() @InputBoolean() autoCloseUnderPad = true;
  @Input() @InputBoolean() recursivePath = true;
  @Input() @InputBoolean() openStrictly = false;
  @Input() @InputNumber() maxLevelIcon = 3;
  @Input() options!: LayoutDefaultOptions;
  @Output() readonly select = new EventEmitter<Menu>();

  get collapsed(): boolean {
    return this.settings.layout.collapsed;
  }

  constructor(
    private menuSrv: MenuService,
    private settings: SettingsService,
    private router: Router,
    private render: Renderer2,
    private cdr: ChangeDetectorRef,
    private ngZone: NgZone,
    private sanitizer: DomSanitizer,
    @Inject(DOCUMENT) private doc: NzSafeAny,
    @Inject(WINDOW) private win: NzSafeAny,
    @Optional() private directionality: Directionality
  ) {}

  private getLinkNode(node: HTMLElement): HTMLElement | null {
    node = node.nodeName === 'A' ? node : (node.parentNode as HTMLElement);
    return node.nodeName !== 'A' ? null : node;
  }

  private floatingClickHandle(e: MouseEvent): boolean {
    e.stopPropagation();
    const linkNode = this.getLinkNode(e.target as HTMLElement);
    if (linkNode == null) {
      return false;
    }
    const id = +linkNode.dataset!['id']!;
    // Should be ingore children title trigger event
    if (isNaN(id)) {
      return false;
    }

    let item: Nav;
    this.menuSrv.visit(this.list, (i: Nav) => {
      if (!item && i._id === id) {
        item = i;
      }
    });
    this.to(item!);
    this.hideAll();
    e.preventDefault();
    return false;
  }

  private clearFloating(): void {
    if (!this.floatingEl) return;
    this.floatingEl.removeEventListener('click', this.floatingClickHandle.bind(this));
    // fix ie: https://github.com/ng-alain/delon/issues/52
    if (this.floatingEl.hasOwnProperty('remove')) {
      this.floatingEl.remove();
    } else if (this.floatingEl.parentNode) {
      this.floatingEl.parentNode.removeChild(this.floatingEl);
    }
  }

  private genFloating(): void {
    this.clearFloating();
    this.floatingEl = this.render.createElement('div');
    this.floatingEl.classList.add(`${FLOATINGCLS}-container`);
    this.floatingEl.addEventListener('click', this.floatingClickHandle.bind(this), false);
    this.bodyEl.appendChild(this.floatingEl);
  }

  private genSubNode(linkNode: HTMLLinkElement, item: Nav): HTMLUListElement {
    const id = `_sidebar-nav-${item._id}`;
    const childNode = item.badge ? linkNode.nextElementSibling!.nextElementSibling! : linkNode.nextElementSibling!;
    const node = childNode.cloneNode(true) as HTMLUListElement;
    node.id = id;
    node.classList.add(FLOATINGCLS);
    node.addEventListener(
      'mouseleave',
      () => {
        node.classList.remove(SHOWCLS);
      },
      false
    );
    this.floatingEl.appendChild(node);
    return node;
  }

  private hideAll(): void {
    const allNode = this.floatingEl.querySelectorAll(`.${FLOATINGCLS}`);
    for (let i = 0; i < allNode.length; i++) {
      allNode[i].classList.remove(SHOWCLS);
    }
  }

  // calculate the node position values.
  private calPos(linkNode: HTMLLinkElement, node: HTMLUListElement): void {
    const rect = linkNode.getBoundingClientRect();
    // bug: https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/14721015/
    const scrollTop = Math.max(this.doc.documentElement.scrollTop, this.bodyEl.scrollTop);
    const docHeight = Math.max(this.doc.documentElement.clientHeight, this.bodyEl.clientHeight);
    const spacing = 5;
    let offsetHeight = -spacing;
    if (docHeight < rect.top + node.clientHeight) {
      offsetHeight = rect.top + node.clientHeight - docHeight + spacing;
    }
    node.style.top = `${rect.top + scrollTop - offsetHeight}px`;
    if (this.dir === 'rtl') {
      node.style.right = `${rect.width + spacing}px`;
    } else {
      node.style.left = `${rect.right + spacing}px`;
    }
  }

  @ZoneOutside()
  showSubMenu(e: MouseEvent, item: Nav): void {
    if (this.collapsed !== true) {
      return;
    }
    e.preventDefault();
    const linkNode = e.target as Element;
    this.genFloating();
    const subNode = this.genSubNode(linkNode as HTMLLinkElement, item);
    this.hideAll();
    subNode.classList.add(SHOWCLS);
    this.calPos(linkNode as HTMLLinkElement, subNode);
  }

  to(item: Menu): void {
    this.select.emit(item);
    if (item.disabled) return;

    if (item.externalLink) {
      if (item.target === '_blank') {
        this.win.open(item.externalLink);
      } else {
        this.win.location.href = item.externalLink;
      }
      return;
    }
    this.ngZone.run(() => this.router.navigateByUrl(item.link!));
  }

  toggleOpen(item: Nav): void {
    if (!this.openStrictly) {
      this.menuSrv.visit(this.list, (i: Nav) => {
        if (i !== item) i._open = false;
      });
      let pItem = item._parent as Nav;
      while (pItem) {
        pItem._open = true;
        pItem = pItem._parent!;
      }
    }
    item._open = !item._open;
    this.cdr.markForCheck();
  }

  _click(): void {
    if (this.isPad && this.collapsed) {
      this.openAside(false);
      this.hideAll();
    }
  }

  closeSubMenu(): void {
    if (this.collapsed) {
      this.hideAll();
    }
  }

  private openedByUrl(url: string | null): void {
    const { menuSrv, recursivePath, openStrictly } = this;
    let findItem: Nav | null = menuSrv.getHit(this.menuSrv.menus, url!, recursivePath, (i: Nav) => {
      i._selected = false;
      if (!openStrictly) {
        i._open = false;
      }
    });
    if (findItem == null) return;

    do {
      findItem._selected = true;
      if (!openStrictly) {
        findItem._open = true;
      }
      findItem = findItem._parent!;
    } while (findItem);
  }

  get collapsedIcon(): string {
    let type = this.collapsed ? 'unfold' : 'fold';
    if (this.settings.layout.direction === 'rtl') {
      type = this.collapsed ? 'fold' : 'unfold';
    }
    return `menu-${type}`;
  }

  toggleCollapsed(): void {
    this.settings.setLayout('collapsed', !this.settings.layout.collapsed);
  }

  ngOnInit(): void {
    const { doc, router, destroy$, menuSrv, settings, cdr } = this;
    this.bodyEl = doc.querySelector('body');
    this.openedByUrl(router.url);
    this.ngZone.runOutsideAngular(() => this.genFloating());
    menuSrv.change.pipe(takeUntil(destroy$)).subscribe(data => {
      menuSrv.visit(data, (i: Nav, _p, depth) => {
        i._text = this.sanitizer.bypassSecurityTrustHtml(i.text!);
        i._needIcon = depth! <= this.maxLevelIcon && !!i.icon;
        if (!i._aclResult) {
          if (this.disabledAcl) {
            i.disabled = true;
          } else {
            i._hidden = true;
          }
        }
        if (this.openStrictly) {
          i._open = i.open != null ? i.open : false;
        }
        const icon = i.icon as MenuIcon;
        if (icon && icon.type === 'svg' && typeof icon.value === 'string') {
          icon.value = this.sanitizer.bypassSecurityTrustHtml(icon.value!!);
        }
      });
      this.list = menuSrv.menus.filter((w: Nav) => w._hidden !== true);
      cdr.detectChanges();
    });
    router.events.pipe(takeUntil(destroy$)).subscribe(e => {
      if (e instanceof NavigationEnd) {
        this.openedByUrl(e.urlAfterRedirects);
        this.underPad();
        this.cdr.detectChanges();
      }
    });
    settings.notify
      .pipe(
        takeUntil(destroy$),
        filter(t => t.type === 'layout' && t.name === 'collapsed')
      )
      .subscribe(() => this.clearFloating());
    this.underPad();

    this.dir = this.directionality.value;
    this.directionality.change?.pipe(takeUntil(destroy$)).subscribe((direction: Direction) => {
      this.dir = direction;
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
    this.clearFloating();
  }

  // #region Under pad

  private get isPad(): boolean {
    return this.doc.defaultView!.innerWidth < 768;
  }

  private underPad(): void {
    if (this.autoCloseUnderPad && this.isPad && !this.collapsed) {
      setTimeout(() => this.openAside(true));
    }
  }

  private openAside(status: boolean): void {
    this.settings.setLayout('collapsed', status);
  }

  // #endregion
}
