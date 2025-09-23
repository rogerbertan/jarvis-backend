package com.bertan.jarvis_backend.exception;

import org.springframework.http.HttpStatus;

public abstract class JarvisException extends RuntimeException {

    private final HttpStatus httpStatus;

    protected JarvisException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    protected JarvisException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}