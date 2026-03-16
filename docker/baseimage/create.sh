#!/bin/bash
set -e

IMAGE_NAME="netschach-baseimage"
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

echo "Baue Baseimage: $IMAGE_NAME ..."
docker build -t "$IMAGE_NAME" "$SCRIPT_DIR"
echo "Fertig! Image '$IMAGE_NAME' wurde erstellt."
