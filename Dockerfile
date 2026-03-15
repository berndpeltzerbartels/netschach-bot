FROM eclipse-temurin:23-jdk

RUN apt-get update && apt-get install -qy git wget && rm -rf /var/lib/apt/lists/*

# Stockfish 18 (stable) direkt von GitHub - x86-64
RUN wget -q https://github.com/official-stockfish/Stockfish/releases/download/sf_18/stockfish-ubuntu-x86-64.tar && \
    tar -xf stockfish-ubuntu-x86-64.tar && \
    mv stockfish/stockfish-ubuntu-x86-64 /usr/local/bin/stockfish && \
    chmod +x /usr/local/bin/stockfish && \
    rm -rf stockfish stockfish-ubuntu-x86-64.tar

WORKDIR /app
RUN git clone -c credential.helper= https://github.com/berndpeltzerbartels/netschach-bot.git . && \
    ./gradlew bootJar -x test --no-daemon

ENV PATH="/usr/games/:${PATH}"
RUN mkdir -p /var/log/netschach
ENTRYPOINT ["java", "-Xmx160m", "-Xms160m", "-jar", "/app/build/libs/netschach-bot.jar"]
