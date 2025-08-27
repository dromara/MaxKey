---
title: Docker Compose部署
sidebar_position: 2
---
## 介绍
Docker 是一个开源的应用容器引擎，让开发者可以打包他们的应用以及依赖包到一个可移植的镜像中，然后发布到任何流行的 Linux或Windows操作系统的机器上，也可以实现虚拟化。

本教程介绍在Docker中如何快速配置和部署MaxKey。

MaxKey官方镜像仓库：<a href="https://hub.docker.com/u/maxkeytop" target="_blank">访问</a>

## 前提条件
在此之前请提前<a target="_blank" href="https://docs.docker.com/engine/install/">安装Docker</a>

## Docker Compose部署
LINUX 7 基于Docker Compose快速部署

### 上传Docker配置文件

把 https://gitee.com/dromara/MaxKey/tree/main/docker 或者https://github.com/dromara/MaxKey/tree/main/docker目录上传到/root目录下


### 启动MaxKey服务
```bash
docker-compose up --build -d
```