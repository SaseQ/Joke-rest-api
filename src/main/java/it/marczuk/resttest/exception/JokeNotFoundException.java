package it.marczuk.resttest.exception;

public class JokeNotFoundException extends RuntimeException {

    public JokeNotFoundException(String message) {
        super(message);
    }
}
