FROM mysql:8.0.21

ENV TZ=Asia/Shanghai \
    MYSQL_DATABASE=maxkey \
    MYSQL_USER=admin \
    MYSQL_PASSWORD=password \
    MYSQL_ROOT_PASSWORD=password


MAINTAINER guox(guox@goodrain.com)

VOLUME ["/var/lib/mysql", "/var/log/mysql"]

EXPOSE 3306

#如果向 sql 目录中存入了数据库初始化脚本，则把下面的注释打开，脚本支持 .sh, .sql .sql.gz
COPY sql/ /docker-entrypoint-initdb.d
COPY mysqld.cnf /etc/mysql/conf.d/mysqld.cnf
