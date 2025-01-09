#!/bin/bash

# 更新包索引
echo "Updating package index..."
sudo apt-get update -y

# 安装 docker.io
echo "Installing docker.io..."
sudo apt-get install -y docker.io

# 安装 docker-compose
echo "Installing docker-compose..."
sudo apt-get install -y docker-compose

echo "Configuring Docker to use Aliyun registry..."
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json > /dev/null <<EOF
{
  "registry-mirrors": [
    "https://docker.m.daocloud.io"  
  ]
}
EOF
echo "Restarting Docker service..."
sudo systemctl daemon-reload
sudo systemctl restart docker

echo "Running the project"
sudo docker-compose up -d