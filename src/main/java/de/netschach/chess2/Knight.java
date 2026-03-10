package de.netschach.chess2;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

class Knight extends Piece {
    Knight(Color color) {
        super(color);
    }

    @Override
    Stream<Move> getMoves(Position position, GameBoard board) {
        Set<Move> moves = new HashSet<>();
        if (position.north() != null) {
            toPosition(position, position.north().northWest(), board).ifPresent(moves::add);
            toPosition(position, position.north().northEast(), board).ifPresent(moves::add);
        }
        if (position.south() != null) {
            toPosition(position, position.south().southWest(), board).ifPresent(moves::add);
            toPosition(position, position.south().southEast(), board).ifPresent(moves::add);
        }
        if (position.west() != null) {
            toPosition(position, position.west().northWest(), board).ifPresent(moves::add);
            toPosition(position, position.west().southWest(), board).ifPresent(moves::add);
        }
        if (position.east() != null) {
            toPosition(position, position.east().northEast(), board).ifPresent(moves::add);
            toPosition(position, position.east().southEast(), board).ifPresent(moves::add);
        }
        return moves.stream();
    }

    private Optional<Move> toPosition(Position from, Position position, GameBoard board) {
        if (position == null) return Optional.empty();
        if (board.getPiece(position) == null) {
            return Optional.of(new Move(from, position, this));
        } else if (board.getPiece(position).getColor() != getColor()) {
            return Optional.of(new Move(from, position, this, board.getPiece(position)));
        }
        return Optional.empty();
    }

    @Override
    public Character getCharForMoveDE() {
        return 'S';
    }

    @Override
    public char getPieceChar() {
        return getColor() == Color.WHITE ? 'N' : 'n';
    }


}
