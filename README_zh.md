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
        <img src="https://img.shields.io/badge/MySQL-8.4.2%2B-brightgreen" />
    </a>
    <a target="_blank" href="http://www.maxkey.top/zh/about/licenses.html">
        <img src="https://img.shields.io/github/license/dromara/MaxKey" />
    </a>
    <a target="_blank" href="https://www.bt.cn/u/AjsXmi">
        <img src="https://img.shields.io/badge/BT_Deploy-Install-20a53a" />
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

8.  配置化的密码策略、访问策略；支持Ip2region或GeoLite2地理库精准IP定位 ，强大安全审计，对用户全生命周期审计、访问行为记录追溯审计、安全合规审计、安全风险预警。

9. 基于Java EE平台，微服务架构，采用Spring、MySQL、Tomcat、Redis、MQ等开源技术，扩展性强。  

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
| v 4.1.7   | 2024/04/01   | <a href="https://www.maxkey.top/zh/about/download.html" target="_blank">下载</a>|
 
 
# 安装部署

| 操作系统       | 安装手册    | 
| --------      | :-----  |
| Windows  | <a href="https://www.maxkey.top/zh/about/download.html"  target="_blank">链接</a>  |
| Linux    | <a href="https://www.maxkey.top/zh/about/download.html"  target="_blank">链接</a>  |
| Docker   | <a href="https://www.maxkey.top/zh/about/download.html"  target="_blank">链接</a>  |
| 宝塔     | <a href="https://www.maxkey.top/zh/about/download.html"  target="_blank">链接</a>  |
 
 
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


以下为部分接入或测试中的用户

