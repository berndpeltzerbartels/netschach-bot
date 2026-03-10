package de.netschach.game;

import de.netschach.chess2.GameBoard;
import de.netschach.chess2.Move;
import de.netschach.chess2.MoveParser;
import de.netschach.event.GameOverEvent;
import de.netschach.event.StockfishMovedEvent;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
class GameEventController {

    @Autowired
    private GameResponsePublisher responsePublisher;

    @Autowired
    private MoveParser moveParser;

    @EventListener
    void stockfishMoved(StockfishMovedEvent e) {
        GameBoard gameBoard = moveParser.parse(e.getMove(), e.getGameBoard());
        log.info("parsed stockfish-move: {} to {}", e.getMove(), gameBoard.getMove().toLongNotation());
        Move botMove = gameBoard.getMove();
        responsePublisher.publishResult(GameResponse.builder()
                .createdAt(LocalDateTime.now())
                .requestId(e.getRequestId())
                .url(e.getCallback())
                .botMove(botMove.toLongNotation())
                .botMoveType(botMove.getClass().getSimpleName())
                .gameStatus(GameStatusTO.create(gameBoard))
                .waitingTime(e.getWaitingTime())
                .build());
    }

    @EventListener
    void gameOverEvent(GameOverEvent e) {
        GameResponse response = GameResponse.builder()
                .createdAt(LocalDateTime.now())
                .requestId(e.getRequestId())
                .url(e.getCallback())
                .waitingTime(0)
                .gameStatus(GameStatusTO.create(e.getGameBoard())).build();
        responsePublisher.publishResult(response);
    }

}
