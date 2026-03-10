package de.netschach.chess2;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Position implements Comparable<Position> {

    static final Position G1 = new Position("g1");
    static final Position A1 = new Position("a1");
    static final Position H1 = new Position("h1");
    static final Position A8 = new Position("a8");
    static final Position H8 = new Position("h8");
    static final Position E1 = new Position("e1");
    static final Position E8 = new Position("e8");
    static final Position C1 = new Position("c1");
    static final Position G8 = new Position("g8");
    static final Position C8 = new Position("c8");

    private char x;
    private int y;

    public Position(char c, int y) {
        this.x = c;
        this.y = y;
        if (this.x < 'a' || this.x > 'h') throw new IllegalStateException();
        if (this.y < 1 || this.y > 8) throw new IllegalStateException();
    }

    public Position(String s) {
        this(Character.toLowerCase(s.charAt(0)), s.charAt(1) - 48);
    }

    Position position(Direction direction) {
        if (direction == Direction.NORTH) {
            return north();
        }
        if (direction == Direction.SOUTH) {
            return south();
        }
        if (direction == Direction.EAST) {
            return east();
        }
        if (direction == Direction.WEST) {
            return west();
        }
        if (direction == Direction.NORTH_EAST) {
            return northEast();
        }
        if (direction == Direction.NORTH_WEST) {
            return northWest();
        }
        if (direction == Direction.SOUTH_EAST) {
            return southEast();
        }
        if (direction == Direction.SOUTH_WEST) {
            return southWest();
        }
        throw new IllegalArgumentException();
    }

    Position north() {
        return y < 8 ? new Position(x, y + 1) : null;
    }

    Position south() {
        return y > 1 ? new Position(x, y - 1) : null;
    }

    Position east() {
        return x < 'h' ? new Position((char) (x + 1), y) : null;
    }

    Position west() {
        return x > 'a' ? new Position((char) (x - 1), y) : null;
    }

    Position northWest() {
        return y < 8 && x > 'a' ? new Position((char) (x - 1), y + 1) : null;
    }

    Position northEast() {
        return y < 8 && x < 'h' ? new Position((char) (x + 1), y + 1) : null;
    }

    Position southWest() {
        return y > 1 && x > 'a' ? new Position((char) (x - 1), y - 1) : null;
    }

    Position southEast() {
        return y > 1 && x < 'h' ? new Position((char) (x + 1), y - 1) : null;
    }

    @Override
    public String toString() {
        return Character.toString(x) + y;
    }

    @Override
    public int compareTo(Position o) {
        int result = Character.valueOf(x).compareTo(o.getX());
        if (result != 0)
            return result;
        return Integer.valueOf(y).compareTo(o.getY());
    }
}
