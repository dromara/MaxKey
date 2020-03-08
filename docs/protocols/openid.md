<h2>1 OpenID Connect简介</h2>
OpenID Connect是基于OAuth 2.0规范族的可互操作的身份验证协议。它使用简单的REST / JSON消息流来实现，和之前任何一种身份认证协议相比，开发者可以轻松集成。

OpenID Connect允许开发者验证跨网站和应用的用户，而无需拥有和管理密码文件。OpenID Connect允许所有类型的客户,包括基于浏览器的JavaScript和本机移动应用程序,启动登录流动和接收可验证断言对登录用户的身份。

扩展阅读参看：官方技术说明<a href="http://openid.net/connect/"  title="http://openid.net/connect/" target="_blank" rel="nofollow">Connect标准（英文） </a> | <a href="http://en.wikipedia.org/wiki/OpenID_Connect"  title="http://en.wikipedia.org/wiki/OpenID_Connect" target="_blank" rel="nofollow">OpenID Connect维基百科（en）</a>


<h2>2 OpenID的历史是什么？</h2>
OpenID Connect是OpenID的第三代技术。首先是原始的OpenID，它不是商业应用，但让行业领导者思考什么是可能的。OpenID 2.0设计更为完善，提供良好的安全性保证。然而，其自身存在一些设计上的局限性，最致命的是其中依赖方必须是网页，但不能是本机应用程序；此外它还要依赖XML，这些都会导致一些应用问题。

OpenID Connect的目标是让更多的开发者使用，并扩大其使用范围。幸运的是，这个目标并不遥远，现在有很好的商业和开源库来帮助实现身份验证机制。

<h2>3 OIDC基础</h2>
简要而言，OIDC是一种安全机制，用于应用连接到身份认证服务器（Identity Service）获取用户信息，并将这些信息以安全可靠的方法返回给应用。

在最初，因为OpenID1/2经常和OAuth协议（一种授权协议）一起提及，所以二者经常被搞混。

OpenID是Authentication，即认证，对用户的身份进行认证，判断其身份是否有效，也就是让网站知道“你是你所声称的那个用户”；
OAuth是Authorization，即授权，在已知用户身份合法的情况下，经用户授权来允许某些操作，也就是让网站知道“你能被允许做那些事情”。
由此可知，授权要在认证之后进行，只有确定用户身份只有才能授权。
(身份验证)+ OAuth 2.0 = OpenID Connect

OpenID Connect是“认证”和“授权”的结合，因为其基于OAuth协议，所以OpenID-Connect协议中也包含了client_id、client_secret还有redirect_uri等字段标识。这些信息被保存在“身份认证服务器”，以确保特定的客户端收到的信息只来自于合法的应用平台。这样做是目的是为了防止client_id泄露而造成的恶意网站发起的OIDC流程。

举个例子。某个用户使用Facebook应用“What online quiz best describes you?” ，该应用可以通过Facebook账号登录，则你可以在应用中发起请求到“身份认证服务器”（也就是Facebook的服务器）请求登录。这时你会看到如下界面，询问是否授权。
<img src="{{ "/images/openid/1.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

在OAuth中，这些授权被称为scope。OpenID-Connect也有自己特殊的scope--openid ,它必须在第一次请求“身份鉴别服务器”（Identity Provider,简称IDP）时发送过去。

<h2>4 OIDC流程</h2>
OAuth2提供了Access Token来解决授权第三方客户端访问受保护资源的问题；相似的，OIDC在这个基础上提供了ID Token来解决第三方客户端标识用户身份认证的问题。OIDC的核心在于在OAuth2的授权流程中，一并提供用户的身份认证信息（ID-Token）给到第三方客户端，ID-Token使用JWT格式来包装，得益于JWT（JSON Web Token）的自包含性，紧凑性以及防篡改机制，使得ID-Token可以安全的传递给第三方客户端程序并且容易被验证。应有服务器，在验证ID-Token正确只有，使用Access-Token向UserInfo的接口换取用户的更多的信息。

有上述可知，OIDC是遵循OAuth协议流程，在申请Access-Token的同时，也返回了ID-Token来验证用户身份。

<h3>4.1 相关定义</h3>

EU：End User，用户。

RP：Relying Party ，用来代指OAuth2中的受信任的客户端，身份认证和授权信息的消费方；

OP：OpenID Provider，有能力提供EU身份认证的服务方（比如OAuth2中的授权服务），用来为RP提供EU的身份认证信息；

ID-Token：JWT格式的数据，包含EU身份认证的信息。

UserInfo Endpoint：用户信息接口（受OAuth2保护），当RP使用ID-Token访问时，返回授权用户的信息，此接口必须使用HTTPS。

下面我们来看看OIDC的具体协议流程。

根据应用客户端的不同，OIDC的工作模式也应该是不同的。和OAuth类似，主要看是否客户端能保证client_secret的安全性。

