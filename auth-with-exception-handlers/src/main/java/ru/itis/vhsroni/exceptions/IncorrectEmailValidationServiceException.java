package ru.itis.vhsroni.exceptions;

public class IncorrectEmailValidationServiceException extends ValidationServiceException{

    public IncorrectEmailValidationServiceException() {
        super("Некорректный email");
    }
}
