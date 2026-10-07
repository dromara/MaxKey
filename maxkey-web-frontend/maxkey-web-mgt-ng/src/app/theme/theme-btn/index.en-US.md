---
type: Theme
title: theme-btn
subtitle: Component-Change Style
cols: 1
order: 1001
module: import { ThemeBtnModule } from '@delon/theme/theme-btn';
---

It is used to switch the customized style file during the running process, so as to play the online skin change function.

## API

### layout-default

| Property | Description | Type | Default |
|----------|-------------|------|---------|
| `[types]` | Type of theme list | `ThemeBtnType[]` | `[ { key: 'default', text: 'Default Theme' }, { key: 'dark', text: 'Dark Theme' }, { key: 'compact', text: 'Compact Theme' }, ]` |
| `[devTips]` | Tips in development | `String` | `When the dark.css file can't be found, you need to run it once: npm run theme` |
| `[deployUrl]` | URL where files will be deployed. Generally needed when using `ng b --deploy-url` | `String` | `-` |
| `(themeChange)` | Theme Change Notification | `EventEmitter<string>` | `-` |
