package de.netschach.chess2;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

class Queen extends Piece {
    Queen(Color color) {
        super(color);
    }

    @Override
    Stream<Move> getMoves(Position position, GameBoard board) {
        Set<Move> moves = new HashSet<>();
        moves.addAll(MoveDirection.NORTH.moves(position, this, board));
        moves.addAll(MoveDirection.SOUTH.moves(position, this, board));
        moves.addAll(MoveDirection.EAST.moves(position, this, board));
        moves.addAll(MoveDirection.WEST.moves(position, this, board));
        moves.addAll(MoveDirection.SOUTH_EAST.moves(position, this, board));
        moves.addAll(MoveDirection.SOUTH_WEST.moves(position, this, board));
        moves.addAll(MoveDirection.NORTH_EAST.moves(position, this, board));
        moves.addAll(MoveDirection.NORTH_WEST.moves(position, this, board));
        return moves.stream();
    }

    @Override
    public Character getCharForMoveDE() {
        return 'D';
    }

    @Override
    public char getPieceChar() {
        return getColor() == Color.WHITE ? 'Q' : 'q';
    }
}
