package it.marczuk.resttest.controller;

import it.marczuk.resttest.exception.CategoryNotFoundException;
import it.marczuk.resttest.exception.ErrorObject;
import it.marczuk.resttest.exception.JokeNotFoundException;
import it.marczuk.resttest.exception.BadRequestToRestTemplateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionAdviceController {

    @ResponseBody
    @ExceptionHandler(JokeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorObject> jokeNotFoundHandler(JokeNotFoundException ex) {

        ErrorObject eObject = new ErrorObject();
        eObject.setTimestamp(LocalDateTime.now());
        eObject.setStatus(HttpStatus.NOT_FOUND.value());
        eObject.setMessage(ex.getMessage());

        return new ResponseEntity<>(eObject, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorObject> categoryNotFoundHandler(CategoryNotFoundException ex) {

        ErrorObject eObject = new ErrorObject();
        eObject.setTimestamp(LocalDateTime.now());
        eObject.setStatus(HttpStatus.NOT_FOUND.value());
        eObject.setMessage(ex.getMessage());

        return new ResponseEntity<>(eObject, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(BadRequestToRestTemplateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorObject> badRequestToRestTemplateHandler(BadRequestToRestTemplateException ex) {

        ErrorObject eObject = new ErrorObject();
        eObject.setTimestamp(LocalDateTime.now());
        eObject.setStatus(HttpStatus.BAD_REQUEST.value());
        eObject.setMessage(ex.getMessage());

        return new ResponseEntity<>(eObject, HttpStatus.BAD_REQUEST);
    }
}
