#!/bin/bash
set -e

IMAGE_NAME="netschach-bot"
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

echo "Baue Image: $IMAGE_NAME ..."
docker build -t "$IMAGE_NAME" "$SCRIPT_DIR"
echo "Fertig! Image '$IMAGE_NAME' wurde erstellt."
