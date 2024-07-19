call setEnvVars.bat

set START_TIME="%date:~0,10% %time:~0,2%:%time:~3,5%"
echo start time %START_TIME%

call %GRADLE_HOME%/bin/gradle configTradition  -b build_cnf.gradle


#pause
pause