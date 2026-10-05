import { DOCUMENT } from '@angular/common';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { Component, DebugElement, signal, Type, ViewChild } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Router, RouterModule, provideRouter } from '@angular/router';

import { ACLService } from '@delon/acl';
import { AlainThemeModule, MenuIcon, MenuService, SettingsService } from '@delon/theme';
import { deepCopy } from '@delon/util/other';
import { WINDOW } from '@delon/util/token';
import { provideNzNoAnimation } from 'ng-zorro-antd/core/animation';
import type { NzSafeAny } from 'ng-zorro-antd/core/types';

import { LayoutDefaultNavComponent, Nav } from './layout-nav.component';
import { LayoutDefaultModule } from './layout.module';

const floatingShowCls = '.sidebar-nav__floating-show';
const MOCKMENUS = [
  {
    text: '主导航',
    group: true,
    children: [
      {
        text: '仪表盘',
        children: [
          { text: 'v1', link: '/v1' },
          { text: 'v2', link: '#/v2', i18n: 'v2-i18n' },
          { text: 'v3' },
          {
            text: 'externalLink-blank',
            externalLink: '//ng-alain.com/blank',
            target: '_blank'
          },
          {
            text: 'externalLink-top',
            externalLink: '//ng-alain.com/top',
            target: '_top'
          }
        ]
      },
      {
        text: 'widgets',
        disabled: true
      }
    ]
  }
] as Nav[];

const MOCKOPENSTRICTLY = [
  {
    text: '',
    group: true,
    children: [
      {
        text: '',
        link: '/v1',
        open: true,
        children: [{ text: '' }]
      },
      {
        text: '',
        link: '/v1',
        open: true,
        children: [{ text: '' }]
      }
    ]
  }
] as Nav[];

class MockACLService {
  can(val: string): boolean {
    return val === 'admin';
  }
}

class MockWindow {
  location = new MockLocation();
  open(): void {}
}
class MockLocation {
  private url!: string;
  get href(): string {
    return this.url;
  }
  set href(url: string) {
    this.url = url;
  }
}

