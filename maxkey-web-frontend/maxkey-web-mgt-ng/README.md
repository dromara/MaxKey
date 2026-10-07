## MaxKey-Web-Mgt-NG      
  
MaxKey单点登录认证系统，谐音为马克思的钥匙，寓意是最大钥匙，是业界领先的IAM身份管理和身份认证产品；
支持OAuth 2.x/OpenID Connect、SAML 2.0、JWT、CAS、SCIM等标准协议；
提供简单、标准、安全和开放的用户身份管理(IDM)、身份认证(AM)、单点登录(SSO)、资源管理和权限管理等

Maxkey Single Sign On system, which means the Maximum key, Leading-Edge IAM Identity and Access management product , 
Support OAuth 2.x/OPENID CONNECT, SAML 2.0, JWT, CAS, SCIM and other standard protocols,
Provide Simple, Standard, Secure and Open Identity management (IDM), Access management (AM), Single Sign On (SSO), RBAC permission management and Resource management.


### 环境搭建

Angular 开发环境至少需要安装 Node.js(Node.js 内置了 npm 无须单独安装）、VSCode编辑器，其中 Node.js 建议安装 LTS 版本，安装完成后可以通过
终端窗口中运行：
```
node -v # 查看 Node.js 当前版本
npm -v # 查看 Npm 当前版本
```
Node.js v24.21.0

Npm 默认从国外源来下载包信息，鉴于国内环境因素，在开始下一步前先安装 nnrm 并切换至淘宝镜像：
```
# 安装 nnrm
npm install -g nnrm
# 将Npm切换至淘宝源（不同 npm 源管理器命令有点不一样，更多细节请参考 nnrm 文档）
nnrm use taobao
```

### 安装
#### 全局AngularCli
安装之前请先确保本地已经安装全局 Angular Cli，只有这样才能随时随地在终端使用 ng 命令，可以通过终端窗口中运行：
```
npm install

npm install @angular/compiler

npm install ngx-cookie-service

npm install crypto-js

npm i --save-dev @types/crypto-js

npm i @types/node

npm i --save-dev @types/node

npm install echarts

```

### 运行
```
npm start

```
启动完成后会打开浏览器访问 http://localhost:8528，若你看到如下页面则代表成功了。


### 生产构建
```
npm start

```


### 异常解决
#### 异常1
```
+ ~~~~
    + CategoryInfo          : SecurityError: (:) []，PSSecurityException
    + FullyQualifiedErrorId : UnauthorizedAccess
```
解决	
```
Set-ExecutionPolicy RemoteSigned -Scope Process
```

#### 异常2
```
npm ERR! Invalid Version:
```

解决	
```
npm update
npm cache clean --force
npm install

```

#### 异常3
```
> husky install

install command is DEPRECATED
.git can't be found
```

解决	
```
git  init
```

### 许可证

>
> MaxKey Web Mgt is a fork to [ng-alain](https://github.com/ng-alain/ng-alain/). Check [LICENSE](/LICENSE-ng-alain) for more details.
>

### 参考资料

[参考资料](https://ng-alain.com/docs/getting-started/zh)
