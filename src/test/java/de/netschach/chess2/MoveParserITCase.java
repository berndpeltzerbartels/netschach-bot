package de.netschach.chess2;


import de.netschach.fen.FenParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static de.netschach.chess2.Color.BLACK;
import static de.netschach.chess2.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MoveParserITCase {

    @Autowired
    private MoveParser moveParser;

    @Autowired
    private FenParser fenParser;

    @Test
    void simpleShortGame() {
        List<? extends Move> moves = moveParser.parse(List.of("e4", "e5", "d4", "Sf6")).getMoves();

        assertThat(moves.get(0).getFrom()).isEqualTo(new Position("e2"));
        assertThat(moves.get(0).getTo()).isEqualTo(new Position("e4"));
        assertThat(moves.get(0).getPiece()).isEqualTo(new Pawn(WHITE));

        assertThat(moves.get(1).getFrom()).isEqualTo(new Position("e7"));
        assertThat(moves.get(1).getTo()).isEqualTo(new Position("e5"));
        assertThat(moves.get(1).getPiece()).isEqualTo(new Pawn(BLACK));

        assertThat(moves.get(2).getFrom()).isEqualTo(new Position("d2"));
        assertThat(moves.get(2).getTo()).isEqualTo(new Position("d4"));
        assertThat(moves.get(2).getPiece()).isEqualTo(new Pawn(WHITE));

        assertThat(moves.get(3).getFrom()).isEqualTo(new Position("g8"));
        assertThat(moves.get(3).getTo()).isEqualTo(new Position("f6"));
        assertThat(moves.get(3).getPiece()).isEqualTo(new Knight(BLACK));
    }

    @Test
    void draw3() {
        GameBoard gameBoard = GameBoard.builder()
                .addPiece(new King(WHITE), "e1")
                .addPiece(new King(BLACK), "e8").build();

        gameBoard = moveParser.parse("Kd1", gameBoard);
        gameBoard = moveParser.parse("Kd8", gameBoard);
        gameBoard = moveParser.parse("Ke1", gameBoard);
        gameBoard = moveParser.parse("Ke8", gameBoard);
        gameBoard = moveParser.parse("Kd1", gameBoard);
        gameBoard = moveParser.parse("Kd8", gameBoard);
        gameBoard = moveParser.parse("Ke1", gameBoard);
        Move move = moveParser.parse("Ke8", gameBoard).getMove();
        assertThat(move.isDraw3()).isTrue();
    }

    @Test
    void check() {
        GameBoard gameBoard = GameBoard.startPosition();
        gameBoard = moveParser.parse("e4", gameBoard);
        gameBoard = moveParser.parse("e5", gameBoard);
        gameBoard = moveParser.parse("Lc4", gameBoard);
        gameBoard = moveParser.parse("d6", gameBoard);
        Move move = moveParser.parse("Lf7", gameBoard).getMove();
        assertThat(move.isCheck()).isTrue();
    }

    @Test
    void checkmate() {
        GameBoard gameBoard = GameBoard.startPosition();
        gameBoard = moveParser.parse("e4", gameBoard);
        gameBoard = moveParser.parse("e5", gameBoard);
        gameBoard = moveParser.parse("Lc4", gameBoard);
        gameBoard = moveParser.parse("d6", gameBoard);
        gameBoard = moveParser.parse("Df3", gameBoard);
        gameBoard = moveParser.parse("h6", gameBoard);
        Move move = moveParser.parse("Df7", gameBoard).getMove();
        assertThat(move.isCheckmate()).isTrue();
    }


    @Test
    void draw50() {
        GameBoard gameBoard = GameBoard.builder()
                .addPiece(new King(WHITE), "a1")
                .addPiece(new King(BLACK), "e8").build();

        gameBoard = moveParser.parse("Kb1", gameBoard);
        gameBoard = moveParser.parse("Kd8", gameBoard);
        gameBoard = moveParser.parse("Kc1", gameBoard);
        gameBoard = moveParser.parse("Ke8", gameBoard);
        gameBoard = moveParser.parse("Kd1", gameBoard);
        gameBoard = moveParser.parse("Kd8", gameBoard);
        gameBoard = moveParser.parse("Ke1", gameBoard);
        gameBoard = moveParser.parse("Ke8", gameBoard);

        gameBoard = moveParser.parse("Kf1", gameBoard);
        gameBoard = moveParser.parse("Kd8", gameBoard);
        gameBoard = moveParser.parse("Kg1", gameBoard);
        gameBoard = moveParser.parse("Ke8", gameBoard);
        gameBoard = moveParser.parse("Kh1", gameBoard);
        gameBoard = moveParser.parse("Kd8", gameBoard);
        gameBoard = moveParser.parse("Kg2", gameBoard);
        gameBoard = moveParser.parse("Ke8", gameBoard);

        gameBoard = moveParser.parse("Kf2", gameBoard);
        gameBoard = moveParser.parse("Kd8", gameBoard);
        gameBoard = moveParser.parse("Ke2", gameBoard);
        gameBoard = moveParser.parse("Ke8", gameBoard);
        gameBoard = moveParser.parse("Kd2", gameBoard);
        gameBoard = moveParser.parse("Kd8", gameBoard);
        gameBoard = moveParser.parse("Kc2", gameBoard);
        gameBoard = moveParser.parse("Ke8", gameBoard);

        gameBoard = moveParser.parse("Kb2", gameBoard);
        gameBoard = moveParser.parse("Kd8", gameBoard);
        gameBoard = moveParser.parse("Ka2", gameBoard);
        gameBoard = moveParser.parse("Ke8", gameBoard);
        gameBoard = moveParser.parse("Kb3", gameBoard);
        gameBoard = moveParser.parse("Kd8", gameBoard);
        gameBoard = moveParser.parse("Kc3", gameBoard);
        gameBoard = moveParser.parse("Ke8", gameBoard);

        gameBoard = moveParser.parse("Kd3", gameBoard);
        gameBoard = moveParser.parse("Kd8", gameBoard);
        gameBoard = moveParser.parse("Ke3", gameBoard);
        gameBoard = moveParser.parse("Ke8", gameBoard);
        gameBoard = moveParser.parse("Kf3", gameBoard);
        gameBoard = moveParser.parse("Kd8", gameBoard);
        gameBoard = moveParser.parse("Kg3", gameBoard);
        gameBoard = moveParser.parse("Ke8", gameBoard);

        gameBoard = moveParser.parse("Kh3", gameBoard);
        gameBoard = moveParser.parse("Kd8", gameBoard);
        gameBoard = moveParser.parse("Kg4", gameBoard);
        gameBoard = moveParser.parse("Ke8", gameBoard);
        gameBoard = moveParser.parse("Kf4", gameBoard);
        gameBoard = moveParser.parse("Kd8", gameBoard);
        gameBoard = moveParser.parse("Ke4", gameBoard);
        gameBoard = moveParser.parse("Ke8", gameBoard);


        gameBoard = moveParser.parse("Kd4", gameBoard);
        Move move = moveParser.parse("Kd8", gameBoard).getMove();

        assertThat(move.isDraw50()).isTrue();
    }

    @Test
    void enPassant() {
        GameBoard gameBoard = GameBoard.startPosition();

        gameBoard = moveParser.parse("e4", gameBoard);
        gameBoard = moveParser.parse("e6", gameBoard);
        gameBoard = moveParser.parse("e5", gameBoard);

        var board = moveParser.parse("d5", gameBoard);
        assertThat(board.getPotentialEnPassantCapturePosition()).isEqualTo(new Position("d5"));

        EnPassant enPassant = (EnPassant) CollectionUtils.lastElement(moveParser.parse("e5d6", board).getMoves());
        assertThat(enPassant.toLongNotation()).isEqualTo("e5xd6");
    }

    @Test
    void pawnSwap() {
        GameBoard gameBoard = GameBoard.builder()
                .addPiece(new King(WHITE), "e1")
                .addPiece(new King(BLACK), "e8")
                .addPiece(new Pawn(WHITE), "g7")
                .addPiece(new Knight(BLACK), "h8")
                .build();

        Move move = CollectionUtils.lastElement(moveParser.parse("g7h8q", gameBoard).getMoves());
        assertThat(move.toLongNotation()).isEqualTo("g7xh8/D+");
    }

    @Test
    void fenWithMove() {
        String fen = "r3k2r/p1q2ppp/Qp2p3/1Nb5/4P1n1/PN2B3/1P3PPP/3R1RK1 b kq - 6 17";
        GameBoard gameBoard = fenParser.parseFen(fen);
        gameBoard = moveParser.parse("Sf6", gameBoard);
        Move move = gameBoard.getMove();

        assertThat(move.getIndex()).isEqualTo(35);
        assertThat(gameBoard.getDraw50Counter().getCount()).isEqualTo(7);
    }

    @Test
    void stockfishGame1() throws IOException {
        GameBoard gameBoard = moveParser.parse(TestUtil.moves("games/game1.txt").collect(Collectors.toList()));
        assertThat(gameBoard.getStatus()).isEqualTo(GameStatus.CHECKMATE);
    }

    @Test
    void stockfishGame2() throws IOException {
        GameBoard gameBoard = moveParser.parse(TestUtil.moves("games/game2.txt").collect(Collectors.toList()));
        assertThat(gameBoard.getStatus()).isEqualTo(GameStatus.CHECKMATE);
    }

    @Test
    void stockfishGame3() throws IOException {
        GameBoard gameBoard = moveParser.parse(TestUtil.moves("games/game3.txt").collect(Collectors.toList()));
        assertThat(gameBoard.getStatus()).isEqualTo(GameStatus.CHECKMATE);
    }

    @Test
    void stockfishGame4() throws IOException {
        GameBoard gameBoard = moveParser.parse(TestUtil.moves("games/game4.txt").collect(Collectors.toList()));
        assertThat(gameBoard.getStatus()).isEqualTo(GameStatus.CHECKMATE);

    }

    @Test
    void netschachGame13() throws IOException {
        moveParser.parse(TestUtil.moves("netschach_game_13.txt").collect(Collectors.toList()));

    }

    @Test
    void netschachGame46() throws IOException {
        // gxh1/D
        GameBoard gameBoard = moveParser.parse(TestUtil.moves("netschach_game_46.txt").collect(Collectors.toList()));
        assertThat(gameBoard.getStatus()).isEqualTo(GameStatus.CHECKMATE);

    }

    @Test
    void netschachGame48() throws IOException {
        moveParser.parse(TestUtil.moves("netschach_game_48.txt").collect(Collectors.toList()));

    }


    @Test
    void netschachGameEnPassant() throws IOException {
        moveParser.parse(TestUtil.moves("netschach_game_en_passant.txt").collect(Collectors.toList()));
    }


    @Test
    void netschachGames() throws IOException {
        List<String> lines = TestUtil.lines("netschach_games.txt").collect(Collectors.toList());
        for (int index = 0; index < lines.size(); index++) {
            System.out.println("********** Game-Index: " + index + "**********");
            testNetschachGame(lines.get(index), index);
        }
    }

    //@Test
    void netschachGamesNew() throws IOException {
        List<String> lines = TestUtil.lines("netschach_games_new.txt").collect(Collectors.toList());
        for (int index = 0; index < lines.size(); index++) {
            System.out.println("********** Game-Index: " + index + "**********");
            try {
                testNetschachGame(lines.get(index), index);
            } catch (Exception e) {

                System.err.println(e.getMessage());
            }
        }
    }

    private void testNetschachGame(String line, int index) {
        long t0 = System.currentTimeMillis();
        List<String> netschachMoves = Arrays.asList(line.split(",")).stream().filter(s -> !s.isBlank()).collect(Collectors.toList());
        List<? extends Move> resultMoves = moveParser.parse(netschachMoves).getMoves();
        long t1 = System.currentTimeMillis();
        System.out.println(resultMoves.size() + ": " + (t1 - t0) + "ms");
    }

}