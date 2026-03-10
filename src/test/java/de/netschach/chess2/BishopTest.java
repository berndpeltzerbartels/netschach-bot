package de.netschach.chess2;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class BishopTest {

    @Test
    void getMoves() {
        String[] expected = {"b1", "b3", "a4", "d1", "d3", "e4", "f5", "g6", "h7"};
        Set<Position> expectedPositions = Arrays.stream(expected).map(Position::new).collect(Collectors.toSet());
        assertThat(new Bishop(Color.WHITE).getMoves(new Position("c2"), GameBoard.emptyBoard()).map(Move::getTo)).containsAll(expectedPositions);
    }

}