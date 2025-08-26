---
title: LINUX部署
sidebar_position: 2
---

# LINUX 7 版本


## JDK 安装

### 下载地址

Eclipse Temurin 8  x64 RPM Package

假设当前安装目录/root
	
```bash
curl -L "https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.8.1%2B1/OpenJDK17U-jdk_x64_linux_hotspot_17.0.8.1_1.tar.gz" -H "Cookie: oraclelicense=accept-securebackup-cookie"  -H "Connection: keep-alive" -O  
```
 
### 解压缩及安装

```bash
tar -zxf OpenJDK17U-jdk_x64_linux_hotspot_17.0.8.1_1.tar.gz
```

完成后本地目录

```
jdk-17.0.8.1+1
```

## 安装配置MySQL 8.0

### 安装MySQL 8.0
假如本地安装过mariadb，请先卸载

#### 安装MySQL官方的yum repository

```bash
curl -L  "https://dev.mysql.com/get/mysql80-community-release-el7-3.noarch.rpm"  -O 
```
 
#### 下载rpm包：

```bash
yum -y install mysql80-community-release-el7-3.noarch.rpm
```
 
#### 安装MySQL服务
 
```bash
yum -y install mysql-community-server
```

#### 安装MySQL问题

```
Failing package is: mysql-community-libs-compat-8.0.28-1.el7.x86_64  GPG Keys are configured as: file:///etc/pki/rpm-gpg/RPM-GPG-KEY-mysql
```

解决方案

```bash
rpm --import https://repo.mysql.com/RPM-GPG-KEY-mysql-2022
```

### 调整配置

编辑 /etc/my.cnf 文件

```ini
character-set-server=utf8
lower_case_table_names=1
```
 
### 启动mysql服务
```bash
    systemctl start mysqld
	
	--停止
	
	systemctl stop mysqld  --无需执行
```
	
### 登录MySQL

 第一次启动MySQL后，就会有临时密码，这个默认的初始密码在/var/log/mysqld.log文件中，我们可以用这个命令来查看：
 
```bash
 grep "password" /var/log/mysqld.log
```
 
### 设置访问权限及密码
```bash

mysql -u root -p;

输入密码

```

```sql
--以下步骤可能要求先修改初始化密码为复杂密码 SET PASSWORD = 'UDF(ez/8Lufi';

set global validate_password.policy=0; --改变密码等级

set global validate_password.length=4; --改变密码最小长度

SET PASSWORD = 'maxkey';

use mysql;

alter user 'root'@'localhost' identified with mysql_native_password by 'maxkey';

flush privileges ;

---修改root用户的访问权限为‘%’

update user set host='%' where user='root';

flush privileges ;

```
 
### 设置开机启动

```
chkconfig --add mysqld
chkconfig mysqld on
```

查看开机启动设置是否成功

```
 chkconfig --list | grep mysql*
 
 # mysqld 0:关闭 1:关闭 2:启用 3:启用 4:启用 5:启用 6:关闭停止
```


## MaxKey安装

### 把MaxKey上传到Linux服务器

### 数据导入

MaxKey对应的版本SQL文件，参见

https://gitee.com/dromara/MaxKey/tree/main/sql

登陆LINUX MYSQL并创建schema maxkey，字符集utf8,数据文件导入到maxkey schema中，

```bash

mysql -u root -p;

输入密码
```

```sql
CREATE DATABASE  IF NOT EXISTS `maxkey` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `maxkey`;

-- 使用source命令，后面参数为脚本文件(如这里用到的.sql),其中v4.0.5.ga是对应的版本号

source your sql path/maxkey.sql;

source your sql path/maxkey_data.sql

```


### 配置hosts

hosts配置文件目录

```
vi /etc/hosts
```

新增如下内容

```
127.0.0.1  sso.maxkey.top
127.0.0.1  mgt.maxkey.top
127.0.0.1  tokenbased.demo.maxkey.top
127.0.0.1  cas.demo.maxkey.top
127.0.0.1  oauth.demo.maxkey.top
```

### 启动

修改set_maxkey_env.sh以下参数，/root/为安装路径

```bash
JAVA_HOME=/root/jdk-17.0.8.1+1

export JAVA_HOME=/root/jdk-17.0.8.1+1
```


```bash
  ./start_maxkey_db.sh & #自行编写
  
  ./start_maxkey.sh &
  
  ./start_maxkey_mgt.sh &
  
  ./start_maxkey_demo.sh &
```

## 前端服务部署

安装nginx,参考windows版本配置，再把windows版本前端文件放入对应的nginx目录下

## 代理服务部署

安装nginx,参考windows版本的代理配置

## 应用访问
<a href="../requirements#应用访问" >应用访问</a>