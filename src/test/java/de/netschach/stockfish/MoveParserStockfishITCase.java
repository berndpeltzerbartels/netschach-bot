package de.netschach.stockfish;

import de.netschach.chess2.GameBoard;
import de.netschach.chess2.Move;
import de.netschach.chess2.MoveParser;
import de.netschach.chess2.TestUtil;
import de.netschach.fen.FenParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@Disabled
@SpringBootTest
public class MoveParserStockfishITCase {

    private static final String STOCKFISH_EXECUTABLE = "/usr/local/bin/stockfish";
    private StockfishEngine stockfishEngine1;
    private StockfishEngine stockfishEngine2;
    private GameBoard board;

    @Autowired
    private MoveParser moveParser;

    @Autowired
    private FenParser fenParser;

    @BeforeEach
    void init() throws IOException {
        stockfishEngine1 = new StockfishEngine();
        stockfishEngine1.open();
        stockfishEngine2 = new StockfishEngine();
        stockfishEngine2.open();
        this.board = GameBoard.startPosition();
    }

    @AfterEach
    void close() {
        stockfishEngine1.close();
        stockfishEngine2.close();
    }

    @Test
    void runGame() throws IOException, InterruptedException {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (!this.engine1WhiteMoves(moves))
                return;
            if (!this.engine2BlackFen(moves))
                return;
        }

    }

    boolean engine1WhiteMoves(List<Move> moves) throws IOException {
        //stockfishEngine1.setLevel(StockfishEngine.MAX_LEVEL);
        stockfishEngine1.setGameByMoves(moves.stream().map(Move::toStockfishMove).collect(Collectors.toList()));
        StockfishBestMoveResult result = stockfishEngine1.bestMoveWithLevel(StockfishEngine.MAX_LEVEL);
        if (result.getBestMove().equals("(none)")) {
            assertThat(Objects.requireNonNull(CollectionUtils.lastElement(moves)).isGameOver()).isTrue();
            System.out.println("over");
            return false;
        }
        board = moveParser.parse(result.getBestMove(), board);
        moves.add(board.getMove());
        log.info("Best move (white): {}", result.getBestMove());
        TestUtil.dump(board);
        return true;
    }

    boolean engine2BlackFen(List<Move> moves) throws IOException, InterruptedException {
        //stockfishEngine1.setLevel(StockfishEngine.MAX_LEVEL);
        stockfishEngine2.setGameByFen(fenParser.parseGameBoard(board));
        stockfishEngine2.setGameByMoves(moves.stream().map(Move::toStockfishMove).collect(Collectors.toList()));
        StockfishBestMoveResult result = stockfishEngine2.bestMoveWithLevel(StockfishEngine.MAX_LEVEL);
        if (result.getBestMove().equals("(none)")) {
            assertThat(Objects.requireNonNull(CollectionUtils.lastElement(moves)).isGameOver()).isTrue();
            System.out.println("over");
            return false;
        }
        board = moveParser.parse(result.getBestMove(), board);
        moves.add(board.getMove());
        log.info("Best move (black): {}", result.getBestMove());
       // TestUtil.dump(board);
        return true;
    }


}
