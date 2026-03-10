package de.netschach.chess2;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class KnightTest {

    @Test
    void getMoves() {
        String[] expected = {"d6", "f6", "d2", "f2", "c5", "c3", "g5", "g3"};
        Set<Position> expectedPositions = Arrays.stream(expected).map(Position::new).collect(Collectors.toSet());
        assertThat(new Knight(Color.WHITE).getMoves(new Position("e4"), GameBoard.emptyBoard()).map(Move::getTo)).containsAll(expectedPositions);
    }

}