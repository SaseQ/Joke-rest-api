package it.marczuk.resttest.exception;

public class TokenMissingException extends RuntimeException {

    public TokenMissingException(String message) {
        super(message);
    }
}
