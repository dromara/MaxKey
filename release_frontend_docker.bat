echo off

set MXK_VERSION=3.5.4
set MXK_REPOSITORY=maxkeytop

set START_TIME="%date:~0,10% %time:~0,2%:%time:~3,5%"
echo start time %START_TIME%

rem call Set-ExecutionPolicy RemoteSigned -Scope Process

cd ./maxkey-web-frontend/maxkey-web-app

call ng     build --prod --base-href /maxkey/

call docker build -f Dockerfile -t %MXK_REPOSITORY%/maxkey-frontend  .

call docker push %MXK_REPOSITORY%/maxkey-frontend

call docker tag  %MXK_REPOSITORY%/maxkey-frontend %MXK_REPOSITORY%/maxkey-frontend:%MXK_VERSION%

call docker push %MXK_REPOSITORY%/maxkey-frontend:%MXK_VERSION%

cd ../../

cd ./maxkey-web-frontend/maxkey-web-mgt-app

call ng     build --prod --base-href /maxkey-mgt/

call docker build -f Dockerfile -t %MXK_REPOSITORY%/maxkey-mgt-frontend  .

call docker push %MXK_REPOSITORY%/maxkey-mgt-frontend

call docker tag %MXK_REPOSITORY%/maxkey-mgt-frontend %MXK_REPOSITORY%/maxkey-mgt-frontend:%MXK_VERSION%

call docker push %MXK_REPOSITORY%/maxkey-mgt-frontend:%MXK_VERSION%

set END_TIME="%date:~0,10% %time:~0,2%:%time:~3,5%"

echo Build Release start at %START_TIME%  complete at %END_TIME%.

pause
