package it.marczuk.resttest.exception;

public class JokeNotFoundExeption extends RuntimeException {

    public JokeNotFoundExeption(String id) {
        super("Could not find joke by id: " + id);
    }
}
