---
layout: zh/default
sidebar_position: 4
---
# VS Code前端开发指南
Visual Studio Code（简称“VS Code” ）是Microsoft开发一个运行于 Mac OS X、Windows和 Linux 之上的，针对于编写现代Web和云应用的跨平台源代码编辑器， 可在桌面上运行，并且可用于Windows，macOS和Linux。它具有对JavaScript，TypeScript和Node.js的内置支持，并具有丰富的其他语言（例如C++，C＃，Java，Python，PHP，Go）和运行时（例如.NET和Unity）扩展的生态系统。

## 开发环境启动
- 启动vs code并选择打开目录

![start](/images/dev/vscode/start.png)

- 选择打开项目目录
![folder](/images/dev/vscode/folder.png)

- 导入完成
![import_s.png](/images/dev/vscode/import_s.png)

## 依赖包安装

```powershell
npm install -g @angular/cli@13.3.0

npm install

npm i --save-dev @angular-devkit/build-angular@13.3.0

yarn install
```
## 统一认证前端

maxkey-web-frontend/maxkey-web-app
```powershell
yarn start
```

## 身份安全管理前端

maxkey-web-frontend/maxkey-web-mgt-app
```powershell
yarn start
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
