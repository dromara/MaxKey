# <img src="images/logo_maxkey.png?raw=true"  width="200px"   alt=""/>

<a href="README_en.md" target="_blank"><b>English</b></a>  |  <a href="README_zh.md" target="_blank"><b>中文</b></a>

# Overview

<b>Maxkey </b> Single Sign On system, which means the Maximum key, <b>Leading-Edge Enterprise-Class IAM Identity and Access management product </b>, Support OAuth 2.x/OPENID CONNECT, SAML 2.0, JWT, CAS, SCIM and other standard protocols, and provide <b> Simple, Standard, Secure and Open </b> Identity management (IDM), Access management (AM), Single Sign On (SSO), RBAC permission management and Resource management.

Official Website <a href="https://www.maxkey.top" target="_blank"><b>Official</b></a> |  <a href="https://maxkeytop.gitee.io" target="_blank"><b>Line2</b></a>

QQ Community: <b> 434469201 </b> 

email: <b> maxkeysupport@163.com </b>

Code Hosting <a href="https://github.com/dromara/MaxKey" target="_blank"><b>GitHub</b></a> | <a href="https://gitee.com/dromara/MaxKey" target="_blank"><b>Gitee</b></a>

<b> Single Sign On </b> (<b> SSO </b >),Users only need to login to the authentication center once , access all the trusted application systems without logging in again.

Key Functions

1) All application systems share one Identity authentication system

2) All application systems can Identify and extract Ticket
 
 
# Features

1.  Standard Protocols

| No.     | Protocols   |  Support  |
| --------| :-----      | :----     |
| 1.1     | OAuth 2.x/OpenID Connect    	| HIGH  |
| 1.2     | SAML 2.0                    	| HIGH  |
| 1.3     | JWT                         	| HIGH  |
| 1.4     | CAS                         	| HIGH  |
| 1.5     | FormBased                   	| MIDDLE|
| 1.6     | TokenBased(Post/Cookie)     	| MIDDLE|
| 1.7     | ExtendApi                   	| LOW   |
| 1.8     | EXT                         	| LOW   |

2. Authentication

| No.     | SignIn Support  | Support   |
| --------| :-----          | :----     |
| 2.1     | Captcha         | letter / number / arithmetic  | 
| 2.2     | Two Factor Authentication  | SMS / TOPT/ Mail     |
| 2.3     | SMS             | Tencent SMS / Alibaba SMS / NetEaseYunXin     |
| 2.4     | TOTP            | Denglu1/Google/Microsoft Authenticator/FreeOTP/Support TOTP or HOTP |
| 2.5     | Domain          | Kerberos/SPNEGO/AD domain|
| 2.6     | LDAP            | OpenLDAP/ActiveDirectory/Standard LDAP Server |
| 2.7     | Social Account  | WeChat/QQ/ Weibo/DingTalk/Google/Facebook/other  | 
| 2.8     | Scan QR Code    | WorkWeiXin/DingTalk Scan QR Code | 


3. Standard Authentication Protocols for applications to integrate sso, secure mobile access, secure API, third-party authentication and Internet authentication.

4. Identity Lifecycle management, support SCIM 2 , and realize Identity supply synchronization through connector based on Apache Kafka agent.

5. The Multi-Tenancy authentication platform , supports the independent management of multiple enterprises under the group company or the data isolation of different departments under the enterprise, so as to reduce the operation and maintenance cost.

6. The platform independence and diversity of environment. It supports web, mobile phone, mobile devices, such as apple IOS, Android, etc., and covers the certification ability from B/S to mobile applications.

7. Variety of authentication mechanisms coexist, each application system can retain the original authentication mechanism, and integrate the authentication of the MaxKey; the application has a high degree of independence, does not rely on the MaxKey, and can use the authentication of the MaxKey to realize single sign on.

8. Based on Java EE platform , microservice architecture, Adopts Spring, MySQL, Tomcat, Redis , Apache Kafka and other open source technologies, and has strong scalability.

9. Open Source, Secure,  Independent and Controllable , License Under Apache 2.0 License & <a href="https://maxkey.top/zh/about/licenses.html" target="_blank">MaxKey copyright</a>. 


# UI

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

Download the current version from Baidu Pan,<a href="https://maxkey.top/zh/about/download.html" target="_blank"> history version</a>

| Version    | Date   |  Docker  |  Pan URL  |  Pan Code  |
| --------   | :----- | :----    | :----     | :----      |
| v 3.1.1 GA | 2021/11/30   |<a href="https://hub.docker.com/u/maxkeytop" target="_blank">Home</a>  |  <a href="https://pan.baidu.com/s/1s4Jq0PYL-P6e42FqGhnrKA" target="_blank">Download</a>  |  **mxk9**  |


# Roadmap

| No.     | Plan    |  Date   |
| --------| :-----  | :----   |
| 1     | Maxkey-Cloud (micro service support)                      |  2021Q3  |
| 2     | Zero trust scenario integration                           |  2021Q4  |
| 3     | OAuth 2.1                                                 |  2022Q1  |
| 4     | React, and Ant Design                                     |  2022Q2  |
| 5     | OpenID Connect optimize                                   |  2022Q2  |
| 6     | Java 17+                                                  |  2022Q3  |
| 7     | Jakarta EE 9+                                             |  2022Q3  |
| 8     | Spring Framework 6                                        |  2022Q4  |
| 9     | Spring Boot 3                                             |  2022Q4  |

# User Registration

<a href="https://github.com/dromara/MaxKey/issues/40" target="_blank"> Click to register </a> as MaxKey user and contribute to MaxKey!

以下为部分接入或测试中的用户
| 序号    | 单位    |  行业   |
| --------| :-----  | :----   |
| 1.1     | 兰州现代职业学院                            |  院校  |
| 1.2     | 长春职业技术学院                            |  院校  |
| 1.3     | 重庆市北碚图书馆                            |  教育  |
| 1.4     | 德清智慧教育平台                            |  教育  |
| 2.1     | 国家能源局                                  |  企业  |
| 2.2     | 360公司                                     |  企业  |
| 2.3     | 深圳市金溢科技股份有限公司                  |  企业  |
| 2.4     | 之江实验室                                  |  企业  |
| 2.5     | 深圳市中悦科技有限公司                      |  企业  |
| 3.1     | 国元证券                                    |  金融  |
| 3.2     | 华夏金融租赁有限公司                        |  金融  |
| 3.3     | 宁波金融资产交易中心                        |  金融  |
| 3.4     | 路特斯中国                                  |  汽车  |
| 3.5     | 北京银泰置业有限公司                        |  企业  |
| 3.6     | 遂宁市经济大数据平台                        |  企业  |
| 3.7     | 同方节能工程技术有限公司                    |  企业  |
| 3.8     | 云南航天工程物探检测股份有限公司            |  企业  |
| 3.9     | 山东港口陆海国际物流集团有限公司            |  企业  |
| 3.10    | 广州无线电集团                              |  企业  |
| 3.11    | 河南新辰环保科技有限公司                    |  企业  |
| 2.12    | 三一华兴                                    |  企业  |
| 2.13    | unicon.net                                  |  企业  |
| 3.14    | 广州思迈特软件有限公司                      |  科技  |
| 3.15    | 尚企云链                                    |  科技  |
| 4.1     | 北京博亚思科技有限公司                      |  科技  |
| 4.2     | 武汉英特沃科技有限公司                      |  科技  |
| 4.3     | 江苏创致信息科技有限公司                    |  科技  |
| 4.4     | 江西云车科技有限公司                        |  科技  |
| 4.5     | 无锡市陶都巨龙软件有限责任公司              |  科技  |