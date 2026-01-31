# 一份用于构建 mysql 镜像的 Dockerfile

- 编辑 my.cnf 来变更配置 
- 上传 sql 脚本到 docker-entrypoint-initdb.d 目录下，可以在首次启动时初始化数据库
- docker-entrypoint-initdb.d下仅保留最近发行版，其余版本在工程代码sql目录下，均使用版本的命名方式