| No.       | 单位    | 
| --------      | :-----  |
| 1 | 中国人民警察大学                            |
| 2 | 福耀科技大学                                |
| 3 | 兰州现代职业学院                            |
| 4 | 长春职业技术学院                            |
| 5 | 云南师范大学                                |
| 6 | 云南农业职业技术学院                        |
| 7 | 惠州卫生职业技术学院                        |
| 8 | 厦门软件职业技术学院                        |
| 9 | 宜昌市三峡中等专业学校                      |
| 10 | 上海图书馆                                  |
| 11 | 重庆市北碚图书馆                            |
| 12 | 天津市劳动保障技师学院                      |
| 13 | 南京财经高等职业技术学校                    |
| 14 | 泸州市教育和体育局                          |
| 15 | 余姚市教育局                                |
| 16 | 河南地矿职业学院                            |
| 17 | 西昌医学高等专科学校                        |
| 18 | 福建卫生职业技术学院                        |
| 19 | 国家高端智能化家用电器创新中心              |
| 20 | 华夏金融租赁有限公司                        |
| 21 | 国宝人寿保险股份有限公司                    |
| 22 | 国元证券                                    |
| 23 | 紫金财产保险股份有限公司                    |
| 24 | 路特斯中国                                  |
| 25 | 奇瑞汽车股份有限公司                        |
| 26 | 宇通客车股份有限公司                        |
| 27 | 国家能源局                                  |
| 28 | 国务院港澳事务办公室                        |
| 29 | 百度智能云身份管理服务                      |
| 30 | 360公司                                     |
| 31 | 三一华兴                                    |
| 32 | 中国金融认证中心                            |
| 33 | 西藏阜康医院                                |
| 34 | 海阳市人民医院                              |
| 35 | 国家中小企业数字化转型促进中心              |
| 36 | 联鹏应用软件（上海）有限公司                |
| 37 | 上海万序健康科技有限公司                    |
| 38 | 上海中商网络股份有限公司                    |
| 39 | 上海半天妖餐饮管理有限公司                  |
| 40 | 上海杨国福企业管理（集团）有限公司          |
| 41 | 上海契胜科技有限公司                        |
| 42 | 纯米科技（上海）股份有限公司                |
| 43 | 中腾信金融信息服务(上海)有限公司            |
| 44 | GAP盖璞（上海）商业有限公司                 |
| 45 | 汤臣倍健股份有限公司                        |
| 46 | 跳羚科技(厦门)有限公司                      |
| 47 | 飞天诚信科技股份有限公司                    |
| 48 | 浪潮工业互联网股份有限公司                  |
| 49 | 唐颐控股有限公司                            |
| 50 | 中创智维科技有限公司                        |
| 51 | 中航金网（北京）电子商务有限公司            |
| 52 | 中国航空制造技术研究院                      |
| 53 | 中建国际投资集团有限公司                    |
| 54 | 同方节能工程技术有限公司                    |
| 55 | 云南航天工程物探检测股份有限公司            |
| 56 | 山东港口陆海国际物流集团有限公司            |
| 57 | 山东埃德森石油工程技术有限公司              |
| 58 | 山东第一医科大学第一附属医院                |
| 59 | 广州无线电集团                              |
| 60 | 广东小天才科技有限公司                      |
| 61 | 广州携旅信息科技有限公司                    |
| 62 | 广州蓝深科技有限公司                        |
| 63 | 广州广汽商贸物流有限公司                    |
| 64 | 广州思迈特软件有限公司                      |
| 65 | 广州新一代人工智能产业园管理有限公司        |
| 66 | 广东鸿正软件技术有限公司                    |
| 67 | 广东汇天航空航天科技有限公司                |
| 68 | 广东漫云物联科技有限公司                    |
| 69 | 深圳市金溢科技股份有限公司                  |
| 70 | 深圳市中悦科技有限公司                      |
| 71 | 深圳能源集团股份有限公司                    |
| 72 | 深圳市东阳光实业发展有限公司                |
| 73 | 深圳云天励飞技术股份有限公司                |
| 74 | 深圳市维玛科技有限公司                      |
| 75 | 深圳市观塘银河电讯科技有限公司              |
| 76 | 金龙机电股份有限公司                        |
| 77 | 佛山众陶联供应链服务有限公司                |
| 78 | 河南新辰环保科技有限公司                    |
| 79 | 河南豫光金铅股份有限公司                    |
| 80 | 黄河科技集团有限公司                        |
| 81 | 豫信电子科技集团有限公司                    |
| 82 | 双汇物流投资有限公司                        |
| 83 | 北京外交人员服务局                          |
| 84 | 北京博亚思科技有限公司                      |
| 85 | 北京银泰置业有限公司                        |
| 86 | 北京和融通支付科技有限公司                  |
| 87 | 北京微通新成网络科技有限公司                |
| 88 | 北京柏橡科技有限公司                        |
| 89 | Best Lawyers Corporation(佳律)              |
| 90 | 浙江领湾网络有限公司                        |
| 91 | 浙江申跃信息科技有限公司                    |
| 92 | 浙江一舟电子科技股份有限公司                |
| 93 | 浙江正元智慧科技有限公司                    |
| 94 | 浙江宇视科技有限公司                        |
| 95 | 杭州市能源集团有限公司                      | 
| 96 | 杭州润为数据科技有限公司                    |
| 97 | 杭州马上自动化科技有限公司                  |
| 98 | 景德镇黑猫集团有限责任公司                  |
| 99 | 得力集实有限公司                            |
| 100 | 海力控股集团有限公司                        |
| 101 | 之江实验室                                  |
| 102 | 丽水市中医医院                              |
| 103 | 宁波金融资产交易中心                        |
| 104 | 德清智慧教育平台                            |
| 105 | 江苏创致信息科技有限公司                    |
| 106 | 无锡市陶都巨龙软件有限责任公司              |
| 107 | 苏州二叶制药有限公司                        |
| 108 | 武汉良之隆食材股份有限公司                  |
| 109 | 民生实业（集团）有限公司                    |
| 110 | TCL华星光电技术有限公司                     |
| 111 | 万宝盛华集团                                |
| 112 | 妙盈科技                                    |
| 113 | 尚企云链                                    |
| 114 | 华奕四库                                    |
| 115 | 海力控股集团                                |
| 116 | 中国融通教育集团                            |
| 117 | 新疆天衡信息系统咨询管理有限公司            |
| 118 | 新开普电子股份有限公司                      |
| 119 | 广西数字浪潮数据服务有限公司                |
| 120 | 百安居中国                                  |
| 121 | 重庆两江协同创新区                          |
| 122 | 重庆征信有限责任公司                        |
| 123 | 万宝盛华大中华有限公司                      |
| 124 | 哈尔滨途图科技有限公司                      |
| 125 | 哈尔滨逐浪文化传媒有限公司                  |
| 126 | 大连电瓷集团股份有限公司                    |
| 127 | 锦州港股份有限公司                          |
| 128 | 古汉中药有限公司                            |
| 129 | 湖南数通信息技术服务有限公司                |
| 130 | 湖南湘邮科技股份有限公司                    |
| 131 | 湖南省公共资源交易平台市场主体注册系统      |
| 132 | 湘潭智慧教育云统一认证平台                  |
| 133 | 南京市智慧医疗投资运营服务有限公司          |
| 134 | 南京领行科技股份有限公司                    |
| 135 | 南凌科技股份有限公司                        |
| 136 | 福建引迈信息技术有限公司                    |
| 137 | 漳州信息产业集团有限公司                    |
| 138 | 厦门茂商科技有限公司                        |
| 139 | 惠州中京电子科技股份有限公司                |
| 140 | 武汉英特沃科技有限公司                      |
| 141 | 武汉博特睿达智能科技有限公司                |
| 142 | 江西云车科技有限公司                        |
| 143 | 天津汉通教育科技有限公司                    |
| 144 | 天津市达恩华天智能科技有限公司              |
| 145 | 企思（天津）科技有限公司                    |
| 146 | 凯盛工业互联网平台                          |
| 147 | 吕梁市医改监测平台                          |
| 148 | 遂宁市经济大数据平台                        |
| 149 | 临沂市城市大脑物联网统一认证平台            |
| 150 | 广州佳医科科技                              |
| 151 | 中科华通科技有限公司                        |
| 152 | 浙江迈德斯特医疗器械科技有限公司            |
| 153 | 瀚华金控股份有限公司                        |
| 154 | 中国职工保险互助会                          |
| 155 | 泸溪河食品（集团）有限公司                  |
| 156 | 上海逸广信息科技有限公司                    |
| 157 | 江西钨业控股集团有限公司                    |
| 158 | 博雷顿科技股份公司                          |
| 159 | 庞加莱(北京)能源科技有限责任公司            |
| 160 | 广州市建筑科学研究院集团有限公司            |
| 161 | 和祐国际医院集团                            |
| 162 | 秦皇岛市妇幼保健院                          |
| 163 | 云钉（厦门）科技有限公司                    |
| 164 | 青数集团                                    |
| 165 | 鲁商集团                                    |
| 166 | 鹏驰五金制品有限公司                        |
| 167 | 云小厨CloudKitchens                         |
| 168 | 深圳民生捷富凯物流有限公司                  |
| 169 | 武汉璞华大数据技术有限公司                  |

