<p align="center" >
    <img src="images/logo_maxkey.png?raw=true"  width="200px"   alt=""/>
</p>
<p align="center">
  <strong>Leading-Edge IAM/IDaas Identity and Access Management Product</strong>
</p>     
<p align="center" >
<a href="README_en.md" target="_blank"><b>English</b></a>  |  <a href="README_zh.md" target="_blank"><b>中文</b></a>
</p>     
<p align="center">
    <a target="_blank" href="http://www.maxkey.top/zh/about/download.html">
        <img src="https://img.shields.io/github/v/release/dromara/MaxKey" />
    </a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/downloads/">
        <img src="https://img.shields.io/badge/JDK-v17%2B-brightgreen" />
    </a>
    <a target="_blank" href="https://www.mysql.com/">
        <img src="https://img.shields.io/badge/MySQL-8.0.12%2B-brightgreen" />
    </a>
    <a target="_blank" href="http://www.maxkey.top/zh/about/licenses.html">
        <img src="https://img.shields.io/github/license/dromara/MaxKey" />
    </a>
</p>

# Overview

<b>Maxkey </b> Single Sign On System, which means the Maximum key, <b>Leading-Edge IAM/IDaas Identity and Access Management product </b>, Support OAuth 2.x/OpenID Connect, SAML 2.0, JWT, CAS, SCIM and other standard protocols, and provide <b> Secure, Standard and Open </b> Identity management (IDM), Access management (AM), Single Sign On (SSO), RBAC permission management and Resource management.

MaxKey focuses on performance, security, and ease of use in enterprise scenarios, is widely used in industries such as healthcare, finance, government, and manufacturing.

Official Website <a href="http://www.maxkey.top/" target="_blank"><b>http://www.maxkey.top/</b></a>

WeChat: 

<img src="images/wechat.jpg?raw=true" width="200px" alt="官方微信"/>

QQ : <b> 1054466084 </b> 

email: <b> support@maxsso.net </b>

Code Hosting <a href="https://github.com/dromara/MaxKey" target="_blank"><b>GitHub</b></a> | <a href="https://gitee.com/dromara/MaxKey" target="_blank"><b>Gitee</b></a>

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


3. Standard Authentication Protocols for applications to integrate sso, secure mobile access, secure API, third-party authentication and Internet authentication.

4. Identity Lifecycle management, support SCIM 2 , and The out of the box connector realizes identity supply synchronization.

5. Simplify Microsoft Active Directory , standard LDAP server organization and account management, and reset password through password self-service.

6. The IDaas Multi-Tenancy authentication platform , supports the independent management of multiple enterprises under the group company or the data isolation of different departments under the enterprise, so as to reduce the operation and maintenance cost.

7. The platform independence and diversity of environment. It supports web, mobile phone, mobile devices, such as apple IOS, Android, etc., and covers the certification ability from B/S to mobile applications.

8. Based on Java EE platform , microservice architecture, Use Spring, MySQL, Tomcat, Redis , MQ and other open source technologies, and has strong scalability.

9. Open Source, Secure,  Independent and Controllable. 


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

Download the current version from Baidu Pan,<a href="http://www.maxkey.top/zh/about/download.html" target="_blank"> history version</a>

| Version    | Date   |  Pan URL (Code) |  Docker  |
| --------   | :----- | :----           | :----    |
| v 4.0.1   | 2023/09/19   | <a href="https://pan.baidu.com/s/1pY_V6rIOnGLaOZtdllwDlw" target="_blank">Download</a> ( **mxk9** )  | <a href="https://hub.docker.com/u/maxkeytop" target="_blank">Home</a>  |

# Install

|  OS       | Manual    | 
| --------      | :-----  |
| Windows  | <a href="http://maxkey.top/zh/conf/tutorial.html?#windows"  target="_blank">Document</a>  |
| Linux    | <a href="http://maxkey.top/zh/conf/tutorial.html?#linux"  target="_blank">Document</a>  |
| Docker   | <a href="http://maxkey.top/zh/conf/deploy_docker.html"  target="_blank">Document</a>  |

# License
 
<a href="https://www.apache.org/licenses/LICENSE-2.0.html" target="_blank"> Apache License, Version 2.0 </a>& <a href="http://www.maxkey.top/zh/about/licenses.html" target="_blank">MaxKey copyright NOTICE</a>


# User Registration

<a href="https://github.com/dromara/MaxKey/issues/40" target="_blank"> Click to register </a> as MaxKey user and contribute to MaxKey!

以下为部分接入或测试中的用户

