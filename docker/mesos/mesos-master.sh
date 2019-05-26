#!/usr/bin/env bash
MASTER_HOST=192.168.0.110
ZK_HOST=192.168.0.110


docker stop mesos-master
docker rm mesos-master
docker run -d --net=host \
  --hostname=$MASTER_HOST \
  -e MESOS_PORT=5050 \
  -e MESOS_ZK=zk://$ZK_HOST:2181/mesos \
  -e MESOS_QUORUM=1 \
  -e MESOS_REGISTRY=in_memory \
  -e MESOS_LOG_DIR=/var/log/mesos \
  -e MESOS_WORK_DIR=/var/tmp/mesos \
  -v "$(pwd)/log/mesos:/var/log/mesos" \
  -v "$(pwd)/work/mesos:/var/tmp/mesos" \
  --name mesos-master \
  mesosphere/mesos-master:1.4.1  --no-hostname_lookup --ip=$MASTER_HOST
