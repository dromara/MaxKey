echo off

call setEnvVars.bat

docker -v

set START_TIME="%date:~0,10% %time:~0,2%:%time:~3,5%"
echo start time %START_TIME%

rem call Set-ExecutionPolicy RemoteSigned -Scope Process

cd ./maxkey-webs/maxkey-web-maxkey

call docker build -f Dockerfile -t %MXK_REPOSITORY%/maxkey  .

rem maxkey:latest
rem push to docker hub
call docker push %MXK_REPOSITORY%/maxkey

rem maxkey:$version
call docker tag  %MXK_REPOSITORY%/maxkey %MXK_REPOSITORY%/maxkey:%MXK_VERSION%
rem push to docker hub
call docker push %MXK_REPOSITORY%/maxkey:%MXK_VERSION%

cd ../../

cd ./maxkey-webs/maxkey-web-mgt

call docker build -f Dockerfile -t %MXK_REPOSITORY%/maxkey-mgt  .

rem maxkey-mgt:latest
rem push to docker hub
call docker push %MXK_REPOSITORY%/maxkey-mgt

rem maxkey-mgt:$version
call docker tag %MXK_REPOSITORY%/maxkey-mgt %MXK_REPOSITORY%/maxkey-mgt:%MXK_VERSION%
rem push to docker hub
call docker push %MXK_REPOSITORY%/maxkey-mgt:%MXK_VERSION%

set END_TIME="%date:~0,10% %time:~0,2%:%time:~3,5%"

echo Build Release start at %START_TIME%  complete at %END_TIME%.

pause
