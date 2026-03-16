#!/bin/bash
set -e
IMAGE_NAME="netschach-bot"
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
echo "Baue Image: $IMAGE_NAME:latest ..."
docker build --no-cache -t "$IMAGE_NAME:latest" "$SCRIPT_DIR"
echo "Fertig! Image '$IMAGE_NAME:latest' wurde erstellt."
