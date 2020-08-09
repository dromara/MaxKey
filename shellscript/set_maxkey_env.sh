#!/bin/bash
echo "-------------------------------------------------------------------------------"
echo "--    Maxkey Sigle Sign On  System                                           --"
echo "--    MaxKey  Version 2.2.0 GA                                               --"
echo "--    Set JAVA_HOME  ....                                                    --"
echo "--    JAVA_HOME   JDK                                                        --"

JAVA_HOME=/opt/jdk-14

export JAVA_HOME=/opt/jdk-14

$JAVA_HOME/bin/java -version
echo "--    JAVA_HOME  $JAVA_HOME "
echo "-------------------------------------------------------------------------------"