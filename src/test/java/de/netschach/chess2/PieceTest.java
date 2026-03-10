package de.netschach.chess2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PieceTest {

    @Test
    void createKing() {
        Piece piece = Piece.createInstance(King.class, Color.WHITE);

        assertThat(piece).isInstanceOf(King.class);
        assertThat(piece.getColor()).isEqualTo(Color.WHITE);
    }

    @Test
    void createQueen() {
        Piece piece = Piece.createInstance(Queen.class, Color.WHITE);

        assertThat(piece).isInstanceOf(Queen.class);
        assertThat(piece.getColor()).isEqualTo(Color.WHITE);
    }

    @Test
    void createPawn() {
        Piece piece = Piece.createInstance(Pawn.class, Color.BLACK);

        assertThat(piece).isInstanceOf(Pawn.class);
        assertThat(piece.getColor()).isEqualTo(Color.BLACK);
    }

    @Test
    void createBishop() {
        Piece piece = Piece.createInstance(Bishop.class, Color.WHITE);

        assertThat(piece).isInstanceOf(Bishop.class);
        assertThat(piece.getColor()).isEqualTo(Color.WHITE);
    }

    @Test
    void createKnight() {
        Piece piece = Piece.createInstance(Knight.class, Color.WHITE);

        assertThat(piece).isInstanceOf(Knight.class);
        assertThat(piece.getColor()).isEqualTo(Color.WHITE);
    }

    @Test
    void createRook() {
        Piece piece = Piece.createInstance(Rook.class, Color.WHITE);

        assertThat(piece).isInstanceOf(Rook.class);
        assertThat(piece.getColor()).isEqualTo(Color.WHITE);
    }
}