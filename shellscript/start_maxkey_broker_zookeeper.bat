@echo off

call set_maxkey_env.bat

call maxkey_broker/bin/windows/zookeeper-server-start.bat maxkey_broker/config/zookeeper.properties
