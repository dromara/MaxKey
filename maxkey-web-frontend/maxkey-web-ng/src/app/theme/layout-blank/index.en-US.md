---
type: Theme
order: 110
title: Blank Layout
---

Used for any top and side areas, typically for highly customizable pages such as large screen data. The blank layout all parameters are prefixed with `@alain-blank-`.

## Usage

Import in `src/styles.less`:

```less
@import '@delon/theme/layout-blank/style/index';
```

## Parameters

| Name | Default | Description |
| --- | --- | --- |
| `@prefix` | `.alain-blank` | Style name prefix |
| `@bg` | `#f5f7fa` | Background color |
| `@content-padding-vertical` | `0` | Vertical padding |
| `@content-padding-horizontal` | `16px` | Horizontal padding |
