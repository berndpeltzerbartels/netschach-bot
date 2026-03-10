package de.netschach.chess2;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class GameBoardBuilder {

    private final Map<Position, Piece> pieces = new HashMap<>();

    private boolean shortCastlingWhite;
    private boolean shortCastlingBlack;

    private boolean longCastlingWhite;
    private boolean longCastlingBlack;
    private Color colorOnMove = Color.WHITE;
    private Position potentialEnPassantCapturePosition;
    private Integer moveCountDraw50;
    private Integer moveIndex;

    public GameBoardBuilder addPiece(Piece piece, Position position) {
        pieces.put(position, piece);
        return this;
    }

    public GameBoardBuilder addPieces(Map<Position, Piece> p) {
        pieces.putAll(p);
        return this;
    }

    public GameBoardBuilder addPiece(Piece piece, String position) {
        return addPiece(piece, new Position(position));
    }

    public GameBoardBuilder setShortCastlingWhite(boolean b) {
        this.shortCastlingWhite = b;
        if (this.shortCastlingWhite) {
            ChessUtils.assertPiece(pieces, King.class, Color.WHITE, Position.E1);
            ChessUtils.assertPiece(pieces, Rook.class, Color.WHITE, Position.H1);
        }
        return this;
    }

    public GameBoardBuilder setLongCastlingWhite(boolean b) {
        this.longCastlingWhite = b;
        if (this.longCastlingWhite) {
            ChessUtils.assertPiece(pieces, King.class, Color.WHITE, Position.E1);
            ChessUtils.assertPiece(pieces, Rook.class, Color.WHITE, Position.A1);
        }
        return this;
    }

    public GameBoardBuilder setShortCastlingBlack(boolean b) {
        this.shortCastlingBlack = b;
        if (this.shortCastlingBlack) {
            ChessUtils.assertPiece(pieces, King.class, Color.BLACK, Position.E8);
            ChessUtils.assertPiece(pieces, Rook.class, Color.BLACK, Position.H8);
        }
        return this;
    }

    public GameBoardBuilder setLongCastlingBlack(boolean b) {
        this.longCastlingBlack = b;
        if (this.longCastlingBlack) {
            ChessUtils.assertPiece(pieces, King.class, Color.BLACK, Position.E8);
            ChessUtils.assertPiece(pieces, Rook.class, Color.BLACK, Position.A8);
        }
        return this;
    }

    public GameBoardBuilder setPotentialEnPassantCapturePosition(Position position) {
        potentialEnPassantCapturePosition = position;
        return this;
    }

    public GameBoardBuilder setColorOnMove(Color color) {
        this.colorOnMove = color;
        return this;
    }

    public GameBoardBuilder moveCountDraw50(Integer moveCountDraw50) {
        this.moveCountDraw50 = moveCountDraw50;
        return this;
    }

    public GameBoard build() {
        if (potentialEnPassantCapturePosition != null) {
            ChessUtils.assertPiece(pieces, Pawn.class, colorOnMove.opposite(), potentialEnPassantCapturePosition);
        }
        return new GameBoard(pieces, colorOnMove, shortCastlingWhite, longCastlingWhite, shortCastlingBlack, longCastlingBlack, potentialEnPassantCapturePosition, moveCountDraw50, moveIndex);
    }


    public GameBoardBuilder moveIndex(Integer moveIndex) {
        this.moveIndex = moveIndex;
        return this;
    }
}


