#!/bin/bash

source ./set_maxkey_env.sh

./maxkey_broker/bin/zookeeper-server-start.sh ./maxkey_broker/config/zookeeper.properties
