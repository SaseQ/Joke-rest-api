package it.marczuk.resttest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionModel {

    private LocalDateTime timestamp;
    private Integer status;
    private String message;
}
