# <img src="images/logo_maxkey.png?raw=true"  width="200px"   alt=""/>

<a href="README_en.md" target="_blank"><b>English</b></a>  |  <a href="README_zh.md" target="_blank"><b>中文</b></a>

# Overview

<b>Maxkey </b> Single Sign On system, which means the Maximum key, <b>Leading-Edge Enterprise-Class IAM Identity and Access management product </b>, Support OAuth 2.0/OPENID CONNECT, SAML 2.0, JWT, CAS, SCIM and other standard protocols, and provide <b> Simple, Standard, Secure and Open </b> Identity management (IDM), Access management (AM), Single Sign On (SSO), RBAC permission management and Resource management.

Official Website <a href="https://www.maxkey.top" target="_blank"><b>Official</b></a> |  <a href="https://maxkeytop.gitee.io" target="_blank"><b>Line2</b></a>

QQ Community: <b> 434469201 </b> 

email: <b> maxkeysupport@163.com </b>

Code Hosting <a href="https://github.com/dromara/MaxKey" target="_blank"><b>GitHub</b></a> | <a href="https://gitee.com/dromara/MaxKey" target="_blank"><b>Gitee</b></a>

<b> Single Sign On </b>(<b> SSO </b >),Users only need to login to the authentication center once , access all the trusted application systems without logging in again.

Key Functions

1) All application systems share one Identity authentication system

2) All application systems can Identify and extract Ticket
 
 
# Features

1.  Standard Protocols

| No.     | Protocols   |  Support  |
| --------| :-----  	| :----  	|
| 1.1     | OAuth 2.0/OpenID Connect   	| HIGH  |
| 1.2     | SAML 2.0   				 	| HIGH  |
| 1.3     | JWT  					    | HIGH  |
| 1.4     | CAS						 	| HIGH  |
| 1.5     | FormBased				    | MIDDLE|
| 1.6     | TokenBased(Post/Cookie)    	| MIDDLE|
| 1.7     | ExtendApi				    | LOW   |
| 1.8     | EXT						 	| LOW   |

2. Authentication

| No.     | SignIn Support  | Support   |
| --------| :-----  		| :----  	|
| 2.1     | Captcha			| letter / number / arithmetic 	| 
| 2.2     | Two Factor Authentication  | SMS / TOPT/ Mail     |
| 2.3     | SMS				| Tencent SMS / Alibaba SMS / NetEaseYunXin 	|
| 2.4     | TOTP			| Denglu1/Google/Microsoft Authenticator/FreeOTP/Support TOTP or HOTP |
| 2.5     | Domain          | Kerberos/SPNEGO/AD domain|
| 2.6     | LDAP 			| OpenLDAP/ActiveDirectory/Standard LDAP Server |
| 2.7     | Social Account  | WeChat/QQ/ Weibo/Dingding/Google/Facebook/other  | 


3. Standard Authentication Protocols for applications to integrate sso, secure mobile access, secure API, third-party authentication and Internet authentication.

4. Identity Lifecycle management, support SCIM 2 , and realize Identity supply synchronization through connector based on Apache Kafka agent.

5. The platform independence and diversity of environment. It supports web, mobile phone, mobile devices, such as apple IOS, Android, etc., and covers the certification ability from B/S to mobile applications.

6. Variety of authentication mechanisms coexist, each application system can retain the original authentication mechanism, and integrate the authentication of the MaxKey; the application has a high degree of independence, does not rely on the MaxKey, and can use the authentication of the MaxKey to realize single sign on.

7. Based on Java platform, Adopts Spring, MySQL, Tomcat, Apache Kafka, Redis and other open source technologies, supports microservices, and has strong scalability.

8. Open Source, Secure,  Independent and Controllable , License Under Apache 2.0 License & <a href="https://maxkey.top/zh/about/licenses.html" target="_blank">MaxKey copyright</a> . 


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

Download the current version of Baidu Pan,<a href="https://maxkey.top/zh/about/download.html" target="_blank"> history version</a>

| Version    | ReleaseDate   |  Download URL  |  Code  |
| --------| :-----  | :----  | :----  |
| v 2.8.1 GA | 2021/06/25    |  <a href="https://pan.baidu.com/s/1XXzqEMZqBIaUAzRIDm9iSg" target="_blank">Download</a>  |  **dplq**  |
 

# Roadmap

1. Zero trust scenario integration

2. Maxkey-Cloud (micro service version)-2021

# User Registration

<a href="https://github.com/dromara/MaxKey/issues/40" target="_blank"> Click to register </a> as MaxKey user and contribute to MaxKey!

以下为部分接入或测试中的用户
| 序号    | 单位   	|  行业   |
| --------| :-----  | :----  |
| 1.1     | 兰州现代职业学院						 	|  院校  |
| 1.2     | 无锡立信高等职业技术学校				    |  院校  |
| 1.3     | 长春职业技术学院						    |  院校  |
| 2.1     | 深圳市金溢科技股份有限公司    				|  企业  |
| 2.2     | 之江实验室				    				|  企业  |
| 2.3     | 深圳市中悦科技有限公司						|  企业  |
| 2.4     | 遂宁市经济大数据平台    					|  企业  |
| 2.5     | 同方节能工程技术有限公司    				|  企业  |
| 2.6     | 云南航天工程物探检测股份有限公司    		|  企业  |
| 2.7     | 山东港口陆海国际物流集团有限公司    		|  企业  |
| 2.8     | 河南新辰环保科技有限公司					|  企业  |
| 2.9     | 华夏金融租赁有限公司    					|  金融  |
| 2.10    | 宁波金融资产交易中心    					|  金融  |
| 2.11    | 武汉英特沃科技有限公司   				 	|  科技  |
| 2.12    | 江苏创致信息科技有限公司  					|  科技  |
| 2.13    | NGROK(ngrok.io)					  			|  科技  |
| 3.1     | 王朔日记    								|  个人  |
| 3.2     | 一席南风（张彬）    						|  个人  |
| 3.3     | glzpcw    									|  个人  |
| 3.4     | ynduck    									|  个人  |