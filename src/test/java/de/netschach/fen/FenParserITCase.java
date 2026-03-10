package de.netschach.fen;

import de.netschach.chess2.EnPassant;
import de.netschach.chess2.GameBoard;
import de.netschach.chess2.MoveParser;
import de.netschach.chess2.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FenParserITCase {

    private static final Pattern EN_PASSANT_PATTERN = Pattern.compile(".*([a-h][1-8]) \\d+ \\d+$");

    @Autowired
    private MoveParser moveParser;

    @Autowired
    private FenParser fenParser;

    @Test
    void stockfishGame1() throws IOException {
        fenTest("games/game1.txt");
    }

    @Test
    void stockfishGame2() throws IOException {
        fenTest("games/game2.txt");
    }

    @Test
    void stockfishGame3() throws IOException {
        fenTest("games/game3.txt");
    }

    @Test
    void stockfishGame4() throws IOException {
        fenTest("games/game4.txt");
    }

    @Test
    void netschachGame13() throws IOException {
        fenTest("netschach_game_13.txt");
    }


    @Test
    void netschachGame48() throws IOException {
        fenTest("netschach_game_48.txt");
    }


    @Test
    void netschachGame46() throws IOException {
        fenTest("netschach_game_46.txt");
    }

    @Test
    void netschachGames() throws IOException {
        List<String> lines = TestUtil.lines("netschach_games.txt").collect(Collectors.toList());
        for (int index = 0; index < lines.size(); index++) {
            System.out.println("********** Game-Index: " + index + "**********");
            fenTest(Arrays.asList(lines.get(index).split(",")).stream().filter(s -> !s.isBlank()).collect(Collectors.toList()));
        }
    }

    void fenTest(String file) throws IOException {
        fenTest(TestUtil.lines(file).collect(Collectors.toList()));
    }

    void fenTest(List<String> moves) {
        String fen2 = null;
        GameBoard gameBoard = GameBoard.startPosition();
        for (String move : moves) {
            gameBoard = moveParser.parse(move, gameBoard);
            String fen = fenParser.parseGameBoard(gameBoard);
            //System.out.println(move + " -> " + gameBoard.getMove().toLongNotation() + ": " + fen);

            if (gameBoard.getMove() instanceof EnPassant) {
                assertThat(EN_PASSANT_PATTERN.matcher(fen2).find()).isTrue();
            }

            GameBoard gameBoard2 = fenParser.parseFen(fen);
            fen2 = fenParser.parseGameBoard(gameBoard2);
            assertThat(fen2).isEqualTo(fen);

            if (gameBoard.getMove() instanceof EnPassant) {
                System.out.println(move + " -> " + gameBoard.getMove().toLongNotation() + ": " + fen);
            }

        }

    }
}