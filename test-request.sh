#!/bin/bash
# Startet den Echo-Server (Callback-Empfänger) und schickt eine Test-Request an den Java-Service.
#
# Voraussetzungen:
#   - Java-Service läuft auf localhost:8080
#   - Node.js installiert (für echo.js)
#
# Verwendung: ./test-request.sh

# host.docker.internal wird von Docker automatisch auf die Mac-IP aufgelöst
HOST_IP="host.docker.internal"
echo ">>> Callback-Host: $HOST_IP"

# Echo-Server starten (empfängt den Callback vom Java-Service)
echo ">>> Starte Echo-Server auf Port 8000..."
node echo.js &
ECHO_PID=$!
sleep 1

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
    "elo": 3000,
    "timeLimitMillis": 1000,
    "fen": "4k3/2Q5/R7/8/8/8/8/4K3 w - - 0 1",
    "moves": ["a6e6", "e8f8", "c7e7"],
    "callback": {
      "protocol": "http",
      "host": "'$HOST_IP'",
      "port": 8000,
      "uri": "/callback"
    }
  }'

echo ""
echo ">>> Request gesendet. Warte auf Callback..."
echo ">>> Erwarteter Zug von Stockfish: f8g8 (einziger legaler Zug)"
echo ""
echo ">>> Java-Service Logs live:"
docker logs -f netschach-bot &
LOGS_PID=$!
echo ">>> (Strg+C zum Beenden)"
wait $ECHO_PID
kill $LOGS_PID 2>/dev/null
