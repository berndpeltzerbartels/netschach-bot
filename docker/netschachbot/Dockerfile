FROM netschach-baseimage

WORKDIR /app
RUN git clone -c credential.helper= https://github.com/berndpeltzerbartels/netschach-bot.git . && \
    ./gradlew bootJar -x test --no-daemon

ENV PATH="/usr/games/:${PATH}"
RUN mkdir -p /var/log/netschach

ENTRYPOINT ["java", "-Xmx160m", "-Xms160m", "-jar", "/app/build/libs/netschach-bot.jar"]
