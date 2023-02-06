echo "clear REPOSITORY IMAGE MaxKey ... "

#maxkey-nginx proxy
docker rmi maxkeytop/maxkey-nginx

#maxkey-frontend
docker rmi maxkeytop/maxkey-frontend

#maxkey-mgt-frontend
docker rmi maxkeytop/maxkey-mgt-frontend

#maxkey
docker rmi maxkeytop/maxkey  

#maxkey-mgt
docker rmi maxkeytop/maxkey-mgt  

#MySQL
docker rmi maxkeytop/mysql  

echo "clear REPOSITORY IMAGE done."