| 单位    |
| :-----  |
| 中国人民警察大学                            |
| 兰州现代职业学院                            |
| 长春职业技术学院                            |
| 云南师范大学                                |
| 云南农业职业技术学院                        |
| 惠州卫生职业技术学院                        |
| 宜昌市三峡中等专业学校                      |
| 重庆市北碚图书馆                            |
| 天津市劳动保障技师学院                      |
| 泸州市教育和体育局                          |
| 余姚市教育局                                |
| 中国金融认证中心                            |
| 国家高端智能化家用电器创新中心              |
| 国元证券                                    |
| 华夏金融租赁有限公司                        |
| 国宝人寿保险股份有限公司                    |
| 瀚华金控股份有限公司                        |
| 路特斯中国                                  |
| 宇通客车股份有限公司                        |
| 国家能源局                                  |
| 国务院港澳事务办公室                        |
| 百度智能云身份管理服务                      |
| 360公司                                     |
| 三一华兴                                    |
| 西藏阜康医院                                |
| 上海逸广信息科技有限公司                    |
| 联鹏应用软件（上海）有限公司                |
| 上海万序健康科技有限公司                    |
| 上海中商网络股份有限公司                    |
| 上海半天妖餐饮管理有限公司                  |
| GAP盖璞（上海）商业有限公司                 |
| 汤臣倍健股份有限公司                        |
| 跳羚科技(厦门)有限公司                      |
| 飞天诚信科技股份有限公司                    |
| 浪潮工业互联网股份有限公司                  |
| 唐颐控股有限公司                            |
| 中创智维科技有限公司                        |
| 中航金网（北京）电子商务有限公司            |
| 中国航空制造技术研究院                      |
| 中建国际投资集团有限公司                    |
| 同方节能工程技术有限公司                    |
| 云南航天工程物探检测股份有限公司            |
| 山东港口陆海国际物流集团有限公司            |
| 山东埃德森石油工程技术有限公司              |
| 山东第一医科大学第一附属医院                |
| 广州无线电集团                              |
| 广州携旅信息科技有限公司                    |
| 广州蓝深科技有限公司                        |
| 广州广汽商贸物流有限公司                    |
| 广州思迈特软件有限公司                      |
| 河南新辰环保科技有限公司                    |
| 黄河科技集团有限公司                        |
| 豫信电子科技集团有限公司                    |
| 双汇物流投资有限公司                        |
| 广东漫云物联科技有限公司                    |
| 深圳市金溢科技股份有限公司                  |
| 深圳市中悦科技有限公司                      |
| 深圳能源集团股份有限公司                    |
| 深圳市东阳光实业发展有限公司                |
| 深圳云天励飞技术股份有限公司                |
| 深圳市维玛科技有限公司                      |
| 深圳市观塘银河电讯科技有限公司              |
| 北京博亚思科技有限公司                      |
| 北京银泰置业有限公司                        |
| 北京和融通支付科技有限公司                  |
| 北京微通新成网络科技有限公司                |
| 北京柏橡科技有限公司                        |
| 浙江领湾网络有限公司                        |
| 浙江申跃信息科技有限公司                    |
| 杭州润为数据科技有限公司                    |
| 杭州马上自动化科技有限公司                  |
| 景德镇黑猫集团有限责任公司                  |
| 之江实验室                                  |
| 丽水市中医医院                              |
| 宁波金融资产交易中心                        |
| 德清智慧教育平台                            |
| 江苏创致信息科技有限公司                    |
| 无锡市陶都巨龙软件有限责任公司              |
| TCL华星光电技术有限公司                     |
| 万宝盛华集团                                |
| 妙盈科技                                    |
| 尚企云链                                    |
| 新疆天衡信息系统咨询管理有限公司            |
| 新开普电子股份有限公司                      |
| 广西数字浪潮数据服务有限公司                |
| 百安居中国                                  |
| 重庆两江协同创新区                          |
| 万宝盛华大中华有限公司                      |
| 哈尔滨途图科技有限公司                      |
| 哈尔滨逐浪文化传媒有限公司                  |
| 大连电瓷集团股份有限公司                    |
| 湖南数通信息技术服务有限公司                |
| 湖南湘邮科技股份有限公司                    |
| 湖南省公共资源交易平台市场主体注册系统      |
| 湘潭智慧教育云统一认证平台                  |
| 南京市智慧医疗投资运营服务有限公司          |
| 南凌科技股份有限公司                        |
| 福建引迈信息技术有限公司                    |
| 漳州信息产业集团有限公司                    |
| 厦门茂商科技有限公司                        |
| 武汉英特沃科技有限公司                      |
| 武汉博特睿达智能科技有限公司                |
| 江西云车科技有限公司                        |
| 天津汉通教育科技有限公司                    |
| 企思（天津）科技有限公司                    |
| 凯盛工业互联网平台                          |
| 吕梁市医改监测平台                          |
| 遂宁市经济大数据平台                        |

# Dromara社区
<a href="https://dromara.org/zh/" target="_blank">**Dromara**</a>致力于微服务云原生解决方案的组织。

 - **开放** 技术栈全面开源共建、 保持社区中立、兼容社区 兼容开源生态
 
 - **愿景** 让每一位开源爱好者，体会到开源的快乐
 
 - **口号** 为往圣继绝学，一个人或许能走的更快，但一群人会走的更远
 
# 知识星球
 
<img src="images/zsxq.png?raw=true"/>