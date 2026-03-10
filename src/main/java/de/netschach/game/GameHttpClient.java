package de.netschach.game;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
class GameHttpClient {

    private final RestTemplate restTemplate = new RestTemplate();

    int send(GameResponse response) {
        log.info("sending {}", response);
        log.info("bot-move:: {}, waiting-time {}", response.getBotMove(), response.getWaitingTime());
        int status = restTemplate.exchange(response.getUrl(), HttpMethod.PUT, new HttpEntity<>(response), Void.class).getStatusCode().value();
        log.info("sending response: {} to {} returned status {}", response.getRequestId(), response.getUrl(), status);
        return status;
    }
}
