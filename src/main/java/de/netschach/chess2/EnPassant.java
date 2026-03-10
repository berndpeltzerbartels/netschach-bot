package de.netschach.chess2;

import lombok.NonNull;

public class EnPassant extends Move {

    private Position capturePosition;

    EnPassant(Position from, Position to, Pawn pawn, GameBoard board) {
        super(from, to, pawn);
        capturePosition = capturePosition();
        setCapturedPiece(board.getPiece(capturePosition));
    }

    void doMove(GameBoard board) {
        @NonNull Piece piece = board.getPieces().remove(getFrom());
        @NonNull Piece captureCompare = board.getPieces().remove(capturePosition);
        if (!getCapturedPiece().equals(captureCompare)) {
            throw new IllegalStateException();
        }
        board.getPieces().put(getTo(), piece);
    }

    private Position capturePosition() {
        if (getPiece().getColor() == Color.WHITE) {
            return new Position(getTo().getX(), 5);
        } else {
            return new Position(getTo().getX(), 4);
        }
    }
}
