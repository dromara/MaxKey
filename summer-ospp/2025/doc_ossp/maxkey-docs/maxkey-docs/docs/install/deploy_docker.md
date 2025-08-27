---
title: Docker部署
sidebar_position: 4
---
## 介绍
Docker 是一个开源的应用容器引擎，让开发者可以打包他们的应用以及依赖包到一个可移植的镜像中，然后发布到任何流行的 Linux或Windows操作系统的机器上，也可以实现虚拟化。

本教程介绍在Docker中如何快速配置和部署MaxKey，在此之前请提前<a target="_blank" href="https://docs.docker.com/engine/install/">安装Docker</a>

MaxKey官方镜像仓库：<a href="https://hub.docker.com/u/maxkeytop" target="_blank">访问</a>

## Docker快速部署
LINUX 7 基于Docker快速部署

1、上传Docker配置文件及相关脚本

把目录 

https://gitee.com/dromara/MaxKey/tree/main/docker 

或者

https://github.com/dromara/MaxKey/tree/main/docker

上传到/root目录下


2、拉取docker镜像

```bash
./maxkey_docker_install.sh
```

3、启动maxkey docker

```bash
./maxkey_docker_start.sh
```

## 应用访问
<a href="../requirements#应用访问" >应用访问</a>

## Docker其他版本

<table border="0" class="table table-striped table-bordered ">
	<thead>
		<tr class="a">
			<th>环境</th>
			<th>贡献者</th>
			<th>地址</th>
		</tr>
	</thead>
	<tbody>
		<tr class="a">
			<td>arm</td>
			<td>zhaokait</td>
			<td> <a href="https://hub.docker.com/r/zhaokait/" target="_blank">docker地址</a>  </td>
		</tr>
	</tbody>
</table>