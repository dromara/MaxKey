import { Component, DebugElement, TemplateRef, ViewChild } from '@angular/core';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { NavigationCancel, NavigationError, RouteConfigLoadEnd, RouteConfigLoadStart } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { createTestContext } from '@delon/testing';
import { NzIconTestModule } from 'ng-zorro-antd/icon/testing';
import { NzMessageService } from 'ng-zorro-antd/message';

import { SettingsService } from '../src/services/settings/settings.service';
import { AlainThemeModule } from '../src/theme.module';
import { LayoutDefaultComponent } from './layout.component';
import { LayoutDefaultModule } from './layout.module';
import { LayoutDefaultOptions } from './types';

describe('theme: layout-default', () => {
  let fixture: ComponentFixture<TestComponent>;
  let dl: DebugElement;
  let context: TestComponent;
  let page: PageObject;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [LayoutDefaultModule, RouterTestingModule, NzIconTestModule, AlainThemeModule],
      declarations: [TestComponent]
    });

    ({ fixture, dl, context } = createTestContext(TestComponent));
    page = new PageObject();
  });

  it('should be custom nav', () => {
    context.nav = context.navTpl;
    fixture.detectChanges();
    page.expectEl('.custom-nav', true);
    page.expectEl('layout-default-nav', false);
  });

  it('should be custom aside user', () => {
    context.asideUser = context.asideUserTpl;
    fixture.detectChanges();
    page.expectEl('.custom-aside-user', true);
  });

  it('should be custom content', () => {
    context.content = context.contentTpl;
    fixture.detectChanges();
    page.expectEl('.custom-content', true);
  });

  it('should be toggle collapsed', () => {
    const srv = TestBed.inject(SettingsService);
    let collapsed = false;
    spyOnProperty(srv, 'layout').and.returnValue({ collapsed });
    fixture.detectChanges();
    const el = page.getEl('.alain-default__nav-item--collapse');
    expect(el.querySelector('.anticon-menu-fold') != null).toBe(true);
    collapsed = true;
    el.click();
    fixture.detectChanges();
    expect(el.querySelector('.anticon-menu-unfold') != null).toBe(true);
  });

  it('#colorWeak', () => {
    const srv = TestBed.inject(SettingsService);
    spyOnProperty(srv, 'layout').and.returnValue({ colorWeak: true });
    fixture.detectChanges();
    expect(document.body.classList).toContain(`color-weak`);
  });

  describe('#options', () => {
    it('#logoLink', () => {
      context.options = { logoLink: '/home' };
      fixture.detectChanges();
      const el = page.getEl<HTMLLinkElement>('.alain-default__header-logo-link');
      expect(el.href.endsWith('/home')).toBe(true);
    });

    it('#logoFixWidth', () => {
      context.options = { logoFixWidth: 100 };
      fixture.detectChanges();
      const el = page.getEl('.alain-default__header-logo');
      expect(el.style.width).toBe(`100px`);
    });

    it('#hideAside', () => {
      context.options = { hideAside: true };
      fixture.detectChanges();
      page.expectEl(`.alain-default__hide-aside`).expectEl(`.alain-default__nav-item--collapse`, false);
    });
  });

  describe('RTL', () => {
    it('should be toggle collapsed', () => {
      const srv = TestBed.inject(SettingsService);
      let collapsed = false;
      spyOnProperty(srv, 'layout').and.returnValue({ collapsed, direction: 'rtl' });
      fixture.detectChanges();
      const el = page.getEl('.alain-default__nav-item--collapse');
      expect(el.querySelector('.anticon-menu-unfold') != null).toBe(true);
      collapsed = true;
      el.click();
      fixture.detectChanges();
      expect(el.querySelector('.anticon-menu-fold') != null).toBe(true);
    });
  });

  describe('lazy load', () => {
    let msgSrv: NzMessageService;
    function lazyTick(): void {
      tick(101);
    }
    function lazyStart(): void {
      context.comp.processEv(new RouteConfigLoadStart({}));
      lazyTick();
    }

    function lazyError(): void {
      context.comp.processEv(new NavigationError(0, '/', {}));
      lazyTick();
    }

    function lazyCancel(reason: string = 'cancel'): void {
      context.comp.processEv(new NavigationCancel(0, '/', reason));
      lazyTick();
    }

    function lazyEnd(): void {
      context.comp.processEv(new RouteConfigLoadEnd({}));
      lazyTick();
    }

    beforeEach(fakeAsync(() => {
      lazyStart();
      msgSrv = TestBed.inject(NzMessageService);
    }));

    it('should toggle fetching status when load lzay config', fakeAsync(() => {
      expect(context.comp.isFetching).toBe(true);
      lazyEnd();
    }));

    describe('when error', () => {
      it('should be invalid module', fakeAsync(() => {
        const spy = spyOn(msgSrv, 'error');
        lazyError();
        expect(context.comp.isFetching).toBe(false);
        expect(spy).toHaveBeenCalled();
        expect(spy.calls.first().args[0]).toContain('Could not load ');
        lazyEnd();
      }));
      it('should be custom error', fakeAsync(() => {
        const spy = spyOn(msgSrv, 'error');
        context.customError = 'test';
        fixture.detectChanges();
        lazyError();
        expect(context.comp.isFetching).toBe(false);
        expect(spy).toHaveBeenCalled();
        expect(spy.calls.first().args[0]).toBe('test');
        lazyEnd();
      }));
      it('should be custom error is null', fakeAsync(() => {
        const spy = spyOn(msgSrv, 'error');
        context.customError = null;
        fixture.detectChanges();
        lazyError();
        expect(context.comp.isFetching).toBe(false);
        expect(spy).not.toHaveBeenCalled();
        lazyEnd();
      }));
      it('should be cancel load config', fakeAsync(() => {
        lazyCancel();
        expect(context.comp.isFetching).toBe(false);
        lazyEnd();
      }));
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
      [options]="options"
      [asideUser]="asideUser"
      [nav]="nav"
      [content]="content"
      [customError]="customError"
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
  `
})
class TestComponent {
  @ViewChild('comp', { static: true }) comp!: LayoutDefaultComponent;
  @ViewChild('asideUserTpl', { static: true }) asideUserTpl!: TemplateRef<void>;
  @ViewChild('navTpl', { static: true }) navTpl!: TemplateRef<void>;
  @ViewChild('contentTpl', { static: true }) contentTpl!: TemplateRef<void>;
  options: LayoutDefaultOptions = {};
  asideUser?: TemplateRef<void> | null;
  nav?: TemplateRef<void> | null;
  content?: TemplateRef<void> | null;
  customError?: string | null;
}
