package it.marczuk.resttest.exception;

import java.util.NoSuchElementException;

public class BadRequestToRestTemplateException extends NoSuchElementException {

    public BadRequestToRestTemplateException(String message) {
        super(message);
    }
}
