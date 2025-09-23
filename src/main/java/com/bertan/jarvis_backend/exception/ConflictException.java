package com.bertan.jarvis_backend.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends JarvisException {

    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause, HttpStatus.CONFLICT);
    }
}