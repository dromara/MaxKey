import { Directionality } from '@angular/cdk/bidi';
import { DOCUMENT, NgTemplateOutlet } from '@angular/common';
import {
  ChangeDetectionStrategy,
  Component,
  OnDestroy,
  Renderer2,
  ViewEncapsulation,
  afterNextRender,
  booleanAttribute,
  effect,
  inject,
  input,
  numberAttribute,
  output,
  signal
} from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { NavigationEnd, Router } from '@angular/router';
import { Menu, MenuIcon, MenuInner, MenuService, SettingsService } from '@delon/theme';
import { WINDOW } from '@delon/util/token';
import { NzBadgeComponent } from 'ng-zorro-antd/badge';
import { NzIconDirective } from 'ng-zorro-antd/icon';
import { NzTooltipDirective } from 'ng-zorro-antd/tooltip';
import { filter } from 'rxjs';

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
    class: 'd-block',
    '(click)': '_click()',
    '(document:click)': 'closeSubMenu()'
  },
  imports: [NgTemplateOutlet, NzIconDirective, NzTooltipDirective, NzBadgeComponent],
  changeDetection: ChangeDetectionStrategy.OnPush,
  encapsulation: ViewEncapsulation.None
})
export class LayoutDefaultNavComponent implements OnDestroy {
  private readonly doc = inject(DOCUMENT);
  private readonly win = inject(WINDOW);
  private readonly router = inject(Router);
  private readonly render = inject(Renderer2);
  private readonly menuSrv = inject(MenuService);
  private readonly settings = inject(SettingsService);
  private readonly sanitizer = inject(DomSanitizer);

  private bodyEl = this.doc.querySelector<HTMLBodyElement>('body')!;
  private floatingEl!: HTMLDivElement;
  dir = inject(Directionality).valueSignal;
  list = signal<Nav[]>([]);

  readonly disabledAcl = input(false, { transform: booleanAttribute });
  readonly autoCloseUnderPad = input(true, { transform: booleanAttribute });
  readonly recursivePath = input(true, { transform: booleanAttribute });
  readonly hideEmptyChildren = input(true, { transform: booleanAttribute });
  readonly openStrictly = input<boolean>();
  readonly maxLevelIcon = input(3, { transform: numberAttribute });
  readonly select = output<Menu>();

  get collapsed(): boolean {
    return this.settings.layout.collapsed;
  }

  constructor() {
    effect(() => {
      const openStrictly = this.openStrictly();
      if (openStrictly != null) {
        this.menuSrv.openStrictly = openStrictly;
      }
    });

    this.menuSrv.change.pipe(takeUntilDestroyed()).subscribe(data => {
      this.menuSrv.visit(data, (i: Nav, _p, depth) => {
        i._text = this.sanitizer.bypassSecurityTrustHtml(i.text!);
        i._needIcon = depth! <= this.maxLevelIcon() && !!i.icon;
        if (!i._aclResult) {
          if (this.disabledAcl()) {
            i.disabled = true;
          } else {
            i._hidden = true;
          }
        }
        const icon = i.icon as MenuIcon;
        if (icon && icon.type === 'svg' && typeof icon.value === 'string') {
          icon.value = this.sanitizer.bypassSecurityTrustHtml(icon.value!);
        }
      });
      if (this.hideEmptyChildren()) this.fixHide(data);
      this.list.set(data.filter((w: Nav) => w._hidden !== true));
    });
    this.router.events.pipe(takeUntilDestroyed()).subscribe(e => {
      if (e instanceof NavigationEnd) {
        this.openByUrl(e.urlAfterRedirects);
        this.underPad();
      }
    });
    this.settings.notify
      .pipe(
        takeUntilDestroyed(),
        filter(t => t.type === 'layout' && t.name === 'collapsed')
      )
      .subscribe(() => this.clearFloating());

    afterNextRender(() => {
      this.underPad();
      this.openByUrl(this.router.url);
      this.genFloating();
    });
  }

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
    //maxkey
    const id = +linkNode.dataset!['id']!;
    // Should be ingore children title trigger event
    if (isNaN(id)) {
      return false;
    }

    let item: Nav;
    this.menuSrv.visit(this.list(), (i: Nav) => {
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
    this.floatingEl.parentNode?.removeChild(this.floatingEl);
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
    allNode.forEach(node => node.classList.remove(SHOWCLS));
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
    if (this.dir() === 'rtl') {
      node.style.right = `${rect.width + spacing}px`;
    } else {
      node.style.left = `${rect.right + spacing}px`;
    }
  }

  protected showSubMenu(e: MouseEvent, item: Nav): void {
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

  protected to(item: Menu): void {
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
    this.router.navigateByUrl(item.link!);
  }

  protected toggleOpen(item: Nav): void {
    this.menuSrv.toggleOpen(item);
  }

  toggleCollapsed(): void {
    this.settings.setLayout('collapsed', !this.settings.layout.collapsed);
  }

  protected _click(): void {
    if (this.isPad && this.collapsed) {
      this.openAside(false);
      this.hideAll();
    }
  }

  protected closeSubMenu(): void {
    if (this.collapsed) {
      this.hideAll();
    }
  }

  private openByUrl(url: string | null): void {
    const { menuSrv, recursivePath } = this;
    this.menuSrv.open(menuSrv.find({ url, recursive: recursivePath() }));
  }

  private fixHide(ls: Nav[]): void {
    const inFn = (list: Nav[]): void => {
      for (const item of list) {
        if (item.children && item.children.length > 0) {
          inFn(item.children);
          if (!item._hidden) {
            item._hidden = item.children.every((v: Nav) => v._hidden);
          }
        }
      }
    };

    inFn(ls);
  }

  // #region Under pad

  private get isPad(): boolean {
    return this.doc.defaultView!.innerWidth < 768;
  }

  private underPad(): void {
    if (this.autoCloseUnderPad() && this.isPad && !this.collapsed) {
      setTimeout(() => this.openAside(true));
    }
  }

  private openAside(status: boolean): void {
    this.settings.setLayout('collapsed', status);
  }

  // #endregion

  ngOnDestroy(): void {
    this.clearFloating();
  }
}
