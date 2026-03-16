FROM eclipse-temurin:23-jdk

RUN apt-get update && apt-get install -qy git wget && rm -rf /var/lib/apt/lists/*

# Stockfish 18 - x86-64 (für Hetzner/Linux Server)
RUN wget -q https://github.com/official-stockfish/Stockfish/releases/download/sf_18/stockfish-ubuntu-x86-64.tar && \
    tar -xf stockfish-ubuntu-x86-64.tar && \
    mv stockfish/stockfish-ubuntu-x86-64 /usr/local/bin/stockfish && \
    chmod +x /usr/local/bin/stockfish && \
    rm -rf stockfish stockfish-ubuntu-x86-64.tar
