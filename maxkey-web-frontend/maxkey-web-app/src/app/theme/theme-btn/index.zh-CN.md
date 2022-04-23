---
type: Theme
title: theme-btn
subtitle: 组件-切换样式
cols: 1
order: 1001
module: import { ThemeBtnModule } from '@delon/theme/theme-btn';
---

用于在运行过程中切换定制样式文件，从而起到换在线换肤功能。

## API

### layout-default

| 成员 | 说明 | 类型 | 默认值 |
|----|----|----|-----|
| `[types]` | 类型列表 | `ThemeBtnType[]` | `[ { key: 'default', text: 'Default Theme' }, { key: 'dark', text: 'Dark Theme' }, { key: 'compact', text: 'Compact Theme' }, ]` |
| `[devTips]` | 开发提示 | `String` | `When the dark.css file can't be found, you need to run it once: npm run theme` |
| `[deployUrl]` | 文件将部署到的 URL，一般到使用 `ng b --deploy-url` 时需要 | `String` | `-` |
| `(themeChange)` | 主题变更通知 | `EventEmitter<string>` | `-` |
