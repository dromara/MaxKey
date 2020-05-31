<h2>忘记密码</h2>

忘记密码一般都是通过2种方式找回：一种是通过预留电话号码发送验证码找回，另一个是通过设定邮箱找回。

主要步骤如下：

1、在登录界面点击“忘记密码”

<img src="{{ "/images/authn/fgpwd-1.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

2、输入用户对应的邮箱或者手机号码，如果找到用户则发送邮件或者手机验证码

<img src="{{ "/images/authn/fgpwd-2.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

3、重置密码，需要输入新的密码及验证码

<img src="{{ "/images/authn/fgpwd-3.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

4、重置成功，提示返回登录界面重新登录

<img src="{{ "/images/authn/fgpwd-4.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h2>验证码</h2>

1、短信验证码  腾讯云短信/阿里云短信/网易云信/定制

2、电子邮件 


<h2>短信认证</h2>

配置maxkey中maxkey.properties

<pre><code class="ini hljs">
#SmsOtpAuthnYunxin SmsOtpAuthnAliyun SmsOtpAuthnTencentCloud
config.otp.sms=SmsOtpAuthnYunxin
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