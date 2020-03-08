<h2>1 SAML 介绍</h2>
 	
SAML即安全断言标记语言，英文全称是Security Assertion Markup Language。它是一个基于XML的标准，用于在不同的安全域(security domain)之间用户身份验证和授权数据交换。在SAML标准定义了身份提供者(identity provider)和服务提供者(service provider)，这两者构成了前面所说的不同的安全域。 SAML是OASIS组织安全服务技术委员会(Security Services Technical Committee)的产品。官方技术说明可参看OASIS Security Services (SAML) TC.
    
使用SAML，在线服务供应商可以联系一个独立的网络身份认证提供者，谁是试图访问受保护的内容的用户进行身份验证。

联邦是指两个或更多可信的业务合作伙伴组成的团体，其遵照的业务和技术协议允许来自联邦合作伙伴(成员公司)的用户以一种安全可靠的方式，无缝地访问另一家合作伙伴的资源。在联邦业务模型中(其中，服务是联邦化的，或可以与业务合作伙伴共享)，根据有关实体间达成的协议，一家公司的用户的身份将被转换，以合法访问另一家公司的Web站点，而另一家公司无需了解该用户的原始身份。


IDP认证中心提供了一个基于SAML的单点登录（SSO）服务，作为身份提供者(Identity provider)，控制用户名、密码和其他信息，用于识别，身份验证和授权用户的Web应用程序。

备注：SAML应用集成需完成应用集成申请，详见SAML相关内容。

通过SAML实现IDP 与其他合作伙伴的联邦身份认证。

<h5>流程说明图</h5>
<img src="{{ "/images/saml/saml1.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<h3>SAML实现联邦身份认证各方职责</h3>
	
<table border="0" class="table table-striped table-bordered ">
    <thead>
      <th>IDP认证中心(Identity Provider/IDP)</th><th>合作伙伴(Service Provider/SP)</th>
    </thead>
    <tbody>
	    <tr>
	        <td>用户身份认证</td>
	        <td>安全断言判定</td>
	    </tr>
	    <tr>
	        <td>联邦身份安全断言</td>
	        <td>联邦身份维护</td>
	    </tr>
	    <tr>
	        <td>用户账号管理</td>
	        <td>服务提供和访问控制</td>
	    </tr>
    
    </tbody>
    </table>


IDP和SP预先完成证书的互信配置，SAML认证基于断言，断言基于证书的加密，传递过程是安全的，只有证书的持有者才能对断言进行解析

重要注意:SAML SSO解决方案仅适用于Web应用程序.

扩展阅读参看：官方技术说明<a href="https://wiki.oasis-open.org/security/FrontPage"  title="https://wiki.oasis-open.org/security/FrontPage" target="_blank" rel="nofollow">SAML标准（英文） </a> | <a href="http://en.wikipedia.org/wiki/Security_Assertion_Markup_Language"  title="http://en.wikipedia.org/wiki/Security_Assertion_Markup_Language" target="_blank" rel="nofollow">SAML维基百科（中文）</a> 

<h2>2 SP-Init SSO流程	</h2>
<img src="{{ "/images/saml/saml2.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

用户试图访问IDP的合作伙伴应用。

合作伙伴应用生成一个SAML身份验证请求。SAML请求编码并嵌入到URL IDP的SSO服务。RelayState参数包含编码的合作伙伴应用程序，用户尝试访问的URL也被嵌入在SSO URL。这的RelayState参数，就是要一个不透明的标识符，不作任何修改或检查传回的。

合作伙伴发送重定向到用户的浏览器。重定向URL编码SAML身份验证请求的，应提交到IDP的SSO服务。

IDP的SAML请求进行解码，并提取两个谷歌的断言消费服务（ACS）和用户的目标URL（RelayState参数）的URL。

IDP的用户进行身份验证。IDP可以通过要求有效的登录凭据，或通过检查有效的会话对用户进行身份验证。

IDP生成一个SAML响应，其中包含身份验证的用户的用户名。按照SAML 2.0规范，这种反应是公共和私人合作伙伴的DSA / RSA密钥数字签名的

IDP SAML响应和RelayState参数进行编码，并将该信息返回到用户的浏览器。IDP提供了一种机制，使浏览器可以转发信息到合作伙伴的ACS。

合作伙伴的ACS使用IDP的公钥验证SAML响应。如果成功验证的响应，ACS将用户重定向的目标URL。

用户被重定向的目标URL，并记录在合作伙伴应用程序。

<h2>3 IDP-Init SSO流程</h2>
<img src="{{ "/images/saml/saml3.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

IDP的用户进行身份验证。IDP可以通过要求有效的登录凭据，或通过检查有效的会话对用户进行身份验证。

IDP生成一个SAML响应，其中包含身份验证的用户的用户名。按照SAML 2.0规范，这种反应是公共和私人合作伙伴的DSA / RSA密钥数字签名的。

IDP SAML响应和RelayState参数进行编码，并将该信息返回到用户的浏览器。IDP提供了一种机制，使浏览器可以转发信息到合作伙伴的ACS。

合作伙伴的ACS使用IDP的公钥验证SAML响应。如果成功验证的响应，ACS将用户重定向的目标URL。

用户被重定向的目标URL，并记录在合作伙伴应用程序。