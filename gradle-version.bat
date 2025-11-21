echo off

call setEnvVars.bat

set GRADLE_VERSION=9.1.0

gradlew.bat wrapper --gradle-version %GRADLE_VERSION%

pause