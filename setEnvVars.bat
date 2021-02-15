echo off
echo set env
set JAVA_HOME=C:\IDES\jdk-15.0.1
set GRADLE_HOME=C:\IDES\gradle-6.7

call %JAVA_HOME%/bin/java -version
call %GRADLE_HOME%/bin/gradle -version
