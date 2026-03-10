package de.netschach.chess2;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChessUtils {

    static void assertPiece(GameBoard board, Class<? extends Piece> type, Color color, String position) {
        assertPiece(board, type, color, new Position(position));
    }

    static void assertPiece(GameBoard board, Class<? extends Piece> type, Color color, Position position) {
        assertPiece(board.getPieces(), type, color, position);
    }

    static void assertPiece(Map<Position, Piece> pieces, Class<? extends Piece> type, Color color, Position position) {
        Piece piece = pieces.get(position);
        if (piece == null)
            throw new IllegalStateException("no piece at " + position); // TODO Spezialisierte Exception
        if (!type.isInstance(piece))
            throw new IllegalStateException("piece is not instance of " + type); // TODO Spezialisierte Exception
        if (piece.getColor() != color)
            throw new IllegalStateException("piece is not of color " + color); // TODO Spezialisierte Exception
    }

    static boolean pieceAtPosition(GameBoard board, Class<? extends Piece> type, Color color, Position position) {
        Piece piece = board.getPiece(position);
        if (piece == null)
            return false;
        if (!type.isInstance(piece))
            return false;
        if (piece.getColor() != color)
            return false;
        return true;
    }

    static Position kingPosition(Map<Position, Piece> pieces, Color color) {
        return pieces.entrySet().stream()
                .filter(e -> e.getValue() != null)
                .filter(e -> King.class.isInstance(e.getValue()))
                .filter(e -> e.getValue().getColor() == color)
                .map(Map.Entry::getKey)
                .findFirst().orElseThrow(IllegalStateException::new);
    }
}
