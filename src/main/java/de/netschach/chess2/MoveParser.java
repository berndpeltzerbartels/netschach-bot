package de.netschach.chess2;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MoveParser {

    @Autowired
    private MoveAttributesParser moveAttributesParser;

    @Autowired
    private MoveMatcher moveMatcher;

    @Autowired
    private StatusUpdater statusUpdater;

    public GameBoard parse(List<String> moves) throws IllegalMoveException {
        return parse(GameBoard.startPosition(), moves);
    }

    public GameBoard parse(GameBoard gameBoard, List<String> moves) throws IllegalMoveException {
        Parser parser = new Parser(gameBoard);
        for (String moveStr : moves) {
            parser.parse(moveStr);
        }
        return parser.getGameBoard();
    }

    public GameBoard parse(String moveStr, GameBoard gameBoard) {
        Parser parser = new Parser(gameBoard);
        parser.parse(moveStr);
        return parser.getGameBoard();
    }

    @Getter
    class Parser {
        private GameBoard gameBoard;
        private List<Move> parsedMoves = new ArrayList<>();

        public Parser(GameBoard gameBoard) {
            this.gameBoard = gameBoard;
        }

        void parse(String moveStr) {
            Move move;
            try {
                move = moveMatcher.findMatchingMove(moveAttributesParser.parse(moveStr, gameBoard.getColorOnMove()), gameBoard, moveStr);
                // System.out.println(move);
            } catch (Exception e) {
                TestUtil.dump(gameBoard);
                throw e;
            }
            gameBoard = gameBoard.doMove(move);
            //System.out.println(attributes.getOrig());
            //System.out.println(move);
            //TestUtil.dump(gameBoard);
            statusUpdater.updateStatus(move, gameBoard);
            parsedMoves.add(move);
        }
    }

}
