package de.netschach.chess2;

import org.springframework.stereotype.Component;

@Component
class StatusUpdater {

    void updateStatus(Move move, GameBoard board) {
        updateIsCheck(move, board);
        if (!updateIsCheckMate(move, board)) {
            updateIsStaleMate(move, board);
        }
        if (!move.isGameOver()) {
            if (!updateIsDraw3(move, board)) {
                updateIsDraw50(move, board);
            }
        }
    }

    private void updateIsCheck(Move move, GameBoard board) {
        if (board.isCheckAgainst(move.getPiece().getColor().opposite())) {
            move.setStatus(GameStatus.CHECK);
            board.setStatus(GameStatus.CHECK);
        }
    }

    private boolean updateIsCheckMate(Move move, GameBoard board) {
        if ((move.isCheck() && board.allLegalMoves().isEmpty())) {
            move.setStatus(GameStatus.CHECKMATE);
            board.setStatus(GameStatus.CHECKMATE);
            return true;
        }
        return false;
    }

    private void updateIsStaleMate(Move move, GameBoard board) {
        if (!move.isCheck() && board.allLegalMoves().isEmpty()) {
            move.setStatus(GameStatus.STALEMATE);
            board.setStatus(GameStatus.STALEMATE);
        }
    }

    private boolean updateIsDraw3(Move move, GameBoard board) {
        if (board.getDraw3Counter().isDraw()) {
            move.setStatus(GameStatus.DRAW3);
            board.setStatus(GameStatus.DRAW3);
            return true;
        }
        return false;
    }

    private void updateIsDraw50(Move move, GameBoard board) {
        if ((board.getDraw50Counter().isDraw())) {
            move.setStatus(GameStatus.DRAW50);
            board.setStatus(GameStatus.DRAW50);
        }
    }
}
