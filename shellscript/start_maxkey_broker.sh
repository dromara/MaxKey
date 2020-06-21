#!/bin/bash

source ./set_maxkey_env.sh

./maxkey_broker/bin/kafka-server-start.sh ./maxkey_broker/config/server.properties
