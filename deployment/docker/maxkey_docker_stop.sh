echo "stop MaxKey ... "

#maxkey-nginx proxy
docker stop maxkey-nginx

#maxkey-frontend
docker stop maxkey-frontend

#maxkey-mgt-frontend
docker stop maxkey-mgt-frontend

#maxkey
docker stop maxkey  

#maxkey-mgt
docker stop maxkey-mgt  

#MySQL
docker stop maxkey-mysql  

echo "stoped done."