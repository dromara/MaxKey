import { TemplateRef } from '@angular/core';

import type { NzSafeAny } from 'ng-zorro-antd/core/types';

export type LayoutDefaultHeaderItemHidden = 'pc' | 'mobile' | 'none';
export type LayoutDefaultHeaderItemDirection = 'left' | 'middle' | 'right';

export interface LayoutDefaultOptions {
  /**
   * Custom Logo Area
   *
   * 自定义 Logo 区域
   */
  logo?: TemplateRef<NzSafeAny> | null;
  /**
   * Logo url of expanded status, default: `./assets/logo-full.svg`
   *
   * 展开时 Logo 地址，默认：`./assets/logo-full.svg`
   */
  logoExpanded?: string;
  /**
   * Logo url of collapsed status, default: `./assets/logo.svg`
   *
   * 收缩时 Logo 地址，默认：`./assets/logo.svg`
   */
  logoCollapsed?: string;
  /**
   * Specify the logo routing address, default: `/`
   *
   * 指定 Logo 路由地址，默认：`/`
   */
  logoLink?: string;
  /**
   * Specify a fixed logo width
   *
   * 指定固定 Logo 宽度
   */
  logoFixWidth?: number;
  /**
   * Hide the sidebar without showing the collapsed icon button, default: `false`
   *
   * 隐藏侧边栏，同时不显收缩图标按钮，默认：`false`
   */
  hideAside?: boolean;

  /**
   * Hide top bar, default: `false`
   *
   * 隐藏顶栏，默认：`false`
   */
  hideHeader?: boolean;

  /**
   * Whether to display the menu collapse button on the top bar, default: `true`
   *
   * 是否在顶栏显示菜单折叠按钮，默认：`true`
   */
  showHeaderCollapse?: boolean;

  /**
   * Whether to show the menu collapse button at the bottom of the sidebar, default: `false`
   *
   * 是否在侧边栏底部显示菜单折叠按钮，默认：`false`
   */
  showSiderCollapse?: boolean;
}
