#!/bin/bash
sudo apt update -y
sudo apt install -y docker.io docker-compose git openjdk-17-jdk
sudo usermod -aG docker $USER
mkdir -p /opt/ticketboard
sudo systemctl enable --now docker
cat > /opt/ticketboard/docker-compose.yml <<'EOF'
version: "3.8"
services:
  db:
    image: postgres:14
    environment:
      POSTGRES_DB: ticketdb
      POSTGRES_USER: ticketuser
      POSTGRES_PASSWORD: securepassword
    volumes:
      - db-data:/var/lib/postgresql/data
  ticketboard:
    image: PLACEHOLDER_DOCKERHUB/ticketboard:latest
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/ticketdb
      SPRING_DATASOURCE_USERNAME: ticketuser
      SPRING_DATASOURCE_PASSWORD: securepassword
      SERVER_PORT: 8080
    ports:
      - "8080:8080"
volumes:
  db-data:
EOF
