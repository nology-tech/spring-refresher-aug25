package io.nology.library.common.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpException {

    public NotFoundException(String message, HttpStatus statusCode) {
        super(message, statusCode);
    }

    public NotFoundException(String message) {
        this(message, HttpStatus.NOT_FOUND);
    }

}
