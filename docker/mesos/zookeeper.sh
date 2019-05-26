#!/usr/bin/env bash
docker stop zookeeper
docker rm zookeeper
docker run --name zookeeper -d -p 2181:2181 --restart always  zookeeper:3.5