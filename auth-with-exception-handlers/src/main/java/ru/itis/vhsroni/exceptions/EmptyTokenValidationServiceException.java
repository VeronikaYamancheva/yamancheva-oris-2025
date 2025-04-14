package ru.itis.vhsroni.exceptions;

public class EmptyTokenValidationServiceException extends ValidationServiceException{

    public EmptyTokenValidationServiceException() {
        super("Токен не предоставлен");
    }
}
