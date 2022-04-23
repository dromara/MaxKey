export type LayoutDefaultHeaderItemHidden = 'pc' | 'mobile' | 'none';
export type LayoutDefaultHeaderItemDirection = 'left' | 'middle' | 'right';

export interface LayoutDefaultOptions {
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

  title?: String;
}
