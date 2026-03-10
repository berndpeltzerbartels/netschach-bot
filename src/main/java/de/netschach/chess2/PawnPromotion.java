package de.netschach.chess2;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
class PawnPromotion extends Move {

    @Getter
    private final Class<? extends Piece> swapPieceType;

    public PawnPromotion(Position from, Position to, Pawn piece, @NonNull Class<? extends Piece> swapPieceType, GameBoard board) {
        super(from, to, piece);
        this.swapPieceType = swapPieceType;
        setCapturedPiece(board.getPiece(to));
    }


    void doMove(GameBoard board) {
        super.doMove(board);
        board.getPieces().put(getTo(), Piece.createInstance(swapPieceType, getPiece().getColor()));
    }

    @Override
    public String toStockfishMove() {
        return super.toStockfishMove() + Piece.stockFishPieceChar(swapPieceType);
    }

    @Override
    public String toLongNotation() {
        StringBuilder s = new StringBuilder();
        s.append(getFrom());
        if (getCapturedPiece() == null) {
            s.append("-");
        } else {
            s.append("x");
        }
        s.append(getTo());
        s.append("/");
        s.append(Piece.createInstance(swapPieceType, getPiece().getColor()).getCharForMoveDE());
        if (isCheckmate()) {
            s.append("++");
        } else if (isCheck()) {
            s.append("+");
        }
        return s.toString();
    }
}

