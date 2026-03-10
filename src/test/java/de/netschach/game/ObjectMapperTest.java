package de.netschach.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class ObjectMapperTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void gameStatus() throws JsonProcessingException {
        //GameStatusTO gameStatus = GameStatusTO.create() // TODO
        //System.out.println(this.objectMapper.writeValueAsString(gameStatus));
    }

}
