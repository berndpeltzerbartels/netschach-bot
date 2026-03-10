package de.netschach.chess2;

import org.junit.jupiter.api.Test;

import static de.netschach.chess2.Color.BLACK;
import static de.netschach.chess2.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;

class StatusUpdaterTest {

    private StatusUpdater updater = new StatusUpdater();

    @Test
    void check() {
        GameBoard gameBoard = GameBoard.builder()
                .addPiece(new King(WHITE), Position.E1)
                .addPiece(new King(BLACK), Position.E8)
                .addPiece(new Rook(WHITE), "b1")
                .setColorOnMove(WHITE).build();
        Rook rook = (Rook) gameBoard.getPiece("b1");
        Move move = new Move("b1", "b8", rook);
        gameBoard = gameBoard.doMove(move);

        updater.updateStatus(move, gameBoard);

        assertThat(move.isCheckmate()).isFalse();
        assertThat(move.isCheck()).isTrue();
    }

    @Test
    void checkmate() {
        GameBoard gameBoard = GameBoard.builder()
                .addPiece(new King(WHITE), "e1")
                .addPiece(new King(BLACK), "e8")
                .addPiece(new Rook(WHITE), "a7")
                .addPiece(new Rook(WHITE), "b1")
                .setColorOnMove(WHITE).build();
        Rook rook = (Rook) gameBoard.getPiece("b1");
        Move move = new Move("b1", "b8", rook);
        gameBoard = gameBoard.doMove(move);

        updater.updateStatus(move, gameBoard);

        assertThat(move.isCheckmate()).isTrue();
    }

    @Test
    void stalemate() {
        GameBoard gameBoard = GameBoard.builder()
                .addPiece(new King(WHITE), "e1")
                .addPiece(new King(BLACK), "h8")
                .addPiece(new Rook(WHITE), "a7")
                .addPiece(new Rook(WHITE), "h1")
                .setColorOnMove(WHITE).build();
        Rook rook = (Rook) gameBoard.getPiece("h1");
        Move move = new Move("h1", "g1", rook);
        gameBoard = gameBoard.doMove(move);

        updater.updateStatus(move, gameBoard);

        assertThat(move.isCheckmate()).isFalse();
        assertThat(move.isCheck()).isFalse();
        assertThat(move.isStalemate()).isTrue();
    }
}