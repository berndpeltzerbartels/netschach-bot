#!/bin/bash

curl -X PUT -iH "Content-Type: application/json" -d '{"callback": {"protocol":"http", "port": 8000, "uri": "/receive.php"}, "level": 5, "moves": ["e4", "Sc6","Sf3", "e5","f1c4", "g8f6","d1e2","f8c5", "d2d3", "0-0","o-o", "h6"]}' https://chess-service2.cfapps.io/api/game
