import { Component, DebugElement, signal, TemplateRef, ViewChild } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import {
  NavigationCancel,
  NavigationError,
  provideRouter,
  RouteConfigLoadEnd,
  RouteConfigLoadStart
} from '@angular/router';

import { createTestContext } from '@delon/testing';
import type { NzSafeAny } from 'ng-zorro-antd/core/types';
import { provideNzIconsTesting } from 'ng-zorro-antd/icon/testing';
import { NzMessageService } from 'ng-zorro-antd/message';

import { LayoutDefaultComponent } from './layout.component';
import { LayoutDefaultModule } from './layout.module';
import { LayoutDefaultService } from './layout.service';
import { LayoutDefaultOptions } from './types';
import { SettingsService } from '../src/services/settings/settings.service';
import { AlainThemeModule } from '../src/theme.module';

describe('theme: layout-default', () => {
  beforeEach(() => {
    vi.useFakeTimers();
  });
  let fixture: ComponentFixture<TestComponent>;
  let dl: DebugElement;
  let context: TestComponent;
  let page: PageObject;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideNzIconsTesting(), provideRouter([])],
      imports: [LayoutDefaultModule, AlainThemeModule, TestComponent]
    });

    ({ fixture, dl, context } = createTestContext(TestComponent));
    page = new PageObject();
  });

  it('should be custom nav', () => {
    context.nav.set(context.navTpl);
    fixture.detectChanges();
    page.expectEl('.custom-nav', true);
    page.expectEl('layout-default-nav', false);
  });

  it('should be custom aside user', () => {
    context.asideUser.set(context.asideUserTpl);
    fixture.detectChanges();
    page.expectEl('.custom-aside-user', true);
  });

  it('should be custom content', () => {
    context.content.set(context.contentTpl);
    fixture.detectChanges();
    page.expectEl('.custom-content', true);
  });

  it('should be toggle collapsed', () => {
    const srv = TestBed.inject(LayoutDefaultService);
    srv.toggleCollapsed(true);
    fixture.detectChanges();
    const el = page.getEl('.alain-default__nav-item--collapse');
    expect(el.querySelector('.anticon-menu-unfold') != null).toBe(true);
    srv.toggleCollapsed(false);
    fixture.detectChanges();
    expect(el.querySelector('.anticon-menu-fold') != null).toBe(true);
  });

  it('#colorWeak', () => {
    const srv = TestBed.inject(SettingsService);
    srv.setLayout('colorWeak', true);
    fixture.detectChanges();
    expect(document.body.classList).toContain(`color-weak`);
  });

  describe('#options', () => {
    it('#logoLink', () => {
      context.options.set({ logoLink: '/home' });
      fixture.detectChanges();
      const el = page.getEl<HTMLLinkElement>('.alain-default__header-logo-link');
      expect(el.href.endsWith('/home')).toBe(true);
    });

    it('#logoFixWidth', () => {
      context.options.set({ logoFixWidth: 100 });
      fixture.detectChanges();
      const el = page.getEl('.alain-default__header-logo');
      expect(el.style.width).toBe(`100px`);
    });

    it('#hideAside', () => {
      context.options.set({ hideAside: true });
      fixture.detectChanges();
      page.expectEl(`.alain-default__hide-aside`).expectEl(`.alain-default__nav-item--collapse`, false);
    });
  });

  describe('RTL', () => {
    it('should be toggle collapsed', () => {
      const settingSrv = TestBed.inject(SettingsService);
      const srv = TestBed.inject(LayoutDefaultService);
      settingSrv.setLayout('direction', 'rtl');
      srv.toggleCollapsed(true);
      fixture.detectChanges();
      const el = page.getEl('.alain-default__nav-item--collapse');
      expect(el.querySelector('.anticon-menu-fold') != null).toBe(true);
      el.click();
      fixture.detectChanges();
      expect(el.querySelector('.anticon-menu-unfold') != null).toBe(true);
    });
  });

  describe('lazy load', () => {
    let msgSrv: NzMessageService;
    async function lazyTick(): Promise<void> {
      await vi.advanceTimersByTimeAsync(101);
    }
    async function lazyStart(): Promise<void> {
      context.comp.processEv(new RouteConfigLoadStart({}));
      await lazyTick();
    }

    async function lazyError(): Promise<void> {
      context.comp.processEv(new NavigationError(0, '/', {}));
      await lazyTick();
    }

    async function lazyCancel(reason: string = 'cancel'): Promise<void> {
      context.comp.processEv(new NavigationCancel(0, '/', reason));
      await lazyTick();
    }

    async function lazyEnd(): Promise<void> {
      context.comp.processEv(new RouteConfigLoadEnd({}));
      await lazyTick();
    }

    beforeEach(async () => {
      await lazyStart();
      msgSrv = TestBed.inject(NzMessageService);
      await vi.runOnlyPendingTimersAsync();
    });

    it('should toggle fetching status when load lzay config', async () => {
      expect(context.comp.showFetching()).toBe(true);
      await lazyEnd();
    });

    describe('when error', () => {
      it('should be invalid module', async () => {
        const spy = vi.spyOn(msgSrv, 'error').mockReturnValue(undefined as NzSafeAny);
        await lazyError();
        expect(context.comp.showFetching()).toBe(false);
        expect(spy).toHaveBeenCalled();
        expect(vi.mocked(spy).mock.calls[0]![0]).toContain('Could not load ');
        await lazyEnd();
      });
      it('should be custom error', async () => {
        const spy = vi.spyOn(msgSrv, 'error').mockReturnValue(undefined as NzSafeAny);
        context.customError.set('test');
        fixture.detectChanges();
        await lazyError();
        expect(context.comp.showFetching()).toBe(false);
        expect(spy).toHaveBeenCalled();
        expect(vi.mocked(spy).mock.calls[0]![0]).toBe('test');
        await lazyEnd();
      });
      it('should be custom error is null', async () => {
        const spy = vi.spyOn(msgSrv, 'error').mockReturnValue(undefined as NzSafeAny);
        context.customError.set(null);
        fixture.detectChanges();
        await lazyError();
        expect(context.comp.showFetching()).toBe(false);
        expect(spy).not.toHaveBeenCalled();
        await lazyEnd();
      });
      it('should be cancel load config', async () => {
        await lazyCancel();
        expect(context.comp.showFetching()).toBe(false);
        await lazyEnd();
      });
    });

    it('#fetchingStrictly', () => {
      const cls = '.alain-default__progress-bar';
      context.fetchingStrictly.set(true);
      context.fetching.set(true);
      fixture.detectChanges();
      page.expectEl(cls, true);
      context.fetching.set(false);
      fixture.detectChanges();
      page.expectEl(cls, false);
    });
  });

  class PageObject {
    getEl<T extends HTMLElement>(cls: string): T {
      return dl.query(By.css(cls)).nativeElement as T;
    }

    expectEl(cls: string, result: boolean = true): this {
      expect(dl.queryAll(By.css(cls)).length > 0).toBe(result);
      return this;
    }
  }
});

