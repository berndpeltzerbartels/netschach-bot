FROM eclipse-temurin:23-jdk

RUN apt-get update && apt-get install -qy git stockfish && rm -rf /var/lib/apt/lists/*

WORKDIR /app
RUN git clone -c credential.helper= https://github.com/berndpeltzerbartels/netschach-bot.git . && \
    ./gradlew bootJar -x test --no-daemon

ENV PATH="/usr/games/:${PATH}"
ENTRYPOINT ["java", "-jar", "/app/build/libs/chess-service2-2.0.18.jar"]
