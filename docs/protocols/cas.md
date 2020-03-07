<h2>1、CAS简介</h2>

CAS 是 Yale 大学发起的一个开源项目，旨在为 Web 应用系统提供一种可靠的单点登录方法，CAS 在 2004 年 12 月正式成为 JA-SIG 的一个项目。CAS 具有以下特点：

【1】开源的企业级单点登录解决方案。

【2】CAS Server 为需要独立部署的 Web 应用。

【3】CAS Client 支持非常多的客户端(这里指单点登录系统中的各个 Web 应用)，包括 Java, .Net, PHP, Perl, Apache, uPortal, Ruby 等。

<h2>2、CAS体系结构</h2>
CAS 体系包含两个部分： CAS Server 和 CAS Client。CAS Server 需要独立部署，主要负责对用户的认证工作；CAS Client 负责处理对客户端受保护资源的访问请求，需要登录时，重定向到 CAS Server。
<img src="{{ "/images/cas/1.jpg" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h2>3、CAS原理</h2>
CAS 最基本的协议过程：
<img src="{{ "/images/cas/2.jpg" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>
 
SSO单点登录访问流程主要有以下步骤：

1. 访问服务：SSO客户端发送请求访问应用系统提供的服务资源。

2. 定向认证：SSO客户端会重定向用户请求到SSO服务器。

3. 用户认证：用户身份认证。

4. 发放票据：SSO服务器会产生一个随机的Service Ticket。

5. 验证票据：SSO服务器验证票据Service Ticket的合法性，验证通过后，允许客户端访问服务。

6. 传输用户信息：SSO服务器验证票据通过后，传输用户认证结果信息给客户端。


<h2>4、 CAS中3个术语</h2>

Ticket Granting ticket (TGT) ：可以认为是CAS Server根据用户名密码生成的一张票，存在Server端

Ticket-granting cookie (TGC) ：其实就是一个Cookie，存放用户身份信息，由Server发给Client端

Service ticket (ST) ：由TGT生成的一次性票据，用于验证，只能用一次。相当于Server发给Client一张票，然后Client拿着这个票再来找Server验证，看看是不是Server签发的。