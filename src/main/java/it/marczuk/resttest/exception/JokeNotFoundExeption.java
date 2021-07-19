package it.marczuk.resttest.exception;

public class JokeNotFoundExeption extends RuntimeException {

    public JokeNotFoundExeption(String message) {
        super(message);
    }
}
