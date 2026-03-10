package de.netschach.fen;

import de.netschach.chess2.*;
import org.junit.jupiter.api.Test;

import static de.netschach.chess2.Color.BLACK;
import static de.netschach.chess2.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;

class FenParserTest {

    private FenParser fenParser = new FenParser();

    @Test
    void startPosition() {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0";
        GameBoard gameBoard = fenParser.parseFen(fen);

        assertThat(gameBoard.getPieces()).isEqualTo(GameBoard.startPosition().getPieces());
    }


    @Test
    void king2() {
        String fen = "8/8/8/2k5/4K3/8/8/8 w - - 10 95";
        GameBoard gameBoard = fenParser.parseFen(fen);

        assertThat(gameBoard.getDraw50Counter().getCount()).isEqualTo(10);

        assertThat(gameBoard.getPieces().get(new Position("e4"))).isInstanceOf(King.class);
        assertThat(gameBoard.getPieces().get(new Position("e4")).getColor()).isEqualTo(WHITE);

        assertThat(gameBoard.getPieces().get(new Position("c5"))).isInstanceOf(King.class);
        assertThat(gameBoard.getPieces().get(new Position("c5")).getColor()).isEqualTo(BLACK);

    }


    @Test
    void moveNumber1() {
        GameBoard gameBoard = GameBoard.startPosition();
        String fen = fenParser.parseGameBoard(gameBoard);
        assertThat(fen).endsWith(" 1");

        Piece piece = gameBoard.getPieces().get(new Position("e2"));
        gameBoard = gameBoard.doMove(new Move(new Position("e2"), new Position("e4"), piece));
        fen = fenParser.parseGameBoard(gameBoard);
        assertThat(fen).endsWith(" 1");

        piece = gameBoard.getPieces().get(new Position("e7"));
        gameBoard = gameBoard.doMove(new Move(new Position("e7"), new Position("e5"), piece));
        fen = fenParser.parseGameBoard(gameBoard);
        assertThat(fen).endsWith(" 1");

        piece = gameBoard.getPieces().get(new Position("d2"));
        gameBoard = gameBoard.doMove(new Move(new Position("d2"), new Position("d4"), piece));
        fen = fenParser.parseGameBoard(gameBoard);
        assertThat(fen).endsWith(" 2");

        piece = gameBoard.getPieces().get(new Position("d7"));
        gameBoard = gameBoard.doMove(new Move(new Position("d7"), new Position("d5"), piece));
        fen = fenParser.parseGameBoard(gameBoard);
        assertThat(fen).endsWith(" 2");
    }

    @Test
    void twoWayParsing() {
        String fen = "r3k2r/p1q2ppp/Qp2p3/1Nb5/4P1n1/PN2B3/1P3PPP/3R1RK1 b kq - 6 17";
        GameBoard gameBoard = fenParser.parseFen(fen);
        String fen2 = fenParser.parseGameBoard(gameBoard);

        assertThat(fen).isEqualTo(fen2);
    }
}