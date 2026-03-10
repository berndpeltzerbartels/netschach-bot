package de.netschach.chess2;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Move {
    final Position from;
    final Position to;

    final Piece piece;

    GameStatus status = GameStatus.DEFAULT;
    Piece capturedPiece;
    private int index;

    private Move previous;

    Move(Position from, Position to, Piece piece, Piece capturedPiece) {
        this.from = from;
        this.to = to;
        this.piece = piece;
        this.capturedPiece = capturedPiece;
    }

    Move(String from, String to, Piece piece) {
        this(new Position(from), new Position(to), piece, null);
    }

    Character getFromX() {
        return from != null ? from.getX() : null;
    }

    Integer getFromY() {
        return from != null ? from.getY() : null;
    }

    Character getToX() {
        return to != null ? to.getX() : null;
    }

    Integer getToY() {
        return to != null ? to.getY() : null;
    }

    void doMove(GameBoard board) {
        @NonNull Piece piece = board.getPieces().remove(from);
        if (capturedPiece != null) {
            @NonNull Piece captureCompare = board.getPieces().remove(to);
            if (!capturedPiece.equals(captureCompare)) {
                throw new IllegalStateException();
            }
        }
        board.getPieces().put(to, piece);
        if (Pawn.class.isInstance(piece)) {
            if (piece.getColor() == Color.WHITE && from.getY() == 2 && to.getY() == 4) {
                board.setPotentialEnPassantCapturePosition(to);
            } else if (piece.getColor() == Color.BLACK && from.getY() == 7 && to.getY() == 5) {
                board.setPotentialEnPassantCapturePosition(to);
            }
        }
    }

    public String toStockfishMove() {
        return from.toString() + to.toString();
    }

    public String toLongNotation() {
        StringBuilder s = new StringBuilder();
        if (!Pawn.class.isInstance(piece))
            s.append(piece.getCharForMoveDE());
        s.append(from);
        if (capturedPiece == null) {
            s.append("-");
        } else {
            s.append("x");
        }
        s.append(to);
        if (status == GameStatus.CHECKMATE) {
            s.append("++");
        } else if (status == GameStatus.CHECK) {
            s.append("+");
        }
        return s.toString();
    }

    public boolean isGameOver() {
        return status == GameStatus.CHECKMATE || status == GameStatus.STALEMATE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (!from.equals(move.from)) return false;
        return to.equals(move.to);
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }

    public boolean isCheckmate() {
        return status == GameStatus.CHECKMATE;
    }

    public boolean isCheck() {
        return status == GameStatus.CHECK;
    }

    public boolean isStalemate() {
        return status == GameStatus.STALEMATE;
    }

    public boolean isDraw3() {
        return status == GameStatus.DRAW3;
    }

    public boolean isDraw50() {
        return status == GameStatus.DRAW50;
    }
}
