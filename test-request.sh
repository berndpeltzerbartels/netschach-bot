#!/bin/bash
# Schickt eine Test-Request an den Java-Service (läuft via Docker Compose).
#
# Voraussetzungen:
#   - Docker Compose läuft: cd docker/compose && docker-compose up -d
#
# Verwendung: ./test-request.sh

HOST_IP="echo"
echo ">>> Callback-Host: $HOST_IP"

# Endspielposition: Weiß König e1, Dame c7, Turm a6 / Schwarz König e8
# Nach 3 Zügen hat Schwarz (Stockfish) genau einen legalen Zug: e8d8
# FEN: 4k3/2Q5/R7/8/8/8/8/4K3 w - - 0 1
# Züge (UCI): a6e6 (Turm e6), e8f8 (König muss nach f8), c7e7 (Dame e7 - Schach)
# → Schwarz hat nur noch d8: e7xd8 ist nicht möglich, einziger Zug ist f8g8
echo ">>> Schicke Request an http://localhost:8080/api/game/v2/bestmove ..."
curl -s -X PUT http://localhost:8080/api/game/v2/bestmove \
  -H "Content-Type: application/json" \
  -d '{
    "requestId": "testrequest001",
    "level": 3,
    "timeLimitMillis": 1000,
    "fen": "4k3/2Q5/R7/8/8/8/8/4K3 w - - 0 1",
    "moves": ["a6e6", "e8f8", "c7e7"],
    "callback": {
      "protocol": "http",
      "host": "echo",
      "port": 8000,
      "uri": "/callback"
    }
  }'

echo ""
echo ">>> Request gesendet. Warte auf Callback..."
echo ">>> Erwarteter Zug von Stockfish: f8g8 (einziger legaler Zug)"
sleep 3
echo ""
echo ">>> Echo-Server Logs (Callback-Antwort von netschach-bot):"
docker logs compose-echo-1
