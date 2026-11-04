# Congress 免登（静默单点登录）对接文档

## 适用场景

上游系统 A 的已登录用户跳转到其他接入 MaxKey 的系统 B 时，不再弹出 MaxKey 登录页，全程无感完成单点登录。

## 前提条件

1. 上游系统 A 在 MaxKey 中注册为应用（任意协议均可），并持有其 **appId（应用ID）** 和 **secret（应用密钥）**。
   - 管理后台 → 应用管理 中查看。
2. 系统 A 的用户与 MaxKey 用户通过 **username（用户名）** 一致对应（MaxKey 中必须存在同名用户）。
3. secret 只允许保存在系统 A 的**服务端**，严禁下发到浏览器或 App。
4. 目标系统 B 的用户必须在 MaxKey 中已授权访问该系统对应的应用（角色/组已配置应用权限），否则跳转后会被 MaxKey 拒绝访问。
5. 若 MaxKey 部署在 nginx 等 HTTPS 反向代理之后，须让后端识别代理协议，否则返回的 `redirect` 可能生成 http 地址：
   - 代理传递 `X-Forwarded-Proto`、`X-Forwarded-Host`；
   - MaxKey 配置 `server.forward-headers-strategy=native`（或 `framework`）。

## 对接方式（系统 A 侧共两步）

### 第 1 步：服务端签发 congress 令牌

用户在系统 A 点击"跳转到系统 B"时，系统 A 后端发起 HTTP 调用：

```http
POST https://{maxkey地址}/sign/authz/congress/create
Authorization: Basic base64({appId}:{secret})
Content-Type: application/json

{
  "username": "zhangsan",
  "appId": "{系统B的应用ID}"
}
```

| 参数 | 位置 | 必填 | 说明 |
|---|---|---|---|
| appId / secret | Basic 认证头 | 是 | 系统 A 在 MaxKey 注册的应用凭据 |
| username | body | 是 | 当前登录用户的用户名，须在 MaxKey 中存在 |
| appId (body) | body | 否 | 目标单点登录应用ID，传入则返回拼好的 redirect |

成功响应：

```json
{
  "code": 0,
  "data": {
    "congress": "a3f8...",
    "redirect": "https://sso.corp.com/maxkey/#/passport/login?congress=a3f8...&redirect_uri=aHR0cHM6Ly8xMTEuNDcuMTMzLjg5OjIxNzE2L3Byb2QtYXBpL2F1dGgvc3NvL21heGtleQ"
  }
}
```

| 字段 | 说明 |
|---|---|
| congress | 一次性登录令牌，**消费一次即失效**；默认有效期 3 分钟，超时需重新签发 |
| redirect | 完整跳转地址，直接 302 给浏览器即可 |

失败响应示例：`{"code": 2, "message": "应用ID或Secret不正确"}`（code != 0 即失败）。

### 第 2 步：浏览器 302 跳转

系统 A 后端将 `redirect` 返回给浏览器跳转（302 / window.location 均可）。浏览器将自动完成：

```
MaxKey 静默登录（无登录页） → 建立 SSO 会话
  → 跳转目标应用在 MaxKey 中配置的「登录地址」(loginUrl)
  → 目标应用自行生成 state，发起标准 OAuth 2.0 authorize 请求
  → MaxKey 检测到已有会话，直接 302 回调目标应用（免登点）
  → 目标应用校验 state 并完成登录
```

> 目标应用作为标准 OAuth 2.0 客户端，state 由其自身生成并校验，因此 redirect_uri 必须指向
> 目标应用的登录地址，而不是 MaxKey 的认证总地址 `/authz/{appId}`。后者属于 IdP 发起式流程，
> 不经过目标应用生成 state，回调时会被目标应用以「state 不能为空」拒绝。

若未传 body 中的 appId，则只返回 congress，系统 A 需自行拼接（以 nginx 部署、前端前缀 `/maxkey` 为例）：

```
https://{sso域名}/maxkey/#/passport/login?congress={congress}&redirect_uri={BASE64URL(目标应用登录地址)}
```

其中 redirect_uri 为目标应用登录地址（应用管理 → 应用 → 登录地址）的 **BASE64URL 编码**（UTF-8）。

目标应用的用户必须在 MaxKey 中已授权访问该系统对应的应用（角色/组已配置应用权限），否则跳转后会被 MaxKey 拒绝访问。

## 时序

```
浏览器          系统A后端                MaxKey                 目标应用B
  │  点击跳转    │                        │                        │
  │ ──────────→ │ POST /authz/congress/create  (Basic appId:secret) │
  │             │ ──────────────────────→│ 校验应用 → trusted登录 → 签发congress
  │             │ ←── congress + redirect │
  │ ←─ 302 redirect                       │
  │ ────────────────────────────────────→│ /#/passport/login      │
  │             （前端消费congress、写会话cookie）                  │
  │ ←─────────────────── 302 目标应用登录地址 ──────────────────────→│
  │                                        │ ←── authorize(带state) ─│
  │                                        │ 已有会话，直接302回调   │
  │                                        │ ─── code & state ─────→│
  │ ←──────────────── 登录完成 ────────────────────────────────────│
```

## 安全说明

1. **secret 即凭据**：持有有效 appId+secret 的应用可为任意用户签发登录令牌，secret 泄露等于交出全量登录能力，务必只存服务端并定期轮换。
2. **一次性**：congress 令牌消费一次即失效，不会因 URL 泄露被重放（仍建议不要记录完整 redirect 到访问日志）。
3. **用户校验**：username 必须在 MaxKey 存在且状态正常，否则签发失败。
4. 该接口为**服务端到服务端**接口，不要从浏览器直接调用（会暴露 secret）。

## 错误码

| code | 场景 |
|---|---|
| 0 | 成功 |
| 2 + "应用认证失败..." | 未携带 Basic 认证 |
| 2 + "应用ID或Secret不正确" | 应用不存在或密钥不匹配 |
| 2 + "目标应用不存在" | body.appId 在 MaxKey 中未注册 |
| 2 + "目标应用未配置登录地址" | 目标应用未配置登录地址(loginUrl) |
| 2 + "用户认证失败..." | username 不存在 / 用户被禁用等 |

## 实现位置（MaxKey 侧）

- 接口：`maxkey-webs/maxkey-web-maxkey/.../web/controller/CongressAuthorizeController.java`
- 复用机制：`TrustedAuthenticationProvider`（受信免密登录，与 RememberMe 同通道）、`AuthTokenService.createCongress`（一次性令牌）、前端 `/#/passport/login?congress=`（静默登录入口）
