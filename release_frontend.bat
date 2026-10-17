echo off

call setEnvVars.bat

set START_TIME="%date:~0,10% %time:~0,2%:%time:~3,5%"
echo start time %START_TIME%

rem call Set-ExecutionPolicy RemoteSigned -Scope Process

cd ./maxkey-web-frontend/maxkey-web-ng

call npm run build:prod 

cd ../../

cd ./maxkey-web-frontend/maxkey-web-mgt-ng

call npm run build:prod

set END_TIME="%date:~0,10% %time:~0,2%:%time:~3,5%"

echo Build Release start at %START_TIME%  complete at %END_TIME%.

pause
