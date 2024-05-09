kill -9 $(ps -ef|grep MaxKeyMgtBoot|grep -v grep|awk '{print $2}')
