call setEnvVars.bat

set START_TIME="%date:~0,10% %time:~0,2%:%time:~3,5%"
echo start time %START_TIME%

call %GRADLE_HOME%/bin/gradle -q projects

echo start build . . .

rem run task build
call %GRADLE_HOME%/bin/gradle clean build -x test

set END_TIME="%date:~0,10% %time:~0,2%:%time:~3,5%"

echo Build Release start at %START_TIME%  complete at %END_TIME%.

pause