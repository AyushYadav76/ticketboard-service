#!/bin/bash
if [ -z "$1" ]; then
  echo "Usage: deploy.sh <image:tag>"
  exit 1
fi
IMAGE_TAG=$1
ssh -o StrictHostKeyChecking=no ubuntu@PLACEHOLDER_EC2_IP << EOF
  cd /opt/ticketboard || mkdir -p /opt/ticketboard && cd /opt/ticketboard
  sed -i "s|image: .*|image: ${IMAGE_TAG}|" docker-compose.yml || true
  docker pull ${IMAGE_TAG} || true
  docker-compose down || true
  docker-compose up -d
  echo ${IMAGE_TAG} > last_successful_tag.txt
EOF
