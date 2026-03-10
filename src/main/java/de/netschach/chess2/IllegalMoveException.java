package de.netschach.chess2;

public class IllegalMoveException extends RuntimeException {

    public IllegalMoveException(String move, String message) {
        super(String.format("%s : %s", move, message));
    }
}
