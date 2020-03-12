call gradleSetEnv.bat

set START_TIME="%date:~0,10% %time:~0,2%:%time:~3,5%"
echo start time %START_TIME%

call %JAVA_HOME%/bin/java -version
call %GRADLE_HOME%/bin/gradle -version

echo start clean . . .

call %GRADLE_HOME%/bin/gradle clean

set END_TIME="%date:~0,10% %time:~0,2%:%time:~3,5%"

echo clean start at %START_TIME%  complete at %END_TIME%.

pause

