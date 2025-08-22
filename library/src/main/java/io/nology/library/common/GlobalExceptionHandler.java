package io.nology.library.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.nology.library.common.exceptions.BadRequestException;
import io.nology.library.common.exceptions.NotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<String>(ex.getMessage(), ex.getStatusCode());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ValidationErrors> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(ex.getErrors(), ex.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrors> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ValidationErrors validationErrors = new ValidationErrors();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> validationErrors.add(err.getField(),
                        err.getDefaultMessage()));
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }
}
