package de.netschach.game;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
class GameResponsePublisher {

    @Autowired
    private GameHttpClient gameHttpClient;

    private final List<GameResponse> responses = new LinkedList<>();

    @Scheduled(fixedDelay = 5000)
    void retry() {
        while (sendNext()) {
            log.info("sent a scheduled response");
        }
    }

    private boolean sendNext() {
        if (!responses.isEmpty()) {
            GameResponse response = responses.remove(0);
            long ageInMinutes = ageMinutes(response);
            log.info("age of {}: {}", response.getRequestId(), ageInMinutes);
            if (ageMinutes(response) > 5) {
                log.info("age of {} minutes, giving up {}", ageInMinutes, response.getRequestId());
                return true;
            }
            return sendResponse(response);
        }
        return false;
    }


    void publishResult(GameResponse gameResponse) {
        responses.add(gameResponse);
        sendNext();
    }

    private boolean sendResponse(GameResponse response) {
        try {
            int status = gameHttpClient.send(response);
            if (status >= 200 && status < 400) {
                log.info("sending response to {} ok", response.getUrl());
                return true;
            }
            return false;
        } catch (Exception e) {
            log.info("sending response to {} failed", response.getUrl());
            responses.add(response);
            return false;
        }
    }

    private long ageMinutes(GameResponse response) {
        return response.getCreatedAt().until(LocalDateTime.now(), ChronoUnit.MINUTES);
    }
}