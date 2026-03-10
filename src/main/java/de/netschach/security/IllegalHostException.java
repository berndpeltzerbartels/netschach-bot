package de.netschach.security;

public class IllegalHostException extends RuntimeException {

    public IllegalHostException(String host) {
        super(host + " is not allowed to send request");
    }
}
