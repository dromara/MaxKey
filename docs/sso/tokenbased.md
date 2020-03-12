<h2>TokenBased应用集成</h2>
本文介绍TokenBased应用如何与MaxKey进行集成。

<h2>应用注册</h2>

应用在MaxKey管理系统进行注册，注册的配置信息如下

<img src="{{ "/images/sso/sso_token_conf.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

LTPA使用Cookie传输令牌

<img src="{{ "/images/sso/sso_token_ltpa_conf.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>


<h2>TokenBased客户端集成</h2>

本文使用JAVA WEB程序为例

jar包依赖如下

https://github.com/shimingxy/MaxKey-Demo/tree/master/maxkey-demo-tokenbase/lib


JSP实现Code

<h4>简单令牌</h4>

https://github.com/shimingxy/MaxKey-Demo/blob/master/maxkey-demo-tokenbase/src/main/webapp/jsontoken.jsp

<h4>基于JSON令牌</h4>

https://github.com/shimingxy/MaxKey-Demo/blob/master/maxkey-demo-tokenbase/src/main/webapp/sampletoken.jsp