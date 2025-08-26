---
title: Docker部署
sidebar_position: 3
---
## 介绍
Docker 是一个开源的应用容器引擎，让开发者可以打包他们的应用以及依赖包到一个可移植的镜像中，然后发布到任何流行的 Linux或Windows操作系统的机器上，也可以实现虚拟化。

本教程介绍在Docker中如何快速配置和部署MaxKey。

MaxKey官方镜像仓库：<a href="https://hub.docker.com/u/maxkeytop" target="_blank">访问</a>

## 前提条件
在此之前请提前<a target="_blank" href="https://docs.docker.com/engine/install/">安装Docker</a>

## Docker部署
LINUX 7 基于Docker部署

### 创建docker网络连接

```bash
docker network create maxkey.top
```

### Docker文件下载

把 https://gitee.com/dromara/MaxKey/tree/main/docker 或者https://github.com/dromara/MaxKey/tree/main/docker目录上传到/root目录下

### 启动MySQL服务
```bash
docker pull mysql:8.0.27

docker run -p 3306:3306   \
-v ./docker-mysql/data:/var/lib/mysql \
-v ./docker-mysql/logs:/var/log/mysql \
-v ./docker-mysql/conf.d:/etc/mysql/conf.d  \
-v ./docker-mysql/docker-entrypoint-initdb.d:/docker-entrypoint-initdb.d  \
--name mysql  \
--hostname mysql \
--network maxkey.top \
-e MYSQL_ROOT_PASSWORD=maxkey  \
-d mysql:8.0.27 
```

### 启动MaxKey服务

请把<b>DATABASE_HOST</b>为实际地址

```bash
docker pull maxkeytop/maxkey:latest

docker 	run -p 9527:9527  \
-e DATABASE_HOST=mysql \
-e DATABASE_PORT=3306 \
-e DATABASE_NAME=maxkey \
-e DATABASE_USER=root \
-e DATABASE_PWD=maxkey \
--name maxkey \
--hostname maxkey \
--network maxkey.top \
-d maxkeytop/maxkey:latest 
```

### 启动MaxKey管理服务

请把<b>DATABASE_HOST</b>为实际地址

```bash
docker pull maxkeytop/maxkey-mgt:latest

docker 	run -p 9526:9526  \
-e DATABASE_HOST=mysql \
-e DATABASE_PORT=3306 \
-e DATABASE_NAME=maxkey \
-e DATABASE_USER=root \
-e DATABASE_PWD=maxkey \
--name maxkey-mgt \
--hostname maxkey-mgt \
--network maxkey.top \
-d maxkeytop/maxkey-mgt:latest 
```


### 启动MaxKey认证前端服务

```bash
docker pull maxkeytop/maxkey-frontend:latest

docker 	run -p 8527:8527  \
--name maxkey-frontend \
--hostname maxkey-frontend \
--network maxkey.top \
-d maxkeytop/maxkey-frontend:latest 
```

### 启动MaxKey管理前端服务

```bash
docker pull maxkeytop/maxkey-mgt-frontend:latest

docker 	run -p 8526:8526  \
--name maxkey-mgt-frontend \
--hostname maxkey-mgt-frontend \
--network maxkey.top \
-d maxkeytop/maxkey-mgt-frontend:latest 
```


### 启动MaxKey代理服务

进入docker-nginx

```bash
cd docker-nginx

docker build -f Dockerfile -t maxkeytop/maxkey-nginx .

docker 	run -p 80:80  \
--name maxkey-nginx \
--hostname maxkey-mgt-frontend \
--network maxkey.top \
-d maxkeytop/maxkey-nginx
```