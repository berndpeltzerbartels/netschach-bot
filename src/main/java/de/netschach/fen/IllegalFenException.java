package de.netschach.fen;

public class IllegalFenException extends RuntimeException {
    public IllegalFenException(String message, String expression) {
        super(message + " in expression : " + expression);
    }
}
