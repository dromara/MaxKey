<h1>MaxKey身份管理</h1>

<h3>身份管理是什么</h3>
**帐户**

计算机处理有关人的数据记录。此类记录包含为其创建和管理帐户的系统所需的技术信息。

**（数字）身份**

由一个数字主体对其自身提出的一组主张的表现。 确认是你！

您是否曾经被公司雇用，进入组织或刚刚创建了新的OA帐户？公司，组织和云实体使用需要您的数据才能正常运行的应用程序：用户名，密码，电子邮件，名字，姓氏等。

这些信息从哪里来？当需要启用更多应用程序时会发生什么？而且，如果您获得晋升并获得了已经可以访问的应用程序的更多权利该怎么办？最重要的是，当您退出或他们轻轻放开您时会发生什么？

简而言之，身份管理在整个“ 身份生命周期”中负责管理身份数据 。


<img src="{{ "/images/im/identityLifecycle.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h3>MaxKey身份管理架构</h3>

基于**Apache Kafka**和MaxKey身份连接器(**Connector**)的管理架构

<img src="{{ "/images/im/maxkey_im.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>


<h3>连接器Connector</h3>

ActiveDirectory Connector


LDAP Connector


JDBC Connector


SCIM 2 Connector


企业微信 Connector


钉钉 Connector


other coming soon