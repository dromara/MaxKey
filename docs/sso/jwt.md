<h2>JWT应用集成</h2>
本文介绍JWT应用如何与MaxKey进行集成。

<h2>应用注册</h2>

应用在MaxKey管理系统进行注册，注册的配置信息如下

<img src="{{ "/images/sso/sso_jwt_conf.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h2>JWT客户端集成</h2>

本文使用JAVA WEB程序为例

jar包依赖如下

https://github.com/shimingxy/MaxKey-Demo/tree/master/maxkey-demo-jwt/lib


JSP实现Code

https://github.com/shimingxy/MaxKey-Demo/blob/master/maxkey-demo-jwt/src/main/webapp/jwtcallback.jsp
