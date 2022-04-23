---
type: Theme
order: 100
title: Default Layout
---

The default layout all parameters are prefixed with `@alain-default-`.

## Usage

### 1. Style import

Import in `src/styles.less`:

```less
@import '@delon/theme/layout-default/style/index';
```

### 2. Using `layout-default` component

Creat a new layout in `src/app/layout/basic/basic.component.ts`:

```ts
import { Component } from '@angular/core';
import { SettingsService, User } from '@delon/theme';
import { LayoutDefaultOptions } from '@delon/theme/layout-default';
import { environment } from '@env/environment';

@Component({
  selector: 'layout-basic',
  template: `
    <layout-default [options]="options" [asideUser]="asideUserTpl" [nav]="navTpl" [content]="contentTpl">
      <layout-default-header-item direction="left">
        <a layout-default-header-item-trigger href="//github.com/ng-alain/ng-alain" target="_blank">
          <i nz-icon nzType="github"></i>
        </a>
      </layout-default-header-item>
      <layout-default-header-item direction="left" hidden="pc">
        <div layout-default-header-item-trigger (click)="searchToggleStatus = !searchToggleStatus">
          <i nz-icon nzType="search"></i>
        </div>
      </layout-default-header-item>
      <layout-default-header-item direction="middle">
        <header-search class="alain-default__search" [toggleChange]="searchToggleStatus"></header-search>
      </layout-default-header-item>
      <layout-default-header-item direction="right" hidden="mobile">
        <header-task></header-task>
      </layout-default-header-item>
      <ng-template #asideUserTpl>
        <div nz-dropdown nzTrigger="click" [nzDropdownMenu]="userMenu" class="alain-default__aside-user">
          <nz-avatar class="alain-default__aside-user-avatar" [nzSrc]="user.avatar"></nz-avatar>
          <div class="alain-default__aside-user-info">
            <strong>{{ user.name }}</strong>
            <p class="mb0">{{ user.email }}</p>
          </div>
        </div>
        <nz-dropdown-menu #userMenu="nzDropdownMenu">
          <ul nz-menu>
            <li nz-menu-item routerLink="/pro/account/center">{{ 'menu.account.center' | i18n }}</li>
            <li nz-menu-item routerLink="/pro/account/settings">{{ 'menu.account.settings' | i18n }}</li>
          </ul>
        </nz-dropdown-menu>
      </ng-template>
      <ng-template #navTpl>
        <layout-default-nav class="d-block py-lg"></layout-default-nav>
      </ng-template>
      <ng-template #contentTpl>
        <router-outlet></router-outlet>
      </ng-template>
    </layout-default>

    <setting-drawer *ngIf="showSettingDrawer"></setting-drawer>
    <theme-btn></theme-btn>
  `,
})
export class LayoutBasicComponent {
  options: LayoutDefaultOptions = {
    logoExpanded: `./assets/logo-full.svg`,
    logoCollapsed: `./assets/logo.svg`,
  };
  searchToggleStatus = false;
  showSettingDrawer = !environment.production;
  get user(): User {
    return this.settings.user;
  }

  constructor(private settings: SettingsService) {}
}
```

In addition, in layout operations, you can subscribe to layout changes through `SettingsService.notify` (for example: sidebar show and hide, etc.). Note that all layout-related changes will pass through this interface, so you need to do `filter` operation.

## API

### layout-default

| Property | Description | Type | Default |
|----------|-------------|------|---------|
| `[options]` | Options of the layout | `LayoutDefaultOptions` | `-` |
| `[asideUser]` | Side user of the layout | `TemplateRef<void>` | `-` |
| `[nav]` | Nav | `TemplateRef<void>` | `-` |
| `[content]` | Content | `TemplateRef<void>` | `-` |
| `[customError]` | Custom exception routing error message, can't show when is `null` | `string, null` | `Could not load ${evt.url} route` |

### LayoutDefaultOptions

| Property | Description | Type | Default |
|----|----|----|-----|
| `[logoExpanded]` | Logo url of expanded status | `string` | `./assets/logo-full.svg` |
| `[logoCollapsed]` | Logo url of collapsed status | `string` | `./assets/logo.svg` |
| `[logoFixWidth]` | Specify a fixed logo width | `number` | - |
| `[logoLink]` | Specify the logo routing address | `string` | `/` |
| `[hideAside]` | Hide the sidebar without showing the collapsed icon button | `boolean` | `false` |

### layout-default-nav

| Property | Description | Type | Default |
|----------|-------------|------|---------|
| `[disabledAcl]` | Displayed `disabled` state when `acl` check fails. | `boolean` | `false` |
| `[autoCloseUnderPad]` | When the route width is less than the Pad width, the sidebar is automatically closed. | `boolean` | `true` |
| `[recursivePath]` | Automatic up recursive lookup, menu data source contains `/ware`, then `/ware/1` is also treated as `/ware` | `boolean` | `true` |
| `[openStrictly]` | Precise check open status, does not auto closed other open item | `boolean` | `false` |
| `[maxLevelIcon]` | Icon displays up to which level | `number` | `3` |
| `(select)` | Callback when clicking menu (including `disabled`) | `EventEmitter<Menu>` | - |

