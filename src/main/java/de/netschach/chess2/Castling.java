package de.netschach.chess2;

abstract class Castling extends Move {
    Castling(Position from, Position to, King piece) {
        super(from, to, piece);
    }

}
