<h2>1 JSON Web Token介绍</h2>

JSON Web Token （JWT）是一个开放标准（<a href="https://tools.ietf.org/html/rfc7519" target="_blank">RFC 7519</a>），它定义了一种紧凑且自包含的方式，用于在各方之间安全地将信息作为JSON对象传输。由于此信息是经过数字签名的，因此可以被验证和信任。可以使用秘密（使用<b>HMAC</b>算法）或使用<b>RSA</b>或<b>ECDSA</b>的公用/专用密钥对对JWT进行签名。

尽管可以对JWT进行加密以在各方之间提供保密性，但我们将重点关注已签名的令牌。签名的令牌可以验证其中包含的声明的完整性，而加密的令牌则将这些声明隐藏在其他方的面前。当使用公钥/私钥对对令牌进行签名时，签名还证明只有持有私钥的一方才是对其进行签名的一方。

扩展阅读参看：官方技术说明 <a href="https://tools.ietf.org/html/rfc7519" target="_blank">JWT</a>

<h2>2 什么时候使用JSON Web Token</h2>

以下是JSON Web令牌有用的一些情况：

<b>授权：</b>这是使用JWT的最常见方案。一旦用户登录，每个后续请求将包括JWT，从而允许用户访问该令牌允许的路由，服务和资源。单一登录是当今广泛使用JWT的一项功能，因为它的开销很小并且可以在不同的域中轻松使用。

<b>信息交换：</b>JSON Web令牌是在各方之间安全地传输信息的好方法。因为可以对JWT进行签名（例如，使用公钥/私钥对），所以您可以确定发件人是他们所说的人。此外，由于签名是使用标头和有效负载计算的，因此您还可以验证内容是否遭到篡改。

<h2>3 JSON Web Token结构</h2>

JSON Web Token以紧凑的形式由三部分组成，这些部分由点（.）分隔，分别是：

Header(标头)

Payload(有效载荷)

Signature(签名)

因此，JWT通常如下所示。
<pre class="prettyprint">
xxxxx.yyyyy.zzzzz
</pre>
让我们分解不同的部分。

<b>Header(标头)</b>

标头通常由两部分组成：令牌的类型（即JWT）和所使用的签名算法，例如HMAC SHA256或RSA。

例如：
<pre><code class="json hljs">
{
  "alg": "HS256",
  "typ": "JWT"
}
</code></pre>
然后，此JSON被<b>Base64Url</b>编码以形成JWT的第一部分。

<b>Payload(有效载荷)</b>

令牌的第二部分是有效负载，其中包含声明。声明是有关实体（通常是用户）和其他数据的声明。索赔有以下三种类型：注册的，公共的和私人索赔。

已注册的权利要求：这些是一组非强制性的但建议使用的预定义权利要求，以提供一组有用的，可互操作的权利要求。其中一些是： <b>iss</b>（发布者）， <b>exp</b>（到期时间）， <b>sub</b>（主题）， <b>aud</b>（受众群体）等。

请注意，声明名称仅是三个字符，因为JWT是紧凑的。

公开声明：使用JWT的人员可以随意定义这些声明。但是为避免冲突，应在 <a href="https://www.iana.org/assignments/jwt/jwt.xhtml" target="_blank">IANA JSON Web令牌注册表</a>中定义它们，或将其定义为包含抗冲突名称空间的URI。

私人权利：这些都是使用它们同意并既不是当事人之间建立共享信息的自定义声明注册或公众的权利要求。

有效负载示例可能是：
<pre><code class="json hljs">
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true
}
</code></pre>
然后，对有效负载进行<b>Base64Url</b>编码，以形成JSON Web令牌的第二部分。

请注意，对于已签名的令牌，此信息尽管可以防止篡改，但任何人都可以读取。除非将其加密，否则请勿将机密信息放入JWT的有效负载或报头元素中。

<b>Signature(签名)</b>

要创建签名部分，您必须获取编码的标头，编码的有效载荷，机密，标头中指定的算法，并对其进行签名。