如果是JS应用，其所有的代码都会被加载到浏览器而暴露出来，没有后端可以保证client_secret的安全性，则需要是使用默认模式流程(Implicit Flow)。

如果是传统的客户端应用，后端代码和用户是隔离的，能保证client_secret的不被泄露，就可以使用授权码模式流程（Authentication Flow）。

此外还有混合模式流程(Hybrid Flow)，简而言之就是以上二者的融合。

OAuth2中还有口令模式和“应有访问模式”的方式来获取Access Token（关于OAuth2的内容，可以参见OAuth2.0 协议入门指南），为什么OIDC没有扩展这些方式呢?
"口令模式"是需要用户提供账号和口令给RP的，既然都已经有用户名和口令了，就不需要在获取什么用户身份了。至于“应有访问模式”，这种方式不需要用户参与，也就无需要认证和获取用户身份了。这也能反映授权和认证的差异，以及只使用OAuth2来做身份认证的事情是远远不够的，也是不合适的。

<h3>4.2 授权码模式流程</h3>
<img src="{{ "/images/openid/2.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

授权码模式流程
和OAuth认证流程类似

RP发送一个认证请求给OP，其中附带client_id；

OP对EU进行身份认证；

OP返回响应，发送授权码给RP；

RP使用授权码向OP索要ID-Token和Access-Token，RP验证无误后返回给RP；

RP使用Access-Token发送一个请求到UserInfo EndPoint； UserInfo EndPoint返回EU的Claims。


<h4>4.2.1 基于Authorization Code的认证请求</h4>
RP使用OAuth2的Authorization-Code的方式来完成用户身份认证，所有的Token都是通过OP的Token EndPoint（OAuth2中定义）来发放的。构建一个OIDC的Authentication Request需要提供如下的参数：

scope：必须。OIDC的请求必须包含值为“openid”的scope的参数。

response_type：必选。同OAuth2。

client_id：必选。同OAuth2。

redirect_uri：必选。同OAuth2。

state：推荐。同OAuth2。防止CSRF, XSRF。

示例如下：
<pre><code class="http hljs">
GET /authorize?
    response_type=code
    &scope=openid%20profile%20email
    &client_id=s6BhdRkqt3
    &state=af0ifjsldkj
    &redirect_uri=https%3A%2F%2Fclient.example.org%2Fcb HTTP/1.1
  Host: server.example.com
</code></pre>
<h4>4.2.2 基于Authorization Code的认证请求的响应</h4>
在OP接收到认证请求之后，需要对请求参数做严格的验证，具体的规则参见http://openid.net/specs/openid-connect-core-1_0.html#AuthRequestValidation，验证通过后引导EU进行身份认证并且同意授权。在这一切都完成后，会重定向到RP指定的回调地址(redirect_uri)，并且把code和state参数传递过去。比如：
<pre><code class="http hljs">
  HTTP/1.1 302 Found
  Location: https://client.example.org/cb?
    code=SplxlOBeZQQYbYS6WxSbIA
    &state=af0ifjsldkj
</code></pre>
<h4>4.2.3 获取ID Token</h4>
RP使用上一步获得的code来请求Token EndPoint，这一步桶OAuth2，就不再展开细说了。然后Token EndPoint会返回响应的Token，其中除了OAuth2规定的部分数据外，还会附加一个id_token的字段。id_token字段就是上面提到的ID Token。例如：
<pre><code class="json hljs">
  HTTP/1.1 200 OK
  Content-Type: application/json
  Cache-Control: no-store
  Pragma: no-cache

  {
   "access_token": "SlAV32hkKG",
   "token_type": "Bearer",
   "refresh_token": "8xLOxBtZp8",
   "expires_in": 3600,
   "id_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjFlOWdkazcifQ.ewogImlzc
     yI6ICJodHRwOi8vc2VydmVyLmV4YW1wbGUuY29tIiwKICJzdWIiOiAiMjQ4Mjg5
     NzYxMDAxIiwKICJhdWQiOiAiczZCaGRSa3F0MyIsCiAibm9uY2UiOiAibi0wUzZ
     fV3pBMk1qIiwKICJleHAiOiAxMzExMjgxOTcwLAogImlhdCI6IDEzMTEyODA5Nz
     AKfQ.ggW8hZ1EuVLuxNuuIJKX_V8a_OMXzR0EHR9R6jgdqrOOF4daGU96Sr_P6q
     Jp6IcmD3HP99Obi1PRs-cwh3LO-p146waJ8IhehcwL7F09JdijmBqkvPeB2T9CJ
     NqeGpe-gccMg4vfKjkM8FcGvnzZUN4_KSP0aAp1tOJ1zZwgjxqGByKHiOtX7Tpd
     QyHE5lcMiKPXfEIQILVq0pc_E2DzL7emopWoaoZTF_m0_N0YzFC6g6EJbOEoRoS
     K5hoDalrcvRYLSrQAZZKflyuVCyixEoV9GfNQC3_osjzw2PAithfubEEBLuVVk4
     XUVrWOLrLl0nx7RkKU8NXNHq-rvKMzqg"
  }
