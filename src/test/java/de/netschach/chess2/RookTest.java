package de.netschach.chess2;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class RookTest {

    @Test
    void getMoves() {
        String[] expected = {"c1", "a2", "c3", "c4", "c5", "c6", "c7", "c8", "d2", "e2", "f2", "g2", "h2"};
        Set<Position> expectedPositions = Arrays.stream(expected).map(Position::new).collect(Collectors.toSet());
        assertThat(new Rook(Color.WHITE).getMoves(new Position("c2"), GameBoard.emptyBoard()).map(Move::getTo)).containsAll(expectedPositions);
    }

}