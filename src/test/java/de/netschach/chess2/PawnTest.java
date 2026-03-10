package de.netschach.chess2;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class PawnTest {

    @Test
    void getMoves() {
        String[] expected = {"c3", "c4"};
        Set<Position> expectedPositions = Arrays.stream(expected).map(Position::new).collect(Collectors.toSet());
        assertThat(new Pawn(Color.WHITE).getMoves(new Position("c2"), GameBoard.startPosition()).map(Move::getTo)).containsAll(expectedPositions);
    }
}