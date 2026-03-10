FROM adoptopenjdk/openjdk12
COPY stockfish_20090216_x64_bmi2 /usr/games/stockfish
RUN chmod u+x /usr/games/stockfish
ENV PATH="/usr/games/:${PATH}"
COPY *.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]