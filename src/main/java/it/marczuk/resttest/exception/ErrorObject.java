package it.marczuk.resttest.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorObject {

    private LocalDateTime timestamp;
    private Integer status;
    private String message;
}
