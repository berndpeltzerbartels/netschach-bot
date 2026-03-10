package de.netschach.chess2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GameStatus {
    DEFAULT(false),
    CHECK(false),
    CHECKMATE(true),
    STALEMATE(true),
    DRAW3(false),
    DRAW50(false);

    private final boolean gameOver;

}
