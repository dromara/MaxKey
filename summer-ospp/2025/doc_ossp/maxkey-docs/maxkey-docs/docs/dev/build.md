---
layout: zh/default
sidebar_position: 5
---
# 项目构建

## Java项目构建
### 配置环境变量
```powershell
setEnvVars.bat

set JAVA_HOME=D:\IDE\jdk-17.0.9+9

set GRADLE_HOME=C:\IDE\gradle-8.8
```


### 标准构建

1.启动构建

```powershell
gradlew build -x test或者release.bat
```

2.构建结果

构建包路径

MaxKey/build

依赖包路径

maxkey-webs/maxkey-web-manage/

maxkey-webs/maxkey-web-maxkey/


### Docker构建

1.Docker 构建配置

```powershell
release_cnf_docker.bat
```

2.启动构建

```powershell
gradlew build jib -x test或者release_docker.bat
```

3.构建的结果

maxkey-web-manage/

maxkey-web-maxkey/


### 传统构建

1.传统 构建配置

```powershell
release_cnf_tradition.bat
```

2.启动构建

gradlew build -x test或者release.bat


3.构建的结果

构建包路径

MaxKey/build/maxkey-jars

依赖包路径

MaxKey/build/MaxKey-v(version)GA


## 前端构建Build

1)MaxKey统一认证前端

maxkey-web-frontend/maxkey-web-app

```powershell
ng build --prod --base-href /maxkey/
```

2)MaxKey身份安全管理前端

maxkey-web-frontend/maxkey-web-mgt-app

```powershell
ng build --prod --base-href /maxkey-mgt/
```

## 问题及解决

问题1
```powershell
 yarn start
 ```

```
CategoryInfo          : SecurityError: ，PSSecurityException
FullyQualifiedErrorId : UnauthorizedAccess
```

解决方案：
```powershell
Set-ExecutionPolicy RemoteSigned -Scope Process
```
