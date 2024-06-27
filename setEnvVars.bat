echo off
echo set env
set JAVA_HOME=C:\IDE\jdk-17.0.2.8
set GRADLE_HOME=C:\IDE\gradle-8.8

call %JAVA_HOME%/bin/java -version
call %GRADLE_HOME%/bin/gradle -version
