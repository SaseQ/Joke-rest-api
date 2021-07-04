package it.marczuk.resttest.controller;

import it.marczuk.resttest.exception.ExceptionModel;
import it.marczuk.resttest.exception.JokeNotFoundExeption;
import org.springframework.http.HttpStatus;
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
    public ExceptionModel JokeNotFoundHandler(JokeNotFoundExeption ex) {
        return new ExceptionModel(LocalDateTime.now(), 404, ex.getMessage());
    }
}
