echo "start MaxKey ... "
#MySQL
docker run -p 3306:3306   \
-v ./docker-mysql/data:/var/lib/mysql \
-v ./docker-mysql/logs:/var/log/mysql \
-v ./docker-mysql/conf.d:/etc/mysql/conf.d  \
-v ./docker-mysql/docker-entrypoint-initdb.d:/docker-entrypoint-initdb.d  \
--name maxkey-mysql  \
--hostname maxkey-mysql \
--network maxkey.top \
-e MYSQL_ROOT_PASSWORD=maxkey  \
-d maxkeytop/mysql:latest

#maxkey
docker 	run -p 9527:9527  \
-e DATABASE_HOST=maxkey-mysql \
-e DATABASE_PORT=3306 \
-e DATABASE_NAME=maxkey \
-e DATABASE_USER=root \
-e DATABASE_PWD=maxkey \
--name maxkey \
--hostname maxkey \
--network maxkey.top \
-d maxkeytop/maxkey:latest 

#maxkey-mgt
docker 	run -p 9526:9526  \
-e DATABASE_HOST=maxkey-mysql \
-e DATABASE_PORT=3306 \
-e DATABASE_NAME=maxkey \
-e DATABASE_USER=root \
-e DATABASE_PWD=maxkey \
--name maxkey-mgt \
--hostname maxkey-mgt \
--network maxkey.top \
-d maxkeytop/maxkey-mgt:latest 

#maxkey-frontend
docker 	run -p 8527:8527  \
--name maxkey-frontend \
--hostname maxkey-frontend \
--network maxkey.top \
-d maxkeytop/maxkey-frontend:latest 

#maxkey-mgt-frontend
docker 	run -p 8526:8526  \
--name maxkey-mgt-frontend \
--hostname maxkey-mgt-frontend \
--network maxkey.top \
-d maxkeytop/maxkey-mgt-frontend:latest 

#maxkey-nginx proxy
docker 	run -p 80:80  \
--name maxkey-nginx \
--hostname maxkey-nginx \
--network maxkey.top \
-d maxkeytop/maxkey-nginx 

docker ps -a

echo "started done."