@Component({
  template: `
    <layout-default
      #comp
      [options]="options()"
      [asideUser]="asideUser()"
      [nav]="nav()"
      [content]="content()"
      [customError]="customError()"
      [fetchingStrictly]="fetchingStrictly()"
      [fetching]="fetching()"
    >
      <layout-default-header-item direction="left">
        <span class="header-left">left</span>
      </layout-default-header-item>
      test
      <layout-default-header-item direction="right">
        <span class="header-right">right</span>
      </layout-default-header-item>
    </layout-default>
    <ng-template #asideUserTpl>
      <span class="custom-aside-user">custom-aside-user</span>
    </ng-template>
    <ng-template #navTpl>
      <span class="custom-nav">custom-nav</span>
    </ng-template>
    <ng-template #contentTpl>
      <span class="custom-content">custom-content</span>
    </ng-template>
  `,
  imports: [LayoutDefaultModule]
})
class TestComponent {
  @ViewChild('comp', { static: true }) comp!: LayoutDefaultComponent;
  @ViewChild('asideUserTpl', { static: true }) asideUserTpl!: TemplateRef<void>;
  @ViewChild('navTpl', { static: true }) navTpl!: TemplateRef<void>;
  @ViewChild('contentTpl', { static: true }) contentTpl!: TemplateRef<void>;
  readonly options = signal<LayoutDefaultOptions>({});
  readonly asideUser = signal<TemplateRef<void> | null | undefined>(undefined);
  readonly nav = signal<TemplateRef<void> | null | undefined>(undefined);
  readonly content = signal<TemplateRef<void> | null | undefined>(undefined);
  readonly customError = signal<string | null | undefined>(undefined);
  readonly fetchingStrictly = signal(false);
  readonly fetching = signal(false);
}
