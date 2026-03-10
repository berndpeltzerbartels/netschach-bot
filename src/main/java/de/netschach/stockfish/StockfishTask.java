package de.netschach.stockfish;

import de.netschach.chess2.GameBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@ToString
class StockfishTask {
    private String gameId;
    private String fen;
    private GameBoard gameBoard;
    private String callback;

    @Setter
    private int timeLimit;

    @Setter
    private Level level;

    @Setter
    private Elo elo;

    @Setter
    private Integer waitingTime;
}
