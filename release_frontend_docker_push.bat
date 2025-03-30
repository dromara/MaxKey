echo off

call setEnvVars.bat

docker -v

set START_TIME="%date:~0,10% %time:~0,2%:%time:~3,5%"
echo start time %START_TIME%

rem call Set-ExecutionPolicy RemoteSigned -Scope Process

cd ./maxkey-web-frontend/maxkey-web-app

rem maxkey-frontend:latest
rem push to docker hub
call docker push %MXK_REPOSITORY%/maxkey-frontend

rem maxkey-frontend:$version
call docker tag  %MXK_REPOSITORY%/maxkey-frontend %MXK_REPOSITORY%/maxkey-frontend:%MXK_VERSION%
rem push to docker hub
call docker push %MXK_REPOSITORY%/maxkey-frontend:%MXK_VERSION%

cd ../../

cd ./maxkey-web-frontend/maxkey-web-mgt-app


rem maxkey-mgt-frontend:latest
rem push to docker hub
call docker push %MXK_REPOSITORY%/maxkey-mgt-frontend

rem maxkey-mgt-frontend:$version
call docker tag %MXK_REPOSITORY%/maxkey-mgt-frontend %MXK_REPOSITORY%/maxkey-mgt-frontend:%MXK_VERSION%
rem push to docker hub
call docker push %MXK_REPOSITORY%/maxkey-mgt-frontend:%MXK_VERSION%

set END_TIME="%date:~0,10% %time:~0,2%:%time:~3,5%"

echo Build Release start at %START_TIME%  complete at %END_TIME%.

pause
