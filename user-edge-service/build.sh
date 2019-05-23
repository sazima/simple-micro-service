#!/usr/bin/env bash

mvn clean install
docker build -t user-edge-service:latest .