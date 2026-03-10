package de.netschach.chess2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoveDirectionTest {

    @Test
    void noOnesIfPieceIsBlocked() {
        assertThat(MoveDirection.NORTH.moves(new Position("a1"), new Rook(Color.WHITE), GameBoard.startPosition())).isEmpty();
        assertThat(MoveDirection.SOUTH.moves(new Position("a8"), new Rook(Color.BLACK), GameBoard.startPosition())).isEmpty();
        assertThat(MoveDirection.EAST.moves(new Position("d1"), new Rook(Color.WHITE), GameBoard.startPosition())).isEmpty();
        assertThat(MoveDirection.WEST.moves(new Position("d1"), new Rook(Color.WHITE), GameBoard.startPosition())).isEmpty();
        assertThat(MoveDirection.NORTH_WEST.moves(new Position("d1"), new Rook(Color.WHITE), GameBoard.startPosition())).isEmpty();
        assertThat(MoveDirection.NORTH_EAST.moves(new Position("d1"), new Rook(Color.WHITE), GameBoard.startPosition())).isEmpty();
        assertThat(MoveDirection.NORTH_WEST.moves(new Position("d8"), new Rook(Color.BLACK), GameBoard.startPosition())).isEmpty();
        assertThat(MoveDirection.SOUTH_EAST.moves(new Position("d8"), new Rook(Color.BLACK), GameBoard.startPosition())).isEmpty();
    }

    @Test
    void north() {
        assertThat(MoveDirection.NORTH.moves(new Position("c2"), new Rook(Color.WHITE), GameBoard.emptyBoard())).hasSize(6);
    }

    @Test
    void south() {
        assertThat(MoveDirection.SOUTH.moves(new Position("c2"), new Rook(Color.WHITE), GameBoard.emptyBoard())).hasSize(1);
    }

    @Test
    void east() {
        assertThat(MoveDirection.EAST.moves(new Position("c2"), new Rook(Color.WHITE), GameBoard.emptyBoard())).hasSize(5);
    }

    @Test
    void west() {
        assertThat(MoveDirection.WEST.moves(new Position("c2"), new Rook(Color.WHITE), GameBoard.emptyBoard())).hasSize(2);
    }

    @Test
    void northWest() {
        assertThat(MoveDirection.NORTH_WEST.moves(new Position("c2"), new Bishop(Color.WHITE), GameBoard.emptyBoard())).hasSize(2);
    }


    @Test
    void northEast() {
        assertThat(MoveDirection.NORTH_EAST.moves(new Position("c2"), new Bishop(Color.WHITE), GameBoard.emptyBoard())).hasSize(5);
    }

    @Test
    void southWest() {
        assertThat(MoveDirection.SOUTH_WEST.moves(new Position("c2"), new Bishop(Color.WHITE), GameBoard.emptyBoard())).hasSize(1);
    }

    @Test
    void southEast() {
        assertThat(MoveDirection.SOUTH_EAST.moves(new Position("c2"), new Bishop(Color.WHITE), GameBoard.emptyBoard())).hasSize(1);
    }

}