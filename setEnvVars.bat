echo off
echo set env
set JAVA_HOME=C:\ide\jdk-17.0.9+9
set GRADLE_HOME=C:\ide\gradle-8.8

call %JAVA_HOME%/bin/java -version
call %GRADLE_HOME%/bin/gradle -version
