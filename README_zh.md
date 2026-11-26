<p align="center" >
    <img src="images/logo_maxkey.png?raw=true"  width="200px"   alt=""/>
</p>   
<p align="center">
  <strong>业界领先的IAM-IDaas身份管理和认证产品</strong>
</p>  
<p align="center" >
<a href="README.md" target="_blank"><b>English</b></a>  |  <a href="README_zh.md" target="_blank"><b>中文</b></a>
</p>
<p align="center">
    <a target="_blank" href="http://www.maxkey.top/zh/about/download.html">
        <img src="https://img.shields.io/github/v/release/dromara/MaxKey?style=for-the-badge" />
    </a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/downloads/">
        <img src="https://img.shields.io/badge/JDK-v21%2B-brightgreen?style=for-the-badge" />
    </a>
    <a target="_blank" href="https://www.mysql.com/">
        <img src="https://img.shields.io/badge/MySQL-8.4%2B-brightgreen?style=for-the-badge" />
    </a>
    <a target="_blank" href="http://www.maxkey.top/zh/about/licenses.html">
        <img src="https://img.shields.io/github/license/dromara/MaxKey?style=for-the-badge" />
    </a>
    
</p>

# 概述

Dromara <b>MaxKey</b>单点登录认证系统是<b>业界领先的IAM-IDaas身份管理和认证产品</b>，谐音为马克思的钥匙，寓意它能够像一把万能钥匙(最大钥匙)一样，解锁复杂的企业安全需求，提供简洁而高效的解决方案。产品支持OAuth 2.x/OpenID Connect、SAML 2.0、JWT、CAS、SCIM等标准协议，提供<b>安全、标准和开放</b>的用户身份管理(IDM)、身份认证(AM)、单点登录(SSO)、RBAC权限管理和资源管理等。

MaxKey注重企业级场景下的性能、安全和易用性，广泛应用于医疗、金融、政府和制造等行业。

MaxKey <b>遵循 Apache License, Version 2.0 开源免费</b>，开源、安全、合规、自主可控。

官方网站  <a href="https://www.maxkey.top/" target="_blank"><b>https://www.maxkey.top/</b></a> 

官方微信: 

<img src="images/wechat.jpg?raw=true" width="200px"  alt="官方微信"/>

官方QQ：<b>1054466084</b> 

邮箱email: <b>support@maxsso.net</b>

在线演示  <a href="https://maxkey.top/zh/about/demo.html" target="_blank"><b>访问演示系统</b></a> 

问题(Issue)指南 <a href="https://gitee.com/dromara/MaxKey/issues?q=is%3Aclosed">已解决问题列表</a>  | <a href="https://gitee.com/dromara/MaxKey/issues/I65IEQ">我要提新问题</a>

代码托管 <a href="https://gitee.com/dromara/MaxKey" target="_blank"><b>Gitee</b></a> | <a href="https://github.com/dromara/MaxKey" target="_blank"><b>GitHub</b></a> | <a href="https://gitcode.com/dromara/MaxKey/overview" target="_blank"><b>GitCode</b></a>

 
><b>单点登录(Single Sign On）</b>简称为<b>SSO</b>，用户只需要登录认证中心一次就可以访问所有相互信任的应用系统，无需再次登录。
>
>**主要功能：**
>1) 所有应用系统共享一个身份认证系统
>2) 所有应用系统能够识别和提取ticket信息
 
