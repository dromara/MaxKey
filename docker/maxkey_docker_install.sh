echo "network create "

docker network create maxkey.top

mysql_version=8.4.2
#MySQL
docker pull mysql:$mysql_version
docker image tag mysql:$mysql_version maxkeytop/mysql

#maxkey
docker pull maxkeytop/maxkey:latest

#maxkey-mgt
docker pull maxkeytop/maxkey-mgt:latest

#maxkey-frontend
docker pull maxkeytop/maxkey-frontend:latest

#maxkey-mgt-frontend
docker pull maxkeytop/maxkey-mgt-frontend:latest

#maxkey-nginx proxy
cd docker-nginx

docker build -f Dockerfile -t maxkeytop/maxkey-nginx .

cd ..

echo "installed done."