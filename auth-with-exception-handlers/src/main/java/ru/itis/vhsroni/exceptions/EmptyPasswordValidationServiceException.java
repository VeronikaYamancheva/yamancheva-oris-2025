package ru.itis.vhsroni.exceptions;

public class EmptyPasswordValidationServiceException extends ValidationServiceException{

    public EmptyPasswordValidationServiceException() {
        super("Пароль не предоставлен");
    }
}
