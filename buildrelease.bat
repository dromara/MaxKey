call setenv.bat

set START_TIME="%date:~0,10% %time:~0,2%:%time:~3,5%"
echo start time %START_TIME%

call %JAVA_HOME%/bin/java -version
call %GRADLE_HOME%/bin/gradle -version

call %GRADLE_HOME%/bin/gradle checkenv

call %GRADLE_HOME%/bin/gradle

call %GRADLE_HOME%/bin/gradle war

call %GRADLE_HOME%/bin/gradle buildRelease

call %GRADLE_HOME%/bin/gradle buildReleaseGear

cd build
rd /q /s libs

rd /q /s tmp

cd ..

set END_TIME="%date:~0,10% %time:~0,2%:%time:~3,5%"

echo Build Release start at %START_TIME%  complete at %END_TIME%.

pause