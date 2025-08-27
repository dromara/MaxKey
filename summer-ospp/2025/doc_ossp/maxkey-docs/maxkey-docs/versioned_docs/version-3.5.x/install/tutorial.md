---
title: 快速使用
sidebar_position: 1
---
# 介绍
为了你更好的使用MaxKey，本教程介绍在Windows中如何快速配置和使用MaxKey。

## 软件下载
访问<a href="https://www.maxkey.top/zh/about/download.html" target="_blank">下载MaxKey</a>并解压到C:盘。

## 目录结构
<table border="0" class="table table-striped table-bordered ">
    <thead>
        <tr>
            <th>序号</th><th>目录/文件</th><th>备注</th>
        </tr>
    </thead>
    <tbody>
                    <tr>
                        <td>1</td>
                        <td>MaxKey单点登陆认证系统介绍-CE-***.pdf</td>
                        <td>系统介绍</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>getting-started.html</td>
                        <td>快速使用文档</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>LICENSE</td>
                        <td>许可证</td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td>NOTICE</td>
                        <td>许可证NOTICE</td>
                    </tr>
                    <tr>
                        <td>5</td>
                        <td>jdk/jre</td>
                        <td>运行时JDK</td>
                    </tr>
                    <tr>
                        <td>6</td>
                        <td>lib</td>
                        <td>公共包</td>
                    </tr>
                    <tr>
                        <td>7</td>
                        <td>maxkey</td>
                        <td>认证服务,端口9527</td>
                    </tr>
                    <tr>
                        <td>8</td>
                        <td>maxkey_frontend</td>
                        <td>认证前端服务,端口8527</td>
                    </tr>
                    <tr>
                        <td>9</td>
                        <td>maxkey_mgt</td>
                        <td>管理服务,端口9526</td>
                    </tr>
                    <tr>
                        <td>10</td>
                        <td>maxkey_mgt_frontend</td>
                        <td>管理前端服务,端口8526</td>
                    </tr>
                    <tr>
                        <td>11</td>
                        <td>nginx-1.20.1-proxy</td>
                        <td>nginx反向代理服务,端口80</td>
                    </tr>
                    <tr>
                        <td>12</td>
                        <td>mysql_***</td>
                        <td>MySQL数据库,端口3306</td>
                    </tr>
                    <tr>
                        <td>13</td>
                        <td>maxkey_demo</td>
                        <td>样例,端口9521</td>
                    </tr>
                    <tr>
                        <td>14</td>
                        <td>start_maxkey.bat</td>
                        <td>启动认证服务器</td>
                    </tr>
                    <tr>
                        <td>15</td>
                        <td>start_maxkey_frontend.bat</td>
                        <td>启动认证前端服务器</td>
                    </tr>
                    <tr>
                        <td>16</td>
                        <td>start_maxkey_mgt.bat</td>
                        <td>启动管理服务器</td>
                    </tr>
                    <tr>
                        <td>17</td>
                        <td>start_maxkey_mgt_frontend.bat</td>
                        <td>启动管理前端服务器</td>
                    </tr>
                    <tr>
                        <td>18</td>
                        <td>start_maxkey_db.bat</td>
                        <td>启动数据库</td>
                    </tr>
                    <tr>
                        <td>19</td>
                        <td>start_maxkey_proxy.bat</td>
                        <td>启动代理服务器</td>
                    </tr>
                    <tr>
                        <td>20</td>
                        <td>start_maxkey_demo.bat</td>
                        <td>启动样例</td>
                    </tr>
                    <tr>
                        <td>21</td>
                        <td>set_maxkey_env.bat</td>
                        <td>环境设置脚本</td>
                    </tr>
                </tbody>
    </table>

## 配置hosts

hosts配置文件目录

```
C:\Windows\System32\drivers\etc
```

新增如下内容

```
127.0.0.1  sso.maxkey.top
127.0.0.1  mgt.maxkey.top
127.0.0.1  tokenbased.demo.maxkey.top
127.0.0.1  cas.demo.maxkey.top
127.0.0.1  oauth.demo.maxkey.top
```

## 服务启动
1)启动数据库

```
start_maxkey_db.bat
```

2)启动认证服务

```
start_maxkey.bat
```

3)启动管理服务

```
start_maxkey_mgt.bat
```

4)启动认证前端服务

```
start_maxkey_frontend.bat
```
                
5)启动管理前端服务

```
start_maxkey_mgt_frontend.bat
```

6)启动代理

```
start_maxkey_proxy.bat
```
				
7)启动样例

```
start_maxkey_demo.bat
```

## 应用访问
在完成安装部署后，打开浏览器，访问以下地址
<table border="0" class="table table-striped table-bordered ">
		<thead>
			<tr>
				<th>序号</th><th>名称</th><th>访问地址</th>
			</tr>
		</thead>
		<tbody>
                        <tr>
                            <td>1</td>
                            <td>认证平台</td>
                            <td><a href="http://sso.maxkey.top/maxkey/"
                                    target="blank">http://sso.maxkey.top/maxkey/</a></td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>管理平台</td>
                            <td><a href="http://mgt.maxkey.top/maxkey-mgt/"
                                    target="blank">http://mgt.maxkey.top/maxkey-mgt/</a></td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td>账户密码</td>
                            <td>admin/maxkey</td>
                        </tr>
                    </tbody>
</table>		

## Docker Compose部署

<a href="./deploy_docker_compose" >Docker Compose部署</a>
