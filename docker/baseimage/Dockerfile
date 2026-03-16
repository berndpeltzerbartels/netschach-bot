FROM eclipse-temurin:23-jdk

RUN apt-get update && apt-get install -qy git wget && rm -rf /var/lib/apt/lists/*

# Stockfish 18 - passend zur Architektur
RUN ARCH=$(uname -m) && \
    if [ "$ARCH" = "aarch64" ]; then \
        wget -q https://github.com/official-stockfish/Stockfish/releases/download/sf_18/stockfish-ubuntu-aarch64.tar && \
        tar -xf stockfish-ubuntu-aarch64.tar && \
        mv stockfish/stockfish-ubuntu-aarch64 /usr/local/bin/stockfish; \
    else \
        wget -q https://github.com/official-stockfish/Stockfish/releases/download/sf_18/stockfish-ubuntu-x86-64.tar && \
        tar -xf stockfish-ubuntu-x86-64.tar && \
        mv stockfish/stockfish-ubuntu-x86-64 /usr/local/bin/stockfish; \
    fi && \
    chmod +x /usr/local/bin/stockfish && \
    rm -rf stockfish stockfish-ubuntu-*.tar