describe('theme: layout-default-nav', () => {
  beforeEach(() => {
    vi.useFakeTimers();
  });
  let fixture: ComponentFixture<TestComponent>;
  let dl: DebugElement;
  let context: TestComponent;
  let router: Router;
  let setSrv: SettingsService;
  let menuSrv: MenuService;
  let page: PageObject;
  let doc: Document;

  function createModule(): void {
    TestBed.configureTestingModule({
      imports: [RouterModule.forRoot([]), AlainThemeModule, LayoutDefaultModule, TestComponent],
      providers: [
        provideNzNoAnimation(),
        provideHttpClient(),
        provideHttpClientTesting(),
        { provide: ACLService, useClass: MockACLService },
        { provide: WINDOW, useFactory: () => new MockWindow() }
      ]
    });
  }

  function createComp(
    needMockNavigateByUrl: boolean = true,
    callback?: () => void,
    host: Type<unknown> = TestComponent
  ): void {
    fixture = TestBed.createComponent(host) as ComponentFixture<TestComponent>;
    dl = fixture.debugElement;
    context = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject<Router>(Router);
    setSrv = TestBed.inject<SettingsService>(SettingsService);
    menuSrv = TestBed.inject<MenuService>(MenuService);
    doc = TestBed.inject(DOCUMENT);
    menuSrv.add(deepCopy(MOCKMENUS));
    page = new PageObject();
    if (needMockNavigateByUrl) vi.spyOn(router, 'navigateByUrl').mockReturnValue(undefined as NzSafeAny);
    if (callback) callback();
  }

  describe('', () => {
    beforeEach(() => createModule());

    describe('[default]', () => {
      it('should be navigate url', () => {
        createComp();
        vi.spyOn(context, 'select').mockReturnValue(undefined);
        const data = deepCopy(MOCKMENUS);
        menuSrv.add(data);
        fixture.detectChanges();
        expect(context.select).not.toHaveBeenCalled();
        expect(router.navigateByUrl).not.toHaveBeenCalled();
        const itemEl = page.getEl<HTMLElement>('.sidebar-nav__depth1 a');
        itemEl!.click();
        fixture.detectChanges();
        expect(context.select).toHaveBeenCalled();
        expect(router.navigateByUrl).toHaveBeenCalled();
      });

      describe('should be navigate external link', () => {
        it('with target is _blank', () => {
          createComp();
          fixture.detectChanges();
          const win = TestBed.inject(WINDOW);
          vi.spyOn(win, 'open').mockReturnValue(undefined as NzSafeAny);
          const itemEl = page.getEl<HTMLElement>('.sidebar-nav__item [data-id="6"]');
          itemEl!.click();
          expect(win.open).toHaveBeenCalled();
        });
        it('with target is _top', () => {
          createComp();
          fixture.detectChanges();
          const win = TestBed.inject(WINDOW);
          const itemEl = page.getEl<HTMLElement>('.sidebar-nav__item [data-id="7"]');
          itemEl!.click();
          expect(win.location.href).toBe(`//ng-alain.com/top`);
        });
      });

      it('should be hide group name', () => {
        createComp();
        fixture.detectChanges();
        page.checkCount('.sidebar-nav__group-title');
        const data = deepCopy(MOCKMENUS) as Nav[];
        data[0].group = false;
        menuSrv.add(data);
        fixture.detectChanges();
        page.checkCount('.sidebar-nav__group-title', 0);
      });

      it('should be toggle open', () => {
        createComp();
        const data = deepCopy(MOCKMENUS);
        menuSrv.add(data);
        fixture.detectChanges();
        expect(data[0].children![0].open).toBe(false);
        const subTitleEl = page.getEl<HTMLElement>('.sidebar-nav__item-link');
        subTitleEl!.click();
        fixture.detectChanges();
        expect(data[0].children![0].open).toBe(true);
      });

      it('should be reset menu when service is changed', () => {
        createComp();
        fixture.detectChanges();
        page.checkText('.sidebar-nav__group-title', MOCKMENUS[0].text);
        const newMenu = deepCopy(MOCKMENUS);
        newMenu[0].text = 'new主导航';
        menuSrv.add(newMenu);
        fixture.detectChanges();
        page.checkText('.sidebar-nav__group-title', newMenu[0].text);
      });

      it('should be block click menu when is disabled', () => {
        createComp();
        vi.spyOn(context, 'select').mockReturnValue(undefined);
        const newMenus = [
          {
            text: '',
            children: [{ text: 'new menu', disabled: true }]
          }
        ];
        menuSrv.add(newMenus);
        expect(context.select).not.toHaveBeenCalled();
        fixture.detectChanges();
        const itemEl = page.getEl<HTMLElement>('.sidebar-nav__item-disabled');
        itemEl!.click();
        fixture.detectChanges();
        expect(context.select).toHaveBeenCalled();
      });

      it('should be support html in text or i18n', () => {
        createComp();
        menuSrv.add([{ text: 'text <strong>1</strong>' }]);
        fixture.detectChanges();
        page.checkText('.sidebar-nav__item', `text 1`);
        menuSrv.add([{ i18n: 'i18n <strong>1</strong>' }]);
        fixture.detectChanges();
        page.checkText('.sidebar-nav__item', `i18n 1`);
      });

      describe('#hideEmptyChildren', () => {
        it('with true', () => {
          createComp();
          menuSrv.add([
            {
              text: 'parent',
              children: [
                { text: 'l1', hide: true },
                { text: 'l2', hide: false }
              ]
            }
          ]);
          fixture.detectChanges();
          page.checkCount('.sidebar-nav__group-title', 1);
          menuSrv.add([
            {
              text: 'parent',
              children: [
                { text: 'l1', hide: true },
                { text: 'l2', hide: true }
              ]
            }
          ]);
          fixture.detectChanges();
          page.checkCount('.sidebar-nav__group-title', 0);
        });
        it('with false', () => {
          createComp();
          context.hideEmptyChildren.set(false);
          fixture.detectChanges();
          menuSrv.add([
            {
              text: 'parent',
              children: [
                { text: 'l1', hide: true },
                { text: 'l2', hide: false }
              ]
            }
          ]);
          fixture.detectChanges();
          page.checkCount('.sidebar-nav__group-title', 1);
          menuSrv.add([
            {
              text: 'parent',
              children: [
                { text: 'l1', hide: true },
                { text: 'l2', hide: true }
              ]
            }
          ]);
          fixture.detectChanges();
          page.checkCount('.sidebar-nav__group-title', 1);
        });
      });
    });

    describe('#icon', () => {
      function updateIcon(icon: string | MenuIcon): void {
        createComp();

        menuSrv.add([
          {
            text: '',
            group: true,
            children: [
              {
                text: '',
                icon
              }
            ]
          }
        ] as Nav[]);

        fixture.detectChanges();
      }
      describe('with icon', () => {
        it('when is string and includes [anticon-]', () => {
          updateIcon('anticon-edit');
          const el = page.getEl('.sidebar-nav__item-icon') as HTMLElement;
          expect(el.classList).toContain('anticon-edit');
        });
        it('when is string and http prefix', () => {
          updateIcon('http://ng-alain/1.jpg');
          page.checkCount('.sidebar-nav__item-img', 1);
        });
        it('when is class string', () => {
          updateIcon('demo-class');
          page.checkCount('.demo-class', 1);
        });
      });
      it('with className', () => {
        updateIcon({ type: 'class', value: 'demo-class' });
        page.checkCount('.demo-class', 1);
      });
      it('with img', () => {
        updateIcon({ type: 'img', value: '1.jpg' });
        page.checkCount('.sidebar-nav__item-img', 1);
      });
      it('with svg', () => {
        updateIcon({ type: 'svg', value: '<svg></svg>' });
        page.checkCount('.sidebar-nav__item-svg', 1);
      });
    });

    describe('[collapsed]', () => {
      describe('#default', () => {
        beforeEach(() => {
          createComp();
          setSrv.layout.collapsed = true;
          fixture.detectChanges();
        });
        it(`should be won't show sub-menu when not collapse`, () => {
          setSrv.layout.collapsed = false;
          fixture.detectChanges();
          page.showSubMenu(false);
        });
        it('should be show sub-menu', () => {
          page.showSubMenu();
        });
        it('should be displayed full submenu', () => {
          const clientHeight = vi.spyOn(doc.documentElement, 'clientHeight', 'get').mockReturnValue(0);
          vi.spyOn(doc.querySelector('body')!, 'clientHeight', 'get').mockReturnValue(0);
          expect(clientHeight).not.toHaveBeenCalled();
          page.showSubMenu();
          expect(clientHeight).toHaveBeenCalled();
        });
        it('should be working when include badge', () => {
          const mockMenu = deepCopy(MOCKMENUS) as Nav[];
          mockMenu[0].children![0].badge = 1;
          menuSrv.add(mockMenu);
          fixture.detectChanges();
          expect(page.getEl('.ant-badge') != null).toBe(true);
          page.showSubMenu();
          expect(page.getEl('.sidebar-nav__floating-container .sidebar-nav__item', true) != null).toBe(true);
        });
        it('should be working when include badgeOverflowCount', () => {
          const mockMenu = deepCopy(MOCKMENUS) as Nav[];
          mockMenu[0].children![0].badge = 100;
          mockMenu[0].children![0].badgeOverflowCount = 99;
          menuSrv.add(mockMenu);
          fixture.detectChanges();
          expect(page.getEl<HTMLElement>('.ant-badge-count')!.childNodes.item(1).nodeValue == ' 99+ ').toBe(true);
        });
        it('should be ingore children title trigger event', () => {
          vi.spyOn(context, 'select').mockReturnValue(undefined);
          expect(context.select).not.toHaveBeenCalled();
          const mockMenu = deepCopy(MOCKMENUS) as Nav[];
          mockMenu[0].children![0].children = [{ text: 'a', children: [{ text: 'b' }] }];
          menuSrv.add(mockMenu);
          fixture.detectChanges();
          page.showSubMenu();
          const containerEl = page.getEl<HTMLElement>(floatingShowCls, true)!;
          (containerEl.querySelector('.sidebar-nav__item-link') as HTMLElement).click();
          expect(context.select).not.toHaveBeenCalled();
        });
      });
      describe('should be hide sub-menu in floating container', () => {
        it('muse be hide via click menu link', () => {
          createComp();
          setSrv.layout.collapsed = true;
          fixture.detectChanges();
          page.showSubMenu();
          page.hideSubMenu();
          expect(router.navigateByUrl).toHaveBeenCalled();
        });
        it('muse be hide via mouse leave area', () => {
          createComp();
          setSrv.layout.collapsed = true;
          fixture.detectChanges();
          page.showSubMenu();
          page.getEl<HTMLElement>(floatingShowCls, true)!.dispatchEvent(new Event('mouseleave'));
          fixture.detectChanges();
          expect(page.getEl<HTMLElement>(floatingShowCls, true)).toBeNull();
        });
        it('muse be not hide via click except menu link area', () => {
          createComp();
          setSrv.layout.collapsed = true;
          fixture.detectChanges();
          page.showSubMenu();
          const containerEl = page.getEl<HTMLElement>(floatingShowCls, true);
          containerEl!.querySelectorAll('li')[1].click();
          fixture.detectChanges();
          expect(router.navigateByUrl).not.toHaveBeenCalled();
        });
        it('muse be hide via click span of menu item', () => {
          createComp();
          setSrv.layout.collapsed = true;
          fixture.detectChanges();
          page.showSubMenu();
          const containerEl = page.getEl<HTMLElement>(floatingShowCls, true);
          containerEl!.querySelectorAll('span')[1].click();
          fixture.detectChanges();
          expect(router.navigateByUrl).toHaveBeenCalled();
        });
        it('muse be hide via document click', () => {
          createComp();
          setSrv.layout.collapsed = true;
          fixture.detectChanges();
          page.showSubMenu();
          document.dispatchEvent(new MouseEvent('click'));
          fixture.detectChanges();
          expect(router.navigateByUrl).not.toHaveBeenCalled();
        });
        it('muse be hide when move to other item', () => {
          createComp();
          setSrv.layout.collapsed = true;
          fixture.detectChanges();
          page.showSubMenu();
          expect(page.isShowSubMenu()).toBe(true);
          const widgetEl = page.getEl<HTMLElement>('.sidebar-nav__item-disabled', true);
          widgetEl!.dispatchEvent(new Event('mouseenter'));
          fixture.detectChanges();
          expect(page.isShowSubMenu()).toBe(false);
        });
      });
      it('#52', () => {
        createComp();
        setSrv.layout.collapsed = true;
        fixture.detectChanges();
        page.showSubMenu();
        vi.spyOn(context.comp['floatingEl'], 'remove').mockReturnValue(undefined);
        page.hideSubMenu();
        expect(page.getEl<HTMLElement>(floatingShowCls, true)).toBeNull();
      });
    });

    describe('#disabledAcl', () => {
      const newMenus = [
        {
          text: '',
          children: [
            { text: 'new menu', acl: 'admin' },
            { text: 'new menu', acl: 'user' }
          ]
        }
      ];
      beforeEach(() => createComp());
      it('should be disabled item when with true', () => {
        context.disabledAcl.set(true);
        fixture.detectChanges();
        menuSrv.add(newMenus);
        fixture.detectChanges();
        const itemEl = page.getEl<HTMLElement>('.sidebar-nav__item [data-id="3"]');
        expect(itemEl!.classList).toContain('sidebar-nav__item-disabled');
      });
      it('should be hidden item when with false', () => {
        context.disabledAcl.set(false);
        fixture.detectChanges();
        menuSrv.add(newMenus);
        fixture.detectChanges();
        const itemEl = page.getEl<HTMLElement>('.sidebar-nav__item [data-id="3"]');
        expect(itemEl == null).toBe(true);
      });
    });

    describe('#openStrictly', () => {
      beforeEach(() => {
        createComp();
        context.openStrictly.set(true);
        fixture.detectChanges();
        menuSrv.add(deepCopy(MOCKOPENSTRICTLY));
        fixture.detectChanges();
      });
      it('should working', () => {
        page.checkCount('.sidebar-nav__open', 2);
      });
      it(`should be won't close other item`, () => {
        const list = dl.queryAll(By.css('.sidebar-nav__item-link'));
        expect(list.length).toBe(4);
        (list[0].nativeElement as HTMLElement).click();
        fixture.detectChanges();
        page.checkCount('.sidebar-nav__open', 1);
        (list[2].nativeElement as HTMLElement).click();
        fixture.detectChanges();
        page.checkCount('.sidebar-nav__open', 0);
      });
    });

    describe('#render_type', () => {
      it('should be render divider', () => {
        createComp();
        menuSrv.add([
          {
            text: '主导航',
            group: true,
            children: [
              {
                text: '仪表盘',
                children: [{ text: 'v1', link: '/v1' }, { render_type: 'divider' }, { text: 'v2', link: '/v2' }]
              },
              { render_type: 'divider' },
              {
                text: 'widgets',
                disabled: true
              }
            ]
          }
        ]);
        fixture.detectChanges();
        page.checkCount('.sidebar-nav__divider', 2);
      });
    });
  });

  describe('[underPad]', () => {
    beforeEach(createModule);

    it('should be auto collapsed when less than pad', async () => {
      const defaultCollapsed = false;
      createComp(
        false,
        () => {
          vi.spyOn(window, 'innerWidth', 'get').mockReturnValue(767);
          setSrv.layout.collapsed = defaultCollapsed;
          fixture.detectChanges();
        },
        UnderPadHostComponent
      );
      router.navigateByUrl('/');
      fixture.detectChanges();
      await vi.advanceTimersByTimeAsync(20);
      expect(setSrv.layout.collapsed).toBe(!defaultCollapsed);
    });
    it(`should be won't collapsed when more than pad`, async () => {
      const defaultCollapsed = false;
      createComp(
        false,
        () => {
          vi.spyOn(window, 'innerWidth', 'get').mockReturnValue(769);
          setSrv.layout.collapsed = defaultCollapsed;
          fixture.detectChanges();
        },
        UnderPadHostComponent
      );
      router.navigateByUrl('/');
      fixture.detectChanges();
      await vi.advanceTimersByTimeAsync(1000);
      expect(setSrv.layout.collapsed).toBe(defaultCollapsed);
    });
    it('should be auto expaned when less than pad trigger click', async () => {
      createComp(true, undefined, UnderPadHostComponent);
      setSrv.layout.collapsed = true;
      fixture.detectChanges();
      vi.spyOn(window, 'innerWidth', 'get').mockReturnValue(767);
      expect(setSrv.layout.collapsed).toBe(true);
      page.getEl<HTMLElement>('.sidebar-nav')!.click();
      fixture.detectChanges();
      await vi.advanceTimersByTimeAsync(20);
      expect(setSrv.layout.collapsed).toBe(false);
    });
  });

  describe('should be recursive path', () => {
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RouterModule.forRoot([]), AlainThemeModule, LayoutDefaultModule, TestComponent],
        providers: [
          provideRouter([
            { path: 'user', component: TestRouteComponent },
            { path: 'user2', component: TestRouteComponent },
            { path: 'user/type', component: TestRouteComponent }
          ])
        ],
        declarations: [TestRouteComponent]
      });
    });
    beforeEach(async () => {
      fixture = TestBed.createComponent(TestComponent);
      dl = fixture.debugElement;
      context = fixture.componentInstance;
      menuSrv = TestBed.inject<MenuService>(MenuService);
      fixture.detectChanges();
      createComp(false);
      menuSrv.add([
        {
          text: '主导航',
          group: true,
          children: [
            { text: 'user1', link: '/user' },
            { text: 'user2', link: '/user' }
          ]
        }
      ]);
      fixture.detectChanges();
      await vi.runOnlyPendingTimersAsync();
    });
    it('with true', async () => {
      context.recursivePath.set(true);
      fixture.detectChanges();
      router.navigateByUrl('/user2');
      await vi.advanceTimersByTimeAsync(0);
      fixture.detectChanges();
      page.checkCount('.sidebar-nav__selected', 0);
      router.navigateByUrl('/user/type');
      await vi.advanceTimersByTimeAsync(0);
      fixture.detectChanges();
      page.checkCount('.sidebar-nav__selected', 1);
    });
    it('with false', async () => {
      context.recursivePath.set(false);
      fixture.detectChanges();
      router.navigateByUrl('/user2');
      await vi.advanceTimersByTimeAsync(0);
      fixture.detectChanges();
      page.checkCount('.sidebar-nav__selected', 0);
      router.navigateByUrl('/user/type');
      await vi.advanceTimersByTimeAsync(0);
      fixture.detectChanges();
      page.checkCount('.sidebar-nav__selected', 0);
    });
    it('should be ingore _open when enabled openStrictly', async () => {
      context.openStrictly.set(true);
      fixture.detectChanges();
      menuSrv.add(deepCopy(MOCKOPENSTRICTLY));
      fixture.detectChanges();
      page.checkCount('.sidebar-nav__open', 2);
      router.navigateByUrl('/user2');
      fixture.detectChanges();
      page.checkCount('.sidebar-nav__open', 2);
    });
  });

  class PageObject {
    getEl<T>(cls: string, body: boolean = false): T | null {
      const el = body
        ? document.querySelector(cls)
        : dl.query(By.css(cls))
          ? dl.query(By.css(cls)).nativeElement
          : null;
      return el ? (el as T) : null;
    }
    checkText(cls: string, value: NzSafeAny): void {
      const el = this.getEl<HTMLElement>(cls);
      expect(el ? el.innerText.trim() : '').toBe(value);
    }
    checkCount(cls: string, count: number = 1): this {
      expect(dl.queryAll(By.css(cls)).length).toBe(count);
      return this;
    }
    /** 期望显示子菜单，默认：`true` */
    showSubMenu(resultExpectShow: boolean = true): void {
      let conEl = this.getEl<HTMLElement>(floatingShowCls, true);
      expect(conEl).toBeNull();
      const subTitleEl = this.getEl<HTMLElement>('.sidebar-nav__item-link');
      subTitleEl!.dispatchEvent(new Event('mouseenter'));
      fixture.detectChanges();
      conEl = this.getEl<HTMLElement>(floatingShowCls, true);
      if (resultExpectShow) {
        expect(conEl).not.toBeNull();
      } else {
        expect(conEl).toBeNull();
      }
    }
    /** 期望隐藏子菜单，默认：`true` */
    hideSubMenu(resultExpectHide: boolean = true): void {
      const containerEl = this.getEl<HTMLElement>(floatingShowCls, true);
      expect(containerEl).not.toBeNull();
      containerEl!.querySelector(resultExpectHide ? 'a' : 'li')!.click();
      fixture.detectChanges();
      const conEl = this.getEl<HTMLElement>(floatingShowCls, true);
      if (resultExpectHide) expect(conEl).toBeNull();
      else expect(conEl).not.toBeNull();
    }
    isShowSubMenu(): boolean {
      return page.getEl(floatingShowCls, true) != null;
    }
  }
});

@Component({
  template: `
    <layout-default-nav
      #comp
      [disabledAcl]="disabledAcl()"
      [autoCloseUnderPad]="autoCloseUnderPad"
      [recursivePath]="recursivePath()"
      [hideEmptyChildren]="hideEmptyChildren()"
      [openStrictly]="openStrictly()"
      (select)="select()"
    />
  `,
  imports: [LayoutDefaultModule]
})
class TestComponent {
  @ViewChild('comp', { static: true })
  comp!: LayoutDefaultNavComponent;
  readonly disabledAcl = signal(false);
  autoCloseUnderPad = false;
  readonly recursivePath = signal(false);
  readonly hideEmptyChildren = signal(true);
  readonly openStrictly = signal(false);
  select(): void {}
}

@Component({
  template: ``,
  // eslint-disable-next-line @angular-eslint/prefer-standalone
  standalone: false
})
class TestRouteComponent {}

/** 覆盖模板的宿主必须是实体组件：AOT 产物上的 overrideTemplate 拿不到 imports */
@Component({
  template: `<layout-default-nav #comp [autoCloseUnderPad]="true" />`,
  imports: [LayoutDefaultModule]
})
class UnderPadHostComponent {}
