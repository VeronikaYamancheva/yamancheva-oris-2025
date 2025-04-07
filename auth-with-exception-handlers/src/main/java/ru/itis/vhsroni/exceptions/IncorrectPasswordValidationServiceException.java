package ru.itis.vhsroni.exceptions;

public class IncorrectPasswordValidationServiceException extends ValidationServiceException{

    public IncorrectPasswordValidationServiceException() {
        super("Некорректный пароль");
    }
}
