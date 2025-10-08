echo off
echo set env
set JAVA_HOME=C:\IDE\jdk-17
set GRADLE_HOME=C:\ide\gradle-8.8

set MXK_VERSION=4.1.9
set MXK_REPOSITORY=maxkeytop

call %JAVA_HOME%/bin/java -version
call %GRADLE_HOME%/bin/gradle -version
