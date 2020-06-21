@echo off

call set_maxkey_env.bat

echo ==    Dont close Window while MySQL is running                               ==
echo ==    MySQL is trying to start                                               ==
echo ==    Please wait  ...                                                       ==
echo ==    MySQL is starting with configration:                                   ==
echo ==        maxkey-mysql\mysql\my.ini                                    ==
echo ===============================================================================

maxkey_mysql\bin\mysqld --defaults-file=maxkey_mysql\my.ini --standalone --console

if errorlevel 1 goto error
goto finish

:error

:finish