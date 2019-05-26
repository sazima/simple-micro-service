#!/usr/bin/env bash
ZK_HOST=192.168.0.110

docker stop marathon
docker rm marathon
docker run -d --net=host \
 --name marathon \
 mesosphere/marathon \
 --http_port 8090 \
 --master zk://$ZK_HOST:2181/mesos \
 --zk zk://$ZK_HOST:2181/marathon

#  --master mesos主节点zk地址
# --zk marathon的zk节点(当前)
