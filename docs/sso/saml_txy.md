<h2>腾讯云集成</h2>
本文介绍腾讯云使用SAML如何与MaxKey进行集成。

<h2>应用注册</h2>

1)首先需要注册<a href="https://cloud.tencent.com/" target="_blank" >腾讯云</a>，并开通SAML认证功能，下载腾讯云SAML元数据，具体说明文档请参照

文档中心&gt;访问管理&gt;用户指南&gt;<a href="https://cloud.tencent.com/document/product/598/30283" target="_blank" >身份提供商 </a>

2)应用在MaxKey管理系统进行注册，注册的配置信息如下，注册时需要提供腾讯云SAML元数据

<img src="{{ "/images/sso/sso_saml_txy_conf.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

扩展属性配置,把腾讯云的3个相关属性配置到MaxKey中

<img src="{{ "/images/sso/sso_saml_txy_conf_ex.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

3)把MaxKey元数据上传到腾讯云，参照腾讯云文档