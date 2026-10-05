import { BreakpointObserver, BreakpointState } from '@angular/cdk/layout';
import { TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs';

import { AlainThemeModule, SettingsService } from '@delon/theme';

import { LayoutDefaultService } from './layout.service';

describe('theme: #LayoutDefaultService', () => {
  let srv: LayoutDefaultService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AlainThemeModule]
    });
    srv = TestBed.inject(LayoutDefaultService);
  });

  it('should be working', () => {
    srv.setOptions({ hideAside: true });
    expect(srv.options().hideAside).toBe(true);
  });

  it('#toggleCollapsed', () => {
    const settings = TestBed.inject(SettingsService);
    settings.setLayout('direction', 'ltr');
    srv.toggleCollapsed(true);
    expect(srv.collapsedIcon()).toBe('menu-unfold');
    srv.toggleCollapsed(false);
    expect(srv.collapsedIcon()).toBe('menu-fold');
    srv.toggleCollapsed();
    expect(srv.collapsedIcon()).toBe('menu-unfold');
    srv.toggleCollapsed();
    expect(srv.collapsedIcon()).toBe('menu-fold');
    // when is direction
    settings.setLayout('direction', 'rtl');
    srv.toggleCollapsed(true);
    expect(srv.collapsedIcon()).toBe('menu-fold');
    srv.toggleCollapsed(false);
    expect(srv.collapsedIcon()).toBe('menu-unfold');
  });

  it('should preserve collapsed state on page refresh on desktop #2630', () => {
    // Simulate page refresh: localStorage already has collapsed=true from user preference
    localStorage.setItem('layout', JSON.stringify({ collapsed: true, fixed: true, boxed: false, lang: null }));

    // Provide a mock BreakpointObserver that simulates desktop (isMatched returns false)
    TestBed.resetTestingModule();
    TestBed.configureTestingModule({
      imports: [AlainThemeModule],
      providers: [
        {
          provide: BreakpointObserver,
          useFactory: () => {
            const mock = {
              observe: vi.fn().mockName('BreakpointObserver.observe'),
              isMatched: vi.fn().mockName('BreakpointObserver.isMatched')
            };
            mock.isMatched.mockReturnValue(false);
            mock.observe.mockReturnValue(
              new Observable<BreakpointState>(subscriber => {
                subscriber.next({ matches: false, breakpoints: {} });
              })
            );
            return mock;
          }
        }
      ]
    });

    const settings = TestBed.inject(SettingsService);
    // Before LayoutDefaultService is created, settings should read collapsed=true from localStorage
    expect(settings.layout.collapsed).toBe(true);

    // LayoutDefaultService constructor calls checkMedia with isMatched=false (desktop)
    // It should NOT overwrite the persisted collapsed state
    TestBed.inject(LayoutDefaultService);
    expect(settings.layout.collapsed).toBe(true);
  });

  it('should be auto collapse on mobile', () => {
    TestBed.resetTestingModule();
    TestBed.configureTestingModule({
      imports: [AlainThemeModule],
      providers: [
        {
          provide: BreakpointObserver,
          useFactory: () => ({
            observe: vi
              .fn()
              .mockName('BreakpointObserver.observe')
              .mockReturnValue(
                new Observable<BreakpointState>(subscriber => {
                  subscriber.next({ matches: true, breakpoints: {} });
                })
              ),
            isMatched: vi.fn().mockName('BreakpointObserver.isMatched').mockReturnValue(true)
          })
        }
      ]
    });

    const settings = TestBed.inject(SettingsService);
    settings.setLayout('collapsed', false);
    TestBed.inject(LayoutDefaultService);
    expect(settings.layout.collapsed).toBe(true);
  });
});
