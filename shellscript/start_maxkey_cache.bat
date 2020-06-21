@echo off

call set_maxkey_env.bat

echo ==    Cache Server.                                                          ==
echo ==    Please wait  ....                                                      ==

echo ===============================================================================
call maxkey_cache\32bit\redis-server.exe





