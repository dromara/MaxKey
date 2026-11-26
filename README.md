<p align="center" >
    <img src="images/logo_maxkey.png?raw=true"  width="200px"   alt=""/>
</p>
<p align="center">
  <strong>Leading-Edge IAM/IDaas Identity and Access Management Product</strong>
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

# Overview

Dromara <b>MaxKey</b> Single Sign-On System is <b>a leading-edge IAM/IDaaS (Identity and Access Management) Product</b>. Its name is a homophone of "Marx's key," symbolizing how it unlocks complex enterprise security needs like a master key while providing simple and efficient solutions. The product supports OAuth 2.x/OpenID Connect, SAML 2.0, JWT, CAS, SCIM and other standard protocols, and provides <b> Secure , Standard and Open </b> Identity Management (IDM), Access Management (AM), Single Sign-On (SSO), RBAC permission management and Resource Management.

MaxKey focuses on performance, security, and ease of use in enterprise scenarios, is widely used in industries such as healthcare, finance, government, and manufacturing.

MaxKey under <b>Apache License, Version 2.0 is free </b>. Open Source, Secure, Compliance, Independent and Controllable . 

Official Website <a href="https://www.maxkey.top/" target="_blank"><b>https://www.maxkey.top/</b></a>

WeChat: 

<img src="images/wechat.jpg?raw=true" width="200px" alt="官方微信"/>

QQ : <b> 1054466084 </b> 

email: <b> support@maxsso.net </b>

Online Demo  <a href="https://maxkey.top/zh/about/demo.html" target="_blank"><b>Access Online Demo</b></a> 

Issue guide <a href="https://gitee.com/dromara/MaxKey/issues?q=is%3Aclosed">Closed List</a>  | <a href="https://gitee.com/dromara/MaxKey/issues/I65IEQ">New Issue</a>


Code Hosting <a href="https://github.com/dromara/MaxKey" target="_blank"><b>GitHub</b></a> | <a href="https://gitee.com/dromara/MaxKey" target="_blank"><b>Gitee</b></a>  | <a href="https://gitcode.com/dromara/MaxKey/overview" target="_blank"><b>GitCode</b></a>

><b> Single Sign On </b>(<b> SSO </b >),Users only need to login to the authentication center once , access all the trusted application systems without logging in again.
>
>**Key Functions**
>1) All application systems share one Identity authentication system
>2) All application systems can Identify and extract Ticket
 
 
# Features

1.  Standard Protocols

| No.     | Protocols   |  Support  |
| --------| :-----      | :----     |
| 1.1     | OAuth 2.x/OpenID Connect    	| HIGH  |
| 1.2     | SAML 2.0                    	| HIGH  |
| 1.3     | JWT                         	| HIGH  |
| 1.4     | CAS                         	| HIGH  |
| 1.5     | SCIM 2.0                        | HIGH  |
| 1.6     | FormBased                   	| MIDDLE|
| 1.7     | TokenBased(Post/Cookie)     	| MIDDLE|
| 1.8     | ExtendApi                   	| LOW   |
| 1.9     | EXT                         	| LOW   |

2. Authentication

| No.     | SignIn Support  | Support   |
| --------| :-----          | :----     |
| 2.1     | Captcha         | letter / number / arithmetic  | 
| 2.2     | Two Factor Authentication  | SMS / TOPT/ Mail     |
| 2.3     | SMS             | Tencent SMS / Alibaba SMS / NetEaseYunXin     |
| 2.4     | TOTP            | Google/Microsoft Authenticator/FreeOTP/Support TOTP or HOTP |
| 2.5     | Domain          | Kerberos/SPNEGO/AD domain|
| 2.6     | LDAP            | OpenLDAP/ActiveDirectory/Standard LDAP Server |
| 2.7     | Social Account  | WeChat/QQ/ Weibo/DingTalk/Google/Facebook/other  | 
| 2.8     | Scan QR Code    | WorkWeiXin/DingTalk/FeiShu Scan QR Code | 
| 2.9     | No Password     | WebAuthn FIDO2/passkey  | 


3. Standard Authentication Protocols for applications to integrate sso, secure mobile access, secure API, third-party authentication and Internet authentication.

4. Identity Lifecycle management, support SCIM 2 , and The out of the box connector realizes identity supply synchronization.

5. Simplify Microsoft Active Directory , standard LDAP server organization and account management, and reset password through password self-service.

6. The IDaas Multi-Tenancy authentication platform , supports the independent management of multiple enterprises under the group company or the data isolation of different departments under the enterprise, so as to reduce the operation and maintenance cost.

7. The platform independence and diversity of environment. It supports web, mobile phone, mobile devices, such as apple IOS, Android, etc., and covers the certification ability from B/S to mobile applications.

8. Configured password and access policies; Supports precise IP positioning in Ip2region or GeoLite2 geographic databases, powerful security auditing, full lifecycle audit of users, traceability audit of access behavior records, security compliance audit, and security risk warning.

9. Based on Java EE platform , microservice architecture, Use Spring, MySQL, Tomcat, Redis and other open source technologies, and has strong scalability.

10. Open Source, Secure, Compliance, Independent and Controllable . 


# Interface

**MaxKey**

Login UI
<img src="images/maxkey_login.png?raw=true"/>

App List UI
<img src="images/maxkey_index.png?raw=true"/>

**MaxKey Management**

Report UI
<img src="images/maxkey_mgt_rpt.png?raw=true"/>

User Management UI
<img src="images/maxkey_mgt_users.png?raw=true"/>

App Management UI
<img src="images/maxkey_mgt_apps.png?raw=true"/>


# Download

| Version    | Date   |  Download URL  |
| --------   | :----- | :----           | 
| v 4.2.0   | 2026/10/21   | <a href="https://www.maxkey.top/zh/about/download.html"  target="_blank">Download</a> |


# Install

|  OS       | Manual    | 
| --------      | :-----  |
| Windows  | <a href="https://www.maxkey.top/zh/about/download.html"  target="_blank">Document</a>  |
| Linux    | <a href="https://www.maxkey.top/zh/about/download.html"  target="_blank">Document</a>  |
| Docker   | <a href="https://www.maxkey.top/zh/about/download.html"  target="_blank">Document</a>  |
| 宝塔 <a target="_blank" href="https://www.bt.cn/u/AjsXmi"> <img src="https://img.shields.io/badge/BT-Install-20a53a" /></a> | <a href="https://www.maxkey.top/zh/about/download.html"  target="_blank">Document</a>  |


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

# User Registration

<a href="https://github.com/dromara/MaxKey/issues/40" target="_blank"> Click to register </a> as MaxKey user and contribute to MaxKey!

