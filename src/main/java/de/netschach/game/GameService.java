package de.netschach.game;

import de.netschach.chess2.GameBoard;
import de.netschach.chess2.IllegalMoveException;
import de.netschach.chess2.MoveParser;
import de.netschach.event.FindBestMoveEvent;
import de.netschach.event.GameOverEvent;
import de.netschach.fen.FenParser;
import de.netschach.stockfish.Elo;
import de.netschach.stockfish.Level;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
class GameService {

    @Autowired
    private ApplicationEventMulticaster multicaster;

    @Autowired
    private MoveParser moveParser;

    @Autowired
    private FenParser fenParser;

    void bestMove(String requestId, Integer level, Integer elo, int timeLimit, String fen, List<String> moves, String callback) throws IllegalMoveException, NoSuchElementException {
        var startBoard = fen != null ? fenParser.parseFen(fen) : GameBoard.startPosition();
        var gameBoard = moveParser.parse(startBoard, moves);
        if (gameBoard.getStatus().isGameOver()) {
            multicaster.multicastEvent(new GameOverEvent(requestId, gameBoard, callback));
            return;
        }
        var bestMoveEvent = new FindBestMoveEvent(requestId, gameBoard, timeLimit, callback);
        if (level != null) {
            bestMoveEvent.setLevel(new Level(level));
        }
        if (elo != null) {
            bestMoveEvent.setElo(new Elo(elo));
        }
        multicaster.multicastEvent(bestMoveEvent);
    }

    GameStatusTO gameStatus(List<String> moves) {
        return GameStatusTO.create(moveParser.parse(moves));
    }

    GameStatusTO gameStatus(List<String> moves, String fen) {
        GameBoard gameBoard = moveParser.parse(fenParser.parseFen(fen), moves);
        return GameStatusTO.create(gameBoard);
    }

}