</code></pre>
其中看起来一堆乱码的部分就是JWT格式的ID-Token。在RP拿到这些信息之后，需要对id_token以及access_token进行验证（具体的规则参见http://openid.net/specs/openid-connect-core-1_0.html#IDTokenValidation和http://openid.net/specs/openid-connect-core-1_0.html#ImplicitTokenValidation）。至此，可以说用户身份认证就可以完成了，后续可以根据UserInfo EndPoint获取更完整的信息。

<h4>4.2.4 安全令牌 ID-Token</h4>
上面提到过OIDC对OAuth2最主要的扩展就是提供了ID-Token。下面我们就来看看ID-Token的主要构成：

iss = Issuer Identifier：必须。提供认证信息者的唯一标识。一般是Url的host+path部分；

sub = Subject Identifier：必须。iss提供的EU的唯一标识；最长为255个ASCII个字符；

aud = Audience(s)：必须。标识ID-Token的受众。必须包含OAuth2的client_id；

exp = Expiration time：必须。ID-Token的过期时间；

iat = Issued At Time：必须。JWT的构建的时间。

auth_time = AuthenticationTime：EU完成认证的时间。如果RP发送认证请求的时候携带max_age的参数，则此Claim是必须的。

nonce：RP发送请求的时候提供的随机字符串，用来减缓重放攻击，也可以来关联ID-Token和RP本身的Session信息。

acr = Authentication Context Class Reference：可选。表示一个认证上下文引用值，可以用来标识认证上下文类。

amr = Authentication Methods References：可选。表示一组认证方法。

azp = Authorized party：可选。结合aud使用。只有在被认证的一方和受众（aud）不一致时才使用此值，一般情况下很少使用。

<pre><code class="json hljs">
{
   "iss": "https://server.example.com",
   "sub": "24400320",
   "aud": "s6BhdRkqt3",
   "nonce": "n-0S6_WzA2Mj",
   "exp": 1311281970,
   "iat": 1311280970,
   "auth_time": 1311280969,
   "acr": "urn:mace:incommon:iap:silver"
  }
</code></pre>
另外ID Token必须使用JWT(JSON Web Token)进行签名和JWE(JSON Web Encryption)加密，从而提供认证的完整性、不可否认性以及可选的保密性。关于JWT的更多内容，请参看JSON Web Token - 在Web应用间安全地传递信息

<h3>4.3 默认模式流程</h3>
<img src="{{ "/images/openid/3.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

默认模式流程
默认流程和OAuth中的类似，只不过也是添加了ID-Token的相关内容。

这里需要说明的是：OIDC的说明文档里很明确的说明了用户的相关信息都要使用JWT形式编码。在JWT中，不应该在载荷里面加入任何敏感的数据。如果传输的是用户的User ID。这个值实际上不是什么敏感内容，一般情况下被知道也是安全的。

但是现在工业界已经不推荐使用OAuth默认模式，而推荐使用不带client_Secret的授权码模式。

<h3>4.4混合模式</h3>
混合模式简而言之就是以上提到的两种模式的混合，不过也有一些小的改变，就是允许直接向客户端返回Access-Token。

业界普遍认为，后端传递Token（比如服务器之间通信）要比前端（比如页面之间）可靠，所以如果直接返回令牌的情况下会把令牌的过期时间设置较短，但是比较

UserInfo Endpoint
可能有的读者发现了，ID-Token只有sub是和EU相关的，这在一般情况下是不够的，必须还需要EU的用户名，头像等其他的资料，OIDC提供了一组公共的cliams，来提供更多用户的信息，这就是——UserIndo EndPoin。

在RP得到Access Token后可以请求此资源，然后获得一组EU相关的Claims，这些信息可以说是ID-Token的扩展，ID-Token中只需包含EU的唯一标识sub即可（避免ID Token过于庞大和暴露用户敏感信息），然后在通过此接口获取完整的EU的信息。此资源必须部署在TLS之上，例如：
<pre><code class="http hljs">
  GET /userinfo HTTP/1.1
  Host: server.example.com
  Authorization: Bearer SlAV32hkKG
</code></pre>
成功之后响应如下：
<pre><code class="json hljs">
  HTTP/1.1 200 OK
  Content-Type: application/json

  {
   "sub": "248289761001",
   "name": "Jane Doe",
   "given_name": "Jane",
   "family_name": "Doe",
   "preferred_username": "j.doe",
   "email": "janedoe@example.com",
   "picture": "http://example.com/janedoe/me.png"
  }
</code></pre>
其中sub代表EU的唯一标识，这个claim是必须的，其他的都是可选的。