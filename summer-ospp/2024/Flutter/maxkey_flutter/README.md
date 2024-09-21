# maxkey_flutter

## [MaxKey APP 客户端 Flutter 开发项目](https://summer-ospp.ac.cn/org/prodetail/24f420154?lang=zh&list=pro)

### 功能
- [x] 账密登录
- [x] 保留登录状态
- [x] 账号信息
- [x] 扫码登录
- [x] 登出
- [x] TOTP 录入、展示和编辑
- [x] TOTP 与账号绑定的持久化
- [x] 检测 token 是否有效
- [x] 多语言
- [x] 切换日夜间模式
- [x] 指定主机和测试链接
- [x] 查看日志

### 编译帮助
1. 安装 Flutter 开发环境 [Install | Flutter](https://docs.flutter.dev/get-started/install)。本项目使用 Flutter 3.24.1
2. （可选）自定义主机地址。在 `[lib/persistent.dart](lib/persistent.dart)` 中修改 `MaxKeyPersistent` 的 `_DEFAULT_HOST` 值
3. 终端运行命令：
   1. 获取依赖库：`flutter pub get`
   2. 生成多语言文件：`flutter gen-l10n`
   3. 构建 Release(Android)：`flutter build apk`。详见 [Build and release an Android app](https://docs.flutter.dev/deployment/android)
   4. 构建 Release(iOS)。详见 [Build and release an iOS app](https://docs.flutter.dev/deployment/ios)

### 使用到的 Package
  dio: 网络请求
  go_router: 路由管理
  mobile_scanner: 扫码
  shared_preferences: 持久化
  auth_totp: TOTP
  logger: 日志

### 目录结构
- lib
  - l10n // 多语言
    - app_en.arb // 英语
    - app_zh.arb // 中文
  - maxkey // MaxKey API
    - maxkey.dart // MaxKey API 单例
    - services // MaxKey API（参照 [maxkey-web-app/src/app/service](https://gitee.com/dromara/MaxKey/tree/main/maxkey-web-frontend/maxkey-web-app/src/app/service)）
  - pages // 页面
  - app_color_scheme.dart // 从 MaxKey 图标生成的主题色
  - main.dart
  - persistent.dart // 持久化
  - repeat_tween_animation_builder.dart // 动画组件
  - totp.dart // TOTP 相关逻辑
  - utils.dart // Logger, route path, str extension