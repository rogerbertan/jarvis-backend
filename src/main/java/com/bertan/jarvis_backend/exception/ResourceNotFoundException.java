package com.bertan.jarvis_backend.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends JarvisException {

    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String resourceType, Long id) {
        super(String.format("%s não encontrado(a) com ID: %d", resourceType, id), HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause, HttpStatus.NOT_FOUND);
    }
}