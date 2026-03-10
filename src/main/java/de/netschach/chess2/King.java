package de.netschach.chess2;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class King extends Piece {

    King(Color color) {
        super(color);
    }

    @Override
    Stream<Move> getMoves(Position position, GameBoard board) {
        Set<Move> moves = new HashSet<>();
        MoveDirection.NORTH.move(position, this, board).ifPresent(moves::add);
        MoveDirection.SOUTH.move(position, this, board).ifPresent(moves::add);
        MoveDirection.EAST.move(position, this, board).ifPresent(moves::add);
        MoveDirection.WEST.move(position, this, board).ifPresent(moves::add);
        MoveDirection.NORTH_WEST.move(position, this, board).ifPresent(moves::add);
        MoveDirection.NORTH_EAST.move(position, this, board).ifPresent(moves::add);
        MoveDirection.SOUTH_WEST.move(position, this, board).ifPresent(moves::add);
        MoveDirection.SOUTH_EAST.move(position, this, board).ifPresent(moves::add);
        moves.addAll(castlings(position, board));
        return moves.stream();
    }

    private Collection<Castling> castlings(Position position, GameBoard board) {
        Set<Castling> moves = new HashSet<>();
        if (board.getMove() != null && board.getMove().isCheck()) {
            return moves;
        }
        int y = position.getY();
        if (board.longCastlingAllowed(getColor())) {
            if (board.getPiece(new Position('b', y)) == null
                    && board.getPiece(new Position('c', y)) == null
                    && board.getPiece(new Position('d', y)) == null) {
                Piece piece = board.getPiece(new Position('a', y));
                if (piece instanceof Rook && piece.getColor() == getColor())
                    moves.add(new LongCastling(this));
            }
        }
        if (board.shortCastlingAllowed(getColor())) {
            if (board.getPiece(new Position('f', y)) == null && board.getPiece(new Position('g', y)) == null) {
                Piece piece = board.getPiece(new Position('h', y));
                if (piece instanceof Rook && piece.getColor() == getColor())
                    moves.add(new ShortCastling(this));
            }
        }
        return moves;
    }

    @Override
    public Character getCharForMoveDE() {
        return 'K';
    }

    @Override
    public char getPieceChar() {
        return getColor() == Color.WHITE ? 'K' : 'k';
    }
}
