package de.netschach.game;

import de.netschach.chess2.GameBoard;
import de.netschach.chess2.IllegalMoveException;
import de.netschach.chess2.MoveParser;
import de.netschach.event.FindBestMoveEvent;
import de.netschach.event.GameOverEvent;
import de.netschach.fen.FenParser;
import lombok.extern.log4j.Log4j2;
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

    void bestMove(String requestId, int level, List<String> moves, String callback) throws IllegalMoveException, NoSuchElementException {
        GameBoard gameBoard = moveParser.parse(moves);
        bestMove(requestId, level, gameBoard, callback);
    }

    void bestMove(String requestId, int level, String fen, List<String> moves, String callback) throws IllegalMoveException, NoSuchElementException {
        GameBoard gameBoard = moveParser.parse(fenParser.parseFen(fen), moves);
        bestMove(requestId, level, gameBoard, callback);
    }

    GameStatusTO gameStatus(List<String> moves) {
        return GameStatusTO.create(moveParser.parse(moves));
    }

    GameStatusTO gameStatus(List<String> moves, String fen) {
        GameBoard gameBoard = moveParser.parse(fenParser.parseFen(fen), moves);
        return GameStatusTO.create(gameBoard);
    }

    private void bestMove(String requestId, int level, GameBoard gameBoard, String callback) {
        if (gameBoard.getStatus().isGameOver()) {
            multicaster.multicastEvent(new GameOverEvent(requestId, gameBoard, callback));
            return;
        }
        multicaster.multicastEvent(new FindBestMoveEvent(requestId, gameBoard, level, callback));
    }


}
