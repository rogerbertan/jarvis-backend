package com.bertan.jarvis_backend.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends JarvisException {

    public ValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause, HttpStatus.BAD_REQUEST);
    }
}