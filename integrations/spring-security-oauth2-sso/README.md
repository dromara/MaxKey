# Spring Security OAuth SSO

### 项目源地址

https://gitee.com/hhw3KevinHou/spring-security-oauth2-sso.git 


#### 介绍
部署一个OAuth 2.0服务器，例如MaxKey。

建立2个协议为 OAuth_v2.1的应用，对应spring-demo-client1，spring-demo-client2，许可确认设置为自动，这样不需要用户手动授权。设置访问控制。





运行spring-demo-client1，spring-demo-client2

访问http://localhost:8080/hello，自动跳转登录页面，登录成功。

访问http://localhost:8081/hello，不需要登录。



任何一个logout，全局退出。



## 单点登录过程

访问http://localhost:8081/hello，发现spring-demo-client2本地应用没有登录

```

spring-demo-client2 发起登录请求：
redirect到：http://sso.maxkey.top/sign/authz/oauth/v20/authorize?client_id=830517174152986624&redirect_uri=http://localhost:8081/login&response_type=code&state=8GAmwd'

显示makey登录界面
用户登录后
maxkey根据应用设置，使用自动approve，不需要用户点击授权。
redirect到：http://localhost:8081/login?code=72107fc4-5305-4aa5-a8d0-14da30ed0ca1&state=8GAmwd

spring-demo-client2 的spring security自动处理，获取access_token：
调用：http://sso.maxkey.top/sign/authz/oauth/v20/token
参数为：
{
    grant_type=[authorization_code], 
    code=[72107fc4-5305-4aa5-a8d0-14da30ed0ca1], 
    redirect_uri=[http://localhost:8081/login], 
    client_id=[830517174152986624], 
    client_secret=[ElHEMDcwMzIwMjMxNjE5NTAyMTIx1K]
}
得到access_token：dbff79de-6efa-4148-aedb-333325dc30c0

spring-demo-client2的spring security使用得到access_token自动获取用户信息：
http://sso.maxkey.top/sign/api/oauth/v20/me

spring-demo-client2得到用户信息后返回前端：
http://localhost:8081/hello

```



访问http://localhost:8080/hello，发现spring-demo-client1本地应用没有登录

```
spring-demo-client1 发起登录请求：
'http://sso.maxkey.top/sign/authz/oauth/v20/authorize?client_id=830447866781630464&redirect_uri=http://localhost:8080/login&response_type=code&state=ZZXxk5'

makey的拦截器发现授权中心已经登录（cookie里有jwt token）
自动approve，不需要用户点击授权。
redirect到：http://localhost:8080/login?code=51f2ae07-7a1c-42ec-a663-be09080ab1d9&state=ZZXxk5

spring-demo-client1 的spring security自动处理，获取access_token：
调用：http://sso.maxkey.top/sign/authz/oauth/v20/token
参数为：
{
    grant_type=[authorization_code], 
    code=[51f2ae07-7a1c-42ec-a663-be09080ab1d9], 
    redirect_uri=[http://localhost:8080/login], 
    client_id=[830447866781630464], 
    client_secret=[QnGYMDcwMzIwMjMxMTQ0MjYwNDcFli]
}
得到access_token：95e277ae-06f2-4f43-af78-8046905b8cea

spring-demo-client1的spring security使用得到access_token自动获取用户信息：
http://sso.maxkey.top/sign/api/oauth/v20/me

spring-demo-client1得到用户信息后返回前端：
http://localhost:8080/hello


```



