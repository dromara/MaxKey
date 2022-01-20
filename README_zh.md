# <img src="images/logo_maxkey.png?raw=true"  width="200px"   alt=""/>


<a href="README_en.md" target="_blank"><b>English</b></a>  |  <a href="README_zh.md" target="_blank"><b>中文</b></a>

# 概述

<b>MaxKey</b>单点登录认证系统，谐音马克思的钥匙寓意是最大钥匙,是<b>业界领先的企业级IAM身份管理和认证产品</b>,支持OAuth 2.x/OpenID Connect、SAML 2.0、JWT、CAS、SCIM等标准协议，提供<b>简单、标准、安全和开放</b>的用户身份管理(IDM)、身份认证(AM)、单点登录(SSO)、RBAC权限管理和资源管理等。

官方网站  <a href="https://www.maxkey.top" target="_blank"><b>官网</b></a> |  <a href="https://maxkeytop.gitee.io" target="_blank"><b>官网二线</b></a>

QQ交流群：<b>434469201</b> 

邮箱email: <b>maxkeysupport@163.com</b>

代码托管 <a href="https://gitee.com/dromara/MaxKey" target="_blank"><b>Gitee</b></a> | <a href="https://github.com/dromara/MaxKey" target="_blank"><b>GitHub</b></a>

 
><b>单点登录(Single Sign On）</b>简称为<b>SSO</b>，用户只需要登录认证中心一次就可以访问所有相互信任的应用系统，无需再次登录。
>
>**主要功能：**
>1) 所有应用系统共享一个身份认证系统
>2) 所有应用系统能够识别和提取ticket信息
 
 
# 产品特性

1.  标准认证协议：

| 序号    | 协议    |  支持   |
| --------| :-----  | :----   |
| 1.1     | OAuth 2.x/OpenID Connect        |  高  |
| 1.2     | SAML 2.0                        |  高  |
| 1.3     | JWT                             |  高  |
| 1.4     | CAS                             |  高  |
| 1.5     | FormBased                       |  中  |
| 1.6     | TokenBased(Post/Cookie)         |  中  |
| 1.7     | ExtendApi                       |  低  |
| 1.8     | EXT                             |  低  |

2. 登录支持

| 序号    | 登录方式      |   支持  |
| --------| :-----        | :----   |
| 2.1     | 动态验证码    | 字母/数字/算术   | 
| 2.2     | 双因素认证    | 短信/时间令牌/邮件 | 
| 2.3     | 短信认证      | 腾讯云短信/阿里云短信/网易云信  |
| 2.4     | 时间令牌      | 登录易/Google/Microsoft Authenticator/FreeOTP/支持TOTP或者HOTP |
| 2.5     | 域认证        | Kerberos/SPNEGO/AD域 |
| 2.6     | LDAP          | OpenLDAP/ActiveDirectory/标准LDAP服务器 |
| 2.7     | 社交账号      | 微信/QQ/微博/钉钉/Google/Facebook/其他  | 
| 2.8     | 扫码登录      | 企业微信/钉钉扫码登录  | 


3. 提供标准的认证接口以便于其他应用集成SSO，安全的移动接入，安全的API、第三方认证和互联网认证的整合。

4. 提供用户生命周期管理，支持SCIM 2协议，基于Apache Kafka代理，通过连接器(Connector)实现身份供给同步。

5. 认证多租户功能，支持集团下多企业独立管理或企业下不同部门数据隔离的，降低运维成本。

6. 认证中心具有平台无关性、环境多样性，支持Web、手机、移动设备等, 如Apple iOS，Andriod等，将认证能力从B/S到移动应用全面覆盖。

7. 多种认证机制并存，各应用系统可保留原有认证机制，同时集成认证中心的认证；应用具有高度独立性，不依赖认证中心，又可用使用认证中心的认证，实现单点登录。

8. 基于Java EE平台，微服务架构，采用Spring、MySQL、Tomcat、Redis、Apache Kafka等开源技术，扩展性强。  

