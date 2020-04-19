<h2>多因素认证（MFA）</h2>

双因素身份认证就是通过你所知道再加上你所能拥有的这二个要素组合到一起才能发挥作用的身份认证系统。双因素认证是一种采用时间同步技术的系统，采用了基于时间、事件和密钥三变量而产生的一次性密码来代替传统的静态密码。每个动态密码卡都有一个唯一的密钥，该密钥同时存放在服务器端，每次认证时动态密码卡与服务器分别根据同样的密钥，同样的随机参数（时间、事件）和同样的算法计算了认证的动态密码，从而确保密码的一致性，从而实现了用户的认证。就像我们去银行办卡送的口令牌.

多因素认证（MFA），是一种计算机访问控制的方法，用户要通过两种以上的认证机制之后，才能得到授权，使用计算机资源。MFA的目的是建立一个多层次的防御，使未经授权的人访问计算机系统或网络更加困难，从而提高安全性。


<h2>TOTP或者HOTP支持</h2>

1、Google Authenticator

2、Microsoft Authenticator

3、FreeOTP

4、支持TOTP或者HOTP协议


优势：**使用简单、安全性高、低成本、无需携带额外设备**


<h2>Google Authenticator支持</h2>

MaxKey支持谷歌验证器(Google Authenticator)双因素身份认证，步骤如下

1、下载Google Authenticator到手机

<img src="{{ "/images/authn/google1.jpg" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

2、登录到Maxkey，进入"安全设置"-->"时间令牌"，如下图

<img src="{{ "/images/authn/google2.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

3、使用Google Authenticator扫描令牌的二维码

<img src="{{ "/images/authn/google3.jpg" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

4、退出MaxKey,进入到登录界面，"安全认证"，输入用户名和密码，同时需要Google Authenticator产生的验证码登录，如下图

<img src="{{ "/images/authn/google4.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

