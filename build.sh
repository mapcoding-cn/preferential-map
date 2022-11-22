#!/bin/bash
# docker tag 39648ea1be3a ccr.ccs.tencentyun.com/pmap/pmap-service:v6
# docker rm -f $(docker ps -a -q)
# docker image prune -a
# docker exec -it $(docker ps -a -q) bash
# tail -fn 1000 /tmp/log/preferential/preferential.log
# docker rm -f $(docker ps -a -q) && docker image prune -a && docker pull ccr.ccs.tencentyun.com/pmap/pmap-service:v11 && docker run -d -p 8080:8080 -v /tmp/log/:/tmp/log/ --name=pmap ccr.ccs.tencentyun.com/pmap/pmap-service:v11 /root/app/start.sh && docker exec -it $(docker ps -a -q) bash -c "tail -fn 1000 /tmp/log/preferential/preferential.log"


#编译
mvn clean package -Dmaven.test.skip=true
#打包镜像
docker login ccr.ccs.tencentyun.com --username=100000526398 -p byc19911022@
docker build . -t ccr.ccs.tencentyun.com/pmap/pmap-service:v11
docker push ccr.ccs.tencentyun.com/pmap/pmap-service:v11
