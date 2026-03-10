package de.netschach.chess2;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static de.netschach.chess2.Color.BLACK;
import static de.netschach.chess2.Color.WHITE;
import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    @Test
    void allLegalMoves() {
        Set<String> expected = Set.of("a2a3", "b2b3", "c2c3", "d2d3", "e2e3", "f2f3", "g2g3", "h2h3", //
                "a2a4", "b2b4", "c2c4", "d2d4", "e2e4", "f2f4", "g2g4", "h2h4", //
                "b1a3", "b1c3", "g1f3", "g1h3");

        Set<String> moves = GameBoard.startPosition().allLegalMoves().stream().map(Move::toStockfishMove).collect(Collectors.toSet());
        assertEquals(expected, moves);
    }


    @Test
    void isCheckAgainstFalse() {
        assertFalse(GameBoard.startPosition().isCheckAgainst(WHITE));
        assertFalse(GameBoard.startPosition().isCheckAgainst(BLACK));
    }

    @Test
    void isCheckAgainstTrue() {
        GameBoard gameBoard = GameBoard.builder()
                .addPiece(new King(WHITE), Position.E1)
                .addPiece(new King(BLACK), Position.E8)
                .addPiece(new Rook(WHITE), new Position("e2"))
                .setColorOnMove(WHITE).build();

        assertFalse(gameBoard.isCheckAgainst(WHITE));
        assertTrue(gameBoard.isCheckAgainst(BLACK));
    }

}