<h2>双因素认证（MFA）</h2>

<b>双因素身份认证</b>就是通过你所知道再加上你所能拥有的这二个要素组合到一起才能发挥作用的身份认证系统。双因素认证是一种采用时间同步技术的系统，采用了基于时间、事件和密钥三变量而产生的一次性密码来代替传统的静态密码。每个动态密码卡都有一个唯一的密钥，该密钥同时存放在服务器端，每次认证时动态密码卡与服务器分别根据同样的密钥，同样的随机参数（时间、事件）和同样的算法计算了认证的动态密码，从而确保密码的一致性，从而实现了用户的认证。就像我们去银行办卡送的口令牌.

多因素认证（MFA），是一种计算机访问控制的方法，用户要通过两种以上的认证机制之后，才能得到授权，使用计算机资源。MFA的目的是建立一个多层次的防御，使未经授权的人访问计算机系统或网络更加困难，从而提高安全性。


<h2>双因素认证</h2>

1、短信认证  腾讯云短信/阿里云短信/网易云信/定制

2、电子邮件 


<h2>短信认证</h2>

配置maxkey中maxkey.properties

<pre><code class="ini hljs">
config.login.mfa=true
#TimeBasedOtpAuthn MailOtpAuthn SmsOtpAuthnYunxin SmsOtpAuthnAliyun SmsOtpAuthnTencentCloud
config.login.mfa.type=TimeBasedOtpAuthn
</code></pre>

<h3>腾讯云短信</h3>
配置maxkey中maxkey.properties
secretId 账号Appkey

secretKey 密钥appSecret

smsSdkAppid 短信SDKAPPID

templateId 短信模板ID

sign 签名

<pre><code class="ini hljs">
config.otp.sms.aliyun.accesskeyid=94395d754eb55693043f5d6a2b772ef4
config.otp.sms.aliyun.accesssecret=05d5485357bc
config.otp.sms.aliyun.templatecode=14860095
config.otp.sms.aliyun.signname=maxkey
</code></pre>

<h3>阿里云短信</h3>
配置maxkey中maxkey.properties

accessKeyId 账号Appkey

accessSecret 密钥appSecret

templateCode 短信模板ID

signName 签名

<pre><code class="ini hljs">
config.otp.sms.tencentcloud.secretid=94395d754eb55693043f5d6a2b772ef4
config.otp.sms.tencentcloud.secretkey=05d5485357bc
config.otp.sms.tencentcloud.smssdkappid=1486220095
config.otp.sms.tencentcloud.templateid=14860095
config.otp.sms.tencentcloud.sign=1486009522
</code></pre>

<h3>网易云信</h3>
配置maxkey中maxkey.properties

appKey 网易云信分配的账号Appkey

appSecret 网易云信分配的密钥appSecret

templateId 短信模板ID

<pre><code class="ini hljs">
config.otp.sms.yunxin.appkey=94395d754eb55693043f5d6a2b772ef4
config.otp.sms.yunxin.appsecret=05d5485357bc
config.otp.sms.yunxin.templateid=14860095
</code></pre>

<h2>电子邮件</h2>

配置邮箱地址

文件
maxkey/application.properties

<pre><code class="ini hljs">
#  EMAIL configuration
spring.mail.default-encoding=utf-8
spring.mail.host=smtp.163.com
spring.mail.port=465
spring.mail.username=maxkey@163.com
spring.mail.password=password
spring.mail.protocol=smtp
spring.mail.properties.ssl=true
spring.mail.properties.sender=maxkey@163.com
</code></pre>

配置maxkey中application.properties

subject 邮件主题

messageTemplate 邮件内容模板，请勿修改参数{0}为用户名，{1}认证码，{2}有效间隔

<pre><code class="ini hljs">
spring.mail.properties.mailotp.message.subject=MaxKey One Time PassWord
spring.mail.properties.mailotp.message.template={0} You Token is {1} , it validity in {2}  minutes.
</code></pre>