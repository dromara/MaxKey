@echo off

call set_maxkey_env.bat

SET JAVA_MARK=MaxKeyRunner
SET JAVA_OPTS= -Xms128m 
SET JAVA_OPTS=%JAVA_OPTS% -Xmx1024m
SET JAVA_OPTS=%JAVA_OPTS% -Dfile.encoding=UTF-8
rem SET JAVA_OPTS="%JAVA_OPTS% -Dfile.encoding=GBK"
SET JAVA_OPTS=%JAVA_OPTS% -DjavaMark=%JAVA_MARK%

SET JAVA_CONF=./maxkey
SET JAVA_LIBPATH=./lib
SET JAVA_LIBMAXKEYPATH=./maxkey
SET JAVA_CLASSPATH=./classes;./bin;%JAVA_CONF%
SET JAVA_MAINCLASS=org.dromara.maxkey.MaxKeyApplication
SET JAVA_EXEC=%JAVA_HOME%/bin/java

rem mk logs dir
if NOT EXIST "./logs" MKDIR "logs"
rem init TEMP_CLASSPATH
SET TEMP_CLASSPATH=
rem new setclasspath.bat
echo SET TEMP_CLASSPATH=%%TEMP_CLASSPATH%%;%%1> setclasspath.bat

FOR  %%i IN (%JAVA_LIBPATH%/*.jar) DO (
CALL setclasspath.bat %JAVA_LIBPATH%/%%i
)

FOR  %%i IN (%JAVA_LIBMAXKEYPATH%/*.jar) DO (
CALL setclasspath.bat %JAVA_LIBMAXKEYPATH%/%%i
)

SET JAVA_CLASSPATH=%JAVA_CLASSPATH%;%TEMP_CLASSPATH%
rem delete setclasspath.bat
DEL setclasspath.bat

rem Display our environment
echo ===============================================================================  
echo Bootstrap Environment 
echo.  
rem echo JAVA_CLASSPATH =  %JAVA_CLASSPATH%
echo JAVA_CONF      =  %JAVA_CONF%  
echo JAVA_OPTS      =  %JAVA_OPTS%  
echo JAVA_HOME      =  %JAVA_HOME%  
echo JAVA           =  %JAVA_EXEC%  
echo JAVA           =  %JAVA_MAINCLASS%  
echo.  
echo ===============================================================================  
echo.  
  
%JAVA_EXEC% %JAVA_OPTS%  -classpath %JAVA_CLASSPATH% %JAVA_MAINCLASS%

echo run finished
PAUSE