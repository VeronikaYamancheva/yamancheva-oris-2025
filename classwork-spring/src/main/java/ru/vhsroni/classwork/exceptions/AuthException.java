package ru.vhsroni.classwork.exceptions;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException{
    private final int errorCode;

    public AuthException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
