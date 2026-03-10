package de.netschach.stockfish;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StockfishEngineITCase {

    private static final String FEN = "b1rb4/3pp3/3rk3/p1pp4/3p3N/1B1N4/4KR1B/8 w - -";

    private static final String FEN_STARTPOS = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private static final String STOCKFISH_EXECUTABLE = "/usr/local/bin/stockfish";
    private StockfishEngine stockfishEngine;

    @BeforeAll
    void init() throws IOException {
        stockfishEngine = new StockfishEngine();
        stockfishEngine.open();
    }


    @AfterAll
    void close() {
        stockfishEngine.close();
    }

    @Test
    public void newGame() throws Exception {
        stockfishEngine.newGame();
        assertEquals(FEN_STARTPOS, stockfishEngine.currentGameToFen());
    }

    @Test
    public void setGame() throws Exception {
        stockfishEngine.setGameByMoves(Arrays.asList("e2e4", "e7e5", "d2d4"));
        assertEquals("rnbqkbnr/pppp1ppp/8/4p3/3PP3/8/PPP2PPP/RNBQKBNR b KQkq - 0 2", stockfishEngine.currentGameToFen());
    }

    @Test
    public void bestMoveWithTimelimit() throws IOException, InterruptedException {
        stockfishEngine.setGameByFen(FEN);
        assertEquals("e2f1", stockfishEngine.bestMoveWithTimelimit(1500).getBestMove());
    }

    @Test
    public void bestMoveWithLevel() throws Exception {
        stockfishEngine.setGameByFen(FEN);
        assertEquals("e2f1", stockfishEngine.bestMoveWithLevel(new Level(20)).getBestMove());
    }


    @Test
    @Disabled
    public void bestMoveWithDepth() throws Exception {
        stockfishEngine.setGameByFen(FEN);
        assertEquals("e2f1", stockfishEngine.bestMoveWithDepth(6).getBestMove());
    }

    @Test
    void checkers() throws Exception {
        String checkers = stockfishEngine.checkers("4k3/2Np1ppp/8/1B4B1/8/8/4R3/4K3 b - -");
        assertTrue(checkers.contains("e2"));
        assertTrue(checkers.contains("c7"));
    }
}
