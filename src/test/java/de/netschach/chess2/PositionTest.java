package de.netschach.chess2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PositionTest {

    private Position position = new Position("e4");

    @Test
    void position() {
        assertThat(position.position(Direction.NORTH)).isEqualTo(position.north());
        assertThat(position.position(Direction.SOUTH)).isEqualTo(position.south());
        assertThat(position.position(Direction.EAST)).isEqualTo(position.east());
        assertThat(position.position(Direction.WEST)).isEqualTo(position.west());
        assertThat(position.position(Direction.NORTH_WEST)).isEqualTo(position.northWest());
        assertThat(position.position(Direction.NORTH_EAST)).isEqualTo(position.northEast());
        assertThat(position.position(Direction.SOUTH_WEST)).isEqualTo(position.southWest());
        assertThat(position.position(Direction.SOUTH_EAST)).isEqualTo(position.southEast());
    }

    @Test
    void north() {
        assertThat(new Position("e7").north()).isEqualTo(new Position("e8"));
        assertThat(new Position("e8").north()).isNull();
    }

    @Test
    void south() {
        assertThat(new Position("e2").south()).isEqualTo(new Position("e1"));
        assertThat(new Position("e1").south()).isNull();
    }

    @Test
    void east() {
        assertThat(new Position("g4").east()).isEqualTo(new Position("h4"));
        assertThat(new Position("h4").east()).isNull();
    }

    @Test
    void west() {
        assertThat(new Position("b4").west()).isEqualTo(new Position("a4"));
        assertThat(new Position("a4").west()).isNull();
    }

    @Test
    void northWest() {
        assertThat(new Position("b7").northWest()).isEqualTo(new Position("a8"));
        assertThat(new Position("e8").northWest()).isNull();
        assertThat(new Position("a3").northWest()).isNull();
    }

    @Test
    void northEast() {
        assertThat(new Position("g7").northEast()).isEqualTo(new Position("h8"));
        assertThat(new Position("e8").northEast()).isNull();
        assertThat(new Position("h3").northEast()).isNull();
    }

    @Test
    void southWest() {
        assertThat(new Position("b2").southWest()).isEqualTo(new Position("a1"));
        assertThat(new Position("e1").southWest()).isNull();
        assertThat(new Position("a3").southWest()).isNull();
    }

    @Test
    void southEast() {
        assertThat(new Position("g2").southEast()).isEqualTo(new Position("h1"));
        assertThat(new Position("e1").southEast()).isNull();
        assertThat(new Position("h3").southEast()).isNull();
    }
}