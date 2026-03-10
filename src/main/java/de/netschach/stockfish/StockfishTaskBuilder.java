package de.netschach.stockfish;

import de.netschach.event.FindBestMoveEvent;
import de.netschach.fen.FenParser;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class StockfishTaskBuilder {

    @Autowired
    private FenParser fenParser;

    StockfishTask create(FindBestMoveEvent e) {
        int moveNumber = e.getGameBoard().getMoveNumber();
        StockfishTask task = StockfishTask.builder()//
                .callback(e.getCallbackUrl())
                .gameBoard(e.getGameBoard())
                .gameId(e.getRequestId())
                .level(e.getLevel())
                .elo(e.getElo())
                .timeLimit(e.getTimeLimit())
                .fen(fenParser.parseGameBoard(e.getGameBoard()))
                .build();
        log.info("stockfish-task created for {}: move-number={},level={}, timelimit={}", task.getGameId(), moveNumber, task.getLevel(), task.getTimeLimit());
        log.info("fen for {} in stockfish-task: {}", task.getGameId(), task.getFen());
        return task;
    }
}
