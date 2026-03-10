package de.netschach.stockfish;

import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedList;

@Getter
class StockfishBestMoveResult {

    private String bestMove;
    private String ponder;
    private int depth;
    private int seldepth;
    private String score;
    private int multipv;

    boolean addLine(String line) {
        LinkedList<String> splittedLine = new LinkedList<>(Arrays.asList(line.split( " ")));
        String first = splittedLine.removeFirst();
        if (first.equals("Stockfish")) {
            return true;
        } else if (first.equals("info")) {
            this.info(splittedLine);
            return true;
        } else if (first.equals("bestmove")){
            this.bestMove = splittedLine.removeFirst();
            if (this.bestMove.equals("(none"))
                return false;
            if (splittedLine.isEmpty())
                return false;
            splittedLine.removeFirst();
            if (!splittedLine.isEmpty())
            this.ponder = splittedLine.removeFirst();
            return false;
        } else {
            return true;
        }

    }

    private void info(LinkedList<String> line) {
        String depth = this.removeAttribute("depth", 1, line);
        if (depth == null)
            return;
        this.depth = Integer.parseInt(depth);
        String seldepth = this.removeAttribute("seldepth", 1, line);
        if (seldepth == null)
            return;
        this.seldepth = Integer.parseInt(seldepth);
        String multipv = this.removeAttribute("multipv", 1, line);
        if (multipv == null)
            return;
        this.multipv = Integer.parseInt(multipv);
        this.score = this.removeAttribute("score", 2, line);

    }

    private String removeAttribute(String attrName, int tokens, LinkedList<String> words) {
        if (words.isEmpty())
            return null;
        String key = words.removeFirst();
        if (key.equals(attrName) && !words.isEmpty()) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < tokens && !words.isEmpty(); i++) {
                s.append(words.removeFirst());
                s.append(" ");
            }
            return s.toString().trim();
        }
        return null;
    }

}
