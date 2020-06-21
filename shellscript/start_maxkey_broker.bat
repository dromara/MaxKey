@echo off

call set_maxkey_env.bat

call maxkey_broker/bin/windows/kafka-server-start.bat maxkey_broker/config/server.properties
