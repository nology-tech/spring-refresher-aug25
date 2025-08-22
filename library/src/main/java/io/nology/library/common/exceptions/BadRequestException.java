package io.nology.library.common.exceptions;

import org.springframework.http.HttpStatus;

import io.nology.library.common.ValidationErrors;

public class BadRequestException extends HttpException {
    private ValidationErrors errors;

    public BadRequestException(String message, HttpStatus statusCode, ValidationErrors errors) {
        super(message, statusCode);
        this.errors = errors;
    }

    public BadRequestException(String message, ValidationErrors errors) {
        this(message, HttpStatus.BAD_REQUEST, errors);
    }

    public ValidationErrors getErrors() {
        return errors;
    }

}
