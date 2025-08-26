---
layout: zh/default
sidebar_position: 2
---
# Eclipse开发指南

Eclipse 是一个开放源代码的、基于Java的可扩展开发平台。就其本身而言，它只是一个框架和一组服务，用于通过插件组件构建开发环境。幸运的是，Eclipse 附带了一个标准的插件集，包括Java开发工具（Java Development Kit，JDK）。


## 开发工具及相关软件

<table border="0" class="table table-striped table-bordered ">
	<thead>
		<th  >软件</th><th>版本</th><th>推荐</th><th>目录</th><th>备注</th>
	</thead>
	<tbody>
		<tr>
			<td>JDK</td>
			<td>17 +</td>
			<td></td>
			<td>C:\ide\jdk-17.0.9+9</td>
			<td>JAVA运行及开发工具包</td>
		</tr>
		<tr>
			<td>eclipse-jee</td>
			<td>2024-09</td>
			<td>推荐</td>
			<td>C:\ide\eclipse-jee-2024-09-R</td>
			<td>JAVA开发工具</td>
		</tr>
		<tr>
			<td>Gradle</td>
			<td>8.8+ </td>
			<td></td>
			<td>C:\ide\gradle-8.8</td>
			<td>代码构建</td>
		</tr>
	</tbody>
</table>	

## 仓库代码下载
- 代码地址：<a href="https://maxkey.top/zh/about/download.html">访问</a>

- 创建eclipse工作区，workspace-maxkey-demo

- 代码克隆到工作区内

```
workspace-maxkey-demo                            #工作区
├── MaxKey                                       #项目目录
```


## 开发环境启动
- 启动eclipse

![start](/images/dev/eclipse/start.png)

- 选择工作区workspace-maxkey-demo

![start_workspace](/images/dev/eclipse/start_workspace.png)

- 选择导入Gradle项目

![import](/images/dev/eclipse/import.png)

- 选择导入项目目录

![import2](/images/dev/eclipse/import2.png)

- 本地Gradle配置

![import3](/images/dev/eclipse/import3.png)

- 确认导入项目信息

![import4](/images/dev/eclipse/import4.png)

- 项目导入等待

![import5](/images/dev/eclipse/import5.png)

- 项目导入完成

![imports](/images/dev/eclipse/import_s.png)

## 后端项目

1)MaxKey统一认证系统

maxkey-webs/maxkey-web-maxkey/src/main/java/org/maxkey/MaxKeyApplication.java 

![sso](/images/dev/eclipse/sso.png)

2)MaxKey身份安全管理系统

maxkey-webs/maxkey-web-mgt/src/main/java/org/maxkey/MaxKeyMgtApplication.java

![mgt](/images/dev/eclipse/mgt.png)

## 项目运行

![run](/images/dev/eclipse/run.png)

## 问题及解决

```
“A cycle was detected in the build path of project: XXX” 
```

解决方法：
 
Eclipse Menu -> Window -> Preferences... -> Java -> Compiler -> Building -> Building path problems -> Circular dependencies -> 将Error改成Warning
