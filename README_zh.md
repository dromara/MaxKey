<p align="center" >
    <img src="images/logo_maxkey.png?raw=true"  width="200px"   alt=""/>
</p>   
<p align="center">
  <strong>业界领先的IAM-IDaas身份管理和认证产品</strong>
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

# 概述

<b>MaxKey</b>单点登录认证系统，谐音马克思的钥匙寓意是最大钥匙,是<b>业界领先的IAM-IDaas身份管理和认证产品</b>,支持OAuth 2.x/OpenID Connect、SAML 2.0、JWT、CAS、SCIM等标准协议，提供<b>安全、标准和开放</b>的用户身份管理(IDM)、身份认证(AM)、单点登录(SSO)、RBAC权限管理和资源管理等。

MaxKey注重企业级场景下的性能、安全和易用性，广泛应用于医疗、金融、政府和制造等行业。

官方网站  <a href="http://www.maxkey.top/" target="_blank"><b>http://www.maxkey.top/</b></a> 

官方微信: 

<img src="images/wechat.jpg?raw=true" width="200px"  alt="官方微信"/>

官方QQ：<b>1054466084</b> 

邮箱email: <b>support@maxsso.net</b>

代码托管 <a href="https://gitee.com/dromara/MaxKey" target="_blank"><b>Gitee</b></a> | <a href="https://github.com/dromara/MaxKey" target="_blank"><b>GitHub</b></a>

 
><b>单点登录(Single Sign On）</b>简称为<b>SSO</b>，用户只需要登录认证中心一次就可以访问所有相互信任的应用系统，无需再次登录。
>
>**主要功能：**
>1) 所有应用系统共享一个身份认证系统
>2) 所有应用系统能够识别和提取ticket信息
 
 
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


3. 提供标准的认证接口以便于其他应用集成SSO，安全的移动接入，安全的API、第三方认证和互联网认证的整合。

4. 提供用户生命周期管理，支持SCIM 2协议；开箱即用的连接器(Connector)实现身份供给同步。

5. 简化微软Active Directory域控、标准LDAP服务器机构和账号管理，密码自助服务重置密码。

6. IDaas多租户功能，支持集团下多企业独立管理或企业下不同部门数据隔离的，降低运维成本。

7. 认证中心具有平台无关性、环境多样性，支持Web、手机、移动设备等, 如Apple iOS，Andriod等，将认证能力从B/S到移动应用全面覆盖。

8. 基于Java EE平台，微服务架构，采用Spring、MySQL、Tomcat、Redis、MQ等开源技术，扩展性强。  

9. 开源、安全、自主可控。 


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

当前版本百度网盘下载,<a href="http://www.//maxkey.top/zh/about/download.html" target="_blank"> 历史版本</a>

| 版本       | 日期    |  网盘(提取码)      |  Docker      |
| --------   | :-----  | :----              |  :----       |
| v 4.0.1   | 2023/09/19   | <a href="https://pan.baidu.com/s/1pY_V6rIOnGLaOZtdllwDlw" target="_blank">下载</a>( **mxk9** )  |<a href="https://hub.docker.com/u/maxkeytop" target="_blank">链接</a>  |
 
 
# 安装部署

| 操作系统       | 安装手册    | 
| --------      | :-----  |
| Windows  | <a href="http://maxkey.top/zh/conf/tutorial.html?#windows"  target="_blank">链接</a>  |
| Linux    | <a href="http://maxkey.top/zh/conf/tutorial.html?#linux"  target="_blank">链接</a>  |
| Docker   | <a href="http://maxkey.top/zh/conf/deploy_docker.html"  target="_blank">链接</a>  |
 
# License
 
<a href="https://www.apache.org/licenses/LICENSE-2.0.html" target="_blank"> Apache License, Version 2.0 </a>& <a href="http://www.maxkey.top/zh/about/licenses.html" target="_blank">MaxKey版权声明</a>

# 接入登记

<a href="https://gitee.com/dromara/MaxKey/issues/I2BNRZ" target="_blank"> 点击进行接入登记</a>，为 MaxKey的发展贡献自己的力量！


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
 
# 中国信通院零信任实验室
 
 <a href="https://mp.weixin.qq.com/s/2T9TCo3EP0o9bD8ArAjUkw" target="_blank">中国信通院零信任实验室</a>
 
# 零信任标准工作组
 
 <a href="https://gitee.com/zero-trust/ZeroTrust" target="_blank">国内最权威的零信任产业组织</a>
 

# 知识星球
 
 <img src="images/zsxq.png?raw=true"/>
