package it.marczuk.resttest.controller;

import it.marczuk.resttest.exception.ErrorObject;
import it.marczuk.resttest.exception.JokeNotFoundExeption;
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
    @ExceptionHandler(JokeNotFoundExeption.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorObject> jokeNotFoundHandler(JokeNotFoundExeption ex) {

        ErrorObject eObject = new ErrorObject();
        eObject.setTimestamp(LocalDateTime.now());
        eObject.setStatus(HttpStatus.NOT_FOUND.value());
        eObject.setMessage(ex.getMessage());

        return new ResponseEntity<>(eObject, HttpStatus.NOT_FOUND);
    }
}
