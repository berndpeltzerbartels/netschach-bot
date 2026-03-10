#!/bin/bash
# Startet den Echo-Server (Callback-Empfänger) und schickt eine Test-Request an den Java-Service.
#
# Voraussetzungen:
#   - Java-Service läuft auf localhost:8080
#   - Node.js installiert (für echo.js)
#
# Verwendung: ./test-request.sh

# Echo-Server starten (empfängt den Callback vom Java-Service)
echo ">>> Starte Echo-Server auf Port 8000..."
node echo.js &
ECHO_PID=$!
sleep 1

# Request an den Java-Service schicken
echo ">>> Schicke Request an http://localhost:8080/api/game/v2/bestmove ..."
curl -s -X PUT http://localhost:8080/api/game/v2/bestmove \
  -H "Content-Type: application/json" \
  -d '{
    "requestId": "testrequest001",
    "elo": 3000,
    "timeLimitMillis": 20000,
    "fen": "4k3/2Q5/R7/8/8/8/8/4K3 w - - 0 1",
    "moves": ["Te6", "Kf8", "De7+"],
    "callback": {
      "protocol": "http",
      "host": "localhost",
      "port": 8000,
      "uri": "/callback"
    }
  }'

echo ""
echo ">>> Request gesendet. Warte auf Callback-Antwort im Echo-Server..."
echo ">>> (Strg+C zum Beenden)"

# Warten bis der User abbricht
wait $ECHO_PID
