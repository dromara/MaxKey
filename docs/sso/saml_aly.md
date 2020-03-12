<h2>阿里云集成</h2>
本文介绍阿里云使用SAML如何与MaxKey进行集成。

<h2>应用注册</h2>

1)首先需要注册<a href="https://www.aliyun.com/" target="_blank" >阿里云</a>，并开通SAML认证功能，下载阿里云SAML元数据，具体说明文档请参照

访问控制 &gt; 单点登录管理（SSO） &gt; <a href="https://helpcdn.aliyun.com/document_detail/93684.html?spm=a2c4g.11186623.3.2.39a64d7bn76ODT" target="_blank" >SSO概览 </a>


2)应用在MaxKey管理系统进行注册，注册的配置信息如下，注册时需要提供阿里云SAML元数据

<img src="{{ "/images/sso/sso_saml_aly_conf.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>


扩展属性配置,把阿里云的3个相关属性配置到MaxKey中

<img src="{{ "/images/sso/sso_saml_aly_conf_ex.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

3)把MaxKey元数据上传到阿里云，参照阿里云文档