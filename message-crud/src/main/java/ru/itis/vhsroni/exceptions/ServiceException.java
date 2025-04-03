package ru.itis.vhsroni.exceptions;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException{

    private final HttpStatus status;

    public ServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
