<h2>第三方账号登录</h2>

为了方便用户的登录，可以通过第三方的账号(例如新浪微博、微信、钉钉等)登录MaxKey，简单配置即可实现用户登录。

本文以新浪微博为例

<img src="{{ "/images/authn/authn_s_1.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h3>登录流程</h3>

<img src="{{ "/images/authn/authn_s.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h3>第三方认证配置</h3>
在新浪微博开放平台https://open.weibo.com/申请接入，新浪配置如下

<img src="{{ "/images/authn/authn_s_2.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<img src="{{ "/images/authn/authn_s_3.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h3>认证配置</h3>
文件
maxkey/config/applicationLogin.properties

<pre><code class="ini hljs">
#enable social sign on
config.login.socialsignon=true
#sina weibo
config.socialsignon.sinaweibo.provider=sinaweibo
#登录提示
config.socialsignon.sinaweibo.provider.name=新浪微博
#图片
config.socialsignon.sinaweibo.icon=images/social/sinaweibo.png
#新浪client.id
config.socialsignon.sinaweibo.client.id=3379757634
#新浪client.secret
config.socialsignon.sinaweibo.client.secret=1adfdf9800299037bcab9d1c238664ba
#
config.socialsignon.sinaweibo.account.id=id
#排序
config.socialsignon.sinaweibo.sortorder=1
</code></pre>

配置maxkey/spring/maxkey-support-social.xml

<pre><code class="xml hljs">
&lt;bean id="socialSignOnWeibo" class="org.maxkey.authn.support.socialsignon.service.SocialSignOnProvider"&gt;
	&lt;property name="provider" value="${config.socialsignon.sinaweibo.provider}"/&gt;
	&lt;property name="providerName" value="${config.socialsignon.sinaweibo.provider.name}"/&gt;
	&lt;property name="icon" value="${config.socialsignon.sinaweibo.icon}"/&gt;
	&lt;property name="clientId" value="${config.socialsignon.sinaweibo.client.id}"/&gt;
	&lt;property name="clientSecret" value="${config.socialsignon.sinaweibo.client.secret}"/&gt;
	&lt;property name="sortOrder" value="${config.socialsignon.sinaweibo.sortorder}"/&gt;
&lt;/bean&gt;

&lt;bean id="socialSignOnProviderService" class="org.maxkey.authn.support.socialsignon.service.SocialSignOnProviderService"&gt;
	&lt;property name="socialSignOnProviders" &gt;
		&lt;list&gt;
			&lt;ref bean="socialSignOnWeibo" /&gt;
		&lt;/list&gt;
	&lt;/property&gt;
&lt;/bean&gt;
</code></pre>

<h3>账号绑定</h3>
登录MaxKey，并绑定新浪微博账号

<img src="{{ "/images/authn/authn_s_4.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h3>登录测试</h3>

退出后，进入登录界面，点击新浪微博图标，跳转到新浪微博，输入用户名和密码后，直接登录MaxKey,即MaxKey信任了微博账号，


<h3>第三方支持</h3>
MaxKey使用第三方的登录认证库，认证支持的第三方，请见JustAuth官方说明

<a href="https://docs.justauth.whnb.wang/#/" target="_blank"  alt="JustAuth">
<img src="{{ "/images/authn/justauth.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>
</a>
