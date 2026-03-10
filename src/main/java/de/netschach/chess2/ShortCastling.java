package de.netschach.chess2;

import lombok.NonNull;

class ShortCastling extends Castling {

    ShortCastling(King piece) {
        super(kingPosition(piece.getColor()), kingTargetPosition(piece.getColor()), piece);
    }


    void doMove(GameBoard board) {
        super.doMove(board);
        @NonNull Position rookPosition = rookPosition(getPiece().getColor());
        @NonNull Rook rook = (Rook) board.getPieces().remove(rookPosition);
        board.getPieces().put(new Position('f', rookPosition.getY()), rook);
    }

    private static Position kingPosition(Color color) {
        return color == Color.WHITE ? new Position('e', 1) : new Position('e', 8);
    }


    private static Position kingTargetPosition(Color color) {
        return color == Color.WHITE ? new Position('g', 1) : new Position('g', 8);
    }


    private static Position rookPosition(Color color) {
        return color == Color.WHITE ? new Position('h', 1) : new Position('h', 8);
    }

    @Override
    public String toLongNotation() {
        StringBuilder s = new StringBuilder("0-0");
        if (isCheckmate()) {
            s.append("++");
        } else if (isCheck()) {
            s.append("+");
        }
        return s.toString();
    }
}
