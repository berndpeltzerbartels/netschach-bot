package de.netschach.chess2;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class KingTest {

    @Test
    void getMoves() {
        String[] expected = {"b1", "b2", "b3", "c3", "d3", "d2", "d1", "c1"};
        Set<Position> expectedPositions = Arrays.stream(expected).map(Position::new).collect(Collectors.toSet());
        assertThat(new King(Color.WHITE).getMoves(new Position("c2"), GameBoard.emptyBoard()).map(Move::getTo)).containsAll(expectedPositions);
    }
}