9. 开源、安全、自主可控，许可证 Apache 2.0 License & <a href="https://maxkey.top/zh/about/licenses.html" target="_blank">MaxKey版权声明</a>。 


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

当前版本百度网盘下载,<a href="https://maxkey.top/zh/about/download.html" target="_blank"> 历史版本</a>

| 版本    | 日期    |  Docker      |  网盘      |  网盘提取码  |
| --------| :-----  | :----        | :----      | :----        |
| v 3.3.0 GA | 2022/01/21    |<a href="https://hub.docker.com/u/maxkeytop" target="_blank">链接</a>  |  <a href="https://pan.baidu.com/s/1BV9K1ki4TnV8R2Fddaqx7w" target="_blank">下载</a>  |  **mxk9**  |


# Roadmap

| 序号    | 计划    |  时间  |
| --------| :-----  | :----  |
| 1     | Maxkey-Cloud (micro service support)                      |  2021Q3  |
| 2     | Zero trust scenario integration                           |  2021Q4  |
| 3     | OAuth 2.1                                                 |  2022Q1  |
| 4     | React, and Ant Design                                     |  2022Q2  |
| 5     | OpenID Connect optimize                                   |  2022Q2  |
| 6     | Java 17+                                                  |  2022Q3  |
| 7     | Jakarta EE 9+                                             |  2022Q3  |
| 8     | Spring Framework 6                                        |  2022Q4  |
| 9     | Spring Boot 3                                             |  2022Q4  |


# 接入登记

<a href="https://gitee.com/dromara/MaxKey/issues/I2BNRZ" target="_blank"> 点击进行接入登记</a>，为 MaxKey的发展贡献自己的力量！


以下为部分接入或测试中的用户

| 行业    | 单位    |
| --------| :-----  |
| 院校    | 兰州现代职业学院                            |
| 院校    | 长春职业技术学院                            |
| 教育    | 重庆市北碚图书馆                            |
| 教育    | 德清智慧教育平台                            |
| 企业    | 国家能源局                                  |
| 企业    | 360公司                                     |
| 企业    | 深圳市金溢科技股份有限公司                  |
| 企业    | 之江实验室                                  |
| 企业    | 深圳市中悦科技有限公司                      |
| 金融    | 国元证券                                    |
| 金融    | 华夏金融租赁有限公司                        |
| 金融    | 宁波金融资产交易中心                        |
| 金融    | 国宝人寿保险股份有限公司                    |
| 金融    | 瀚华金控股份有限公司                        |
| 汽车    | 路特斯中国                                  |
| 企业    | 北京银泰置业有限公司                        |
| 企业    | 遂宁市经济大数据平台                        |
| 企业    | 同方节能工程技术有限公司                    |
| 企业    | 云南航天工程物探检测股份有限公司            |
| 企业    | 山东港口陆海国际物流集团有限公司            |
| 企业    | 广州无线电集团                              |
| 企业    | 河南新辰环保科技有限公司                    |
| 企业    | 三一华兴                                    |
| 企业    | unicon.net                                  |
| 企业    | 深圳市东阳光实业发展有限公司                |
| 科技    | 广州思迈特软件有限公司                      |
| 科技    | 尚企云链                                    |
| 科技    | 北京博亚思科技有限公司                      |
| 科技    | 武汉英特沃科技有限公司                      |
| 科技    | 江苏创致信息科技有限公司                    |
| 科技    | 湖南数通信息技术服务有限公司                |
| 科技    | 广东漫云物联科技有限公司                    |
| 科技    | 江西云车科技有限公司                        |
| 科技    | 无锡市陶都巨龙软件有限责任公司              |

# Dromara社区
<a href="https://dromara.org/zh/" target="_blank">**Dromara**</a>致力于微服务云原生解决方案的组织。

 - **开放** 技术栈全面开源共建、 保持社区中立、兼容社区 兼容开源生态
 
 - **愿景** 让每一位开源爱好者，体会到开源的快乐
 
 - **口号** 为往圣继绝学，一个人或许能走的更快，但一群人会走的更远