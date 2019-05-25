#!/usr/bin/env bash
docker run -d --net=host \
 mesosphere/marathon:v1.5.2 \
 --master zk://192.168.0.110:2181/mesos \
  --zk zk://192.168.0.110:2181/marathon
#  --master mesos主节点的地址
# --zk 当前地址
