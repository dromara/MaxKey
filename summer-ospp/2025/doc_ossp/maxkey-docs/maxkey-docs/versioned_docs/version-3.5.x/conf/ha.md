---
title: 高可用性
sidebar_position: 1
---
# 高可用性
High Availability

## 通常架构
Architecture
![微服务架构Microservice](/images/maxkey_ha.png)

## 微服务架构
Microservice Architecture

![微服务架构Microservice](/images/maxkey_ha_micro.png)



## 高可用性配置

### 环境准备
准备两台服务器，并安装MaxKey

### Redis缓存准备

安装Redis服务或者Redis集群

### 修改配置文件
目录MaxKey-v*GA/maxkey/中以下文件
```
application-https(http).properties
```
maxkey-web-maxkey*.jar中的

```
application-https(http).properties。
```

Redis配置

```ini
#redis
spring.redis.host=127.0.0.1
spring.redis.port=6379
spring.redis.password=password
spring.redis.timeout=10000
spring.redis.jedis.pool.max-wait=1000
spring.redis.jedis.pool.max-idle=200
spring.redis.lettuce.pool.max-active=-1
spring.redis.lettuce.pool.min-idle=0
```

启动Sessions存储在Redis配置(v3.5.0之前版本)

```ini
# Session store type.
spring.session.store-type=redis
# Session timeout. If a duration suffix is not specified, seconds is used.
server.servlet.session.timeout=1800
# Sessions flush mode.
spring.session.redis.flush-mode=on_save 
# Namespace for keys used to store sessions.
spring.session.redis.namespace=spring:session 
```

### Nginx转发配置

  请参照Nginx官方网站完成配置
  
  nginx-1.19.9关键转发配置如下
  
```
    #服务器的集群maxkeycluster ,weight是权重的意思，权重越大，分配的概率越大。
    upstream  sso.maxkey.top{  
       server    127.0.0.1:8080  weight=10;
       #server    127.0.0.1:8082  weight=10;  
	   ip_hash;
    } 
	
    server {
        listen       443 ssl;
        server_name  localhost;

        ssl_certificate      C:/IDES/nginx-1.19.9/maxkey.pem;
        ssl_certificate_key  C:/IDES/nginx-1.19.9/maxkey.key;

        ssl_session_cache    shared:SSL:1m;
        ssl_session_timeout  5m;

        ssl_ciphers  HIGH:!aNULL:!MD5;
        ssl_prefer_server_ciphers  on;

        location / {
            root   html;
            index  index.html index.htm;
        }
		
        #服务器集群路径
        location /maxkey/ {
            proxy_pass http://sso.maxkey.top/maxkey/;
        }
    }
```


### 证书签发

请参照如何用acme.sh申请证书

<a href ="https://github.com/acmesh-official/acme.sh/wiki/How-to-issue-a-cert">英文 </a>

<a href ="https://github.com/acmesh-official/acme.sh/wiki/%E8%AF%B4%E6%98%8E" >中文 </a>