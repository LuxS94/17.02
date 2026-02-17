package org.example._7_02.exceptions;

import org.example._7_02.dto.ErrorsDTO;
import org.example._7_02.dto.ErrorsListDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorsHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public ErrorsDTO handleNotFound(NotFoundException ex) {
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(ValidationExceptions.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsListDTO handleValidationExceptions(ValidationExceptions ex) {
        return new ErrorsListDTO(ex.getMessage(), LocalDateTime.now(), ex.getErrorsMessages());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsDTO handleGenericServerError(Exception ex) {
        ex.printStackTrace();
        return new ErrorsDTO("Errore!", LocalDateTime.now());

    }

}
