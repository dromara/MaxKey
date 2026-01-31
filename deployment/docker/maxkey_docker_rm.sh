echo "rm MaxKey ... "

./maxkey_docker_stop.sh

#maxkey-nginx proxy
docker rm maxkey-nginx

#maxkey-frontend
docker rm maxkey-frontend

#maxkey-mgt-frontend
docker rm maxkey-mgt-frontend

#maxkey
docker rm maxkey  

#maxkey-mgt
docker rm maxkey-mgt  

#MySQL
docker rm maxkey-mysql  

echo "rm done."