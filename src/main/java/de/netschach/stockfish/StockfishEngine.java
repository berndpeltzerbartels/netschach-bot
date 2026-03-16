package de.netschach.stockfish;

import de.netschach.StreamUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
class StockfishEngine {

    static final Level MAX_LEVEL = new Level(20);
    private static final String KEY_CHECKERS = "Checkers:";
    private static final String KEY_BEST_MOVE = "bestmove";
    private static final String KEY_FEN = "Fen:";
    private static final String KEY_PONDER = "ponder";
    private final Object lock = new Object();
    private Process engineProcess;
    private BufferedReader processErrorReader;
    private final String stockfishPath;
    private BufferedReader processReader;
    private BufferedWriter processWriter;

    StockfishEngine() {
        stockfishPath = getStockfishPath();
        log.info("created engine with path: {}", stockfishPath);
    }

    private static String getStockfishPath() {
        String pathVariable = System.getenv("PATH");
        log.info("path-variable: {}, separator", pathVariable, File.pathSeparator);
        return Arrays.stream(pathVariable.split(File.pathSeparator))
                .map(path -> new File(path, "stockfish"))
                .filter(File::canExecute)
                .map(File::getAbsolutePath)
                .collect(StreamUtil.findOne(NoSuchElementException::new));

    }

    private static String extractFirstValue(String key, String output) {
        if (output == null)
            return null;
        Pattern pattern = Pattern.compile(key + "[ \t]*([^ ^t]*)");
        Matcher matcher = pattern.matcher(output);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static String extractValues(String key, String output) {
        if (output == null)
            return null;
        Pattern pattern = Pattern.compile(key + "[ \t]*(.*)");
        Matcher matcher = pattern.matcher(output);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }

    synchronized void open() throws IOException {
        this.engineProcess = Runtime.getRuntime().exec(stockfishPath);
        this.processReader = new BufferedReader(new InputStreamReader(this.engineProcess.getInputStream()));
        this.processWriter = new BufferedWriter(new OutputStreamWriter(this.engineProcess.getOutputStream()));
        this.waitForReady();
    }

    synchronized boolean isAlive() {
        return this.engineProcess != null && this.engineProcess.isAlive();
    }

    synchronized void setGameByMoves(List<String> moves) throws IOException {
        StringBuilder command = new StringBuilder("position startpos");
        if (!moves.isEmpty()) {
            command.append(" moves ").append(moves.stream().collect(Collectors.joining(" ")));
        }
        this.sendCommand(command.toString());
    }

    synchronized void setGameByFen(String fen) throws IOException {
        this.sendCommand("position fen " + fen);
    }

    synchronized void newGame() throws IOException {
        String command = "position startpos";
        this.sendCommand(command);
    }

    synchronized String currentGameToFen() throws IOException {
        this.sendCommand("d");
        String output = this.readOutput(KEY_FEN);
        return extractValues(KEY_FEN, output);
    }

    synchronized void setLevel(Level level) throws IOException {
        this.setOption("UCI_LimitStrength", false);
        this.setOption("Skill Level", level.value());
    }

    synchronized void setElo(Elo elo) throws IOException {
        this.setOption("UCI_LimitStrength", true);
        this.setOption("UCI_Elo", elo.value());
    }

    synchronized void setMinimumThinkingTime(int timeMillis) throws IOException {
        this.setOption("Minimum Thinking Time", timeMillis);
    }

    synchronized StockfishBestMoveResult bestMoveWithTimelimit(int timeLimit) throws IOException {
        this.sendCommand("go movetime " + timeLimit);
        return this.readBestMoveResult();
    }

    synchronized StockfishBestMoveResult bestMoveWithDepth(int depth) throws IOException {
        this.sendCommand("go depth " + depth);
        return this.readBestMoveResult();
    }

    synchronized StockfishBestMoveResult bestMoveInfinite(int depth) throws IOException {
        this.sendCommand("go infinite ");
        return this.readBestMoveResult();
    }

    synchronized StockfishBestMoveResult bestMoveWithLevel(Level level) throws IOException {
        this.setLevel(level);
        this.sendCommand("go");
        return this.readBestMoveResult();
    }

    synchronized String checkers(String fen) throws IOException {
        this.setGameByFen(fen);
        this.sendCommand("d");
        String output = this.readOutput(KEY_CHECKERS);

        return extractValues(KEY_CHECKERS, output);
    }

    synchronized void setCores(int cores) throws IOException {
        this.setOption("Threads", cores);
    }

    synchronized void sendReady() throws IOException {
        this.sendCommand("isready");
        this.waitForReady();
    }

    synchronized void setMultiPV(int value) throws IOException {
        this.setOption("MultiPV", value);
    }

    synchronized void close() {
        try {
            this.sendCommand("quit");
            this.engineProcess.destroy();
            this.processWriter.close();
            this.processReader.close();
        } catch (IOException e) {
        }
    }

    synchronized void sendCommand(String command) throws IOException {
        this.processWriter.write(command + "\n\r");
        this.processWriter.flush();
    }

    synchronized void setOption(String name, Object value) throws IOException {
        this.sendCommand("setoption name " + name + " value " + value);
    }

    synchronized void waitForReady() throws IOException {
        this.sendCommand("isready");
        String line;
        while ((line = this.processReader.readLine()) != null) {
            if (line.trim().equals("readyok")) {
                return;
            }
        }
    }

    private String readOutput(String key) throws IOException {
        String line;
        while ((line = this.processReader.readLine()) != null) {
            if (line.startsWith(key)) {
                return line;
            }
        }
        return null;
    }

    private StockfishBestMoveResult readBestMoveResult() throws IOException {
        StockfishBestMoveResult result = new StockfishBestMoveResult();
        String line;
        while ((line = this.processReader.readLine()) != null) {
            if (!result.addLine(line)) {
                break;
            }
        }
        return result;
    }


}

