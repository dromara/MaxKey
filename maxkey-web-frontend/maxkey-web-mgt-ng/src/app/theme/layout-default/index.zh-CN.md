---
type: Theme
order: 100
title: 默认布局
---

默认布局所有参数都以 `@alain-default-` 开头。

## 使用方式

### 1、导入样式

在 `src/styles.less` 引入：

```less
@import '@delon/theme/layout-default/style/index';
```

### 2、使用 `layout-default` 组件

在 `src/app/layout/basic/basic.ts` 创建一个新的布局：

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
          <nz-icon nzType="github" />
        </a>
      </layout-default-header-item>
      <layout-default-header-item direction="left" hidden="pc">
        <div layout-default-header-item-trigger (click)="searchToggleStatus = !searchToggleStatus">
          <nz-icon nzType="search" />
        </div>
      </layout-default-header-item>
      <layout-default-header-item direction="middle">
        <header-search class="alain-default__search" [toggleChange]="searchToggleStatus" />
      </layout-default-header-item>
      <layout-default-header-item direction="right" hidden="mobile">
        <header-task />
      </layout-default-header-item>
      <ng-template #asideUserTpl>
        <div nz-dropdown nzTrigger="click" [nzDropdownMenu]="userMenu" class="alain-default__aside-user">
          <nz-avatar class="alain-default__aside-user-avatar" [nzSrc]="user.avatar" />
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
        <layout-default-nav class="d-block py-lg" />
      </ng-template>
      <ng-template #contentTpl>
        <router-outlet />
      </ng-template>
    </layout-default>

    @if (showSettingDrawer) {
      <setting-drawer />
    }
    <theme-btn />
  `,
})
export class LayoutBasic {
  readonly user = inject(SettingsService).user;
  protected options: LayoutDefaultOptions = {
    logoExpanded: `./assets/logo-full.svg`,
    logoCollapsed: `./assets/logo.svg`
  };
  protected searchToggleStatus = signal(false);
  protected showSettingDrawer = !environment.production;
}
```

通过 `LayoutDefaultService` 服务可以在运行时动态管理布局。除此之外，在布局的操作都可以通过 `SettingsService.notify` 来订阅布局的变化（例如：侧边栏的展开与收缩等），注意所有布局相关的变化都会通过这个接口，所以需要做好 `filter` 操作。

## API

### layout-default

| 成员 | 说明 | 类型 | 默认值 |
|----|----|----|-----|
| `[options]` | 选项 | `LayoutDefaultOptions` | `-` |
| `[asideUser]` | 侧边用户信息 | `TemplateRef<void>` | `-` |
| `[asideBottom]` | 侧边底部信息 | `TemplateRef<void>` | `-` |
| `[nav]` | 导航信息 | `TemplateRef<void>` | `-` |
| `[content]` | 内容信息 | `TemplateRef<void>` | `-` |
| `[customError]` | 自定义异常路由错误消息，当 `null` 时表示不显示错误消息 | `string, null` | `Could not load ${evt.url} route` |
| `[fetchingStrictly]` | 是否完全受控顶部加载动画状态 | `boolean` | `false` |
| `[fetching]` | 顶部加载动画状态 | `boolean` | `false` |

### LayoutDefaultOptions

| 成员 | 说明 | 类型 | 默认值 |
|----|----|----|-----|
| `[logo]` | 自定义 Logo 区域 | `TemplateRef<void>` | - |
| `[logoExpanded]` | 展开时 Logo 地址 | `string` | `./assets/logo-full.svg` |
| `[logoCollapsed]` | 收缩时 Logo 地址 | `string` | `./assets/logo.svg` |
| `[logoFixWidth]` | 指定固定 Logo 宽度 | `number` | - |
| `[logoLink]` | 指定 Logo 路由地址 | `string` | `/` |
| `[hideAside]` | 隐藏侧边栏，同时不显收缩图标按钮 | `boolean` | `false` |
| `[hideHeader]` | 隐藏顶栏 | `boolean` | `false` |
| `[showHeaderCollapse]` | 是否在顶栏显示菜单折叠按钮 | `boolean` | `true` |
| `[showSiderCollapse]` | 是否在侧边栏底部显示菜单折叠按钮 | `boolean` | `false` |

