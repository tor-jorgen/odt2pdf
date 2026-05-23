#!/bin/bash

# Scan libs
docker run \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v .:/mount -w /mount  \
  -v trivy-cache:/root/.cache/ \
  aquasec/trivy:latest --severity HIGH,CRITICAL rootfs .

# Check config
docker run \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v .:/mount -w /mount  \
  -v trivy-cache:/root/.cache/ \
  aquasec/trivy:latest --severity HIGH,CRITICAL config .
