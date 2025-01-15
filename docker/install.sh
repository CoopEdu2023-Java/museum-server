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

echo "Restarting Docker service..."
sudo systemctl daemon-reload
sudo systemctl restart docker

echo "Running the project"
sudo docker-compose up -d