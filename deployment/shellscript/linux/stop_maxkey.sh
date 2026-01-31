kill -9 $(ps -ef|grep MaxKeyBoot|grep -v grep|awk '{print $2}')

