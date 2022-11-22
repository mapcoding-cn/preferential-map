#!/bin/bash

#工作目录
APP_HOME=/root/app
echo "app home is $APP_HOME"

#创建日志目录
LOG_PATH=/tmp/log/preferential
if [ ! -d $LOG_PATH ]; then
  mkdir -p $LOG_PATH
  echo "$LOG_PATH create success"
fi


#解压
cd $APP_HOME
unzip -o $JAR_NAME
echo " $JAR_NAME unzip success"

export JAVA_OPTS="-server -Xms2g -Xmx2g -XX:+UnlockExperimentalVMOptions -XX:+UseZGC -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$LOG_PATH/dump -verbose:gc -Xlog:gc*,safepoint:$LOG_PATH/gc.log:time,uptime:filecount=5,filesize=100M -XX:+FlightRecorder"
export JAVA_AGENTS_PARAM="-javaagent:/usr/local/agent/transmittable-thread-local-2.12.6.jar "

#启动应用
echo "java_opts is $JAVA_OPTS, starting..."
java $JAVA_AGENTS_PARAM $JAVA_OPTS -Dspring.profiles.active=pro -jar $JAR_NAME
