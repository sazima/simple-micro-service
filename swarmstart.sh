#!/usr/bin/env bash
./api-gateway-zuul/build.sh
./course-dubbo-service/build.sh
./course-edge-service/build.sh
./message-thrift-python-service/build.sh
./user-edge-service/build.sh
./user-thrift-service/build.sh