例如，如果要使用HMAC SHA256算法，则将通过以下方式创建签名：
<pre><code class="java hljs">
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret)
</code></pre>  
签名用于验证消息在此过程中没有更改，并且对于使用私钥进行签名的令牌，它还可以验证JWT的发送者是它所说的真实身份。

<b>结合一起</b>

输出是三个由点分隔的Base64-URL字符串，可以在HTML和HTTP环境中轻松传递这些字符串，与基于XML的标准（例如SAML）相比，它更紧凑。

下面显示了一个JWT，它已对先前的标头和有效负载进行了编码，并用一个秘密进行了签名。 编码的JWT
<img src="{{ "/images/jwt/encoded-jwt3.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>
如果您想使用JWT并将这些概念付诸实践，则可以使用jwt.io Debugger解码，验证和生成JWT。<a href="http://jwt.io/" target="_blank" >JWT.io调试器</a>
<img src="{{ "/images/jwt/legacy-app-auth-5.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>


<h2>4 JSON Web Token工作机制</h2>

在身份验证中，当用户使用其凭据成功登录时，将返回JSON Web令牌。由于令牌是凭据，因此必须格外小心以防止安全问题。通常，令牌的保留时间不应超过要求的时间。

由于缺乏安全性，您也不应该将敏感的会话数据存储在浏览器中。

每当用户想要访问受保护的路由或资源时，用户代理通常应使用授权<b>Authorization</b>在<b>Bearer</b>承载模式标头中发送JWT 。标头的内容应如下所示：
<pre class="prettyprint">
Authorization: Bearer [token]
</pre>
在某些情况下，这可以是无状态授权机制。服务器的受保护路由将在<b>Authorization</b>标头中检查有效的JWT ，如果存在，则将允许用户访问受保护的资源。如果JWT包含必要的数据，则可以减少查询数据库中某些操作的需求，尽管这种情况并非总是如此。

如果令牌是在<b>Authorization</b>标头中发送的，则跨域资源共享（CORS）不会成为问题，因为它不使用cookie。

下图显示了如何获取JWT并将其用于访问API或资源：
<img src="{{ "/images/jwt/client-credentials-grant.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>
JSON Web令牌如何工作

应用程序或客户端向授权服务器请求授权。这是通过不同的授权流程之一执行的。例如，典型的符合<a href="http://openid.net/connect/" target="_blank">OpenID Connect</a>的 Web应用程序将<a href="http://openid.net/specs/openid-connect-core-1_0.html#CodeFlowAuth"  target="_blank">/oauth/authorize</a>使用授权代码流通过端点。
授予授权后，授权服务器会将访问令牌返回给应用程序。
该应用程序使用访问令牌来访问受保护的资源（例如API）。
请注意，使用签名的令牌，令牌中包含的所有信息都会暴露给用户或其他方，即使他们无法更改它。这意味着您不应将机密信息放入令牌中。

<h2>5 如何使用JSON Web Token</h2>

让我们谈谈与Simple Web Tokens（SWT）和Security Assertion Markup Language Tokens安全性声明标记语言令牌（SAML）相比，JSON Web Tokens（JWT）的优势。

由于JSON不如XML冗长，因此在编码时JSON的大小也较小，从而使JWT比SAML更为紧凑。这使得JWT是在HTML和HTTP环境中传递的不错的选择。

在安全方面，只能使用HMAC算法由共享机密对SWT进行对称签名。但是，JWT和SAML令牌可以使用X.509证书形式的公用/专用密钥对进行签名。与签名JSON的简单性相比，使用XML数字签名对XML进行签名而不引入模糊的安全漏洞是非常困难的。

JSON解析器在大多数编程语言中都很常见，因为它们直接映射到对象。相反，XML没有自然的文档到对象映射。与SAML断言相比，这使使用JWT更加容易。

关于用法，JWT是在Internet规模上使用的。这强调了在多个平台（尤其是移动平台）上对JSON Web令牌进行客户端处理的简便性。
<img src="{{ "/images/jwt/comparing-jwt-vs-saml2.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>
比较已编码的JWT和已编码的SAML的长度 编码的JWT和编码的SAML的长度比较