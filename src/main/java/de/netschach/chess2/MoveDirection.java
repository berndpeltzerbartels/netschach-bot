package de.netschach.chess2;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class MoveDirection {

    private final Direction direction;

    static final MoveDirection NORTH = new MoveDirection(Direction.NORTH);
    static final MoveDirection SOUTH = new MoveDirection(Direction.SOUTH);
    static final MoveDirection EAST = new MoveDirection(Direction.EAST);
    static final MoveDirection WEST = new MoveDirection(Direction.WEST);
    static final MoveDirection NORTH_WEST = new MoveDirection(Direction.NORTH_WEST);
    static final MoveDirection NORTH_EAST = new MoveDirection(Direction.NORTH_EAST);
    static final MoveDirection SOUTH_WEST = new MoveDirection(Direction.SOUTH_WEST);
    static final MoveDirection SOUTH_EAST = new MoveDirection(Direction.SOUTH_EAST);

    Collection<Move> moves(Position position, Piece piece, GameBoard board) {
        Set<Move> moves = new HashSet<>();
        Position p = position.position(direction);
        while (p != null) {
            Move move = new Move(position, p, piece);
            Piece another = board.getPiece(p);
            if (another != null) {
                if (piece.getColor() != another.getColor()) {
                    move.setCapturedPiece(another);
                    moves.add(move);
                }
                break;
            }
            moves.add(move);
            p = p.position(direction);
        }
        return moves;
    }

    Optional<Move> move(Position position, Piece piece, GameBoard board) {
        Position p = position.position(direction);
        if (p != null) {
            Move move = new Move(position, p, piece);
            Piece another = board.getPiece(p);
            if (another != null) {
                if (piece.getColor() != another.getColor()) {
                    move.setCapturedPiece(another);
                } else {
                    return Optional.empty();
                }
            }
            return Optional.of(move);
        }
        return Optional.empty();
    }
}
