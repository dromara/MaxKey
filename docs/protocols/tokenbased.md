<h2>1 TokenBased介绍</h2>

TokenBased(基于令牌)的认证，是一种简单的令牌的认证，即认证中心和应用共享凭证或者数字证书，认证中心使用HTTP POST的方式提交令牌到应用系统，应用系统并随后进行身份验证；

<h2>2 交互概要</h2>
 
该技术的实现步骤是：
 <ol>
  <li>一个未经身份验证的用户通过浏览器访问应用系统。</li>
  <li>应用系统跳转到认证中心，请求认证。</li>
  <li>用户填写自己的用户名和密码，然后按下提交按钮。</li>
  <li>认证中心完成用户认证，生成令牌并提交到应用系统认证地址。</li>
  <li>应用系统使用共享凭证或者数字证书验证令牌，从令牌中获取用户认证信息。</li>
  <li>应用系统完成系统登录。</li>
</ol>

<h3>2.1 令牌加密或者签名方式</h3>

<ol>
  <li>加密方式：DES、DESede、AES、Blowfish，默认采用DES。</li>
  <li>签名方式：服务端使用RSA数字证书私钥加密，客户端使用RSA数字证书公钥验证。</li>
</ol>

<h3>2.2 令牌格式</h3>

<pre><code class="json hljs">
{
	"randomId":"652ec5f5-fff2-4b8e-b88d-e7ff3a217bca",
	"uid":"29e82574-b37a-46ab-bac1-5fecbd24b24b",
	"username":"zhangs1020",
	"email":"zhangs1020@connsec.com",
	"windowsAccount":"ZHANGS1020",
	"employeeNumber":"ZHANGS1020",
	"departmentId":"1000212",
	"department":"IT信息中心",
	"displayName":"张三",
	"at":"2015-03-11T15:17:03.855Z",
	"expires":"2015-03-11T15:18:03.855Z"
}
</code></pre> 

randomId是即时生成的随机数<br>
at是当前认证的时间<br>
expires是过期的间隔<br>
其他的字段可在管理控制台配置

<h2>3 简单令牌</h2>

认证用户名@@认证时间(UTC时间),例如：
<pre class="prettyprint">
testUser@2010-01-01T01:01:01.001Z
</pre>
<h3>3.1 令牌加密</h3>

加密步骤：
 <ol>
  <li>申请公共的秘钥。</li>
  <li>使用秘钥对产生的Token使用DES、DESede、AES、Blowfish进行加密，默认采用DES。</li>
  <li>对加密的数据进行BASE64URL编码。</li>
</ol>


简单token加密结果：<br>
<pre class="prettyprint">
Y00jv2TCCuk365uB2-nDCUdboygeYFoUfETC7BNXr73dQWwFNRrfYltczDQ5iWg8NTO-GsP--VlR6L-JyNhZSg
</pre>
<h3>3.2 令牌签名</h3>

token的签名格式：BASE64URL(UTF8(data)).BASE64URL(UTF8(signature))，中间用"<em style='font-size: 30px;  font-style: normal;'>.</em>"分开，前半部分是数据，后半部分是签名书数据，例如：<br>
<pre class="prettyprint">
eyJpc3MiOiJqb2UiLA0KICJleHAiOjEzMDA4MTkzODAsDQogImh0dHA6Ly9leGFtcGxlLmNvbS9pc19yb290Ijp0cnVlfQ<em style="font-size: 40px;  font-style: normal;">.</em>dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk
</pre>

<h2>4 LTPA介绍</h2>
    
LTPA是Lightweight ThirdParty Authentication简称，轻量级第三方认证，支持在一个因特网域中的一组 Web 服务器之间使用单一登录的认证框架，即通过cookie来传输Token。

当服务器配置LTPA认证方式，用户通过浏览器成功登录，服务器会自动发送一个session cookie给浏览器，此cookie中包含一个加密和签名Security Token信息，应用服务器根据Security Token解析得到登录用户信息自动完成应用系统认证。

<h3>4.1 交互概要</h3>
 
 该技术的实现步骤是：
 <ol>
  <li>一个未经身份验证的用户通过浏览器访问应用系统，应用系统跳转到认证中心。</li>
  <li>认证中心完成用户登录，把Security Token发给浏览器，并跳转到应用系统。</li>
  <li>应用系统解析Security Token，得出用户登录信息。</li>
  <li>应用系统使用用户信息完成自身的登录。</li>
</ol>