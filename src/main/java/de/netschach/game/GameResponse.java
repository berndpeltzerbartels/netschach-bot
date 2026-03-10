package de.netschach.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
class GameResponse {

    @NonNull
    private final String requestId;

    @NonNull
    private final GameStatusTO gameStatus;

    private final String botMove;

    private final String botMoveType;

    @NonNull
    private final String url;

    @NonNull
    private final Integer waitingTime;

    @JsonIgnore
    private final LocalDateTime createdAt;
}