# 开源推荐
- [[ JinBooks财务记账系统 ]](https://gitee.com/jinbooks/jinbooks)： 对标商业财务记账产品，告别Excel手工记账，实现记账无纸化办公
- [[ CeresCRM客户管理系统 ]](https://gitee.com/jinbooks/cerescrm)： 轻量级CRM客户关系管理产品，聚焦线索、商机、客户、合同、回款
- [[ Surpass API权限开放平台 ]](https://gitee.com/tomsun28/bootshiro)： 基于RBAC的API和权限开放平台，统一企业权限管理&基于SQL零代码开发 API 服务

# 产品特性

1. 标准协议

| 序号    | 协议    |  支持   |
| --------| :-----  | :----   |
| 1.1     | OAuth 2.x/OpenID Connect        |  高  |
| 1.2     | SAML 2.0                        |  高  |
| 1.3     | JWT                             |  高  |
| 1.4     | CAS                             |  高  |
| 1.5     | SCIM 2.0                        | 高   |
| 1.6     | FormBased                       |  中  |
| 1.7     | TokenBased(Post/Cookie)         |  中  |
| 1.8     | ExtendApi                       |  低  |
| 1.9     | EXT                             |  低  |


2. 登录支持

| 序号    | 登录方式      |   支持  |
| --------| :-----        | :----   |
| 2.1     | 动态验证码    | 字母/数字/算术   | 
| 2.2     | 双因素认证    | 短信/时间令牌/邮件 | 
| 2.3     | 短信认证      | 腾讯云短信/阿里云短信/网易云信  |
| 2.4     | 时间令牌      | Google/Microsoft Authenticator/FreeOTP/支持TOTP或者HOTP |
| 2.5     | 域认证        | Kerberos/SPNEGO/AD域 |
| 2.6     | LDAP          | OpenLDAP/ActiveDirectory/标准LDAP服务器 |
| 2.7     | 社交账号      | 微信/QQ/微博/钉钉/Google/Facebook/其他  | 
| 2.8     | 扫码登录      | 企业微信/钉钉/飞书扫码登录  | 
| 2.9     | 无密码登录    | WebAuthn FIDO2/passkey  | 


3. 提供标准的认证接口以便于其他应用集成SSO，安全的移动接入，安全的API、第三方认证和互联网认证的整合。

4. 提供用户生命周期管理，支持SCIM 2协议；开箱即用的连接器(Connector)实现身份供给同步。

5. 简化微软Active Directory域控、标准LDAP服务器机构和账号管理，密码自助服务重置密码。

6. IDaas多租户功能，支持集团下多企业独立管理或企业下不同部门数据隔离的，降低运维成本。

7. 认证中心具有平台无关性、环境多样性，支持Web、手机、移动设备等, 如Apple iOS，Andriod等，将认证能力从B/S到移动应用全面覆盖。

8.  配置化的密码策略、访问策略；支持Ip2region或GeoLite2地理库精准IP定位 ，强大安全审计，对用户全生命周期审计、访问行为记录追溯审计、安全合规审计、安全风险预警。

9. 基于Java EE平台，微服务架构，采用Spring、MySQL、Tomcat、Redis等开源技术，扩展性强。  

10. 开源、安全、合规、自主可控。 


# 界面

**MaxKey认证**

登录界面
<img src="images/maxkey_login.png?raw=true"/>
 
主界面
<img src="images/maxkey_index.png?raw=true"/>

**MaxKey管理**

访问报表
<img src="images/maxkey_mgt_rpt.png?raw=true"/>

用户管理
<img src="images/maxkey_mgt_users.png?raw=true"/>

应用管理
<img src="images/maxkey_mgt_apps.png?raw=true"/>


# 下载

| 版本       | 日期    |  下载              | 
| --------   | :-----  | :----              | 
| v 4.2.0   | 2026/10/21   | <a href="https://www.maxkey.top/zh/about/download.html" target="_blank">下载</a>|
 
 
# 安装部署

| 操作系统       | 安装手册    |
| --------      | :-----  | 
| Windows  | <a href="https://www.maxkey.top/zh/about/download.html"  target="_blank">链接</a>  | 
| Linux    | <a href="https://www.maxkey.top/zh/about/download.html"  target="_blank">链接</a>  | 
| Docker   | <a href="https://www.maxkey.top/zh/about/download.html"  target="_blank">链接</a>  | 
| 宝塔 <a target="_blank" href="https://www.bt.cn/u/AjsXmi"> <img src="https://img.shields.io/badge/BT-Install-20a53a" /></a>| <a href="https://www.maxkey.top/zh/about/download.html"  target="_blank">链接</a>  |
 
 
# License
 
<a href="http://www.maxkey.top/zh/about/licenses.html" target="_blank">Apache License, Version 2.0</a>

 
# 中国信通院零信任实验室
 
 <a href="https://mp.weixin.qq.com/s/2T9TCo3EP0o9bD8ArAjUkw" target="_blank">中国信通院零信任实验室</a>
 
# 零信任标准工作组
 
 <a href="https://gitee.com/zero-trust/ZeroTrust" target="_blank">国内最权威的零信任产业组织</a>
 
# Gitee最有价值开源项目GVP

<a href="http://maxkey.top/zh/about/welcome.html" target="_blank">Gitee-最有价值开源项目GVP</a>

# Dromara社区
<a href="https://dromara.org/zh/" target="_blank">**Dromara**</a>致力于微服务云原生解决方案的组织。

 - **开放** 技术栈全面开源共建、 保持社区中立、兼容社区 兼容开源生态
 
 - **愿景** 让每一位开源爱好者，体会到开源的快乐
 
 - **口号** 为往圣继绝学，一个人或许能走的更快，但一群人会走的更远
 
# 知识星球
 
 <img src="images/zsxq.png?raw=true"/>
 
# 接入登记

<a href="https://gitee.com/dromara/MaxKey/issues/I2BNRZ" target="_blank"> 点击进行接入登记</a>，为 MaxKey的发展贡献自己的力量！

