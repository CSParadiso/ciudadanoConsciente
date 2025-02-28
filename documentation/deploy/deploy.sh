#!/bin/bash
set -e

# Variables
REPO_BASE_RAW_URL="https://raw.githubusercontent.com/CSParadiso/ciudadanoConsciente/refs/heads/main/documentation/deploy"
NGINX_BASE="/etc/nginx"
NGINX_AVAILABLE="$NGINX_BASE/sites-available"
NGINX_ENABLED="$NGINX_BASE/sites-enabled"

echo "=== Updating package list and installing dependencies ==="
sudo apt update
sudo apt install -y nginx docker.io docker-compose certbot curl

echo "=== Creating temporary deploy directory ==="
TEMP_DEPLOY_DIR=$(mktemp -d)
echo "Temporary directory: $TEMP_DEPLOY_DIR"
cd "$TEMP_DEPLOY_DIR"

echo "=== Downloading required files ==="
# Download non-Nginx files into the temporary directory
curl -o compose.yaml "$REPO_BASE_RAW_URL/compose.yaml"
curl -o init_CIUCO.sql "$REPO_BASE_RAW_URL/init_CIUCO.sql"
curl -o init_KC.sql "$REPO_BASE_RAW_URL/init_KC.sql"
curl -o realm_export.json "$REPO_BASE_RAW_URL/realm_export.json"


echo "=== Downloading Nginx configuration files ==="
# Download Nginx config files directly into the sites-available directory (requires sudo)
sudo curl -o "$NGINX_AVAILABLE/ciudadano" "$REPO_BASE_RAW_URL/ciudadano"
sudo curl -o "$NGINX_AVAILABLE/ciuco" "$REPO_BASE_RAW_URL/ciuco"
sudo curl -o "$NGINX_AVAILABLE/keycloak" "$REPO_BASE_RAW_URL/keycloak"
sudo curl -o "$NGINX_AVAILABLE/keycloak-rest-admin" "$REPO_BASE_RAW_URL/keycloak-rest-admin"
sudo curl -o "$NGINX_BASE/nginx.conf" "$REPO_BASE_RAW_URL/nginx.conf"

echo "=== Creating symbolic links in sites-enabled ==="
# Create symbolic links individually for clarity
sudo ln -sf "$NGINX_AVAILABLE/ciudadano" "$NGINX_ENABLED/ciudadano"
sudo ln -sf "$NGINX_AVAILABLE/ciuco" "$NGINX_ENABLED/ciuco"
sudo ln -sf "$NGINX_AVAILABLE/keycloak" "$NGINX_ENABLED/keycloak"
sudo ln -sf "$NGINX_AVAILABLE/keycloak-rest-admin" "$NGINX_ENABLED/keycloak-rest-admin"

echo "=== Verifying Nginx configuration ==="
sudo nginx -t

echo "=== Restarting and enabling Nginx ==="
sudo systemctl restart nginx
sudo systemctl enable nginx

echo "=== Starting Docker containers using Docker Compose ==="
sudo docker compose up -d

echo "=== Deployment completed successfully ==="

echo "=== Generating certificates with certbot ==="
sudo certbot

