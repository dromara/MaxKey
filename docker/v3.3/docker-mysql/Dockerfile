FROM mysql:8.0.27

ENV TZ=Asia/Shanghai \
    MYSQL_DATABASE=maxkey \
    MYSQL_USER=maxkey \
    MYSQL_PASSWORD=maxkey \
    MYSQL_ROOT_PASSWORD=maxkey


MAINTAINER maxkey(maxkeysupport@163.com)

VOLUME ["/var/lib/mysql", "/root/mysql/data"]
VOLUME ["/var/log/mysql", "/root/mysql/logs"]


EXPOSE 3306

#如果向 sql 目录中存入了数据库初始化脚本，则把下面的注释打开，脚本支持 .sh, .sql .sql.gz
COPY sql/ /docker-entrypoint-initdb.d
COPY mysqld.cnf /etc/mysql/conf.d/mysqld.cnf
