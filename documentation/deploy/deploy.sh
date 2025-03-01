#!/bin/bash
set -e

# Variables
REPO_BASE_RAW_URL="https://raw.githubusercontent.com/CSParadiso/ciudadanoConsciente/refs/heads/main/documentation/deploy"

echo "=== Updating package list and installing dependencies ==="
sudo apt update
sudo apt install -y docker.io docker-compose

echo "=== Creating deploy directory ==="
sudo mkdir deploy
cd deploy

echo "=== Downloading required files ==="
# Download non-Nginx files into the temporary directory
sudo curl -o compose.yaml "$REPO_BASE_RAW_URL/compose.yaml"
sudo curl -o init_CIUCO.sql "$REPO_BASE_RAW_URL/init_CIUCO.sql"
sudo curl -o init_KC.sql "$REPO_BASE_RAW_URL/init_KC.sql"
sudo curl -o .env "$REPO_BASE_RAW_URL/.env"

echo "=== Generating folder for filesystem data  ==="
sudo mkdir /var/tmp/ciuco
sudo mkdir /var/tmp/ciuco/thumbnail_images
sudo mkdir /var/tmp/ciuco/content_images
sudo mkdir /var/log/ciuco

echo "=== Permissions configuration ==="
sudo chown -R 185:root /var/log/ciuco
sudo chown -R 185:root /var/tmp/ciuco

echo "=== Script successfully executed ==="

