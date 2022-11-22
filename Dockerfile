FROM centos:7
COPY ./centos7_base.repo /etc/yum.repos.d/CentOS-Base.repo
#设置时区
RUN  \cp -f /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
#安装软件
RUN yum install -y wget zip java-11-openjdk.x86_64 unzip less

#环境变量
ENV JAR_NAME=preferential-1.0.0-SNAPSHOT.jar
ENV APP_PATH=/root/app/
ENV MVN_PATH=preferential-map-app
ENV APP_NAME=preferential
ENV log_file=/tmp/log/preferential

#复制资源
COPY ./$MVN_PATH/target/$JAR_NAME $APP_PATH/$JAR_NAME
COPY ./start.sh $APP_PATH/start.sh
COPY ./transmittable-thread-local-2.12.6.jar /usr/local/agent/transmittable-thread-local-2.12.6.jar

#启动服务
WORKDIR $APP_PATH
RUN chmod +x  $APP_PATH/start.sh