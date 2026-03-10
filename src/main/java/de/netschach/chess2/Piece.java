package de.netschach.chess2;

import lombok.Data;

import java.lang.reflect.Constructor;
import java.util.stream.Stream;

@Data
public abstract class Piece {
    private final Color color;


    abstract Stream<Move> getMoves(Position position, GameBoard board);

    public static Class<? extends Piece> pieceFor(char c) {
        switch (Character.toUpperCase(c)) {
            case 'K':
                return King.class;
            case 'D':
            case 'Q':
                return Queen.class;
            case 'T':
            case 'R':
                return Rook.class;
            case 'L':
            case 'B':
                return Bishop.class;
            case 'S':
            case 'N':
                return Knight.class;
            case 'P':
                return Pawn.class;
            default:
                throw new IllegalArgumentException("piece char");
        }
    }

    static char stockFishPieceChar(Class<? extends Piece> type) {
        if (King.class.equals(type)) return 'k';
        if (Queen.class.equals(type)) return 'q';
        if (Rook.class.equals(type)) return 'r';
        if (Knight.class.equals(type)) return 'n';
        if (Bishop.class.equals(type)) return 'b';
        throw new IllegalArgumentException("type=" + type);
    }

    public static Piece createInstance(Class<? extends Piece> pieceType, Color color) {
        try {
            Constructor<Piece> constructor = (Constructor<Piece>) pieceType.getDeclaredConstructor(Color.class);
            constructor.setAccessible(true);
            return constructor.newInstance(color);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + getColor() + ")";

    }

    public abstract Character getCharForMoveDE();

    public abstract char getPieceChar();
}