> The component data comes from `MenuService` (which is structured as [Menu](/theme/menu#Menu)), and the operation of `MenuService` is auto synchronized to the component.

### layout-default-header-item

| Property | Description | Type | Default |
|----------|-------------|------|---------|
| `[hidden]` | Hidden behavior of the header item | `pc, mobile, none` | `nones` |
| `[direction]` | Direction of the header item | `left, middle, right` | `right` |

### layout-default-header-item-trigger

The trigger style of the head item.

## Layout description

In the upper-left-right layout mode, it is applied to the development of the **business page**. Its specification details:

+ Top area height `64px`（parameter：`@header-hg`）
+ Side area width `200px`（parameter：`@aside-wd`）
+ Hide side navigation when the screen is below `1140px` wide
+ Turn the side navigation to the `fixed` state when the screen is below `1140px` wide
+ Mainly includes a [layout-default-nav](/theme/layout-default/en?#layout-default-nav) component

> Parameters are adjustable as needed by the `src/styles/theme.less` file.

**Top area**

location：*src/app/layout/base/header*

Scaffolding provides some regular top-level components by default, which are stored in the *components* directory. At the same time `@delon/abc` also provides several top components (eg：[notice-icon](/components/notice-icon) Notification menu component. You can build it yourself or develop it yourself based on the components provided.

> Scaffolding supports responsive layout. For the top area, you may need to hide some components under the small screen, so you can add `hidden-xs` to the corresponding DOM node to automatically hide when the screen is smaller than `768px`.

**Side area**

location：*src/app/layout/default/sidebar*

Only one user information and main menu are included. The main menu is a [layout-default-nav](/theme/layout-default/en?#layout-default-nav) component.

**Internal area**

The content area is the business page area, the specification details：

+ Content distance page standard, side, right scroll bar, bottom, this margin is based on a standard Dashboard Gutter width `24px`.

## Less Parameters

| Name | Default | Description |
|------|---------|-------------|
| `@alain-default-prefix` | `.alain-default` | Style name prefix |
| `@alain-default-ease` | `cubic-bezier(.25, 0, .15, 1)` | Animation filter function |
| `@alain-default-header-hg` | `64px` | Height of header |
| `@alain-default-header-bg` | `@primary-color` | Background-color of header |
| `@alain-default-header-padding` | `@layout-gutter * 2` | Horizontal padding of header |
| `@alain-default-header-search-enabled` | `true` | Whether top search |
| `@alain-default-header-icon-fs` | `18px` | Font size of icon |
| `@alain-default-header-logo-max-height` | `36px` | Max height of logo |
| `@alain-default-aside-wd` | `200px` | Width of aside |
| `@alain-default-aside-bg` | `#fff` | Background-color of aside |
| `@alain-default-aside-scrollbar-width` | `0` | Scrollbar width of aside |
| `@alain-default-aside-scrollbar-height` | `0` | Scrollbar height of aside |
| `@alain-default-aside-scrollbar-track-color` | `transparent` | Scrollbar track color of aside |
| `@alain-default-aside-scrollbar-thumb-color` | `transparent` | Scrollbar thumb color of aside |
| `@alain-default-aside-nav-fs` | `14px` | Font size of nav name |
| `@alain-default-aside-nav-icon-width` | `14px` | Width of nav icon |
| `@alain-default-aside-nav-img-wh` | `14px` | Width & height of nav image |
| `@alain-default-aside-nav-padding-top-bottom` | `@layout-gutter` | Vertical padding of nav |
| `@alain-default-aside-nav-item-height` | `38px` | Item height of nav |
| `@alain-default-aside-nav-text-color` | `rgba(0, 0, 0, 0.65)` | Nav text color |
| `@alain-default-aside-nav-text-hover-color` | `#108ee9` | Nav text hover color |
| `@alain-default-aside-nav-group-text-color` | `rgba(0, 0, 0, 0.43)` | Group text color |
| `@alain-default-aside-nav-selected-text-color` | `#108ee9` | Nav selected text color |
| `@alain-default-aside-nav-selected-bg` | `#fcfcfc` | Nav selected background color |
| `@alain-default-aside-collapsed-wd` | `@layout-gutter * 8` | Width of aside collapsed |
| `@alain-default-aside-collapsed-nav-fs` | `24px` | Font size of aside collapsed |
| `@alain-default-aside-collapsed-nav-img-wh` | `24px` | Width & height nav image of aside collapsed |
| `@alain-default-content-heading-bg` | `#fafbfc` | Heading background color of content area |
| `@alain-default-content-heading-border` | `#efe3e5` | Heading bottom border color of content area |
| `@alain-default-content-padding` | `@layout-gutter * 3` | Padding of content area |
| `@alain-default-content-bg` | `#f5f7fa` | Background color of content area |
| `@alain-default-widget-app-icons-enabled` | `true` | Whether the app-icon widget styles |
| `@alain-default-aside-user-enabled` | `true` | Whether the user styles of aside |

## FAQ

### Why are there two shortcut menus?

The shortcut menu generation rules are uniformly searched under the `0` index，and get in the following order:

1. [Recommended] children have `shortcutRoot: true` which is the highest priority
2. Otherwise, find the link with the word [dashboard], if it exists, create a shortcut entry below the menu.
3. Otherwise placed at the 0 node position

Therefore, it's recommended to keep a valid `shortcutRoot: true` data under the `0` index of the menu data.

### FAQ

**Hide main menu item**

You can set `hide: true` in the menu.

**Hide auto-generated navigation hide breadcrumbs**

You can set `hideInBreadcrumb: true` in the menu.

**About level**

Although unlimited levels are supported, please keep no more than four levels (including groups) for user experience.

**How to update a menu item**

The menu will be re-rendered via calling `MenuService.setItem(key, newValue)`, please refer to the definition of [Menu](/theme/menu#Menu).

