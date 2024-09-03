# maxkey_flutter

MaxKey Flutter project.

### 待做：
- [x] 账密登录
- [x] 手机号登录
- [x] 保留登录状态
- [x] 账号信息
- [x] 扫码登录
- [x] 登出
- [x] TOTP 录入
- [x] TOTP 展示
- [x] TOTP 与账号绑定的持久化
- [x] 支持多个 TOTP
- [x] 检测 token 是否有效
- [x] 完善错误处理和提示
- [x] 优化用户界面
- [x] 多语言

登录页：
- MaxKey LOGO
- 用户名、密码、验证码
- 登录
- 设置

主页面：
- 用户卡片（包括扫码登录按钮）
- 添加 TOTP 按钮
- TOTP 列表（可删除某个 TOTP）

账号页：
- 用户卡片
- 详细信息
- 登出
- 设置

设置页：
- 主题模式（跟随系统、日间、夜间）
- 地址、端口（提供请求 sign/get 以验证连接性的功能）
- 查看日志

持久化：
- token
- host ip
- user's totp list