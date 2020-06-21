@echo off

call set_maxkey_env.bat

echo ==    Web Server Apache Tomcat.                                              ==
echo ==    Web Application Server will started.                                   ==
echo ==    Please wait  ....                                                      ==

echo ==    Start Apache Tomcat...                                                 ==
echo ===============================================================================
set CATALINA_HOME=./maxkey_wiki
call maxkey_wiki\bin\startup.bat





