package de.netschach.game;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
class GameResponseStore {

    private final List<GameResponse> responses = Collections.synchronizedList(new LinkedList<>());

    void addResponse(GameResponse response) {
        responses.add(response);
    }

}
