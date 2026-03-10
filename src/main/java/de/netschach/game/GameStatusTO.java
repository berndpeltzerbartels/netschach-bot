package de.netschach.game;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.netschach.chess2.Color;
import de.netschach.chess2.GameBoard;
import de.netschach.chess2.GameStatus;
import de.netschach.chess2.Move;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Accessors(fluent = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GameStatusTO {

    private boolean check;
    private boolean checkMate;
    private boolean draw3;
    private boolean draw50;
    private boolean staleMate;
    private List<String> moves;
    private boolean gameOver;
    private TreeMap<String, Character> pieces;
    private int hashCode;
    private Color colorToMove;


    public static GameStatusTO create(GameBoard board) {
        return new GameStatusTO()
                .checkMate(board.getStatus() == GameStatus.CHECKMATE)
                .check(board.getStatus() == GameStatus.CHECK)
                .staleMate(board.getStatus() == GameStatus.STALEMATE)
                .draw3(board.getStatus() == GameStatus.DRAW3)
                .draw50(board.getStatus() == GameStatus.DRAW50)
                .gameOver(board.getStatus().isGameOver())
                .moves(board.getMoves().stream().map(Move::toLongNotation).collect(Collectors.toList()))
                .pieces(asPieces(board))
                .colorToMove(board.getColorOnMove().opposite());
    }

    public static GameStatusTO statusAtStart() {
        return new GameStatusTO()
                .moves(Collections.emptyList())
                .pieces(asPieces(GameBoard.startPosition()))
                .colorToMove(Color.WHITE);
    }

    private static TreeMap<String, Character> asPieces(GameBoard board) {
        TreeMap<String, Character> map = new TreeMap<>();
        board.getPieces().entrySet().forEach(e -> {
            map.put(e.getKey().toString(), e.getValue().getPieceChar());
        });
        return map;
    }


}
