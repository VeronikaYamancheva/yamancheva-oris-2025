package ru.itis.vhsroni.exceptions;

public class EmptyEmailValidationServiceException extends ValidationServiceException{

    public EmptyEmailValidationServiceException() {
        super("Email не предоставлен");
    }
}
