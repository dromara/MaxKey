<h2>1 什么是OAuth2</h2>

OAuth： OAuth（开放授权）是一个开放标准，允许用户授权第三方网站访问他们存储在另外的服务提供者上的信息，而不需要将用户名和密码提供给第三方网站或分享他们数据的所有内容。

OAuth2.0：对于用户相关的OpenAPI（例如获取用户信息，动态同步，照片，日志，分享等），为了保护用户数据的安全和隐私，第三方网站访问用户数据前都需要显式的向用户征求授权。

采用OAuth2.0标准协议来进行用户身份验证和获取用户授权，相对于之前的OAuth1.0协议，其认证流程更简单和安全。

扩展阅读参看：<a href="http://oauth.net/2/" target="_blank">OAuth标准（英文）</a> | <a href="http://zh.wikipedia.org/zh/OAuth"  target="_blank">OAuth维基百科（中文）</a>

<h2>2 应用场景</h2>

第三方应用授权登录：在APP或者网页接入一些第三方应用时，时长会需要用户登录另一个合作平台，比如QQ，微博，微信的授权登录。
<img src="{{ "/images/oauth2/qq.jpg" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

原生app授权：app登录请求后台接口，为了安全认证，所有请求都带token信息，如果登录验证、请求后台数据。

前后端分离单页面应用（spa）：前后端分离框架，前端请求后台数据，需要进行oauth2安全认证，比如使用vue、react后者h5开发的app。

<h2>3 名词定义</h2>

（1） Third-party application：第三方应用程序，本文中又称"客户端"（client），比如打开知乎，使用第三方登录，选择qq登录，这时候知乎就是客户端。

（2）HTTP service：HTTP服务提供商，本文中简称"服务提供商"，即上例的qq。

（3）Resource Owner：资源所有者，本文中又称"用户"（user）,即登录用户。

（4）User Agent：用户代理，本文中就是指浏览器。

（5）Authorization server：认证服务器，即服务提供商专门用来处理认证的服务器。

（6）Resource server：资源服务器，即服务提供商存放用户生成的资源的服务器。它与认证服务器，可以是同一台服务器，也可以是不同的服务器。

<h2>4 运行流程</h2>

<img src="{{ "/images/oauth2/flow.jpg" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

（A）用户打开客户端以后，客户端要求用户给予授权。

（B）用户同意给予客户端授权。

（C）客户端使用上一步获得的授权，向认证服务器申请令牌。

（D）认证服务器对客户端进行认证以后，确认无误，同意发放令牌。

（E）客户端使用令牌，向资源服务器申请获取资源。

（F）资源服务器确认令牌无误，同意向客户端开放资源。

<h2>5 四种授权模式</h2>

授权码模式（authorization code）

简化模式（implicit）

密码模式（resource owner password credentials）

客户端模式（client credentials）

<h3>5.1 授权码模式</h3>

授权码模式（authorization code）是功能最完整、流程最严密的授权模式。
<img src="{{ "/images/oauth2/code.jpg" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

（1）用户访问客户端，后者将前者导向认证服务器，假设用户给予授权，认证服务器将用户导向客户端事先指定的"重定向URI"（redirection URI），同时附上一个授权码。

（2）客户端收到授权码，附上早先的"重定向URI"，向认证服务器申请令牌：GET /oauth/token?response_type=code&client_id=test&redirect_uri=重定向页面链接。请求成功返回code授权码，一般有效时间是10分钟。

（3）认证服务器核对了授权码和重定向URI，确认无误后，向客户端发送访问令牌（access token）和更新令牌（refresh token）。POST /oauth/token?response_type=authorization_code&code=SplxlOBeZQQYbYS6WxSbIA&redirect_uri=重定向页面链接。请求成功返回access Token和refresh Token。


<h3>5.2 简化模式Implicit</h3>

适用于公开的浏览器单页应用
<img src="{{ "/images/oauth2/implicit.jpg" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

Access Token直接从授权服务器返回(只有前端渠道)

不支持refresh tokens

假定资源所有者和公开客户应用在同一个设备上

最容易受安全攻击


<h3>5.3 用户名密码 Resource Owner Credentials</h3>
<img src="{{ "/images/oauth2/resource.jpg" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

使用用户名密码登录的应用，例如桌面App

使用用户名/密码作为授权方式从授权服务器上获取access token

一般不支持refresh token

假定资源拥有者和公开客户子啊相同设备上


<h3>5.4 客户端凭证 Client Credentials</h3>
<img src="{{ "/images/oauth2/client.jpg" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

适用于服务器见通信场景，机密客户代表它自己或者一个用户

只有后端渠道，使用客户凭证获取一个access token

因为客户凭证可以使用对称或者非对称加密，该方式支持共享密码或者证书

