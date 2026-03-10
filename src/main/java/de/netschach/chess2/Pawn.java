package de.netschach.chess2;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class Pawn extends Piece {
    Pawn(Color color) {
        super(color);
    }

    @Override
    Stream<Move> getMoves(Position position, GameBoard board) {
        Set<Move> moves = new HashSet<>();
        moves.addAll(pawnPromotions(position, board));
        enPassant(position, board).ifPresent(moves::add);
        twoFields(position, board).ifPresent(moves::add);
        oneFields(position, board).ifPresent(moves::add);
        moves.addAll(simpleCaptures(position, board));
        return moves.stream();
    }

    private Optional<EnPassant> enPassant(Position position, GameBoard board) {
        if (board.getPotentialEnPassantCapturePosition() != null) {
            if (getColor() == Color.WHITE) {
                if (position.getY() == 5 && Math.abs(board.getPotentialEnPassantCapturePosition().getX() - position.getX()) == 1) {
                    Position to = new Position(board.getPotentialEnPassantCapturePosition().getX(), 6);
                    return Optional.of(new EnPassant(position, to, this, board));
                }
            } else if (position.getY() == 4 && Math.abs(board.getPotentialEnPassantCapturePosition().getX() - position.getX()) == 1) {
                Position to = new Position(board.getPotentialEnPassantCapturePosition().getX(), 3);
                return Optional.of(new EnPassant(position, to, this, board));
            }
        }
        return Optional.empty();
    }

    private Collection<PawnPromotion> pawnPromotions(Position position, GameBoard board) {
        Set<PawnPromotion> moves = new HashSet<>();
        if (getColor() == Color.WHITE) {
            if (position.getY() == 7) {
                Position to = position.north();
                if (board.getPiece(to) == null) {
                    moves.addAll(pawnPromotions(position, to, board));
                }
                if (position.getX() > 'a') {
                    to = position.northWest();
                    if (board.getPiece(to) != null && board.getPiece(to).getColor() == Color.BLACK) {
                        moves.addAll(pawnPromotions(position, to, board));
                    }
                }
                if (position.getX() < 'h') {
                    to = position.northEast();
                    if (board.getPiece(to) != null && board.getPiece(to).getColor() == Color.BLACK) {
                        moves.addAll(pawnPromotions(position, to, board));
                    }
                }
            }
        } else if (position.getY() == 2) {
            Position to = position.south();
            if (board.getPiece(to) == null) {
                moves.addAll(pawnPromotions(position, to, board));
            }
            if (position.getX() > 'a') {
                to = position.southWest();
                if (board.getPiece(to) != null && board.getPiece(to).getColor() == Color.WHITE) {
                    moves.addAll(pawnPromotions(position, to, board));
                }
            }
            if (position.getX() < 'h') {
                to = position.southEast();
                if (board.getPiece(to) != null && board.getPiece(to).getColor() == Color.WHITE) {
                    moves.addAll(pawnPromotions(position, to, board));
                }
            }
        }
        return moves;
    }

    private Collection<PawnPromotion> pawnPromotions(Position from, Position to, GameBoard board) {
        return Set.of(new PawnPromotion(from, to, this, Queen.class, board),
                new PawnPromotion(from, to, this, Rook.class, board),
                new PawnPromotion(from, to, this, Bishop.class, board),
                new PawnPromotion(from, to, this, Knight.class, board));
    }

    private Optional<Move> twoFields(Position position, GameBoard board) {
        if (getColor() == Color.WHITE) {
            if (position.getY() == 2 && board.getPiece(position.north()) == null && board.getPiece(position.north().north()) == null) {
                return Optional.of(new Move(position, position.north().north(), this));
            }
        } else if (position.getY() == 7 && board.getPiece(position.south()) == null && board.getPiece(position.south().south()) == null) {
            return Optional.of(new Move(position, position.south().south(), this));
        }
        return Optional.empty();
    }

    private Optional<Move> oneFields(Position position, GameBoard board) {
        if (getColor() == Color.WHITE) {
            if (position.getY() < 7 && board.getPiece(position.north()) == null) {
                return Optional.of(new Move(position, position.north(), this));
            }
        } else if (position.getY() > 2 && board.getPiece(position.south()) == null) {
            return Optional.of(new Move(position, position.south(), this));
        }
        return Optional.empty();
    }

    private Collection<Move> simpleCaptures(Position position, GameBoard board) {
        Set<Move> moves = new HashSet<>();
        if (getColor() == Color.WHITE && position.getY() < 7) {
            Position to = position.northWest();
            if (to != null) {
                Piece anotherPiece = board.getPiece(to);
                if (anotherPiece != null && anotherPiece.getColor() != getColor()) {
                    moves.add(new Move(position, to, this, anotherPiece));
                }
            }
            to = position.northEast();
            if (to != null) {
                Piece anotherPiece = board.getPiece(to);
                if (anotherPiece != null && anotherPiece.getColor() != getColor()) {
                    moves.add(new Move(position, to, this, anotherPiece));
                }
            }

        } else if (getColor() == Color.BLACK && position.getY() > 2) {
            Position to = position.southWest();
            if (to != null) {
                Piece anotherPiece = board.getPiece(to);

                if (anotherPiece != null && anotherPiece.getColor() != getColor()) {
                    moves.add(new Move(position, to, this, anotherPiece));
                }
            }
            to = position.southEast();
            if (to != null) {
                Piece anotherPiece = board.getPiece(to);
                if (anotherPiece != null && anotherPiece.getColor() != getColor()) {
                    moves.add(new Move(position, to, this, anotherPiece));
                }
            }
        }
        return moves;
    }

    @Override
    public Character getCharForMoveDE() {
        return null;
    }

    @Override
    public char getPieceChar() {
        return getColor() == Color.WHITE ? 'P' : 'p';
    }
}
