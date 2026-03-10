package de.netschach.chess2;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

class Bishop extends Piece {
    Bishop(Color color) {
        super(color);
    }

    @Override
    Stream<Move> getMoves(Position position, GameBoard board) {
        Set<Move> moves = new HashSet<>();
        moves.addAll(MoveDirection.SOUTH_EAST.moves(position, this, board));
        moves.addAll(MoveDirection.SOUTH_WEST.moves(position, this, board));
        moves.addAll(MoveDirection.NORTH_EAST.moves(position, this, board));
        moves.addAll(MoveDirection.NORTH_WEST.moves(position, this, board));
        return moves.stream();
    }

    @Override
    public Character getCharForMoveDE() {
        return 'L';
    }

    @Override
    public char getPieceChar() {
        return getColor() == Color.WHITE ? 'B' : 'b';
    }
}
