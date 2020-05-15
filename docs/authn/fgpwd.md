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


<h2>短信验证码</h2>

<h3>腾讯云短信</h3>
配置maxkey中spring/maxkey-security.xml
secretId 账号Appkey

secretKey 密钥appSecret

smsSdkAppid 短信SDKAPPID

templateId 短信模板ID

sign 签名

<pre><code class="xml hljs">
&lt;bean id="tfaMobileOptAuthn" class="org.maxkey.crypto.password.opt.impl.sms.SmsOtpAuthnTencentCloud"&gt;
	&lt;property name="secretId" value="94395d754eb55693043f5d6a2b772ef4" /&gt;
	&lt;property name="secretKey" value="05d5485357bc" /&gt;
	&lt;property name="smsSdkAppid" value="1486220095" /&gt;
	&lt;property name="templateId" value="14860095" /&gt;
	&lt;property name="sign" value="1486009522" /&gt;
&lt;/bean&gt;

</code></pre>

<h3>阿里云短信</h3>
配置maxkey中spring/maxkey-security.xml

accessKeyId 账号Appkey

accessSecret 密钥appSecret

templateCode 短信模板ID

signName 签名

<pre><code class="xml hljs">
&lt;bean id="tfaMobileOptAuthn" class="org.maxkey.crypto.password.opt.impl.sms.SmsOtpAuthnAliyun"&gt;
	&lt;property name="accessKeyId" value="94395d754eb55693043f5d6a2b772ef3" /&gt;
	&lt;property name="accessSecret" value="05d5485357bc" /&gt;
	&lt;property name="templateCode" value="SMS_187590021" /&gt;
	&lt;property name="signName" value="MaxKey" /&gt;
&lt;/bean&gt;

</code></pre>

<h3>网易云信</h3>
配置maxkey中spring/maxkey-security.xml

appKey 网易云信分配的账号Appkey

appSecret 网易云信分配的密钥appSecret

templateId 短信模板ID

<pre><code class="xml hljs">
&lt;bean id="tfaMobileOptAuthn" class="org.maxkey.crypto.password.opt.impl.sms.SmsOtpAuthnYunxin"&gt;
	&lt;property name="appKey" value="94395d754eb55693043f5d6a2b772ef4" /&gt;
	&lt;property name="appSecret" value="05d5485357bc" /&gt;
	&lt;property name="templateId" value="14860095" /&gt;
&lt;/bean&gt;

</code></pre>

<h2>电子邮件</h2>

配置邮箱地址

文件
maxkey/config/applicationConfig.properties

<pre><code class="ini hljs">
#  EMAIL configuration
config.email.username=maxkey@163.com
config.email.password=password
config.email.smtpHost=smtp.163.com
config.email.port=465
config.email.senderMail=maxkey@163.com
config.email.ssl=true
</code></pre>

配置maxkey中spring/maxkey-security.xml

subject 邮件主题

messageTemplate 邮件内容模板，请勿修改参数{0}为用户名，{1}认证码，{2}有效间隔

<pre><code class="xml hljs">

&lt;bean id="tfaMailOptAuthn" class="org.maxkey.crypto.password.opt.impl.MailOtpAuthn"&gt;
	&lt;property name="subject" value="MaxKey One Time PassWord" /&gt;
	&lt;property name="messageTemplate" value="{0} You Token is {1} , it validity in {2}  minutes." /&gt;
&lt;/bean&gt;
</code></pre>