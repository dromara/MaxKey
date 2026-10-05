---
type: Theme
order: 110
title: 空白布局
---

用于无须任何顶部和侧边区域，一般用于高定制性页面，诸如大屏幕数据等。默认布局所有参数都以 `@alain-blank-` 开头。

## 使用方式

在 `src/styles.less` 引入：

```less
@import '@delon/theme/layout-blank/style/index';
```

## 参数

| 名称 | 默认值 | 功能 |
| --- | --- | --- |
| `@prefix` | `.alain-blank` | 布局样式前缀 |
| `@bg` | `#f5f7fa` | 背景色 |
| `@content-padding-vertical` | `0` | 垂直内边距 |
| `@content-padding-horizontal` | `16px` | 水平内边距 |