### layout-default-nav

| 成员 | 说明 | 类型 | 默认值 |
|----|----|----|-----|
| `[disabledAcl]` | `acl` 校验失败时以 `disabled` 状态显示 | `boolean` | `false` |
| `[autoCloseUnderPad]` | 小于Pad宽度时路由切换后自动关闭侧边栏 | `boolean` | `true` |
| `[recursivePath]` | 自动向上递归查找，菜单数据源包含 `/ware`，则 `/ware/1` 也视为 `/ware` 项 | `boolean` | `true` |
| `[hideEmptyChildren]` | 当所有子项都为隐藏时，是否也隐藏父级 | `boolean` | `true` |
| `[openStrictly]` | 展开完全受控，不再自动关闭已展开的项 | `boolean` | `false` |
| `[maxLevelIcon]` | Icon最多显示到第几层 | `number` | `3` |
| `(select)` | 点击菜单时回调（包含 `disabled`） | `EventEmitter<Menu>` | - |

> 组件的数据来自 `MenuService`（其结构为 [Menu](/theme/menu#Menu)）， `MenuService` 的操作会自动同步至该组件。

### layout-default-header-item

| 成员 | 说明 | 类型 | 默认值 |
|----|----|----|-----|
| `[hidden]` | 隐藏行为 | `pc, mobile, none` | `nones` |
| `[direction]` | 方向 | `left, middle, right` | `right` |

### layout-default-header-item-trigger

头部项的触发样式。

### layout-default-top-menu-item

头部业务菜单项，使用方式请参考 [layout.component.ts](https://github.com/ng-alain/delon/blob/master/src/dev/layout.component.ts#L65-L72)([预览](https://ng-alain.com/dev/home))。

## 布局说明

按上-左-右布局方式，运用于**业务页**的开发。其规范细节：

+ 顶部区域高度 `64px`（参数：`@header-hg`）
+ 侧边区域宽度 `200px`（参数：`@aside-wd`）
  + 当屏幕低于 `1140px` 宽时隐藏侧边导航
  + 当屏幕低于 `1140px` 宽时打开侧边导航为 `fixed` 状态
  + 主要包括一个 [layout-default-nav](/theme/layout-default/zh?#layout-default-nav) 组件

> 参数是指可以通过 `src/styles/theme.less` 文件按需要调整。

**顶部区域**

位置：*src/app/layout/base/widgets*。

脚手架默认提供了一些常规顶部区域组件，这些组件都存放于 *components* 目录中。同时 `@delon/abc` 也提供若干顶部组件（例如：[notice-icon](/components/notice-icon) 通知菜单组件）。你可以根据提供的组件自行组合或自行开发。

> 脚手架支持响应式布局，对于顶部区域可能会是在小屏幕下需要隐藏一些组件，因此你可以在对应的DOM节点上加上 `hidden-xs` 表示当屏幕小于 `768px` 时自动隐藏。

**侧边区域**

位置：*src/app/layout/default/sidebar*。

只包括一个用户信息和主菜单。主菜单是一个 [layout-default-nav](/theme/layout-default/zh?#layout-default-nav)。

**内部区域**

内容区域是业务页区域，规范细节：

+ 内容距离页面标准、侧边、右边滚动条、底部，这四边距依一个标准Dashboard的Gutter宽度 `24px`。

## 样式参数

| 名称 | 默认值 | 功能 |
|----|-----|----|
| `@alain-default-prefix` | `.alain-default` | 布局样式前缀 |
| `@alain-default-ease` | `cubic-bezier(.25, 0, .15, 1)` | 动画过滤函数 |
| `@alain-default-header-hg` | `64px` | 顶部高度 |
| `@alain-default-header-bg` | `@primary-color` | 顶部背景色 |
| `@alain-default-header-padding` | `@layout-gutter * 2` | 顶部左右内边距 |
| `@alain-default-header-search-enabled` | `true` | 是否开启顶部搜索框 |
| `@alain-default-header-icon-fs` | `18px` | 顶部 Icon 大小 |
| `@alain-default-header-logo-max-height` | `36px` | Logo 图最高高度 |
| `@alain-default-aside-wd` | `200px` | 侧边栏宽度 |
| `@alain-default-aside-bg` | `#fff` | 侧边栏背景色 |
| `@alain-default-aside-scrollbar-width` | `0` | 侧边栏滚动条宽度 |
| `@alain-default-aside-scrollbar-height` | `0` | 侧边栏滚动条高度 |
| `@alain-default-aside-scrollbar-track-color` | `transparent` | 侧边栏滚动条的轨道颜色 |
| `@alain-default-aside-scrollbar-thumb-color` | `transparent` | 侧边栏滚动条小方块颜色 |
| `@alain-default-aside-nav-fs` | `14px` | 侧边栏菜单字号 |
| `@alain-default-aside-nav-icon-width` | `14px` | 侧边栏菜单 ICON 宽度 |
| `@alain-default-aside-nav-img-wh` | `14px` | 侧边栏菜单图像宽高 |
| `@alain-default-aside-nav-padding-top-bottom` | `@layout-gutter` | 侧边栏菜单项上下内边距 |
| `@alain-default-aside-nav-padding-left-right` | `@layout-gutter * 2` | 侧边栏菜单项左右内边距 |
| `@alain-default-aside-nav-text-color` | `rgba(0, 0, 0, 0.65)` | 侧边栏菜单文本颜色 |
| `@alain-default-aside-nav-text-hover-color` | `#108ee9` | 侧边栏菜单文本悬停颜色 |
| `@alain-default-aside-nav-group-text-color` | `rgba(0, 0, 0, 0.43)` | 侧边栏菜单分组文本颜色 |
| `@alain-default-aside-nav-selected-text-color` | `#108ee9` | 侧边栏菜单激活时文本颜色 |
| `@alain-default-aside-nav-selected-bg` | `#fcfcfc` | 侧边栏菜单激活时背景颜色 |
| `@alain-default-aside-nav-divider-bg` | `#efe3e5` | 分隔线颜色 |
| `@alain-default-aside-collapsed-wd` | `@layout-gutter * 8` | 侧边栏收缩后宽度 |
| `@alain-default-aside-collapsed-nav-fs` | `24px` | 侧边栏收缩后文本字号 |
| `@alain-default-aside-collapsed-nav-img-wh` | `24px` | 侧边栏收缩后图像宽高 |
| `@alain-default-content-heading-bg` | `#fafbfc` | 内容区域标题背景色 |
| `@alain-default-content-heading-border` | `#efe3e5` | 内容区域标题底部边框色 |
| `@alain-default-content-padding` | `@layout-gutter * 3` | 内容区域内边距 |
| `@alain-default-content-bg` | `#f5f7fa` | 内容区域背景色 |
| `@alain-default-widget-app-icons-enabled` | `true` | 是否 app-icon 小部件样式 |
| `@alain-default-aside-user-enabled` | `true` | 是否侧边栏用户信息样式 |

## 常见问题

### 为什么会有两个快捷菜单

快捷菜单生成规则统一在 `0` 索引下查找，并按以下顺序来获取：

1. 【推荐】 children 存在 `shortcutRoot: true` 则最优先
2. 否则查找带有【dashboard】字样链接，若存在则在此菜单的下方创建快捷入口
3. 否则放在0节点位置

因此，建议在菜单数据的 `0` 索引下保持一个有效的 `shortcutRoot: true` 数据。

### 常见问题

**隐藏主菜单项**

表示永远不显示菜单，可以在菜单设置 `hide: true`。

**隐藏自动生成导航隐藏面包屑**

表示不显示该节点，可以在菜单设置 `hideInBreadcrumb: true`。

**关于层级**

虽然支持无限层级，但为了用户体验请保持最多不超过四层（含组别）。

**如何更新某个菜单项**

当调用 `MenuService.setItem(key, newValue)` 时会自动重新渲染主菜单，其中 `key` 必须是存在值，请参考 [Menu](/theme/menu#Menu) 的定义。

**如何控制菜单展开**

利用 `LayoutDefaultService.toggleCollapsed()` 来运行时手动控制。
