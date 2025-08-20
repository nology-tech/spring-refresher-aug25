package io.nology.library.common.exceptions;

import org.springframework.http.HttpStatus;

public abstract class HttpException extends Exception {
    private final HttpStatus statusCode;

    public HttpException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

}
