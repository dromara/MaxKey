echo off
echo set env
set JAVA_HOME=D:\IDE\jdk-17
set GRADLE_HOME=D:\IDE\gradle-7.2

call %JAVA_HOME%/bin/java -version
call %GRADLE_HOME%/bin/gradle -version
